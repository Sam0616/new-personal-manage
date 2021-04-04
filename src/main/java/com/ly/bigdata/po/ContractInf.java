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
 * @since 2021-03-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ContractInf implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    @TableField("DEPT_ID")
    private Integer deptId;

    @TableField("JOB_ID")
    private Integer jobId;

    @TableField("STATUS_ID")
    private Integer statusId;

    @TableField("EMP_ID")
    private Integer empId;

    @TableField("TRAIN_CONTRACT")
    private Integer trainContract;

    @TableField("LABOR_CONTRACT")
    private Integer laborContract;

    @TableField("CREATE_DATE")
    private Date createDate;

    @TableField("CONFIDENTIALITY_CONTRACT")
    private Integer confidentialityContract;


    @TableField(exist = false)
    private DeptInf deptInf;
    @TableField(exist = false)
    private JobInf jobInf;
    @TableField(exist = false)
    private EmployeeInf employeeInf;


}
