package com.ly.bigdata.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ly.bigdata.po.*;
import com.ly.bigdata.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 陈太康
 * @since 2021-03-28
 */
@Controller
@RequestMapping("/contract")
public class ContractInfController {

    @Autowired
    private ContractInfService contractInfService;
    @Autowired
    EmployeeInfService employeeInfService;
    @Autowired
    private JobInfService jobInfService;
    @Autowired
    private DeptInfService deptInfService;
    @Autowired
    private ConfidentialitycontractInfService confidentialitycontractInfService;
    @Autowired
    private LaborcontractInfService laborcontractInfService;
    @Autowired
    private TraincontractInfService traincontractInfService;
    @Autowired
    private UserInfService userInfService;

    @RequestMapping("/list")
    public String list(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                       @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                       Model model,
                       @RequestParam(value = "content", required = false) String content) {
        Page<ContractInf> page = new Page<>(pageNo, pageSize);
        List<ContractInf> list = contractInfService.findContractInfAll(page, content);
        model.addAttribute("list", page.getRecords());
        model.addAttribute("pageNo", page.getCurrent());//当前页
        model.addAttribute("pageSize", page.getSize());////每页记录数
        model.addAttribute("count", page.getTotal());//总记录数*/

        return "contract/list";
    }


    @RequestMapping("/delete")
    public String del(Integer id) {
        contractInfService.removeById(id);
        return "redirect:/contract/list";
    }



    @RequestMapping("/toadd")
    public String toadd() {
        System.err.println("~~~~toadd~~~~~~~");
        return "contract/add";
    }

    @RequestMapping("/add")
    public String add(ContractInf contractInf, String employeeName, Model model) {
        QueryWrapper<EmployeeInf> wrapper = new QueryWrapper<>();
        wrapper.eq("name", employeeName);
        EmployeeInf employeeInf = employeeInfService.getOne(wrapper);
        if (employeeInf == null) {
            model.addAttribute("message", "员工必须存在才能添加！！！！");
            return "contract/add";
        } else {
            contractInf.setCreateDate(new Date());
            contractInf.setDeptId(employeeInf.getDeptId());
            contractInf.setEmpId(employeeInf.getId());
            contractInf.setJobId(employeeInf.getJobId());
            System.err.println("~~~~~~~~" + contractInf);
            contractInfService.save(contractInf);
            return "redirect:/contract/list";
        }
    }

    @RequestMapping("/toedit")
    public String toedit(Integer id, Model model) {
        System.err.println("~~~进进进~~~");
        ContractInf contractInf = contractInfService.getById(id);
        String empName = employeeInfService.getById(contractInf.getEmpId()).getName();
        model.addAttribute("empName", empName);
        List<JobInf> list = jobInfService.list();
        List<DeptInf> list1 = deptInfService.list();
        List<ConfidentialitycontractInf> list2 = confidentialitycontractInfService.list();
        List<LaborcontractInf> list3 = laborcontractInfService.list();
        List<TraincontractInf> list4 = traincontractInfService.list();


        model.addAttribute("job_list", list);
        model.addAttribute("dept_list", list1);
        model.addAttribute("confidentialitycontract_list", list2);
        model.addAttribute("laborcontract_list", list3);
        model.addAttribute("traincontract_list", list4);
        model.addAttribute("contract", contractInf);


        return "contract/edit";
    }

    @RequestMapping("/edit")
    public String edit(ContractInf contractInf) {
        System.err.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"+contractInf);
        contractInfService.saveOrUpdate(contractInf);
        return "redirect:/contract/list";
    }

@RequestMapping("/pedit")
public String pedit(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                   @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                   Model model,
                   String id,
                   @RequestParam(value = "content", required = false) String content) {

    Page<ContractInf> page = new Page<>(pageNo, pageSize);
    String username = userInfService.getById(id).getUsername();
    List<ContractInf> list = contractInfService.findSalaryPersonal(page, content,username);
    model.addAttribute("list", page.getRecords());
    model.addAttribute("pageNo", page.getCurrent());//当前页
    model.addAttribute("pageSize", page.getSize());////每页记录数
    model.addAttribute("count", page.getTotal());//总记录数*/

    return "contract/list";
}

}

