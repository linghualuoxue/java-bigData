package com.bj.xnbb.regix.domain;

import java.io.Serializable;

/**
 * Created by hasee on 2017/8/4.
 */
public class RegixDomain implements Serializable{


    private String type;//类型

    private String rule;//规则



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


}
