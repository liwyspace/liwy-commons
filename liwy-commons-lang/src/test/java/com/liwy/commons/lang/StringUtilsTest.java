package com.liwy.commons.lang;

import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * StringUtils的测试类
 *
 * @author liwy
 */
public class StringUtilsTest {
    @Test
    public void testIsEmpty() {
        assertThat(StringUtils.isEmpty(""), is(true));
        assertThat(StringUtils.isEmpty(null), is(true));
        assertThat(StringUtils.isEmpty(" "), is(false));
        assertThat(StringUtils.isEmpty("\n"), is(false));
        assertThat(StringUtils.isEmpty("\t"), is(false));
        assertThat(StringUtils.isEmpty("null"), is(false));
        assertThat(StringUtils.isEmpty("liwy"), is(false));
        assertThat(StringUtils.isEmpty("  liwy  "), is(false));
    }

    @Test
    public void testIsBlank() {
        assertThat(StringUtils.isBlank(""), is(true));
        assertThat(StringUtils.isBlank(" "), is(true));
        assertThat(StringUtils.isBlank(null), is(true));
        assertThat(StringUtils.isBlank("\n"), is(true));
        assertThat(StringUtils.isBlank("\t"), is(true));
        assertThat(StringUtils.isBlank("liwy"), is(false));
        assertThat(StringUtils.isBlank("  liwy  "), is(false));
    }

    @Test
    public void testTrim() {
        assertThat(StringUtils.trim(""), is(""));
        assertThat(StringUtils.trim(" "), is(""));
        assertThat(StringUtils.trim(null), nullValue());
        assertThat(StringUtils.trim("\n"), is(""));
        assertThat(StringUtils.trim("\t"), is(""));
        assertThat(StringUtils.trim("liwy"), is("liwy"));
        assertThat(StringUtils.trim("  liwy  "), is("liwy"));
    }

    @Test
    public void testUnicodeEscaped() {
        assertThat(StringUtils.unicodeEscaped(' '), is("\\u0020"));
        assertThat(StringUtils.unicodeEscaped('a'), is("\\u0061"));
        assertThat(StringUtils.unicodeEscaped('Z'), is("\\u005a"));
        assertThat(StringUtils.unicodeEscaped('0'), is("\\u0030"));
        assertThat(StringUtils.unicodeEscaped('9'), is("\\u0039"));
        assertThat(StringUtils.unicodeEscaped('@'), is("\\u0040"));
        assertThat(StringUtils.unicodeEscaped('姚'), is("\\u59da"));
    }


    @Test
    public void testStr2unicode() {
        assertThat(StringUtils.str2unicode("   "), is("\\u0020\\u0020\\u0020"));
        assertThat(StringUtils.str2unicode("liwy"), is("\\u006c\\u0069\\u0077\\u0079"));
        assertThat(StringUtils.str2unicode("李文姚"), is("\\u674e\\u6587\\u59da"));
    }

    @Test
    public void testUnicode2str() {
        assertThat(StringUtils.unicode2str("\\u674e\\u6587\\u59da"), is("李文姚"));
        assertThat(StringUtils.unicode2str("\\u006c\\u0069\\u0077\\u0079"), is("liwy"));
        assertThat(StringUtils.unicode2str("\u006c\u0069\u0077\u0079"), is("liwy"));
        assertThat(StringUtils.unicode2str("liwy"), is("liwy"));
    }

    @Test
    public void testChangeCharset() throws UnsupportedEncodingException {
        System.out.println(System.getProperty("file.encoding"));//当前文件的编码方式
        String liwy = "hello! 李文姚！";
        assertThat(StringUtils.changeCharset(liwy, "UTF-8", "UTF-8"), is("hello! 李文姚！"));
        String gbkLiwy = StringUtils.changeCharset(liwy, "UTF-8", "GBK");
        assertThat(StringUtils.changeCharset(gbkLiwy, "GBK", "UTF-8"), is("hello! 李文姚！"));
        String isoLiwy = StringUtils.changeCharset(liwy, "UTF-8", "ISO-8859-1");
        assertThat(StringUtils.changeCharset(isoLiwy, "ISO-8859-1", "UTF-8"), is("hello! 李文姚！"));
    }

    @Test
    public void testFull2Half() {
        for (int i = 65281; i < 65375; i++) {
            System.out.println((char) i + "\t" + (char) (i - 65248));
        }

        assertThat(StringUtils.full2Half(""), is(""));
        assertThat(StringUtils.full2Half("   "), is("   "));
        assertThat(StringUtils.full2Half("\t"), is("\t"));
        assertThat(StringUtils.full2Half(null), is(""));
        assertThat(StringUtils.full2Half("12343，liwy"), is("12343,liwy"));
        assertThat(StringUtils.full2Half("12１２＄liwy"), is("1212$liwy"));
        assertThat(StringUtils.full2Half("李文姚ｌｉｗｙ"), is("李文姚liwy"));
    }

    @Test
    public void testHalf2Full() {
        assertThat(StringUtils.half2Full(""), is(""));
        assertThat(StringUtils.half2Full("   "), is("   "));
        assertThat(StringUtils.half2Full("\t"), is("\t"));
        assertThat(StringUtils.half2Full(null), is(""));
        assertThat(StringUtils.half2Full("12343，liwy"), is("１２３４３，ｌｉｗｙ"));
        assertThat(StringUtils.half2Full("12１２＄liwy"), is("１２１２＄ｌｉｗｙ"));
        assertThat(StringUtils.half2Full("李文姚ｌｉｗｙ"), is("李文姚ｌｉｗｙ"));
    }
}
