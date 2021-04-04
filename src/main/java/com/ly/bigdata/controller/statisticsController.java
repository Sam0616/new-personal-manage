package com.ly.bigdata.controller;

import com.ly.bigdata.service.EmployeeInfService;
import com.ly.bigdata.vo.sexNum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller

public class statisticsController {
    @Autowired
    private EmployeeInfService employeeInfService;


    @RequestMapping("/statistics")
    public String toStatistics() {
        return "statistics/statistics";
    }



    @RequestMapping("/echart")
    public String toEchart() {
        return "statistics/echart";
    }

    @ResponseBody
    @RequestMapping("/sexStatis")
    public Object sexStatis() {
        List<sexNum> sexNums = employeeInfService.selectSexes();
        return sexNums;
    }





    @ResponseBody
    @RequestMapping("/sexStatis2")
    public Object sexStatis2() {
        List<sexNum> sexNums = employeeInfService.selectSexes();
        return sexNums;
    }

}
