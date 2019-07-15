package com.liwy.commons.lang.io;

import com.liwy.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runners.JUnit4;

import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * <p>路径工具类</p>
 *
 * @author liwy
 */
public class PathUtilsTest {

    /**
     * 获得参数clazz所在的Jar文件的绝对路径
     */
    @Test
    public void testGetJarPath() {
        assertThat(PathUtils.getJarPath(JUnit4.class), endsWith("/junit/junit/4.12/junit-4.12.jar"));
        assertThat(PathUtils.getJarPath(StringUtils.class), endsWith("/liwy-commons/liwy-commons-lang/target/classes/"));
    }

    /**
     * 获取类加载路径
     */
    @Test
    public void testGetClassPath() {
        assertThat(PathUtils.getClassPath(), endsWith("/liwy-commons/liwy-commons-lang/target/test-classes/"));
        assertThat(PathUtils.getClassPath(this.getClass()), endsWith("/liwy-commons/liwy-commons-lang/target/test-classes/"));
        assertThat(PathUtils.getClassPath(PathUtils.class), endsWith("/liwy-commons/liwy-commons-lang/target/test-classes/"));
    }

    /**
     * 获得资源相对路径对应的URL
     */
    @Test
    public void testGetResource() {
        assertThat(PathUtils.getResource("test.properties").getPath(),
                endsWith("/liwy-commons/liwy-commons-lang/target/test-classes/test.properties"));
        assertThat(PathUtils.getResource("/test.properties", this.getClass()).getPath(),
                endsWith("/liwy-commons/liwy-commons-lang/target/test-classes/test.properties"));
        assertThat(PathUtils.getResource("/test.properties", PathUtils.class).getPath(),
                endsWith("/liwy-commons/liwy-commons-lang/target/test-classes/test.properties"));
    }

    /**
     * 获取指定类的路径
     */
    @Test
    public void testGetObjectPath() {
        System.out.println(PathUtils.getObjectPath(this.getClass()));
        System.out.println(PathUtils.getObjectPath(StringUtils.class));
    }

    /**
     * 获取项目路径
     */
    @Test
    public void testGetProjectPath() {
        System.out.println(PathUtils.getProjectPath());
    }
}
