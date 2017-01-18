package com.bj.sxt.common;

/**
 * Created by Administrator on 2017/1/17.
 */
public enum DateEnum {
    YEAR("year"),SEASON("season"),MONTH("month"),WEEK("week"),DAY("day"),HOUR("hour");

    public final String name;

    private DateEnum(String name){
        this.name = name;
    }

    public static DateEnum valueOfName(String name){
        for (DateEnum type : values()) {
            if(type.name.equals(name)){
                return type;
            }

        }
        return null;
    }


}
