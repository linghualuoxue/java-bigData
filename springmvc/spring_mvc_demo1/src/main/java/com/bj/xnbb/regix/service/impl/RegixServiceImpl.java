package com.bj.xnbb.regix.service.impl;

import com.bj.xnbb.regix.domain.PageModel;
import com.bj.xnbb.regix.domain.RegixDomain;
import com.bj.xnbb.regix.service.RegixService;
import com.bj.xnbb.util.JedisUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hasee on 2017/8/4.
 */
@Service
public class RegixServiceImpl implements RegixService<RegixDomain> {

    static final Logger log =  Logger.getLogger(RegixServiceImpl.class);

    @Override
    public PageModel<RegixDomain> getPageView() {
        Jedis jedis =null;
        PageModel<RegixDomain> pm=null;
        try {
            String key = "regix";
            jedis = JedisUtil.getClient();

            pm = new PageModel<RegixDomain>();

            boolean exist = jedis.exists(key);
            Map<String,String> value = jedis.hgetAll(key);
            pm.setTotal(value.size());
            pm.setRecords(value.size());
            pm.setPage(1);
            List<RegixDomain> rows = new ArrayList<>();
            pm.setRows(rows);
            if(!exist){
                return pm;
            }
            for (Map.Entry<String, String> entry : value.entrySet()) {
                RegixDomain rd = new RegixDomain();
                rd.setType(entry.getKey());
                rd.setRule(entry.getValue());
                rows.add(rd);
            }
            return pm;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JedisUtil.close(jedis);
        }
        return null;
    }

    @Override
    public RegixDomain getDomainByType(String type) {
        Jedis jedis =null;
        try {
            String key = "regix";
            jedis = JedisUtil.getClient();
            RegixDomain rd =  new RegixDomain();
            String regix = jedis.hget(key,type);
            rd.setType(type);
            rd.setRule(regix);
            return rd;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JedisUtil.close(jedis);
        }
        return null;
    }

    @Override
    public void saveOrUpdate(RegixDomain domain) {
        Jedis jedis = null;
        try {
            String key = "regix";
            jedis = JedisUtil.getClient();

            boolean exist = jedis.exists(key);
            if(!exist){
                Map<String,String> map  = new HashMap<>();
                map.put(domain.getType(),domain.getRule());
                jedis.hmset(key,map);
                log.info("新增规则：type="+domain.getType()+";rule="+domain.getRule());
            }else{
                jedis.hset(key,domain.getType(),domain.getRule());//插入或者更新操作
                log.info("更改规则：type="+domain.getType()+";rule="+domain.getRule());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JedisUtil.close(jedis);
        }
    }

    @Override
    public void deleteRegix(String type) {
        Jedis jedis = null;
        try {
            String key = "regix";
            jedis = JedisUtil.getClient();
            log.info("删除规则：type="+type+";rule="+jedis.hget(key,type));
            jedis.hdel(key,type);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JedisUtil.close(jedis);
        }
    }
}
