package com.bj.sxt.com.bj.sxt.util;

/**
 * Created by user on 2017/1/10.
 */
public class RegionInfo {
    //
    public static final String DEFAULT_VALUE = GlobalConstants.DEFAULT_VALUE;
    private String country = GlobalConstants.DEFAULT_VALUE;
    private String provice = GlobalConstants.DEFAULT_VALUE;
    private String city = GlobalConstants.DEFAULT_VALUE;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvice() {
        return provice;
    }

    public void setProvice(String provice) {
        this.provice = provice;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}