package yl.com.dao;

import com.alibaba.fastjson.JSONObject;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.JdbcUtils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;


public class JsonRowMapper implements RowMapper<JSONObject> {

    @Override
    public JSONObject mapRow(ResultSet rs, int row) throws SQLException {
        String key = null;
        Object obj = null;
        JSONObject json = new JSONObject();
        try {
            ResultSetMetaData rsm = rs.getMetaData();
            int count = rsm.getColumnCount();
            for (int i = 1; i <= count ; i++) {
                key = JdbcUtils.lookupColumnName(rsm,i);
                obj = JdbcUtils.getResultSetValue(rs,i);
                json.put(key,obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return json;
    }
}
