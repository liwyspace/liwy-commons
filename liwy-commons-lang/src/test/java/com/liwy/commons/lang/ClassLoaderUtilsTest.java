package com.liwy.commons.lang;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * <p>类加载工具测试类</p>
 *
 * @author liwy
 */
public class ClassLoaderUtilsTest {
    /**
     * 获取当前线程的ClassLoader
     */
    @Test
    public void testGetContextClassLoader() {
        ClassLoader contextClassLoader = ClassLoaderUtils.getContextClassLoader();
        assertThat(contextClassLoader.getClass().getName(), is("sun.misc.Launcher$AppClassLoader"));
    }

    /**
     * 获取ClassLoader
     */
    @Test
    public void testGetClassLoader() {
        ClassLoader classLoader = ClassLoaderUtils.getClassLoader();
        assertThat(classLoader.getClass().getName(), is("sun.misc.Launcher$AppClassLoader"));
    }
}
