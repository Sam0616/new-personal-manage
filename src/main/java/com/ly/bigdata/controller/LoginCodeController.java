package com.ly.bigdata.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ly.bigdata.po.UserInf;
import com.ly.bigdata.po.UservisitInf;
import com.ly.bigdata.service.UserInfService;
import com.ly.bigdata.service.UservisitInfService;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class LoginCodeController {


    @Autowired
    private UserInfService userInfService;
    @Autowired
    private UservisitInfService uservisitInfService;
    @Autowired
    private StringRedisTemplate redisTemplate;


    @RequestMapping("/login")
    public String login(String loginname, String password, String user_input_verifyCode,
                        HttpSession session, Model model, HttpServletRequest request,
                        HttpServletResponse response) throws UnsupportedEncodingException {

        String code = (String) session.getAttribute("code");
//        if (code.equalsIgnoreCase(user_input_verifyCode)) {
        if (true) {
            //验证码正确
            QueryWrapper<UserInf> wrapper = new QueryWrapper<>();
            //MD5加密
            String pwd = DigestUtils.md5DigestAsHex(password.getBytes());
            wrapper.eq("loginname", loginname).eq("password", pwd);
            UserInf userInf = userInfService.getOne(wrapper);
            if (userInf != null) {
                //判断该用户是否已被审核过
                if (userInf.getStatusId() == 1) {
                    //把当前用户放入session
                    session.setAttribute("user_session", userInf);
                    session.setAttribute("number", 3);
                    getClientInfo(request, loginname, response);
                    return "redirect:/toindex";
                } else {
                    //判断该用户没被审核过
                    model.addAttribute("message", "您还未通过审核，暂时不能登录！");
                    return "loginForm";
                }
            } else {
                System.err.println("===用户名密码不正确===========================");
                model.addAttribute("message", "用户名密码不正确");
                return "loginForm";
            }
        } else {
            System.err.println("===验证码不正确===========================");
            model.addAttribute("message", "验证码不正确");
            return "loginForm";

        }
    }


    public void getClientInfo(HttpServletRequest request, String loginname, HttpServletResponse response) throws UnsupportedEncodingException {
        String agent = request.getHeader("user-Agent");
        System.err.println(agent);

        //User Agent中文名为用户代理，简称 UA，它是一个特殊字符串头，使得服务器
        //能够识别客户使用的操作系统及版本、CPU 类型、浏览器及版本、
        //浏览器渲染引擎、浏览器语言、浏览器插件等
        UserAgent userAgent = UserAgent.parseUserAgentString(agent);
        //获取浏览器
        Browser browser = userAgent.getBrowser();
        System.err.println(browser.getName());
        //获取浏览器版本
        Version bv = userAgent.getBrowserVersion();
        //System.out.println(bv.getVersion());
        // 获取操作系统
        OperatingSystem os = userAgent.getOperatingSystem();
        //System.out.println(os.getName());

        String iphone = "";
        if (agent.contains("Windows NT")) {
            //pc型号获取方法实现
            String pc_regex = " \\((.*); ";
            Pattern pattern = Pattern.compile(pc_regex);
            Matcher matcher = pattern.matcher(agent);
            while (matcher.find()) {
                iphone = matcher.group(1);
            }
            agent = "PC端";
        } else {
            String type = "";
            if (agent.contains("iPhone") || agent.contains("iPod") || agent.contains("iPad")) {
                type = "ios";
            } else if (agent.contains("Android") || agent.contains("Linux")) {
                type = "apk";
            } else if (agent.indexOf("micromessenger") > 0) {
                type = "wx";
            }
            String iphone_regex = "\\((.*)\\) Apple";
            Pattern pattern = Pattern.compile(iphone_regex);
            Matcher matcher = pattern.matcher(agent);

            while (matcher.find()) {
                iphone = matcher.group(1);
            }
            agent = "移动端" + type;
        }

        System.out.println(iphone);
        System.out.println(agent);

        System.out.println("---------------------获取ip--------------------------");
        String ipAddress = null;
        if (request.getHeader("x-forwarded-for") == null) {
            ipAddress = request.getRemoteAddr();
        } else {
            if (request.getHeader("x-forwarded-for").length() > 15) {
                String[] aStr = request.getHeader("x-forwarded-for").split(",");
                ipAddress = aStr[0];
            } else {
                ipAddress = request.getHeader("x-forwarded-for");
            }
        }
        System.out.println(ipAddress);

        // 封装UservisitInf
        UservisitInf uservisitInf = new UservisitInf();
        uservisitInf.setLoginname(loginname);
        uservisitInf.setLoginTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        uservisitInf.setVisitIp(ipAddress);
        uservisitInf.setUserAddress("");
        uservisitInf.setUserFrom(agent);
        uservisitInf.setBrowser(browser.getName());
        uservisitInf.setOpsystem(os.getName());
        uservisitInf.setVersion(bv.getVersion());
        uservisitInf.setIphone(iphone);

        // 写入数据库
        uservisitInfService.save(uservisitInf);

        String s = null;
        try {
            s = new ObjectMapper().writeValueAsString(uservisitInf);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        // 写入redis
        redisTemplate.opsForList().leftPush("users", s);
        //为实现登出时间放入用户访问对象
        request.getSession().setAttribute("USER_VISIT", uservisitInf);

 /*       //编码
        String encode = URLEncoder.encode(s, "utf-8");
        //解码
        //URLDecoder.decode(cookies[i].getName(),"utf-8")
        Cookie cookie = new Cookie("USERV_ISIT_cookie", encode);
        response.addCookie(cookie);// addCookie后，如果已经存在相同名字的cookie，则最新的覆盖旧的cookie*/
    }






    @RequestMapping("/checkCode")
    @ResponseBody
    public void checkCode(HttpServletResponse response, HttpSession session) throws IOException {
        //生成验证码 awt/ swing 组件来生成的
        //创建返回的字节数组
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ServletOutputStream out = response.getOutputStream();
        //定义验证码的图片
        drawingCode(output, session);

        output.writeTo(out);

    }

    public void drawingCode(ByteArrayOutputStream output, HttpSession session) throws IOException {
        //随机生成五个字符 包含 字母和 数字
        String code = "";
        for (int i = 0; i < 5; i++) {
            code += randomChar();
        }

        int w = 100;//图片的宽度
        int h = 30;//图片的高度
        //定义好了画板
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        //得到画布 在画布上面 真正画东西
        Graphics g = image.getGraphics();
        g.setColor(Color.GREEN);//设定颜色
        g.fillRect(0, 0, w, h);//设定画布

        // 设定图片边框
        g.setColor(Color.GRAY);
        g.drawRect(0, 0, w - 1, h - 1);

        g.setColor(randomColor());//字体颜色
        g.setFont(randomFont());//字体样式
        g.drawString(code, 10, 22);
        g.dispose();

        //要把验证码放到 session范围中
        session.setAttribute("code", code);
        System.err.println("================" + code);
        ImageIO.write(image, "jpg", output);

    }

    // 生成随机的字体
    private Font randomFont() {
        Random r = new Random();
        String[] fontNames = {"宋体", "华文楷体", "黑体", "微软雅黑", "楷体_GB2312"};
        int index = r.nextInt(fontNames.length);
        String fontName = fontNames[index];// 生成随机的字体名称
        int style = r.nextInt(4);
        int size = r.nextInt(3) + 24; // 生成随机字号, 24 ~ 28
        return new Font(fontName, style, size);
    }

    // 生成随机的颜色
    private Color randomColor() {
        Random r = new Random();
        int red = r.nextInt(150);
        int green = r.nextInt(150);
        int blue = r.nextInt(150);
        return new Color(red, green, blue);
    }

    public char randomChar() {
        Random r = new Random(); //0和O比较像  1 和 l  没有 0 1 数字 没有 O l字母
        String s = "23456789abcdefghjkmnopqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ";
        int randomNum = r.nextInt(s.length());//0-s.length之间
        char c = s.charAt(randomNum);//c 就在 s 中的 随机一个字符
        return c;
    }
}
