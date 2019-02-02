package com.liwy.commons.lang.json;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;

/**
 * <b>名称：</b> JSON工具类<br/>
 * <b>作者：</b> wenyao02.li <br/>
 * <b>创建时间：</b> 2018/6/7 16:41 <br/>
 * <b>版本：</b> V1.0 <br/>
 */
public class JsonUtils {

    /**
     * JSON字符串转为实体类对象
     */
    public static <T> T toBean(String jsonString, Class<T> beanClass) {
        return null;
    }

    /**
     * 实体类对象转换为JSON字符串
     */
    public static String toJsonStr(Object obj) {
        JSON.toJSONString(new ArrayList<>());
        return null;
    }

    /**
     * 实体类对象转换为JSON字符串-格式化
     */
    public static String toJsonPrettyStr(Object obj) {
        return null;
    }
}
