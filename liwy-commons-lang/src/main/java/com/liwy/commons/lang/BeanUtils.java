package com.liwy.commons.lang;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * <b>名称：</b> <br/>
 * <b>作者：</b> wenyao02.li <br/>
 * <b>创建时间：</b> 2018/6/7 16:42 <br/>
 * <b>版本：</b> V1.0 <br/>
 */
public class BeanUtils {
    /**
     * Map转换为Bean对象
     *
     * @param <T> Bean类型
     * @param map {@link Map}
     * @param beanClass Bean Class
     * @param isIgnoreError 是否忽略注入错误
     * @return Bean
     */
    public static <T> T mapToBean(Map<?, ?> map, Class<T> beanClass, boolean isIgnoreError) {
        return fillBeanWithMap(map, ReflectUtil.newInstance(beanClass), isIgnoreError);
    }

    /**
     * Map转换为Bean对象<br>
     * 忽略大小写
     *
     * @param <T> Bean类型
     * @param map Map
     * @param beanClass Bean Class
     * @param isIgnoreError 是否忽略注入错误
     * @return Bean
     */
    public static <T> T mapToBeanIgnoreCase(Map<?, ?> map, Class<T> beanClass, boolean isIgnoreError) {
        return fillBeanWithMapIgnoreCase(map, ReflectUtil.newInstance(beanClass), isIgnoreError);
    }

    /**
     * Map转换为Bean对象
     *
     * @param <T> Bean类型
     * @param map {@link Map}
     * @param beanClass Bean Class
     * @param copyOptions 转Bean选项
     * @return Bean
     */
    public static <T> T mapToBean(Map<?, ?> map, Class<T> beanClass, CopyOptions copyOptions) {
        return fillBeanWithMap(map, ReflectUtil.newInstance(beanClass), copyOptions);
    }



    /**
     * 对象转Map，不进行驼峰转下划线，不忽略值为空的字段
     *
     * @param bean bean对象
     * @return Map
     */
    public static Map<String, Object> beanToMap(Object bean) {
        return beanToMap(bean, false, false);
    }

    /**
     * 对象转Map
     *
     * @param bean bean对象
     * @param isToUnderlineCase 是否转换为下划线模式
     * @param ignoreNullValue 是否忽略值为空的字段
     * @return Map
     */
    public static Map<String, Object> beanToMap(Object bean, boolean isToUnderlineCase, boolean ignoreNullValue) {
        return beanToMap(bean, new HashMap<String, Object>(), isToUnderlineCase, ignoreNullValue);
    }

    /**
     * 对象转Map<br>
     *
     * <pre>
     * 1. 字段筛选，可以去除不需要的字段
     * 2. 字段变换，例如实现驼峰转下划线
     * 3. 自定义字段前缀或后缀等等
     * </pre>
     *
     * @param bean bean对象
     * @param targetMap 目标的Map
     * @param isToUnderlineCase
     * @param ignoreNullValue 是否忽略值为空的字段
     * @return Map
     */
    public static Map<String, Object> beanToMap(Object bean, Map<String, Object> targetMap, boolean isToUnderlineCase, boolean ignoreNullValue) {
        if (bean == null) {
            return null;
        }

        final Collection<PropDesc> props = BeanUtil.getBeanDesc(bean.getClass()).getProps();

        String key;
        Method getter;
        Object value;
        for (PropDesc prop : props) {
            key = prop.getFieldName();
            // 过滤class属性
            // 得到property对应的getter方法
            getter = prop.getGetter();
            if (null != getter) {
                // 只读取有getter方法的属性
                try {
                    value = getter.invoke(bean);
                } catch (Exception ignore) {
                    continue;
                }
                if (false == ignoreNullValue || (null != value && false == value.equals(bean))) {
                    key = isToUnderlineCase ? StringUtils.toUnderlineCase(key) : key;
                    if (null != key) {
                        targetMap.put(key, value);
                    }
                }
            }
        }
        return targetMap;
    }
}
