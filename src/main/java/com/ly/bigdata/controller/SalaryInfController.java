package com.ly.bigdata.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ly.bigdata.po.*;
import com.ly.bigdata.service.*;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
 * @since 2021-03-27
 */
@Controller
@RequestMapping("/salary")
public class SalaryInfController {
    @Autowired
    private SalaryInfService salaryInfService;
    @Autowired
    EmployeeInfService employeeInfService;
    @Autowired
    private JobInfService jobInfService;
    @Autowired
    private DeptInfService deptInfService;
    @Autowired
    private UserInfService userInfService;

    @RequestMapping("/list")
    public String list(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                       @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                       Model model,
                       @RequestParam(value = "content", required = false) String content) {
        Page<SalaryInf> page = new Page<>(pageNo, pageSize);
        List<SalaryInf> list = salaryInfService.findSalaryInfAll(page, content);
        model.addAttribute("list", page.getRecords());
        model.addAttribute("pageNo", page.getCurrent());//当前页
        model.addAttribute("pageSize", page.getSize());////每页记录数
        model.addAttribute("count", page.getTotal());//总记录数*/

        return "salary/list";
    }


    @RequestMapping("/toadd")
    public String toadd() {
        System.err.println("~~~~toadd~~~~~~~");
        return "salary/add";
    }

    @RequestMapping("/add")
    public String add(SalaryInf salaryInf, String employeeName, Model model) {
        QueryWrapper<EmployeeInf> wrapper = new QueryWrapper<>();
        wrapper.eq("name", employeeName);
        EmployeeInf employeeInf = employeeInfService.getOne(wrapper);
        if (employeeInf == null) {
            model.addAttribute("message", "员工必须存在才能添加！！！！");
            return "salary/add";
        } else {
            salaryInf.setCreateDate(new Date());
            salaryInf.setDeptId(employeeInf.getDeptId());
            salaryInf.setEmpId(employeeInf.getId());
            salaryInf.setJobId(employeeInf.getJobId());
            System.err.println("~~~~~~~~" + salaryInf);
            salaryInfService.save(salaryInf);
            return "redirect:/salary/list";
        }
    }


    @RequestMapping("/delete")
    public String deladminleave(Integer id) {
        salaryInfService.removeById(id);
        return "redirect:/salary/list";
    }


    @RequestMapping("/toedit")
    public String toedit(Integer id, Model model) {

        SalaryInf salaryInf = salaryInfService.getById(id);
        String empName = employeeInfService.getById(salaryInf.getEmpId()).getName();
        model.addAttribute("empName", empName);
        List<JobInf> list = jobInfService.list();
        List<DeptInf> list1 = deptInfService.list();
        System.err.println("~~~~~~~~~~~~~~~~~~"+list);
        System.err.println("~~~~~~~~~~~~~~~~~~"+list1);
        model.addAttribute("job_list", list);
        model.addAttribute("dept_list", list1);
        model.addAttribute("salary", salaryInf);
        System.err.println("~~~~~~~~~~~~~~~~~~"+salaryInf);

        return "salary/edit";
    }

    @RequestMapping("/edit")
    public String edit(SalaryInf salaryInf) {
        System.out.println(salaryInf);
        salaryInfService.saveOrUpdate(salaryInf);
        return "redirect:/salary/list";
    }


    @RequestMapping("/pedit")
    public String Plist(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                       @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                       Model model,
                       @RequestParam(value = "content", required = false) String content,
                        String id) {
        Page<SalaryInf> page = new Page<>(pageNo, pageSize);
        System.err.println("~~~~~~~~~id~~~~~~~~~~~"+id);
        String username = userInfService.getById(id).getUsername();
        List<SalaryInf> list = salaryInfService.findSalaryInfPersonal(page, content,username);
        model.addAttribute("list", page.getRecords());
        model.addAttribute("pageNo", page.getCurrent());//当前页
        model.addAttribute("pageSize", page.getSize());////每页记录数
        model.addAttribute("count", page.getTotal());//总记录数*/

        return "salary/pedit";
    }
}

