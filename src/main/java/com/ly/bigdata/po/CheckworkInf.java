package com.ly.bigdata.po;

import com.baomidou.mybatisplus.annotation.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author 陈太康
 * @since 2021-03-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CheckworkInf implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    @TableField("DEPT_ID")
    private Integer deptId;
    @TableField(exist = false)
    private DeptInf deptInf;

    @TableField("JOB_ID")
    private Integer jobId;
    @TableField(exist = false)
    private JobInf jobInf;

    @TableField("EMP_ID")
    private Integer empId;
    @TableField(exist = false)
    private EmployeeInf employeeInf;

    @TableField("WORKINGDAYS")
    private Integer workingdays;

    @TableField("DAYSLEAVE")
    private Integer daysleave;

    @TableField("CREATEDATE")
    private Date createdate;

    @TableField("DAYSOUT")
    private Integer daysout;


}
