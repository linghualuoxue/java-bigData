package yl.com.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Date;

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

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

}
