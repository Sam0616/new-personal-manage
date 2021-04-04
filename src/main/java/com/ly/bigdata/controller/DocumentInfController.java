package com.ly.bigdata.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ly.bigdata.po.DeptInf;
import com.ly.bigdata.po.DocumentInf;
import com.ly.bigdata.po.NoticeInf;
import com.ly.bigdata.po.UserInf;
import com.ly.bigdata.service.DocumentInfService;
import com.ly.bigdata.service.UserInfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 陈太康
 * @since 2021-03-26
 */
@Controller
@RequestMapping("/document")
public class DocumentInfController {

    @Autowired
    private DocumentInfService documentInfService;
    @Autowired
    private UserInfService userInfService;


    @RequestMapping("/list")
    public String list(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                       @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                       Model model,
                       @RequestParam(value = "content", required = false) String content) {

        Page<DocumentInf> page = new Page<>(pageNo, pageSize);
        if (content != null && !content.equals("")) {
            QueryWrapper<DocumentInf> wrapper = new QueryWrapper<>();
            wrapper.like("title", "%" + content + "%")
                    .or().like("remark", "%" + content + "%");
            documentInfService.page(page, wrapper);
        } else {
            documentInfService.page(page, null);
        }


        List<DocumentInf> list2 = new ArrayList<>();
        List<DocumentInf> list = page.getRecords();
        list.forEach(item -> {
            Integer id = item.getUserId();
            UserInf userInf = userInfService.getById(id);
            String username = userInf.getUsername();

            item.setUsername(username);
            list2.add(item);

        });

        model.addAttribute("list", list2);
        model.addAttribute("pageNo", page.getCurrent());//当前页
        model.addAttribute("pageSize", page.getSize());////每页记录数
        model.addAttribute("count", page.getTotal());//总记录数

        return "document/list";
    }

    @RequestMapping("/toadd")
    public String toadd() {
        return "document/add";
    }

    @RequestMapping("/add")
    public String add(DocumentInf documentInf, MultipartFile file, HttpServletRequest request) throws Exception {
        // 找上传文件的位置
        String realPath = request.getServletContext().getRealPath("/WEB-INF/files");
        System.err.println("~~~realPath~~~" + realPath);
        // 上传文件名称
        String originalFilename = file.getOriginalFilename();
        System.err.println("~~~originalFilename~~~" + originalFilename);

        // 上传文件，复制文件
        File file1 = new File(realPath, originalFilename);
        //把file1这个文件路径所指向的文件上传到对应的目录下。
        file.transferTo(file1);

        documentInf.setFilename(originalFilename);
        documentInf.setCreatedate(new Date());
        UserInf userInf = (UserInf) request.getSession().getAttribute("user_session");
        documentInf.setUserId(userInf.getId());
        // 数据入库
        documentInfService.save(documentInf);
        return "redirect:/document/list";
    }


    @RequestMapping("/toedit")
    public String toedit(Integer id, Model model) {
        DocumentInf byId = documentInfService.getById(id);
        model.addAttribute("job", byId);
        return "document/edit";
    }

    @RequestMapping("/edit")
    public String edit(DocumentInf documentInf, MultipartFile file, HttpServletRequest request) throws Exception {
        // 找上传文件的位置
        String realPath = request.getServletContext().getRealPath("/WEB-INF/files");
        System.err.println("~~~realPath~~~" + realPath);
        // 上传文件名称
        String originalFilename = file.getOriginalFilename();
        System.err.println("~~~originalFilename~~~" + originalFilename);

        // 上传文件，复制文件
        File file1 = new File(realPath, originalFilename);
        //把file1这个文件路径所指向的文件上传到对应的目录下。
        file.transferTo(file1);

        documentInf.setFilename(originalFilename);
        UserInf userInf = (UserInf) request.getSession().getAttribute("user_session");
        documentInf.setUserId(userInf.getId());
        System.err.println("documentInf~~~~~~~~~~~~~~~~~~~~~" + documentInf);
        documentInfService.saveOrUpdate(documentInf);
        return "redirect:/document/list";
    }

    @RequestMapping("/delete")
    public String del(Integer id) {
        documentInfService.removeById(id);
        return "redirect:/document/list";
    }


    @RequestMapping("/downLoad")
    public ResponseEntity<byte[]> downloadfile(String filename, HttpServletRequest request) throws Exception {
        System.err.println("！！！！！！！！！！~~~~~~~~~~~~~~~~~~~~~" + filename);
        //找文件的位置
        String realPath = request.getServletContext().getRealPath("/WEB-INF/files");
        // 找到文件
        File file = new File(realPath, filename);
        // io流=======  FileInputStream流被称为文件字节输入流，意思指对文件数据以字节的形式进行读取操作如读取图片视频等
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] buf = new byte[fileInputStream.available()];
        fileInputStream.read(buf);
        HttpStatus status = HttpStatus.OK;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", URLEncoder.encode(filename, "utf-8"));
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(buf, headers, status);
        fileInputStream.close();
        return responseEntity;

    }


}

