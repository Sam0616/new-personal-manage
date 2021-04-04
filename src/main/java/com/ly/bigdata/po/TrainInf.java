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
 * @since 2021-03-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TrainInf implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer empId;

    @TableField(exist = false)
    private String ename;

    private Integer deptId;

    @TableField(exist = false)
    private String dname;

    private Integer jobId;

    @TableField(exist = false)
    private String jname;

    @TableField(exist = false)
    private String completionName;

    private String content;

    private String startdate;

    private String enddate;

    private Integer totallength;

    private Integer completion;

    private Integer grade;

    /**
     * 创建时间
     */
    private Date traintime;


}
