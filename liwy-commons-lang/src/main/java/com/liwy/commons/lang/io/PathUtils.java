package com.liwy.commons.lang.io;

import com.liwy.commons.lang.ClassLoaderUtils;

import java.net.URL;

/**
 * <p>路径工具类</p>
 *
 * <ul>
 * <li><b>getJarPath</b>        - 获得参数clazz所在的Jar文件的绝对路径</li>
 * <li><b>getClassPath</b>      - 获取到classes目录</li>
 * <li><b>getResource</b>       - 获得资源相对路径对应的URL</li>
 * <li><b>getObjectPath</b>     - 获取指定类的路径</li>
 * <li><b>getProjectPath</b>    - 获取到项目的路径</li>
 * <li><b>getWEB_INF</b>        - 获取 web-inf目录</li>
 * <li><b>getRootPath</b>       - 获取 root目录</li>
 * </ul>
 *
 * @author liwy
 * @version v1.0.2
 */
public class PathUtils {

    /**
     * 获得参数clazz所在的Jar文件的绝对路径
     */
    public static String getJarPath(Class<?> clazz) {
        return clazz.getProtectionDomain().getCodeSource().getLocation().getFile();
    }

    /**
     * 获取到classes目录
     *
     * @return path
     */
    public static String getClassPath() {
        return ClassLoaderUtils.getClassLoader().getResource("").getPath();
    }

    /**
     * 获取到classes目录
     *
     * @return path
     */
    public static String getClassPath(Class<?> clazz) {
        return clazz.getResource("/").getPath();
    }

    /**
     * 获得资源相对路径对应的URL
     *
     * @param resource
     * @return
     */
    public static URL getResource(String resource) {
        return ClassLoaderUtils.getClassLoader().getResource(resource);
    }

    /**
     * 获得资源相对路径对应的URL
     *
     * @param resource
     * @param clazz
     * @return
     */
    public static URL getResource(String resource, Class<?> clazz) {
        return clazz.getResource(resource);
    }

    /**
     * 获取指定类的路径
     *
     * @param clazz
     * @return path
     */
    public static String getObjectPath(Class<?> clazz) {
        return clazz.getResource(".").getPath();
    }

    /**
     * 获取到项目的路径
     *
     * @return path
     */
    public static String getProjectPath() {
        return System.getProperty("user.dir");
    }

    /**
     * 获取 web-inf目录
     *
     * @return path
     */
    public static String getWEB_INF() {
        return getClassPath().replace("classes/", "");
    }

    /**
     * 获取 web-inf目录
     *
     * @return path
     */
    public static String getWEB_INF(Class<?> clazz) {
        return getClassPath(clazz).replace("classes/", "");
    }

    /**
     * 获取 root目录
     *
     * @return path
     */
    public static String getRootPath() {
        return getWEB_INF().replace("WEB-INF/", "");
    }

    /**
     * 获取 root目录
     *
     * @return path
     */
    public static String getRootPath(Class<?> clazz) {
        return getWEB_INF(clazz).replace("WEB-INF/", "");
    }
}
