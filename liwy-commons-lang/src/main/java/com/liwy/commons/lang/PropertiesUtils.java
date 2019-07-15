package com.liwy.commons.lang;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.Properties;

/**
 * <p>常用Properties工具类</p>
 *
 * <ul>
 * <li><b>loadFromFile</b>      - 从文件路径加载properties</li>
 * <li><b>loadFromString</b>    - 从字符串内容加载Properties</li>
 * <li><b>writeToFile</b>       - Properties写入文件</li>
 * </ul>
 *
 * @author liwy
 * @version v1.0.2
 */
public class PropertiesUtils {
    /**
     * 从文件路径加载properties. 默认使用utf-8编码解析文件
     *
     * @param generalPath
     * @return
     */
    public static Properties loadFromFile(String generalPath) {
        Properties p = new Properties();
        try {
            Reader reader = new InputStreamReader(new FileInputStream(generalPath), "UTF-8");
            p.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return p;
    }

    /**
     * 从字符串内容加载Properties
     *
     * @param content
     * @return
     */
    public static Properties loadFromString(String content) {
        Properties p = new Properties();
        try {
            Reader reader = new StringReader(content);
            p.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return p;
    }

    /**
     * 写入文件
     *
     * @param p
     * @param file
     * @return void
     */
    public static void writeToFile(final Properties p, final String file) {
        try {
            p.store(new FileOutputStream(file), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
