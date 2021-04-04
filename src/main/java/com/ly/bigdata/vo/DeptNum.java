package com.ly.bigdata.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
@Data
@EqualsAndHashCode
public class DeptNum implements Serializable {
    private String name;
    private Integer num;
}
