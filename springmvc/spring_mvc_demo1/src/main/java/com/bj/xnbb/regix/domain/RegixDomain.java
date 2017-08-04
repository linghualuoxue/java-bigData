package com.bj.xnbb.regix.domain;

import java.io.Serializable;

/**
 * Created by hasee on 2017/8/4.
 */
public class RegixDomain implements Serializable{

    private String id; //id

    private String type;//类型

    private String rule;//规则

    private String idDelete;//是否删除 T:删除 F:没有删除

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getIdDelete() {
        return idDelete;
    }

    public void setIdDelete(String idDelete) {
        this.idDelete = idDelete;
    }
}
