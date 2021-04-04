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

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 陈太康
 * @since 2021-03-26
 */
@Controller
@RequestMapping("/checkwork")
public class CheckworkInfController {
    @Autowired
    private CheckworkInfService checkworkInfService;
    @Autowired
    private DeptInfService deptInfService;
    @Autowired
    private EmployeeInfService employeeInfService;
    @Autowired
    private JobInfService jobInfService;
    @Autowired
    private LeaveInfService leaveInfService;
    @Autowired
    private LeavetypeInfService leavetypeInfService;
    @Autowired
    private LeavestatusInfService leavestatusInfService;


    @RequestMapping("/list")
    public String list(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                       @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                       Model model,
                       @RequestParam(value = "content", required = false) String content) {
        System.err.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~jinlaile!!!!!!!!!!!!!!!!!!!!!");
        Page<CheckworkInf> page = new Page<>(pageNo, pageSize);
        List<CheckworkInf> list = checkworkInfService.selectEmpAll(page, content);
        System.err.println("~~~~~~~~~~~~~~~~list~~~~~~~~~~~~~~~~~~~" + list);
        model.addAttribute("list", page.getRecords());
        model.addAttribute("pageNo", page.getCurrent());//当前页
        model.addAttribute("pageSize", page.getSize());////每页记录数
        model.addAttribute("count", page.getTotal());//总记录数*/

        return "checkwork/list";
    }

    @RequestMapping("/delete")
    public String del(Integer id) {
        checkworkInfService.removeById(id);
        return "redirect:/checkwork/list";
    }

    @RequestMapping("/toadd")
    public String toadd() {
        return "checkwork/add";
    }

    @RequestMapping("/add")
    public String add(CheckworkInf checkworkInf, String employeeName, Model model) {
        //  System.err.println(employeeName);
        QueryWrapper<EmployeeInf> wrapper = new QueryWrapper<>();
        wrapper.eq("name", employeeName);
        EmployeeInf employeeInf = employeeInfService.getOne(wrapper);
        if (employeeInf == null) {
            //员工不存在
            model.addAttribute("message", "员工不存在,无法添加！");
            return "checkwork/add";
        } else {

            checkworkInf.setEmpId(employeeInf.getId());
            checkworkInf.setJobId(employeeInf.getJobId());
            checkworkInf.setDeptId(employeeInf.getDeptId());
            checkworkInf.setCreatedate(new Date());
            System.err.println(checkworkInf);
            checkworkInfService.save(checkworkInf);
            return "redirect:/checkwork/list";
        }
    }


    @RequestMapping("/toedit")
    public String toedit(Integer id, Model model) {

        CheckworkInf checkworkInf = checkworkInfService.getById(id);
        String empName = employeeInfService.getById(checkworkInf.getEmpId()).getName();
        model.addAttribute("empName", empName);

        List<JobInf> list = jobInfService.list();
        List<DeptInf> list1 = deptInfService.list();
//        System.err.println("~~~~~~~~~~~~~~~~~~"+list);
//        System.err.println("~~~~~~~~~~~~~~~~~~"+list1);
        model.addAttribute("job_list", list);
        model.addAttribute("dept_list", list1);
        model.addAttribute("checkwork", checkworkInf);
        return "checkwork/edit";
    }

    @RequestMapping("/edit")
    public String edit(CheckworkInf checkworkInf) {
        System.out.println("checkworkInf~~~~~~~~~~~~~~~~" + checkworkInf);
        checkworkInfService.saveOrUpdate(checkworkInf);
        return "redirect:/checkwork/list";
    }


