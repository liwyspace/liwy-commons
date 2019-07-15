package com.liwy.commons.lang;

/**
 * <p>类加载工具类</p>
 *
 * <ul>
 * <li><b>getContextClassLoader</b>     - 获取当前线程的ClassLoader</li>
 * <li><b>getClassLoader</b>            - 获取ClassLoader</li>
 * </ul>
 *
 * @author liwy
 * @version v1.0.2
 */
public class ClassLoaderUtils {
    /**
     * <p>获取当前线程的{@link ClassLoader}</p>
     *
     * @return 当前线程的class loader
     * @see Thread#getContextClassLoader()
     */
    public static ClassLoader getContextClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * <p>获取{@link ClassLoader}</p>
     *
     * <p>获取顺序如下：</p>
     * <pre>
     * 1、获取当前线程的ContextClassLoader
     * 2、获取{@link ClassLoaderUtils}类对应的ClassLoader
     * 3、获取系统ClassLoader（{@link ClassLoader#getSystemClassLoader()}）
     * </pre>
     *
     * @return 类加载器
     */
    public static ClassLoader getClassLoader() {
        ClassLoader classLoader = getContextClassLoader();
        if (classLoader == null) {
            classLoader = ClassLoaderUtils.class.getClassLoader();
            if (null == classLoader) {
                classLoader = ClassLoader.getSystemClassLoader();
            }
        }
        return classLoader;
    }
}
