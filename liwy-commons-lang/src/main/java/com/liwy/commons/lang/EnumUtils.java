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
 * <li><b>CodeEnum</b>      - 包含Code的枚举接口</li>
 * <li><b>ValueEnum</b>     - 包含Value的枚举接口</li>
 * <li><b>NameEnum</b>      - 包含Name的枚举接口</li>
 * </ul>
 *
 * <ul>
 * <li><b>getEnumMap</b>               - 获取枚举字符串值和枚举对象的Map对应</li>
 * <li><b>getEnumList</b>              - 获取枚举值列表</li>
 * <li><b>getEnum</b>                  - 通过enumName获取枚举值</li>
 * <li><b>getEnumIgnoreCase</b>        - 通过enumName获取枚举值, 忽略大小写</li>
 * <li><b>isValidEnum</b>              - 校验enumName是否是枚举中的值</li>
 * <li><b>isValidEnumIgnoreCase</b>    - 校验enumName是否是枚举中的值, 忽略大小写</li>
 * <li><b>getEnumByCode</b>            - 通过code获取枚举</li>
 * <li><b>getEnumByValue</b>           - 通过value获取枚举</li>
 * <li><b>getEnumByName</b>            - 通过name获取枚举</li>
 * </ul>
 *
 * @author liwy
 * @version v1.0.2
 */
public class EnumUtils {
    /**
     * <p>获取枚举字符串值和枚举对象的Map对应，使用LinkedHashMap保证有序<br>
     * 结果中键为枚举名，值为枚举对象</p>
     *
     * @param enumClass
     * @return java.util.Map<java.lang.String, E>
     */
    public static <E extends Enum<E>> Map<String, E> getEnumMap(final Class<E> enumClass) {
        final Map<String, E> map = new LinkedHashMap<String, E>();
        for (final E e : enumClass.getEnumConstants()) {
            map.put(e.name(), e);
        }
        return map;
    }

    /**
     * <p>获取枚举值列表</p>
     *
     * @param enumClass
     * @return java.util.List<E>
     */
    public static <E extends Enum<E>> List<E> getEnumList(final Class<E> enumClass) {
        return new ArrayList(Arrays.asList(enumClass.getEnumConstants()));
    }

    /**
     * <p>通过enumName获取枚举值</p>
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
     * <p>通过enumName获取枚举值, 忽略大小写</p>
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
     * <p>校验enumName是否是枚举中的值</p>
     *
     * @param enumClass
     * @param enumName
     * @return boolean
     */
    public static <E extends Enum<E>> boolean isValidEnum(final Class<E> enumClass, final String enumName) {
        return getEnum(enumClass, enumName) != null;
    }

    /**
     * <p>校验enumName是否是枚举中的值, 忽略大小写</p>
     *
     * @param enumClass
     * @param enumName
     * @return boolean
     */
    public static <E extends Enum<E>> boolean isValidEnumIgnoreCase(final Class<E> enumClass, final String enumName) {
        return getEnumIgnoreCase(enumClass, enumName) != null;
    }

    /**
     * 通过code获取枚举
     *
     * @param enumClass
     * @param code
     * @param <E>
     * @param <T>
     * @return
     */
    public static <E extends CodeEnum<T>, T> E getEnumByCode(final Class<E> enumClass, T code) {
        if (enumClass == null || !enumClass.isEnum() || code == null) {
            return null;
        }
        for (E each : enumClass.getEnumConstants()) {
            if (code.equals(each.getCode())) {
                return each;
            }
        }
        return null;
    }

    /**
     * 通过value获取枚举
     *
     * @param enumClass
     * @param value
     * @param <E>
     * @param <T>
     * @return
     */
    public static <E extends ValueEnum<T>, T> E getEnumByValue(final Class<E> enumClass, T value) {
        if (enumClass == null || !enumClass.isEnum() || value == null) {
            return null;
        }
        for (E each : enumClass.getEnumConstants()) {
            if (value.equals(each.getValue())) {
                return each;
            }
        }
        return null;
    }

    /**
     * 通过name获取枚举
     *
     * @param enumClass
     * @param name
     * @param <E>
     * @param <T>
     * @return
     */
    public static <E extends NameEnum<T>, T> E getEnumByName(final Class<E> enumClass, T name) {
        if (enumClass == null || !enumClass.isEnum() || name == null) {
            return null;
        }
        for (E each : enumClass.getEnumConstants()) {
            if (name.equals(each.getName())) {
                return each;
            }
        }
        return null;
    }

    /**
     * 包含Code的枚举接口
     *
     * @param <T>
     */
    public interface CodeEnum<T> {
        /**
         * 获取Code
         *
         * @return
         */
        T getCode();
    }

    /**
     * 包含Value的枚举接口
     *
     * @param <T>
     */
    public interface ValueEnum<T> {
        /**
         * 获取Value
         *
         * @return
         */
        T getValue();
    }

    /**
     * 包含Name的枚举接口
     *
     * @param <T>
     */
    public interface NameEnum<T> {
        /**
         * 获取Name
         *
         * @return
         */
        T getName();
    }
}
