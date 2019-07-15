package com.liwy.commons.lang;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>常用Object工具类</p>
 *
 * <ul>
 * <li><b>defaultIfNull</b>             - 如果为null，返回默认值</li>
 * <li><b>compare</b>                   - 比较对象大小</li>
 * <li><b>min</b>                       - 获取最小对象</li>
 * <li><b>max</b>                       - 获取最大对象</li>
 * <li><b>mapToBean</b>                 - Map转Bean类型</li>
 * <li><b>beanToMap</b>                 - Bean对象转Map</li>
 * <li><b>getSimpleProperty</b>         - 通过Get方法获取简单bean的属性值</li>
 * <li><b>setSimpleProperty</b>         - 通过Set方法设置简单bean的属性值</li>
 * <li><b>getPropertyDescriptors</b>    - 获取bean的所有有效属性描述</li>
 * <li><b>getPropertyDescriptor</b>     - 获取bean的指定有效属性描述</li>
 * </ul>
 *
 * @author liwy
 * @version v1.0.2
 */
public class ObjectUtils {

    /**
     * <p>如果为null，返回默认值</p>
     *
     * <pre>
     * ObjectUtils.defaultIfNull(null, null)      = null
     * ObjectUtils.defaultIfNull(null, "")        = ""
     * ObjectUtils.defaultIfNull(null, "zz")      = "zz"
     * ObjectUtils.defaultIfNull("abc", *)        = "abc"
     * ObjectUtils.defaultIfNull(Boolean.TRUE, *) = Boolean.TRUE
     * </pre>
     *
     * @param <T>          the type of the object
     * @param object       the {@code Object} to test, may be {@code null}
     * @param defaultValue the default value to return, may be {@code null}
     * @return {@code object} if it is not {@code null}, defaultValue otherwise
     */
    public static <T> T defaultIfNull(final T object, final T defaultValue) {
        return object != null ? object : defaultValue;
    }

    /**
     * 比较对象大小
     *
     * @param c1
     * @param c2
     * @param <T>
     * @return
     */
    public static <T extends Comparable<? super T>> int compare(final T c1, final T c2) {
        return compare(c1, c2, false);
    }

    /**
     * 比较对象大小
     *
     * @param c1
     * @param c2
     * @param nullGreater null是否为最大
     * @param <T>
     * @return
     */
    public static <T extends Comparable<? super T>> int compare(final T c1, final T c2, final boolean nullGreater) {
        if (c1 == c2) {
            return 0;
        } else if (c1 == null) {
            return nullGreater ? 1 : -1;
        } else if (c2 == null) {
            return nullGreater ? -1 : 1;
        }
        return c1.compareTo(c2);
    }

    /**
     * 获取最小的对象
     *
     * @param values
     * @param <T>
     * @return
     */
    public static <T extends Comparable<? super T>> T min(final T... values) {
        T result = null;
        if (values != null) {
            for (final T value : values) {
                if (compare(value, result, true) < 0) {
                    result = value;
                }
            }
        }
        return result;
    }

    /**
     * 获取最大的对象
     *
     * @param values
     * @param <T>
     * @return
     */
    public static <T extends Comparable<? super T>> T max(final T... values) {
        T result = null;
        if (values != null) {
            for (final T value : values) {
                if (compare(value, result, false) > 0) {
                    result = value;
                }
            }
        }
        return result;
    }

    /**
     * Map转Bean类型
     *
     * @param bean
     * @param properties
     * @return void
     */
    public static void mapToBean(final Object bean, final Map<String, ? extends Object> properties)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        if ((bean == null) || (properties == null)) {
            return;
        }
        for (final Map.Entry<String, ? extends Object> entry : properties.entrySet()) {
            final String name = entry.getKey();
            if (name == null) {
                continue;
            }

            setSimpleProperty(bean, name, entry.getValue(), true);
        }

    }

    /**
     * 对象转Map
     *
     * @param bean
     * @return java.util.Map<java.lang.String, java.lang.Object>
     */
    public static Map<String, Object> beanToMap(Object bean) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        if (bean == null) {
            return (new java.util.HashMap());
        }
        Map<String, Object> beanMap = new HashMap();

        PropertyDescriptor[] descriptors = getPropertyDescriptors(bean);
        for (PropertyDescriptor descriptor : descriptors) {
            String name = descriptor.getName();
            Method readMethod = descriptor.getReadMethod();
            if (readMethod != null) {
                beanMap.put(name, readMethod.invoke(bean));
            }
        }
        return beanMap;
    }

    /**
     * 通过Get方法获取属性的值
     *
     * @param bean
     * @param name
     * @return java.lang.Object
     */
    public static Object getSimpleProperty(final Object bean, final String name)
            throws IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {

        if (bean == null) {
            throw new IllegalArgumentException("No bean specified");
        }
        if (name == null) {
            throw new IllegalArgumentException("No name specified for bean class '" +
                    bean.getClass() + "'");
        }

        // Retrieve the property getter method for the specified property
        final PropertyDescriptor descriptor =
                getPropertyDescriptor(bean, name);
        if (descriptor == null) {
            throw new NoSuchMethodException("Unknown property '" +
                    name + "' on class '" + bean.getClass() + "'");
        }
        final Method readMethod = descriptor.getReadMethod();
        if (readMethod == null) {
            throw new NoSuchMethodException("Property '" + name +
                    "' has no getter method in class '" + bean.getClass() + "'");
        }

        // Call the property getter and return the value
        final Object value = readMethod.invoke(bean);
        return (value);
    }

    /**
     * 通过Set方法设置属性的值
     *
     * @param bean
     * @param name
     * @param value
     * @param ignore 是否忽略不合法的属性
     * @return void
     */
    public static void setSimpleProperty(final Object bean, String name, final Object value, boolean ignore)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        if (bean == null) {
            throw new IllegalArgumentException("No bean specified");
        }
        if (name == null) {
            throw new IllegalArgumentException("No name specified for bean class '" +
                    bean.getClass() + "'");
        }

        final PropertyDescriptor descriptor =
                getPropertyDescriptor(bean, name);
        if (descriptor != null) {
            final Method writeMethod = descriptor.getWriteMethod();
            if (writeMethod != null) {
                final Object[] values = new Object[1];
                values[0] = value;
                writeMethod.invoke(bean, values);
            } else if (!ignore) {
                throw new NoSuchMethodException("Property '" + name +
                        "' has no setter method in class '" + bean.getClass() + "'");
            }
        } else if (!ignore) {
            throw new NoSuchMethodException("Unknown property '" +
                    name + "' on class '" + bean.getClass() + "'");
        }
    }

    /**
     * 获取bean的所有有效属性描述
     *
     * @param bean
     * @return java.beans.PropertyDescriptor[]
     */
    public static PropertyDescriptor[] getPropertyDescriptors(Object bean) {
        BeanInfo beanInfo = null;
        try {
            beanInfo = Introspector.getBeanInfo(bean.getClass());
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
        if (descriptors == null) {
            descriptors = new PropertyDescriptor[0];
        }
        return descriptors;
    }

    /**
     * 获取bean的指定有效属性描述
     *
     * @param bean
     * @param name
     * @return java.beans.PropertyDescriptor
     */
    public static PropertyDescriptor getPropertyDescriptor(Object bean, String name) {
        PropertyDescriptor[] propertyDescriptors = getPropertyDescriptors(bean);
        for (final PropertyDescriptor pd : propertyDescriptors) {
            if (name.equals(pd.getName())) {
                return pd;
            }
        }
        return null;
    }

}


