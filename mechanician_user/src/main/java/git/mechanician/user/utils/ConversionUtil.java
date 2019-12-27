package git.mechanician.user.utils;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 字符转换
 *
 * @author zhaosongbin
 * @date 13:46
 */
public class ConversionUtil {


    /**
     * String转为Result
     *
     * @param s
     * @return com.gkhealth.idata.common.result.Result
     * @Author zhaosongbin
     * @Date 13:47 2018/12/27
     */
    public static Object stringToObject(String s) {
        JSONObject jsonObject = JSONObject.fromObject(s);
        return JSONObject.toBean(jsonObject, Object.class);
    }


    public static <T> T jsonToBean(Object jsonObj) {
        JSONObject jsonObject = JSONObject.fromObject(jsonObj);
        T obj = (T) JSONObject.toBean(jsonObject);
        return obj;
    }


    public static JSONObject objToJSONObject(Object o) {
        return stringToJSONObject(objToJson(o));
    }


    /**
     * String转JSONArray
     *
     * @param s
     * @return net.sf.json.JSONArray
     * @Author zhaosongbin
     * @Date 14:11 2018/12/27
     */
    public static JSONArray stringToJSONArray(String s) {
        return JSONArray.fromObject(s);
    }


    /**
     * String转JSONObject
     *
     * @param s
     * @return net.sf.json.JSONObject
     * @Author zhaosongbin
     * @Date 14:11 2018/12/27
     */
    public static JSONObject stringToJSONObject(String s) {
        return JSONObject.fromObject(s);
    }


    /**
     * 将对象转换为json格式字符串
     *
     * @param obj
     * @return json string
     */
    public static String objToJson(Object obj) {
        ObjectMapper om = new ObjectMapper();
        try {
            String json = om.writeValueAsString(obj);
            return json;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将json形式字符串转换为java实体类
     */
    public static <T> T jsonToObj(String jsonStr, Class<T> clazz) {
        ObjectMapper om = new ObjectMapper();
        T readValue = null;
        try {
            readValue = om.readValue(jsonStr, clazz);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return readValue;
    }

    /**
     * 将json形式字符串转换为java实体类
     */
    public static <T> T jsonStringToObj(String jsonStr, Class<T> clazz) throws IOException {
        ObjectMapper om = new ObjectMapper();
        T readValue = null;
        return om.readValue(jsonStr, clazz);
    }

    /**
     * 获取利用反射获取类里面的值和名称
     *
     * @param obj
     * @return
     * @throws IllegalAccessException
     */
    public static Map<String, Object> objToMap(Object obj) {
        Map<String, Object> map = new HashMap<>(16);

        try {

            Class<?> clazz = obj.getClass();
            System.out.println(clazz);
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                String fieldName = field.getName();
                Object value = field.get(obj);
                map.put(fieldName, value);
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return map;
    }

    /**
     * map转jsonString
     *
     * @param map
     * @return
     */
    public static String mapToJsonString(Map map) {
        try {
            return JSON.toJSONString(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
