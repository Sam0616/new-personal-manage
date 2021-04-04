package com.ly.bigdata.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ly.bigdata.po.*;
import com.ly.bigdata.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 陈太康
 * @since 2021-03-25
 */
@Controller
@RequestMapping("/employee")
public class EmployeeInfController {
    @Autowired
    private EmployeeInfService employeeInfService;
    @Autowired
    private SexInfService sexInfService;
    @Autowired
    private JobInfService jobInfService;
    @Autowired
    private DeptInfService deptInfService;
    @Autowired
    private EducationInfService educationInfService;
    @Autowired
    private UserInfService userInfService;


    @RequestMapping("/list")
    public String list(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                       @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                       Model model,
                       @RequestParam(value = "content", required = false) String content) {
        Page<EmployeeInf> page = new Page<>(pageNo, pageSize);
        employeeInfService.selectEmpAll(page, content);

        model.addAttribute("list", page.getRecords());
        model.addAttribute("pageNo", page.getCurrent());//当前页
        model.addAttribute("pageSize", page.getSize());////每页记录数
        model.addAttribute("count", page.getTotal());//总记录数
        return "employee/list";
    }

    @RequestMapping("/association")
    public String association(Model model) {
        return "employee/association";
    }

    @RequestMapping("/toassociation")
    public String toassociation(String loginname, Model model) {
        QueryWrapper<UserInf> wrapper = new QueryWrapper<>();
        wrapper.eq("loginname", loginname);
        UserInf userInf = userInfService.getOne(wrapper);
        // System.err.println("~~~~~userInf~~~~~~~" + userInf);
        if (userInf == null) {
            //您还不是用户
            model.addAttribute("message", "登录名不存在，请先联系管理员注册！");
            return "employee/association";
        } else {
            //验证员工是否已经存在
            QueryWrapper<EmployeeInf> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("user_id", userInf.getId()).select(new String[]{"id", "dept_id", "job_id", "name", "address", "phone", "sex_id", "education_id", "createdate", "user_id"});
            EmployeeInf employeeInf = employeeInfService.getOne(wrapper1);
            //System.err.println("~~~~~~~~~~~~" + employeeInf);
            if (employeeInf != null) {
                //员工存在，不可以添加
                model.addAttribute("message", "员工存在，不可以添加！");
                return "employee/association";
            } else {
                List<SexInf> sex_list = sexInfService.list(null);
                List<DeptInf> dept_list = deptInfService.list(null);
                List<EducationInf> education_list = educationInfService.list(null);
                List<JobInf> job_list = jobInfService.list(null);
                model.addAttribute("listSex", sex_list);
                model.addAttribute("listDept", dept_list);
                model.addAttribute("listEdu", education_list);
                model.addAttribute("listJob", job_list);
                model.addAttribute("user", userInf);
                return "employee/add";
            }
        }
    }


    @RequestMapping("/add")
    public String add(EmployeeInf employeeInf, String educationId) {

        System.err.println("~~~~~~educationId~~~~~~~~" + educationId);
        Date date = new Date();
        employeeInf.setCreatedate(date);
        employeeInfService.save(employeeInf);
        return "redirect:/employee/list";
    }


    @RequestMapping("/delete")
    public String del(Integer id) {

        employeeInfService.removeById(id);
        return "redirect:/employee/list";
    }

    @RequestMapping("/toedit")
    public String toedit(Integer id, Model model) {

        EmployeeInf employeeInf = employeeInfService.getById(id);

        List<SexInf> sex_list = sexInfService.list(null);
        List<DeptInf> dept_list = deptInfService.list(null);
        List<EducationInf> education_list = educationInfService.list(null);
        List<JobInf> job_list = jobInfService.list(null);
        model.addAttribute("employee", employeeInf);
        model.addAttribute("sex_list", sex_list);
        model.addAttribute("dept_list", dept_list);
        model.addAttribute("education_list", education_list);
        model.addAttribute("job_list", job_list);
        return "employee/edit";
    }

    @RequestMapping("/edit")
    public String edit(EmployeeInf employeeInf) {
        System.out.println(employeeInf);
        employeeInfService.updateById(employeeInf);
        return "redirect:/employee/list";
    }


    @RequestMapping("/plist")
    public String topedit(Integer id, Model model) {
      //  System.err.println("id============"+id);
        EmployeeInf employeeInf = employeeInfService.getById(id);
        model.addAttribute("employee", employeeInf);
        String name = educationInfService.getById(employeeInf.getEducationId()).getName();
        String name2 = jobInfService.getById(employeeInf.getJobId()).getName();
        String name3 = deptInfService.getById(employeeInf.getDeptId()).getName();
        model.addAttribute("eduname", name);
        model.addAttribute("empjobname", name2);
        model.addAttribute("empdeptname", name3);

        return "employee/plist";
    }





    @RequestMapping("/topadd")
    public String topadd(Model model, Integer id) {

        EmployeeInf employeeInf = employeeInfService.getById(id);
        model.addAttribute("employee", employeeInf);
        String name = educationInfService.getById(employeeInf.getEducationId()).getName();
        String name2 = jobInfService.getById(employeeInf.getJobId()).getName();
        String name3 = deptInfService.getById(employeeInf.getDeptId()).getName();
        String email = userInfService.getById(employeeInf.getUserId()).getEmail();
        model.addAttribute("email",email);
        model.addAttribute("eduname", name);
        model.addAttribute("empjobname", name2);
        model.addAttribute("empdeptname", name3);
        List<SexInf> sex_list = sexInfService.list(null);
        List<DeptInf> dept_list = deptInfService.list(null);
        List<EducationInf> education_list = educationInfService.list(null);
        List<JobInf> job_list = jobInfService.list(null);

        model.addAttribute("sex_list", sex_list);
        model.addAttribute("dept_list", dept_list);
        model.addAttribute("education_list", education_list);
        model.addAttribute("job_list", job_list);

        return "employee/padd";
    }


    @RequestMapping("/padd")
    public String padd(EmployeeInf employeeInf,Model model){
//        System.err.println("~~~~~~~emloy~~~~"+employeeInf);
        employeeInfService.saveOrUpdate(employeeInf);
        Integer id = employeeInf.getId();
        return "redirect:/employee/plist?id="+id;
    }

}

