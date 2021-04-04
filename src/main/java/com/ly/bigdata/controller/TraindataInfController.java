package com.ly.bigdata.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ly.bigdata.po.TraindataInf;
import com.ly.bigdata.po.UserInf;
import com.ly.bigdata.service.TraindataInfService;
import com.ly.bigdata.service.UserInfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 陈太康
 * @since 2021-03-29
 */
@Controller
@RequestMapping("/traindata")
public class TraindataInfController {

    @Autowired
    private TraindataInfService traindataInfService;
    @Autowired
    private UserInfService userInfService;

    @RequestMapping("/list")
    public String list(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                       @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                       Model model,
                       @RequestParam(value = "content", required = false) String content) {


        Page<TraindataInf> page = new Page<>(pageNo, pageSize);
        if (content != null && !content.equals("")) {
            QueryWrapper<TraindataInf> wrapper = new QueryWrapper<>();
            wrapper.like("title", "%" + content + "%")
                    .or().like("remark", "%" + content + "%");
            traindataInfService.page(page, wrapper);
        } else {
            traindataInfService.page(page, null);
        }
        List<TraindataInf> list = page.getRecords();
        list.forEach(item -> {
            UserInf userInf = userInfService.getById(item.getUserId());
            String username = userInf.getUsername();
            item.setUserName(username);
        });

        model.addAttribute("list", page.getRecords());
        model.addAttribute("pageNo", page.getCurrent());//当前页
        model.addAttribute("pageSize", page.getSize());////每页记录数
        model.addAttribute("count", page.getTotal());//总记录数

        return "traindata/list";
    }


    @RequestMapping("/play")
    public String play(Model model, String filename) {
        model.addAttribute("filename", filename);
        System.err.println("~~~~" + filename);
        return "traindata/play";
    }

    @RequestMapping("/toadd")
    public String toadd() {
        //System.err.println("~~~~~");
        return "traindata/add";
    }

    @RequestMapping("/add")

    public String add(TraindataInf traindataInf, MultipartFile file, Model model, HttpServletRequest request) throws Exception {
        // 找上传文件的位置

        // String realPath = request.getServletContext().getRealPath("/WEB-INF/files");
        String realPath = System.getProperty("user.dir") + "/src/main/resources/static/upload";
        System.err.println("~~~realPath~~~" + realPath);
        // 上传文件名称
        String originalFilename = file.getOriginalFilename();
        System.err.println("~~~originalFilename~~~" + originalFilename);

        // 上传文件，复制文件
        File file1 = new File(realPath, originalFilename);
        //把file1这个文件路径所指向的文件上传到对应的目录下。
        file.transferTo(file1);

        traindataInf.setFilename(originalFilename);
        traindataInf.setCreatedate(new Date());
        UserInf userInf = (UserInf) request.getSession().getAttribute("user_session");
        traindataInf.setUserId(userInf.getId());
        // 数据入库
        traindataInfService.save(traindataInf);
        return "redirect:/traindata/list";
    }

    @RequestMapping("/delete")
    public String delUser(Integer id, String filename) {
        boolean result = traindataInfService.removeById(id);
        return "redirect:/traindata/list";
    }


    @RequestMapping("/download")
    public ResponseEntity<byte[]> upload(String filename) throws IOException {
        String realPath = System.getProperty("user.dir") + "/src/main/resources/static/upload";
        System.err.println("filename~~~~~~~~~"+filename);
        System.err.println("realPath~~~~~~~~~~~~~"+realPath);
        File file = new File(realPath, filename);

        InputStream is = new FileInputStream(file);
        byte[] buf = new byte[is.available()];
        is.read(buf);

        HttpStatus status = HttpStatus.OK;
        HttpHeaders headers = new HttpHeaders();                      //URLEncoder给文件名用utf-8的形式加密
        headers.setContentDispositionFormData("attachment", URLEncoder.encode(filename, "utf-8"));
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<byte[]>(buf, headers, status);
        is.close();
        return responseEntity;
    }


}

