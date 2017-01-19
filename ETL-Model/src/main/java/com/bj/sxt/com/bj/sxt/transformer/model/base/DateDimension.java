package com.bj.sxt.com.bj.sxt.transformer.model.base;

import com.bj.sxt.com.bj.sxt.util.TimeUtils;
import com.bj.sxt.common.DateEnum;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2017/1/17.
 */
public class DateDimension extends BaseDimension{

    private int id;
    private int year;
    private int season;
    private int month;
    private int week;
    private int day;
    private String type;
    private Date calendar = new Date();

    public DateDimension() {
    }

    public DateDimension(int id, int year, int season, int month, int week, int day, String type, Date calendar) {
        this.id = id;
        this.year = year;
        this.season = season;
        this.month = month;
        this.week = week;
        this.day = day;
        this.type = type;
        this.calendar = calendar;
    }

    public DateDimension(int year, int season, int month, int week, int day, String type, Date calendar) {
        this.year = year;
        this.season = season;
        this.month = month;
        this.week = week;
        this.day = day;
        this.type = type;
        this.calendar = calendar;
    }

    public static DateDimension buildDate(long time, DateEnum type){

        int year = TimeUtils.getDateInfo(time,DateEnum.YEAR);
        Calendar calendar = Calendar.getInstance();
        calendar.clear();

        if(DateEnum.YEAR.equals(type)){
            calendar.set(year,0,1);
            return new DateDimension(year,0,0,0,0,type.name,calendar.getTime());
        }
        int season = TimeUtils.getDateInfo(time, DateEnum.SEASON);
        if(DateEnum.SEASON.equals(type)){
            int month = (3*season-2);
            calendar.set(year,month-1,1);
            return new DateDimension(year,season,0,0,0,type.name,calendar.getTime());
        }
        int month = TimeUtils.getDateInfo(time, DateEnum.MONTH);
        if(DateEnum.MONTH.equals(type)){
            calendar.set(year,month-1,1);
            return new DateDimension(year,season,month,0,0,type.name,calendar.getTime());
        }
        int week = TimeUtils.getDateInfo(time, DateEnum.WEEK);
        //未完待续
        return null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCalendar() {
        return calendar;
    }

    public void setCalendar(Date calendar) {
        this.calendar = calendar;
    }

    public int compareTo(BaseDimension o) {
        return 0;
    }

    public void write(DataOutput dataOutput) throws IOException {

    }

    public void readFields(DataInput dataInput) throws IOException {

    }
}
