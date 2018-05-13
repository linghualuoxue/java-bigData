package yl.com.dao;

import com.alibaba.fastjson.JSONObject;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;

/**
 * 基础数据访问
 */
public class BaseJdbcDao {

    /*JSON数据行映射器*/
    private static final JsonRowMapper jrm = new JsonRowMapper();
    /*JDBC调用模板*/
    private JdbcTemplate jdbcTemplate = null;
    /*启动时间*/
    private static Date startTime;

    public void initJdbcTemplate(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        if(startTime == null){
            startTime = getCurrentTime();
        }
    }

    /*获取数据库的当前时间*/
    private Date getCurrentTime() {
        return this.getJdbcTemplate().queryForObject("SELECT SYSTIMESTAMP FROM DUAL",Date.class);
    }

    /**
     * 查询json列表
     * @param sql
     * @param obj
     * @return
     */
    public List<JSONObject> queryForJsonList(String sql,Object ...obj){
        return this.getJdbcTemplate().query(sql,jrm,obj);
    }

    /*查询json数据*/
    public JSONObject queryForJsonObject(String sql,Object ...obj){
        List<JSONObject> listJsonObject = queryForJsonList(sql,obj);
        if(listJsonObject==null || listJsonObject.size()<1){
            return null;
        }
        return listJsonObject.get(0);
    }

    /**
     * 查询文本
     * @param sql
     * @param obj
     * @return
     */
    public String queryForString(String sql,Object ...obj){
        List<String> dataList = this.getJdbcTemplate().queryForList(sql,obj,String.class);
        if(dataList == null || dataList.size()<1){
            return null;
        }
        return dataList.get(0);
    }


    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


}
