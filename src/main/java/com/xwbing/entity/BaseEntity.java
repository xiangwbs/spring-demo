package com.xwbing.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 274378722226517825L;
    private String id;
    private String creator;
    private String modifier;
    @JSONField(name = "is_deleted")
    private String isDeleted;
    @JSONField(name = "create_time")
    private Date createTime;
    @JSONField(name = "modified_time")
    private Date modifiedTime;
    private Integer sort;
}
