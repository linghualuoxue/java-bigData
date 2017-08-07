package com.bj.xnbb.util;

import com.bj.xnbb.regix.constant.Constant;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by hasee on 2017/8/7.
 * 单例对象
 */
public class JedisUtil {

    private static  JedisPool JEDIS_POOL ;

    public JedisUtil() {
        init();
    }

   private void  init(){
       JedisPoolConfig jedisPoolConfig =  new JedisPoolConfig();//redis连接池配置对象
        jedisPoolConfig.setMaxTotal(12);//连接池最大连接数量
        jedisPoolConfig.setMaxIdle(5);//连接池最大空闲数量
        jedisPoolConfig.setMinIdle(0);//连接池最小空闲数量
        jedisPoolConfig.setMaxWaitMillis(15000);//到达最大连接数调用者的阻塞时间
        jedisPoolConfig.setMinEvictableIdleTimeMillis(300000);//连接空闲的最小时间
        jedisPoolConfig.setSoftMinEvictableIdleTimeMillis(-1);//多余最小闲置连接将被移除
        jedisPoolConfig.setNumTestsPerEvictionRun(3);//设置每次检查闲置连接的个数
        jedisPoolConfig.setTestOnBorrow(false);//申请连接时检查连接是否有效
        jedisPoolConfig.setTestOnReturn(false);//返回连接时，检查连接是否有效
        jedisPoolConfig.setTestWhileIdle(false);//空闲超时，是否执行检查有效
        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(60000);//空闲检查时间
        jedisPoolConfig.setBlockWhenExhausted(true);//当连接数耗尽是否阻塞

       JEDIS_POOL = new JedisPool(jedisPoolConfig, BasicConfiguration.getInstance().getValue(Constant.JEDIS_HOST),
               Integer.parseInt(BasicConfiguration.getInstance().getValue(Constant.JEDIS_PORT)));
    }
    static class JedisHelper{
        public static JedisUtil jedisUtil = new JedisUtil();
    }

    private static JedisUtil getInstance(){
        return JedisHelper.jedisUtil;
    }

   public static Jedis getClient(){
       return getInstance().JEDIS_POOL.getResource();
   }

   public static void close(Jedis jedis){
       getInstance().JEDIS_POOL.returnResource(jedis); //归还连接
   }

}
