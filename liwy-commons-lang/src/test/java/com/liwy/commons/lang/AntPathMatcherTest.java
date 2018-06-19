package com.liwy.commons.lang;

import org.junit.Assert;
import org.junit.Test;

/**
 * <b>模块：</b> <br/>
 * <b>名称：</b> <br/>
 * <b>描述：</b> <br/>
 * <b>作者：</b> wenyao02.li <br/>
 * <b>创建时间：</b> 2018/6/14 14:32 <br/>
 * <b>版本：</b> V1.0 <br/>
 */
public class AntPathMatcherTest {
    @Test
    public void testMatch() {
        AntPathMatcher matcher = new AntPathMatcher();
        Assert.assertTrue(matcher.isMatch("src/main/java/com/liwy","src/main/java/com/liwy"));
        Assert.assertTrue(matcher.isMatch("src/main/**","src/main/java/com/liwy"));
        Assert.assertTrue(matcher.isMatch("src/main/**/","src/main/java/com/liwy"));
        Assert.assertTrue(matcher.isMatch("src/main/**","src/main/java/com/liwy/"));
        Assert.assertTrue(matcher.isMatch("src/main/java/com/liwy/**","src/main/java/com/liwy"));
        Assert.assertTrue(matcher.isMatch("**/com/liwy","src/main/java/com/liwy"));
        Assert.assertTrue(matcher.isMatch("src/main/**/com/liwy","src/main/java/com/liwy"));
        Assert.assertTrue(matcher.isMatch("src/**/liwy","src/main/java/com/liwy"));
        Assert.assertTrue(matcher.isMatch("src/**/java/**/liwy","src/main/java/com/liwy"));
        Assert.assertTrue(matcher.isMatch("src/**/ja*/**/liwy","src/main/java/com/liwy"));


        Assert.assertFalse(matcher.isMatch("/src/main/java/com/liwy","src/main/java/com/liwy"));
        Assert.assertFalse(matcher.isMatch("src/main/java/com/liwy/","src/main/java/com/liwy"));
        Assert.assertFalse(matcher.isMatch("src/main/java/com/liwy/*","src/main/java/com/liwy"));
        Assert.assertFalse(matcher.isMatch("/**/com/liwy","src/main/java/com/liwy"));

    }
}