    @RequestMapping("/adminleavelist")
    public String adminLeaveList(Model model, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize, @RequestParam(value = "content", required = false) String content) {
        Page<LeaveInf> page = new Page<>(pageNum, pageSize);
        leaveInfService.findLeaveInfAll(page, content);

        List<LeaveInf> list = page.getRecords();
        Map<Integer, String> typeMap = new HashMap<>();
        Map<Integer, String> statusMap = new HashMap<>();
        for (LeaveInf leaveInf : list) {
            LeavetypeInf leavetypeInf = leavetypeInfService.getById(leaveInf.getLeavetype());
            LeavestatusInf leavestatusInf = leavestatusInfService.getById(leaveInf.getLeavestatus());
            typeMap.put(leaveInf.getId(), leavetypeInf.getName());
            statusMap.put(leaveInf.getId(), leavestatusInf.getName());
        }
        model.addAttribute("typeMap", typeMap);
        model.addAttribute("statusMap", statusMap);
        model.addAttribute("list", list);
        model.addAttribute("pageNo", page.getCurrent());//当前页
        model.addAttribute("pageSize", page.getSize());////每页记录数
        model.addAttribute("count", page.getTotal());//总记录数*/

        return "checkwork/adminleavelist";
    }

    @RequestMapping("/toadminleaveedit")
    public String toAdminLeaveEdit(Integer id, Model model) {
        LeaveInf leaveInf = leaveInfService.getById(id);
        List<LeavestatusInf> leavestatus_list = leavestatusInfService.list(null);
        List<LeavetypeInf> leavetype_list = leavetypeInfService.list(null);
        model.addAttribute("leaveInf", leaveInf);
        model.addAttribute("leavestatus_list", leavestatus_list);
        model.addAttribute("leavetype_list", leavetype_list);
        return "checkwork/adminleaveedit";
    }

    @RequestMapping("/adminleaveedit")
    public String adminleaveedit(LeaveInf leaveInf) {
        //System.err.println(leaveInf);
        leaveInfService.saveOrUpdate(leaveInf);
        return "redirect:/checkwork/adminleavelist";
    }

    @RequestMapping("/deladminleave")
    public String deladminleave(Integer id) {
        leaveInfService.removeById(id);
        return "redirect:/checkwork/adminleavelist";
    }

    @RequestMapping("/pedit")
    public String pedit(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                        @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                        Model model,
                        @RequestParam(value = "content", required = false) String content,
                        HttpSession session) {
        System.err.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~jinlaiddle!!!!!!!!!!!!!!!!!!!!!");
        Page<CheckworkInf> page = new Page<>(pageNo, pageSize);
        List<CheckworkInf> list = checkworkInfService.selectEmpAll(page, content);

        list.forEach(item -> {
            UserInf user_session = (UserInf) session.getAttribute("user_session");
            String username = user_session.getUsername();
            //  System.err.println(item.getEmployeeInf().getName());
            if (item.getEmployeeInf().getName().equals(username)) {
                model.addAttribute("checkwork", item);
            }
        });
//        model.addAttribute("list", page.getRecords());
        model.addAttribute("pageNo", page.getCurrent());//当前页
        model.addAttribute("pageSize", page.getSize());////每页记录数
        model.addAttribute("count", page.getTotal());//总记录数*/


        return "checkwork/pedit";
    }


    @RequestMapping("/leaveadd")
    public String leaveadd(HttpSession session, Model model) {
        List<LeavetypeInf> leavetype_list = leavetypeInfService.list();
        model.addAttribute("leavetype_list", leavetype_list);
        UserInf user_session = (UserInf) session.getAttribute("user_session");
        Integer empId = user_session.getEmpId();
        EmployeeInf employeeInf = employeeInfService.getById(empId);
        if (employeeInf == null) {
            return "checkwork/leaveadd";
        } else {
            model.addAttribute("employee", employeeInf);
            return "checkwork/leaveadd";
        }
    }

