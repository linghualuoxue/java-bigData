package com.bj.xnbb.es_search.domain;

/**
 * Created by hasee on 2017/7/31.
 */
public class RequestParam {

    private int stime;
    private int etime;
    private String province;
    private String city;
    private String yys;
    private String keyword;

    public int getStime() {
        return stime;
    }

    public void setStime(int stime) {
        this.stime = stime;
    }

    public int getEtime() {
        return etime;
    }

    public void setEtime(int etime) {
        this.etime = etime;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getYys() {
        return yys;
    }

    public void setYys(String yys) {
        this.yys = yys;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }


    @Override
    public String toString() {
        return "RequestParam{" +
                "stime=" + stime +
                ", etime=" + etime +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", yys='" + yys + '\'' +
                ", keyword='" + keyword + '\'' +
                '}';
    }
}
