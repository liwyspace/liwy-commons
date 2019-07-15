package com.liwy.commons.lang;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * <p>Ant风格Path匹配模式工具的测试类</p>
 *
 * @author liwy
 */
public class AntPathMatcherTest {

    /**
     * 测试是否匹配
     */
    @Test
    public void testIsMatch() {
        assertThat(AntPathMatcher.isMatch("src/main/java/com/liwy", "src/main/java/com/liwy"), is(true));
        assertThat(AntPathMatcher.isMatch("src/main/**", "src/main/java/com/liwy"), is(true));
        assertThat(AntPathMatcher.isMatch("src/main/**/", "src/main/java/com/liwy"), is(true));
        assertThat(AntPathMatcher.isMatch("src/main/**", "src/main/java/com/liwy/"), is(true));
        assertThat(AntPathMatcher.isMatch("src/main/java/com/liwy/**", "src/main/java/com/liwy"), is(true));
        assertThat(AntPathMatcher.isMatch("**/com/liwy", "src/main/java/com/liwy"), is(true));
        assertThat(AntPathMatcher.isMatch("src/main/**/com/liwy", "src/main/java/com/liwy"), is(true));
        assertThat(AntPathMatcher.isMatch("src/**/liwy", "src/main/java/com/liwy"), is(true));
        assertThat(AntPathMatcher.isMatch("src/**/java/**/liwy", "src/main/java/com/liwy"), is(true));
        assertThat(AntPathMatcher.isMatch("src/**/ja*/**/liwy", "src/main/java/com/liwy"), is(true));

        assertThat(AntPathMatcher.isMatch("/src/main/java/com/liwy", "src/main/java/com/liwy"), is(false));
        assertThat(AntPathMatcher.isMatch("src/main/java/com/liwy/", "src/main/java/com/liwy"), is(false));
        assertThat(AntPathMatcher.isMatch("src/main/java/com/liwy/*", "src/main/java/com/liwy"), is(false));
        assertThat(AntPathMatcher.isMatch("/**/com/liwy", "src/main/java/com/liwy"), is(false));

    }
}
