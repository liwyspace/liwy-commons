package com.liwy.commons.lang;

import com.liwy.commons.lang.io.PathUtils;
import org.junit.Test;

import java.util.Properties;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * <p>异常工具的测试类</p>
 *
 * @author liwy
 */
public class PropertiesUtilsTest {

    /**
     * 从文件路径加载properties. 默认使用utf-8编码解析文件
     */
    @Test
    public void testLoadFromFile() {
        Properties properties = PropertiesUtils.loadFromFile(PathUtils.getResource("test.properties").getPath());
        assertThat(properties.getProperty("liwy.name"), is("liwenyao"));
        assertThat(properties.getProperty("liwy.age"), is("22"));
        assertThat(properties.getProperty("liwy.web"), is("www.oscafe.net"));
    }

    /**
     * 从字符串内容加载Properties
     */
    @Test
    public void testLoadFromString() {
        Properties properties = PropertiesUtils.loadFromString("liwy.name=liwenyao\nliwy.age=22\nliwy.web=www.oscafe.net");
        assertThat(properties.getProperty("liwy.name"), is("liwenyao"));
        assertThat(properties.getProperty("liwy.age"), is("22"));
        assertThat(properties.getProperty("liwy.web"), is("www.oscafe.net"));
    }

    /**
     * 写入文件
     */
    @Test
    public void testWriteToFile() {
        Properties properties = new Properties();
        properties.setProperty("copy.liwy.name", "liwy");
        properties.setProperty("copy.liwy.age", "23");
        PropertiesUtils.writeToFile(properties, PathUtils.getClassPath() + "copy-test.properties");
    }

}

