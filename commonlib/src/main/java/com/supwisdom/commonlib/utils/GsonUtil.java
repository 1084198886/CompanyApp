package com.supwisdom.commonlib.utils;

//import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zzq
 * @version 1.0.1
 * @date 2018/4/16.
 * @desc gson
 * 在项目选型的时候可以使用Google的Gson和阿里巴巴的FastJson两种并行使用，
 * 如果只是功能要求，没有性能要求，可以使用google的Gson，
 * 如果有性能上面的要求可以使用Gson将bean转换json确保数据的正确，使用FastJson将Json转换Bean
 */

public class GsonUtil {
    private static Gson gson;
    private static JsonParser jsonParser = new JsonParser();


    static {
        gson = new Gson();
    }

    private GsonUtil() {
    }

    /**
     * string转JsonObject
     *
     * @param gsonString
     * @return
     */
    public static JsonObject GsonToJsonObject(String gsonString) {
        return jsonParser.parse(gsonString).getAsJsonObject();
    }

    /**
     * 转成json
     *
     * @param object
     * @return
     */
    public static String GsonString(Object object) {
        return gson.toJson(object);
    }

    /**
     * 转成bean
     *
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> T GsonToBean(String gsonString, Class<T> cls) {
//        return JSON.parseObject(gsonString, cls);
        return gson.fromJson(gsonString, cls);
    }


    /**

    /**
     * 转成list
     * 泛型在编译期类型被擦除导致报错
     * @param gsonString
     * @param cls
     * @return
     */
//  public static <T> List<T> GsonToList(String gsonString, Class<T> cls) {
//    List<T> list = null;
//    if (gson != null) {
//      list = gson.fromJson(gsonString, new TypeToken<List<T>>() {
//      }.getType());
//    }
//    return list;
//  }

    /**
     * 转成list
     *
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> List<T> GsonToList(String gsonString, Class<T> cls) {
        List<T> list = new ArrayList<T>();
        if (gson != null) {
            JsonArray array = jsonParser.parse(gsonString).getAsJsonArray();
            for (final JsonElement elem : array) {
                list.add(gson.fromJson(elem, cls));
            }
        }
        return list;
    }

    /**
     * 转成list中有map的
     *
     * @param gsonString
     * @return
     */
    public static <T> List<Map<String, T>> GsonToListMaps(String gsonString, Class<T> cls) {
        List<Map<String, T>> list = null;
        if (gson != null) {
            list = gson.fromJson(gsonString, new TypeToken<List<Map<String, T>>>() {
            }.getType());
        }
        return list;
    }

    /**
     * 转成map的
     *
     * @param gsonString
     * @return
     */
    public static <T> Map<String, T> GsonToMaps(String gsonString, Class<T> cls) {
        Map<String, T> map = null;
        if (gson != null) {
            map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        }
        return map;
    }
}