    @RequestMapping("/leaveInsert")
    public String leaveInsert(String employee_name, Model model, LeaveInf leaveInf) throws ParseException {

        QueryWrapper<EmployeeInf> wrapper = new QueryWrapper<>();
        wrapper.eq("name", employee_name);
        EmployeeInf employeeInf = employeeInfService.getOne(wrapper);
        if (employeeInf == null) {
            model.addAttribute("message", "员工必须存在才能添加！！");
            return "checkwork/leaveadd";
        } else {
            Integer id = employeeInf.getId();
            QueryWrapper<LeaveInf> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("emp_id", id);
            LeaveInf leaveInf1 = leaveInfService.getOne(wrapper1);
            if (leaveInf1 != null) {
                //已经有请假记录,不让再请
                model.addAttribute("message", "已经申请请假,不能再请！！");
                return "checkwork/leaveadd";
            } else {
                Integer employeeInfId = employeeInf.getId();
                Integer deptId = employeeInf.getDeptId();
                Integer jobId = employeeInf.getJobId();
                leaveInf.setDeptId(deptId);
                leaveInf.setJobId(jobId);
                leaveInf.setLeavestatus(2);
                leaveInf.setLeavetime(new Date());
                String startdata = leaveInf.getStartdata();
                String substring = startdata.substring(0, startdata.indexOf("T"));
                //注入请假开始日期
                leaveInf.setStartdata(substring);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = sdf.parse(substring);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                String leavedays = leaveInf.getLeavedays();
                int i = Integer.parseInt(leavedays);
                calendar.add(Calendar.DAY_OF_MONTH, i);
                String T3 = sdf.format(calendar.getTime());
                //注入请假结束日期
                leaveInf.setEnddata(T3);
                leaveInfService.save(leaveInf);
                return "redirect:/checkwork/leavelist";
            }
        }
    }


    @RequestMapping("/leavelist")
    public String LeaveList(HttpSession session, Model model, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize, @RequestParam(value = "content", required = false) String content) {
        Page<LeaveInf> page = new Page<>(pageNum, pageSize);
        leaveInfService.findLeaveInfAll(page, content);

        List<LeaveInf> list = page.getRecords();
        Map<Integer, String> typeMap = new HashMap<>();
        Map<Integer, String> statusMap = new HashMap<>();
        for (LeaveInf leaveInf : list) {
            UserInf user_session = (UserInf) session.getAttribute("user_session");
            String username = user_session.getUsername();
            if (leaveInf.getEmployeeInf().getName().equals(username)) {
                model.addAttribute("leave", leaveInf);
            }

            LeavetypeInf leavetypeInf = leavetypeInfService.getById(leaveInf.getLeavetype());
            LeavestatusInf leavestatusInf = leavestatusInfService.getById(leaveInf.getLeavestatus());
            typeMap.put(leaveInf.getId(), leavetypeInf.getName());
            statusMap.put(leaveInf.getId(), leavestatusInf.getName());
        }
        model.addAttribute("typeMap", typeMap);
        model.addAttribute("statusMap", statusMap);
//        model.addAttribute("list", list);
        model.addAttribute("pageNo", page.getCurrent());//当前页
        model.addAttribute("pageSize", page.getSize());////每页记录数
        model.addAttribute("count", page.getTotal());//总记录数*/

        return "checkwork/leavelist";
    }


    @RequestMapping("/toleaveedit")
    public String toLeaveEdit(Integer id, Model model) {
        LeaveInf leaveInf = leaveInfService.getById(id);
        List<LeavestatusInf> leavestatus_list = leavestatusInfService.list(null);
        List<LeavetypeInf> leavetype_list = leavetypeInfService.list(null);
        model.addAttribute("leaveInf", leaveInf);
        model.addAttribute("leavestatus_list", leavestatus_list);
        model.addAttribute("leavetype_list", leavetype_list);
        return "checkwork/edit";
    }

    @RequestMapping("/leaveedit")
    public String leaveedit(LeaveInf leaveInf) {
        //System.err.println(leaveInf);
        leaveInfService.saveOrUpdate(leaveInf);
        return "redirect:/checkwork/leavelist";
    }


}

