package yl.com.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FastJsonConvert {

    /**
     * 将json字符串转化为对象
     * @param data
     * @param clzss
     * @param <T>
     * @return
     */
    public <T> T convertJson2Object(String data,Class<T> clzss){
        try {
            T t = JSON.parseObject(data,clzss);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将JSONObject转化为对象
     * @param data
     * @param clzss
     * @param <T>
     * @return
     */
    public <T> T convertJson2Object(JSONObject data,Class<T> clzss){
        try {
        T t = JSONObject.toJavaObject(data,clzss);
        return t;
    } catch (Exception e) {
        e.printStackTrace();
        return null;
      }
    }

    /**
     * 将字符串转化为集合
     */
    public <T> List<T> convertJson2List(String data, Class<T> clzss){
        try {
            List<T> t = JSON.parseArray(data,clzss);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将JSONObject集合转化为字符串集合
     * @param data
     * @param clzss
     * @param <T>
     * @return
     */
    public <T> List<T> convertJson2List(List<JSONObject> data,Class<T> clzss){
        try {
            List<T> list = new ArrayList<T>();
            if(data!=null) {
                for (JSONObject json : data) {
                    list.add(convertJson2Object(json, clzss));
                }
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 对象转化为字符串
     * @param obj
     * @return
     */
    public static String convertObject2Json(Object obj){
        try {
            String json = JSON.toJSONString(obj);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将对象转化为jsonObject
     * @param obj
     * @return
     */
    public static JSONObject convertobject2JsonObject(Object obj){
        try {
            JSONObject jsonObject = (JSONObject)JSONObject.toJSON(obj);
            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
