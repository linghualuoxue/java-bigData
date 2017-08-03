package com.bj.xnbb.ssmTest.constant;

/**
 * Created by hasee on 2017/7/20.
 */
public class Constant {

    /**
     *  连接zk的超时时间
     */
    public static final int ZK_SESSION_TIMEOUT = 5000;

    /**
     * 连接zk的注册节点
     */
    public static final String ZK_REGIST_PATH = "/web_server";


    public static final String ZK_REGIST_DATA = ZK_REGIST_PATH+"/data";


}
