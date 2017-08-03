package com.bj.xnbb.es_search.domain;

import java.util.List;

/**
 * Created by hasee on 2017/8/1.
 */
public class WeijizhanDetail {

    private String wid;
    private String province;
    private String city;
    private String body;
    private List<Latitude> list;


    public String getWid() {
        return wid;
    }

    public void setWid(String wid) {
        this.wid = wid;
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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<Latitude> getList() {
        return list;
    }

    public void setList(List<Latitude> list) {
        this.list = list;
    }

    public static class Latitude{
        private String wjz_longitude;
        private String wjz_latitude;
        private String date;

        public String getWjz_longitude() {
            return wjz_longitude;
        }

        public void setWjz_longitude(String wjz_longitude) {
            this.wjz_longitude = wjz_longitude;
        }

        public String getWjz_latitude() {
            return wjz_latitude;
        }

        public void setWjz_latitude(String wjz_latitude) {
            this.wjz_latitude = wjz_latitude;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }
}
