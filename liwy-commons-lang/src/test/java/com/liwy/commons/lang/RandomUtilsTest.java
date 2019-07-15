package com.liwy.commons.lang;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * <p>随机工具的测试类</p>
 *
 * @author liwy
 */
public class RandomUtilsTest {

    /**
     * 返回随机的布尔值
     */
    @Test
    public void testNextBoolean() {
        assertThat(RandomUtils.nextBoolean(), anyOf(is(true), is(false)));
    }

}

