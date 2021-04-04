package com.ly.bigdata.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ly.bigdata.po.*;
import com.ly.bigdata.service.CompletionInfService;
import com.ly.bigdata.service.EmployeeInfService;
import com.ly.bigdata.service.TrainInfService;
import com.ly.bigdata.service.UserInfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Wrapper;
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
@RequestMapping("/train")
public class TrainInfController {

    @Autowired
    private TrainInfService trainInfService;
    @Autowired
    private CompletionInfService completionInfService;
    @Autowired
    private EmployeeInfService employeeInfService;
    @Autowired
    private UserInfService userInfService;


    @RequestMapping("")
    public String toTrain() {
        System.err.println("train");
        return "train/train";
    }

    @RequestMapping("/admintrainlist")
    public String list(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                       @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                       Model model,
                       @RequestParam(value = "content", required = false) String content) {
        Page<TrainInf> page = new Page<>(pageNo, pageSize);

        List<TrainInf> list = trainInfService.selectAll(page, content);
        list.forEach(item -> {
            CompletionInf completionInf = completionInfService.getById(item.getCompletion());
            String name = completionInf.getName();
            item.setCompletionName(name);
        });
        model.addAttribute("list", page.getRecords());
        model.addAttribute("pageNo", page.getCurrent());//当前页
        model.addAttribute("pageSize", page.getSize());////每页记录数
        model.addAttribute("count", page.getTotal());//总记录数*/
        return "train/admintrainlist";
    }


    @RequestMapping("/toedit")
    public String toedit(Integer id, Model model) {
        System.err.println("~~~进进进~~~");
        TrainInf trainInf = trainInfService.getById(id);
        String empName = employeeInfService.getById(trainInf.getEmpId()).getName();
        List<CompletionInf> completionInfs = completionInfService.list();
        model.addAttribute("completion_list", completionInfs);
        model.addAttribute("empName", empName);
        model.addAttribute("train", trainInf);
        System.err.println("~~~~~~~~~~~~" + trainInf);
        return "train/edit";
    }

    @RequestMapping("/edit")
    public String edit(TrainInf trainInf) {
        trainInfService.saveOrUpdate(trainInf);
        return "redirect:/train/admintrainlist";
    }


    @RequestMapping("/traintoadd")
    public String totraintoadd(Model model) {
        List<CompletionInf> completionInfs = completionInfService.list();
        model.addAttribute("completion_list", completionInfs);

        return "train/add";
    }
    @RequestMapping("/delete")
    public String del(Integer id) {
        trainInfService.removeById(id);
        return "redirect:/train/admintrainlist";
    }

    @RequestMapping("add")
    public String traintoadd(Model model, String employee_name, TrainInf trainInf) {


        QueryWrapper<EmployeeInf> wrapper = new QueryWrapper<>();
        wrapper.eq("name", employee_name);
        EmployeeInf employeeInf = employeeInfService.getOne(wrapper);
        if (employeeInf == null) {

            model.addAttribute("message", "员工不存在，无法添加！");
            return "train/add";
        } else {

            trainInf.setDeptId(employeeInf.getDeptId());
            trainInf.setEmpId(employeeInf.getId());
            trainInf.setJobId(employeeInf.getJobId());
            System.err.println("~~~~~~~~~~"+trainInf);
            trainInfService.save(trainInf);
            return "redirect:/train/admintrainlist";
        }
    }




    @RequestMapping("/trainlist")
    public String Plist(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                       @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                       Model model,
                       String id,
                       @RequestParam(value = "content", required = false) String content) {
        Page<TrainInf> page = new Page<>(pageNo, pageSize);
        String username = userInfService.getById(id).getUsername();
        List<TrainInf> list = trainInfService.selectTrainPersonal(page, content,username);
        list.forEach(item -> {
            CompletionInf completionInf = completionInfService.getById(item.getCompletion());
            String name = completionInf.getName();
            item.setCompletionName(name);
        });
        model.addAttribute("list", page.getRecords());
        model.addAttribute("pageNo", page.getCurrent());//当前页
        model.addAttribute("pageSize", page.getSize());////每页记录数
        model.addAttribute("count", page.getTotal());//总记录数*/
        return "train/trainlist";
    }

}

