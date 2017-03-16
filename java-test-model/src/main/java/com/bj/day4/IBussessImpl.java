package com.bj.day4;

/**
 * Created by user on 2017/3/16.
 */
public class IBussessImpl implements Ibussess{
    @Override
    public int getPrice(String good) {
        return "yifu".equals(good)?10:20;
    }
}
