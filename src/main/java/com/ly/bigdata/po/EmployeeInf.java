package com.ly.bigdata.po;

import com.baomidou.mybatisplus.annotation.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

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
 * @since 2021-03-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class EmployeeInf implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer deptId;

    private Integer jobId;

    private String name;

    private String cardId;

    private String address;

    private String phone;


    private Integer sexId;

    private Integer educationId;

    @TableField(exist = false)
    private DeptInf dept;
    @TableField(exist = false)
    private UserInf user;
    @TableField(exist = false)
    private EducationInf education;
    @TableField(exist = false)
    private JobInf job;

    /**
     * 创建时间
     */
    private Date createdate;

    private Integer userId;


}
