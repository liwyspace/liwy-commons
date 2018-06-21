package com.liwy.commons.lang;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>常用枚举工具类</p>
 *
 * <ul>
 *  <li><b>getEnumMap</b>
 *      - 获取枚举字符串值和枚举对象的Map对应</li>
 *  <li><b>getEnumList</b>
 *      - 获取枚举值列表</li>
 *  <li><b>getEnum</b>
 *      - 通过enumName获取枚举值</li>
 *  <li><b>getEnumIgnoreCase</b>
 *      - 通过enumName获取枚举值, 忽略大小写</li>
 *  <li><b>isValidEnum</b>
 *      - 校验enumName是否是枚举中的值</li>
 *  <li><b>isValidEnumIgnoreCase</b>
 *      - 校验enumName是否是枚举中的值, 忽略大小写</li>
 * </ul>
 *
 * @author liwy
 * @version v1.0.1
 */
public class EnumUtils {
    /**
     * 获取枚举字符串值和枚举对象的Map对应，使用LinkedHashMap保证有序<br>
     * 结果中键为枚举名，值为枚举对象
     *
     * @param enumClass
     * @return java.util.Map<java.lang.String,E>
     */
    public static <E extends Enum<E>> Map<String, E> getEnumMap(final Class<E> enumClass) {
        final Map<String, E> map = new LinkedHashMap<>();
        for (final E e: enumClass.getEnumConstants()) {
            map.put(e.name(), e);
        }
        return map;
    }

    /**
     * 获取枚举值列表
     *
     * @param enumClass
     * @return java.util.List<E>
     */
    public static <E extends Enum<E>> List<E> getEnumList(final Class<E> enumClass) {
        return new ArrayList<>(Arrays.asList(enumClass.getEnumConstants()));
    }

    /**
     * 通过enumName获取枚举值
     *
     * @param enumClass
     * @param enumName
     * @return E
     */
    public static <E extends Enum<E>> E getEnum(final Class<E> enumClass, final String enumName) {
        if (enumName == null) {
            return null;
        }
        try {
            return Enum.valueOf(enumClass, enumName);
        } catch (final IllegalArgumentException ex) {
            return null;
        }
    }

    /**
     * 通过enumName获取枚举值, 忽略大小写
     *
     * @param enumClass
     * @param enumName
     * @return E
     */
    public static <E extends Enum<E>> E getEnumIgnoreCase(final Class<E> enumClass, final String enumName) {
        if (enumName == null || !enumClass.isEnum()) {
            return null;
        }
        for (final E each : enumClass.getEnumConstants()) {
            if (each.name().equalsIgnoreCase(enumName)) {
                return each;
            }
        }
        return null;
    }

    /**
     * 校验enumName是否是枚举中的值
     *
     * @param enumClass
     * @param enumName
     * @return boolean
     */
    public static <E extends Enum<E>> boolean isValidEnum(final Class<E> enumClass, final String enumName) {
        return getEnum(enumClass, enumName) != null;
    }

    /**
     * 校验enumName是否是枚举中的值, 忽略大小写
     *
     * @param enumClass
     * @param enumName
     * @return boolean
     */
    public static <E extends Enum<E>> boolean isValidEnumIgnoreCase(final Class<E> enumClass, final String enumName) {
        return getEnumIgnoreCase(enumClass, enumName) != null;
    }
}
