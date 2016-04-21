package com.example.xuqunxing.test.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class GsonExUtil {
    /**
     * 时间格式
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /**
     * 创建GSON
     * @author:qiuchen
     * @createTime:2012-9-24
     * @return
     */
    public static Gson getGson(){
        return new GsonBuilder().serializeNulls().setDateFormat(DATE_FORMAT).create();
    }
    /**
     * 将字符串数组转化为对象集合
     * @author:qiuchen
     * @createTime:2012-9-24
     * @param <T>
     * @param jsonStr
     * @param tClass
     * @return
     */
    public static <T> List<T> json2Collection(String jsonStr,Class<T> tClass){
        return getGson().fromJson(jsonStr,new TypeToken<List<T>>(){}.getType());
    }

    public static Object parsData(String json, Class clazz) {
        try {

            Gson gson = new Gson();
            return gson.fromJson(json, clazz);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

    public static Object StringToDate(Object o) {
        try {

            Gson gson = new Gson();
            return gson.toJson(o);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

    public static <T>Object parsListData(String json, Class clazz) {
        try {

            Gson gson = new Gson();
            return gson.fromJson(json,  new TypeToken<List<T>>() {}.getType());
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }
    public static String class2Data(Object object){
        try {
            Gson gson = new Gson();
            return gson.toJson(object);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }
}
