package com.liwy.commons.lang;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>常用的字符串操作工具类</p>
 *
 * <p>常用字符串常量</p>
 * <ul>
 * <li><b>EMPTY</b>         - {@code ""}</li>
 * <li><b>SPACE</b>         - {@code " "}</li>
 * <li><b>UNDERLINE</b>     - {@code "_"}</li>
 * <li><b>DOT</b>           - {@code "."}</li>
 * <li><b>COMMA</b>         - {@code ","}</li>
 * <li><b>DELIM_START</b>   - {@code "{"}</li>
 * <li><b>DELIM_END</b>     - {@code "}"}</li>
 * <li><b>BRACKET_START</b> - {@code "["}</li>
 * <li><b>BRACKET_END</b>   - {@code "]"}</li>
 * <li><b>NULL</b>          - {@code "null"}</li>
 * </ul>
 *
 * <p>常用字符串操作</p>
 * <ul>
 * <li><b>isEmpty</b>               - 检查字符串是否为空</li>
 * <li><b>isNotEmpty</b>            - 检查字符串是否为非空</li>
 * <li><b>isBlank</b>               - 检查字符串是否为空白</li>
 * <li><b>isNotBlank</b>            - 检查字符串是否为非空白</li>
 * <li><b>isAlpha</b>               - 检查字符串是否为纯字母</li>
 * <li><b>isNumeric</b>             - 检查字符串是否为纯数字</li>
 * <li><b>isAlphanumeric</b>        - 检查字符串是否为纯字母与数字</li>
 * <li><b>isAlphanumericUnderline</b>        - 检查字符串是否为纯字母与数字与下划线</li>
 * <li><b>isMixedCase</b>                    - 检查是否同时包含大写与小写字母</li>
 * <li><b>isContains</b>            - 检查字符串是否包含某字符</li>
 * <li><b>trim</b>                  - 删除前后空白</li>
 * <li><b>toChar</b>                - 字符串转char</li>
 * <li><b>unicodeEscaped</b>        - 获取字符的unicode编码</li>
 * <li><b>str2unicode</b>           - 字符串转Unicode串</li>
 * <li><b>unicode2str</b>           - Unicode串转字符串</li>
 * <li><b>upperFirst</b>            - 首字母大写</li>
 * <li><b>lowerFirst</b>            - 首字母小写</li>
 * <li><b>changeCharset</b>         - 改变字符串的字符集</li>
 * <li><b>half2Full</b>             - 改变字符串中半角字符为全角字符</li>
 * <li><b>full2Half</b>             - 改变字符串中全角字符为半角字符</li>
 * <li><b>toPrintString</b>         - 将对象转换为可读的字符串</li>
 * <li><b>toUnderlineCase</b>       - 将驼峰式命名的字符串转换为下划线方式</li>
 * <li><b>toSymbolCase</b>          - 将驼峰式命名的字符串转换为使用符号连接方式</li>
 * <li><b>toCamelCase</b>           - 将下划线方式命名的字符串转换为驼峰式</li>
 * <li><b>abbreviate</b>            - 使用指定字符替换字符串超长部分</li>
 * </ul>
 *
 * @author liwy
 * @version v1.0.1
 */
public class StringUtils {
    /**
     * 常用字符串常量
     */
    public static final String EMPTY = "";
    public static final String SPACE = " ";
    public static final String UNDERLINE = "_";
    public static final String DOT = ".";
    public static final String COMMA = ",";
    public static final String DELIM_START = "{";
    public static final String DELIM_END = "}";
    public static final String BRACKET_START = "[";
    public static final String BRACKET_END = "]";
    public static final String NULL = "null";
    public static final String LF = "\n";
    public static final String CR = "\r";
    public static final String OBJ = "{}";

    public static final char C_SPACE = ' ';
    public static final char C_DOT = '.';
    public static final char C_SLASH = '/';
    public static final char C_BACKSLASH = '\\';
    public static final char C_CR = '\r';
    public static final char C_LF = '\n';
    public static final char C_UNDERLINE = '_';
    public static final char C_DASHED = '-';
    public static final char C_COMMA = ',';
    public static final char C_DELIM_START = '{';
    public static final char C_DELIM_END = '}';
    public static final char C_BRACKET_START = '[';
    public static final char C_BRACKET_END = ']';
    public static final char C_COLON = ':';
    public static final char C_DOUBLE_QUOTES = '"';
    public static final char C_SINGLE_QUOTE = '\'';
    public static final char C_AMP = '&';


    /**
     * 为发现位置
     */
    private static final int INDEX_NOT_FOUND = -1;

    /**
     * Unicode通配符
     */
    private static final String PATTERN_UNICODE = "(\\\\u([0-9a-fA-F]{4}))";

    /**
     * Unicode字典
     */
    private static final char[] HEX_DIGITS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * <p>校验是否为空字符串 ("") 或 null.</p>
     *
     * <pre>
     * StringUtil.isEmpty(null)      = true
     * StringUtil.isEmpty("")        = true
     * StringUtil.isEmpty(" ")       = false
     * StringUtil.isEmpty("liwy")     = false
     * StringUtil.isEmpty("  liwy  ") = false
     * StringUtil.isEmpty("\n") = false
     * StringUtil.isEmpty("\t") = false
     * </pre>
     *
     * @param cs 要检查的字符序列，可能是null
     * @return {@code true} 如果字符序列为空或空
     */
    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    /**
     * <p>字符串非空</p>
     *
     * <pre>
     * StringUtils.isNotEmpty(null)      = false
     * StringUtils.isNotEmpty("")        = false
     * StringUtils.isNotEmpty(" ")       = true
     * StringUtils.isNotEmpty("bob")     = true
     * StringUtils.isNotEmpty("  bob  ") = true
     * </pre>
     *
     * @param cs
     * @return
     */
    public static boolean isNotEmpty(final CharSequence cs) {
        return !isEmpty(cs);
    }

    /**
     * <p>检查字符串是否为  空 ("") 或 null或空白.</p>
     *
     * <pre>
     * StringUtil.isBlank(null)      = true
     * StringUtil.isBlank("")        = true
     * StringUtil.isBlank(" ")       = true
     * StringUtil.isBlank("liwy")     = false
     * StringUtil.isBlank("  liwy  ") = false
     * StringUtil.isBlank("\n")       = true
     * StringUtil.isBlank("\t")       = true
     * </pre>
     *
     * @param cs 要检查的字符序列，可能是null
     * @return {@code true} 如果字符序列为空白
     */
    public static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(cs.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>字符串非空白</p>
     *
     * <pre>
     * StringUtils.isNotBlank(null)      = false
     * StringUtils.isNotBlank("")        = false
     * StringUtils.isNotBlank(" ")       = false
     * StringUtils.isNotBlank("bob")     = true
     * StringUtils.isNotBlank("  bob  ") = true
     * </pre>
     *
     * @param cs
     * @return
     */
    public static boolean isNotBlank(final CharSequence cs) {
        return !isBlank(cs);
    }

    /**
     * <p>是否只包含字母</p>
     *
     * <pre>
     * StringUtils.isAlpha(null)   = false
     * StringUtils.isAlpha("")     = false
     * StringUtils.isAlpha("  ")   = false
     * StringUtils.isAlpha("abc")  = true
     * StringUtils.isAlpha("ab2c") = false
     * StringUtils.isAlpha("ab-c") = false
     * </pre>
     *
     * @param cs 要检查的字符序列，可能是null
     * @return {@code true} 如果字符序列全为字母
     */
    public static boolean isAlpha(final CharSequence cs) {
        if (isEmpty(cs)) {
            return false;
        }
        final int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isLetter(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>是否为纯数字</p>
     *
     * <pre>
     * StringUtils.isNumeric(null)   = false
     * StringUtils.isNumeric("")     = false
     * StringUtils.isNumeric("  ")   = false
     * StringUtils.isNumeric("123")  = true
     * StringUtils.isNumeric("\u0967\u0968\u0969")  = true
     * StringUtils.isNumeric("12 3") = false
     * StringUtils.isNumeric("ab2c") = false
     * StringUtils.isNumeric("12-3") = false
     * StringUtils.isNumeric("12.3") = false
     * StringUtils.isNumeric("-123") = false
     * StringUtils.isNumeric("+123") = false
     * </pre>
     *
     * @param cs 要检查的字符序列，可能是null
     * @return {@code true} 如果字符序列全为数字
     */
    public static boolean isNumeric(final CharSequence cs) {
        if (isEmpty(cs)) {
            return false;
        }
        final int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isDigit(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>是否只包含字母或数字</p>
     *
     * <pre>
     * StringUtils.isAlphanumeric(null)   = false
     * StringUtils.isAlphanumeric("")     = false
     * StringUtils.isAlphanumeric("  ")   = false
     * StringUtils.isAlphanumeric("abc")  = true
     * StringUtils.isAlphanumeric("ab c") = false
     * StringUtils.isAlphanumeric("ab2c") = true
     * StringUtils.isAlphanumeric("ab-c") = false
     * </pre>
     *
     * @param cs 要检查的字符序列，可能是null
     * @return {@code true} 如果字符序列为字母或数字
     */
    public static boolean isAlphanumeric(final CharSequence cs) {
        if (isEmpty(cs)) {
            return false;
        }
        final int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isLetterOrDigit(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>是否只包含字母数字和下划线</p>
     *
     * <pre>
     * StringUtils.isAlphanumericUnderline(null)   = false
     * StringUtils.isAlphanumericUnderline("")     = true
     * StringUtils.isAlphanumericUnderline("__")   = true
     * StringUtils.isAlphanumericUnderline("abc")  = true
     * StringUtils.isAlphanumericUnderline("ab_c") = true
     * StringUtils.isAlphanumericUnderline("ab2c") = true
     * StringUtils.isAlphanumericUnderline("ab-c") = false
     * </pre>
     *
     * @param cs 要检查的字符序列，可能是null
     * @return {@code true} 如果字符序列为字母或数字或下划线
     */
    public static boolean isAlphanumericUnderline(final CharSequence cs) {
        if (cs == null) {
            return false;
        }
        final int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isLetterOrDigit(cs.charAt(i)) && cs.charAt(i) != '_') {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>必须同时包含大写和小写字符</p>
     *
     * <pre>
     * StringUtils.isMixedCase(null)    = false
     * StringUtils.isMixedCase("")      = false
     * StringUtils.isMixedCase("ABC")   = false
     * StringUtils.isMixedCase("abc")   = false
     * StringUtils.isMixedCase("aBc")   = true
     * StringUtils.isMixedCase("A c")   = true
     * StringUtils.isMixedCase("A1c")   = true
     * StringUtils.isMixedCase("a/C")   = true
     * StringUtils.isMixedCase("aC\t")  = true
     * </pre>
     *
     * @param cs 要检查的字符序列，可能是null
     * @return {@code true} 必须包含多种类型字符
     */
    public static boolean isMixedCase(final CharSequence cs) {
        if (isEmpty(cs) || cs.length() == 1) {
            return false;
        }
        boolean containsUppercase = false;
        boolean containsLowercase = false;
        final int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (containsUppercase && containsLowercase) {
                return true;
            } else if (Character.isUpperCase(cs.charAt(i))) {
                containsUppercase = true;
            } else if (Character.isLowerCase(cs.charAt(i))) {
                containsLowercase = true;
            }
        }
        return containsUppercase && containsLowercase;
    }

    /**
     * 判断字符串是否包含某个字符
     *
     * @param str
     * @param searchChar
     * @return boolean
     */
    public static boolean isContains(String str, int searchChar) {
        if (isEmpty(str)) {
            return false;
        }
        return str.indexOf(searchChar) >= 0;
    }

    /**
     * 判断字符串是否包含某个字符串
     *
     * @param seq
     * @param searchSeq
     * @return boolean
     */
    public static boolean isContains(CharSequence seq, CharSequence searchSeq) {
        if (seq == null || searchSeq == null) {
            return false;
        }
        return seq.toString().indexOf(searchSeq.toString()) >= 0;
    }

    /**
     * 字符串中某个字符的位置
     *
     * @param str
     * @param searchChar
     * @return boolean
     */
    public static int indexOf(String str, int searchChar) {
        if (isEmpty(str)) {
            return INDEX_NOT_FOUND;
        }
        return str.indexOf(searchChar);
    }

    /**
     * 字符串中某个字符串的位置
     *
     * @param seq
     * @param searchSeq
     * @return boolean
     */
    public static int indexOf(CharSequence seq, CharSequence searchSeq) {
        if (seq == null || searchSeq == null) {
            return INDEX_NOT_FOUND;
        }
        return seq.toString().indexOf(searchSeq.toString());
    }

    /**
     * <p>查询字符串中某个字符的数量</p>
     *
     * <pre>
     * StringUtils.countMatches(null, *)       = 0
     * StringUtils.countMatches("", *)         = 0
     * StringUtils.countMatches("abba", 0)  = 0
     * StringUtils.countMatches("abba", 'a')   = 2
     * StringUtils.countMatches("abba", 'b')  = 2
     * StringUtils.countMatches("abba", 'x') = 0
     * </pre>
     *
     * @param str the CharSequence to check, may be null
     * @param ch  the char to count
     * @return the number of occurrences, 0 if the CharSequence is {@code null}
     */
    public static int countMatches(final CharSequence str, final char ch) {
        if (isEmpty(str)) {
            return 0;
        }
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (ch == str.charAt(i)) {
                count++;
            }
        }
        return count;
    }

    /**
     * <p>查询字符串中某个字符串的数量</p>
     *
     * <pre>
     * StringUtils.countMatches(null, *)       = 0
     * StringUtils.countMatches("", *)         = 0
     * StringUtils.countMatches("abba", null)  = 0
     * StringUtils.countMatches("abba", "")    = 0
     * StringUtils.countMatches("abba", "a")   = 2
     * StringUtils.countMatches("abba", "ab")  = 1
     * StringUtils.countMatches("abba", "xxx") = 0
     * </pre>
     *
     * @param str the CharSequence to check, may be null
     * @param sub the substring to count, may be null
     */
    public static int countMatches(final CharSequence str, final CharSequence sub) {
        if (isEmpty(str) || isEmpty(sub)) {
            return 0;
        }
        int count = 0;
        int idx = 0;
        while ((idx = str.toString().indexOf(sub.toString(), idx)) != INDEX_NOT_FOUND) {
            count++;
            idx += sub.length();
        }
        return count;
    }

    /**
     * <p>是否以某字符串结尾</p>
     *
     * <pre>
     * StringUtils.endsWith(null, null)      = true
     * StringUtils.endsWith(null, "def")     = false
     * StringUtils.endsWith("abcdef", null)  = false
     * StringUtils.endsWith("abcdef", "def") = true
     * StringUtils.endsWith("ABCDEF", "def") = false
     * StringUtils.endsWith("ABCDEF", "cde") = false
     * StringUtils.endsWith("ABCDEF", "")    = true
     * </pre>
     *
     * @param str    the CharSequence to check, may be null
     * @param suffix the suffix to find, may be null
     * @return {@code true} if the CharSequence ends with the suffix
     */
    public static boolean endsWith(final CharSequence str, final CharSequence suffix) {
        return endsWith(str, suffix, false);
    }

    /**
     * <p>校验字符串是否以某字符串结尾</p>
     *
     * @param str        the CharSequence to check, may be null
     * @param suffix     the suffix to find, may be null
     * @param ignoreCase indicates whether the compare should ignore case
     *                   (case insensitive) or not.
     * @return {@code true} if the CharSequence end with the prefix
     */
    private static boolean endsWith(final CharSequence str, final CharSequence suffix, final boolean ignoreCase) {
        if (str == null || suffix == null) {
            return str == suffix;
        }
        if (suffix.length() > str.length()) {
            return false;
        }
        final int strOffset = str.length() - suffix.length();
        return regionMatches(str, ignoreCase, strOffset, suffix, 0, suffix.length());
    }

    /**
     * <p>校验是否以某字符串开头</p>
     *
     * <pre>
     * StringUtils.startsWith(null, null)      = true
     * StringUtils.startsWith(null, "abc")     = false
     * StringUtils.startsWith("abcdef", null)  = false
     * StringUtils.startsWith("abcdef", "abc") = true
     * StringUtils.startsWith("ABCDEF", "abc") = false
     * </pre>
     *
     * @param str    the CharSequence to check, may be null
     * @param prefix the prefix to find, may be null
     * @return {@code true} if the CharSequence starts with the prefix
     */
    public static boolean startsWith(final CharSequence str, final CharSequence prefix) {
        return startsWith(str, prefix, false);
    }

    /**
     * <p>校验是否以某字符串开头</p>
     *
     * @param str        the CharSequence to check, may be null
     * @param prefix     the prefix to find, may be null
     * @param ignoreCase indicates whether the compare should ignore case
     *                   (case insensitive) or not.
     * @return {@code true} if the CharSequence starts with the prefix
     */
    private static boolean startsWith(final CharSequence str, final CharSequence prefix, final boolean ignoreCase) {
        if (str == null || prefix == null) {
            return str == prefix;
        }
        if (prefix.length() > str.length()) {
            return false;
        }
        return regionMatches(str, ignoreCase, 0, prefix, 0, prefix.length());
    }

    /**
     * Green implementation of regionMatches.
     *
     * @param cs         the {@code CharSequence} to be processed
     * @param ignoreCase whether or not to be case insensitive
     * @param thisStart  the index to start on the {@code cs} CharSequence
     * @param substring  the {@code CharSequence} to be looked for
     * @param start      the index to start on the {@code substring} CharSequence
     * @param length     character length of the region
     * @return whether the region matched
     */
    private static boolean regionMatches(final CharSequence cs, final boolean ignoreCase, final int thisStart,
                                         final CharSequence substring, final int start, final int length) {
        if (cs instanceof String && substring instanceof String) {
            return ((String) cs).regionMatches(ignoreCase, thisStart, (String) substring, start, length);
        }
        int index1 = thisStart;
        int index2 = start;
        int tmpLen = length;

        // Extract these first so we detect NPEs the same as the java.lang.String version
        final int srcLen = cs.length() - thisStart;
        final int otherLen = substring.length() - start;

        // Check for invalid parameters
        if (thisStart < 0 || start < 0 || length < 0) {
            return false;
        }

        // Check that the regions are long enough
        if (srcLen < length || otherLen < length) {
            return false;
        }

        while (tmpLen-- > 0) {
            final char c1 = cs.charAt(index1++);
            final char c2 = substring.charAt(index2++);

            if (c1 == c2) {
                continue;
            }

            if (!ignoreCase) {
                return false;
            }

            // The same check as in String.regionMatches():
            if (Character.toUpperCase(c1) != Character.toUpperCase(c2)
                    && Character.toLowerCase(c1) != Character.toLowerCase(c2)) {
                return false;
            }
        }

        return true;
    }

    /**
     * <p>清除左右空白，如果为null则返回null</p>
     *
     * <pre>
     * StringUtils.trim(null)       = null
     * StringUtils.trim("")         = ""
     * StringUtils.trim(" ")       	= ""
     * StringUtils.trim("liwy")     = "liwy"
     * StringUtils.trim(" liwy ") 	= "liwy"
     * StringUtils.trim("\n")       = ""
     * StringUtils.trim("\t")       = ""
     * </pre>
     *
     * @param str 要处理的字符串，可能是null
     * @return 处理后的字符串，如果输入为null，则为""
     */
    public static String trim(final String str) {
        return str == null ? null : str.trim();
    }

    /**
     * <p>清除左右空白，如果为null则返回""</p>
     *
     * <pre>
     * StringUtils.trimToEmpty(null)          = ""
     * StringUtils.trimToEmpty("")            = ""
     * StringUtils.trimToEmpty("     ")       = ""
     * StringUtils.trimToEmpty("abc")         = "abc"
     * StringUtils.trimToEmpty("    abc    ") = "abc"
     * </pre>
     *
     * @param str the String to be trimmed, may be null
     * @return the trimmed String, or an empty String if {@code null} input
     */
    public static String trimToEmpty(final String str) {
        return str == null ? EMPTY : str.trim();
    }

    /**
     * <p>清除左右空白，如果为null或""则返回null</p>
     *
     * <pre>
     * StringUtils.trimToNull(null)          = null
     * StringUtils.trimToNull("")            = null
     * StringUtils.trimToNull("     ")       = null
     * StringUtils.trimToNull("abc")         = "abc"
     * StringUtils.trimToNull("    abc    ") = "abc"
     * </pre>
     *
     * @param str the String to be trimmed, may be null
     * @return the trimmed String
     */
    public static String trimToNull(final String str) {
        final String ts = trim(str);
        return isEmpty(ts) ? null : ts;
    }

    /**
     * <p>字符串转char</p>
     *
     * @param str
     * @return
     */
    public static char toChar(final String str) {
        if (isEmpty(str)) {
            throw new IllegalArgumentException("The String must not be empty");
        }
        return str.charAt(0);
    }

    /**
     * 转为字符串
     *
     * @param c
     * @return
     */
    public static String toString(final char c) {
        return String.valueOf(c);
    }

    public static String toString(final boolean b) {
        return String.valueOf(b);
    }

    public static String toString(final int i) {
        return String.valueOf(i);
    }

    public static String toString(final long l) {
        return String.valueOf(l);
    }

    public static String toString(final float f) {
        return String.valueOf(f);
    }

    public static String toString(final double d) {
        return String.valueOf(d);
    }

    public static String toString(final Object obj) {
        return (obj == null) ? "" : obj.toString();
    }

    /**
     * <p>获取字符的unicode编码</p>
     *
     * <pre>
     * StringUtils.unicodeEscaped(' ')	=	\u0020
     * StringUtils.unicodeEscaped('a')	=	\u0061
     * StringUtils.unicodeEscaped('Z')	=	\u005a
     * StringUtils.unicodeEscaped('0')	=	\u0030
     * StringUtils.unicodeEscaped('9')	=	\u0039
     * StringUtils.unicodeEscaped('@')	=	\u0040
     * StringUtils.unicodeEscaped('姚')	=	\u59da
     * </pre>
     *
     * @param ch 要处理的字符
     * @return Unicode编码
     */
    public static String unicodeEscaped(final char ch) {
        StringBuilder sb = new StringBuilder(6);
        sb.append("\\u");
        sb.append(HEX_DIGITS[(ch >> 12) & 15]);
        sb.append(HEX_DIGITS[(ch >> 8) & 15]);
        sb.append(HEX_DIGITS[(ch >> 4) & 15]);
        sb.append(HEX_DIGITS[(ch) & 15]);
        return sb.toString();
    }

    /**
     * <p>将普通字符串转义为unicode串形式的字符串</p>
     *
     * <pre>
     * StringUtils.str2unicode("   ")	=	\u0020\u0020\u0020
     * StringUtils.str2unicode("liwy")	=	\u006c\u0069\u0077\u0079
     * StringUtils.str2unicode("李文姚")	=	\u674e\u6587\u59da
     * </pre>
     *
     * @param cs 要转换的字符序列
     * @return Unicode串形式的字符串
     */
    public static String str2unicode(final CharSequence cs) {
        String unicodeStr = "";
        for (int i = 0; i < cs.length(); i++) {
            unicodeStr += unicodeEscaped(cs.charAt(i));
        }
        return unicodeStr;
    }

    /**
     * <p>将unicode串形式的字符串反转义为普通字符串</p>
     *
     * <pre>
     * StringUtil.unicode2str("\\u674e\\u6587\\u59da")			=	"李文姚"
     * StringUtil.unicode2str("\\u006c\\u0069\\u0077\\u0079")	=	"liwy"
     * StringUtil.unicode2str("\u006c\u0069\u0077\u0079")		=	"liwy"
     * StringUtil.unicode2str("liwy")							=	"liwy"
     * </pre>
     *
     * @param str Unicode串形式的字符串
     * @return 转换后的字符串
     */
    public static String unicode2str(String str) {
        Pattern pattern = Pattern.compile(PATTERN_UNICODE);
        Matcher matcher = pattern.matcher(str);
        char ch;
        while (matcher.find()) {
            ch = (char) Integer.parseInt(matcher.group(2), 16);
            str = str.replace(matcher.group(1), ch + "");
        }
        return str;
    }

    /**
     * 大写首字母<br>
     * 例如：str = name, return Name
     *
     * @param str 字符串
     * @return 字符串
     */
    public static String upperFirst(CharSequence str) {
        if (null == str) {
            return null;
        }
        if (str.length() > 0) {
            char firstChar = str.charAt(0);
            if (Character.isLowerCase(firstChar)) {
                return Character.toUpperCase(firstChar) + str.subSequence(1, str.length()).toString();
            }
        }
        return str.toString();
    }

    /**
     * 小写首字母<br>
     * 例如：str = Name, return name
     *
     * @param str 字符串
     * @return 字符串
     */
    public static String lowerFirst(CharSequence str) {
        if (null == str) {
            return null;
        }
        if (str.length() > 0) {
            char firstChar = str.charAt(0);
            if (Character.isUpperCase(firstChar)) {
                return Character.toLowerCase(firstChar) + str.subSequence(1, str.length()).toString();
            }
        }
        return str.toString();
    }

    /**
     * <p>转小写</p>
     *
     * <pre>
     * StringUtils.lowerCase(null)  = null
     * StringUtils.lowerCase("")    = ""
     * StringUtils.lowerCase("aBc") = "abc"
     * </pre>
     *
     * @param str the String to lower case, may be null
     * @return the lower cased String, {@code null} if null String input
     */
    public static String lowerCase(final String str) {
        if (str == null) {
            return null;
        }
        return str.toLowerCase();
    }

    /**
     * <p>转大写</p>
     *
     * <pre>
     * StringUtils.upperCase(null)  = null
     * StringUtils.upperCase("")    = ""
     * StringUtils.upperCase("aBc") = "ABC"
     * </pre>
     *
     * @param str the String to upper case, may be null
     * @return the upper cased String, {@code null} if null String input
     */
    public static String upperCase(final String str) {
        if (str == null) {
            return null;
        }
        return str.toUpperCase();
    }

    /**
     * <p>字符串反转</p>
     *
     * <pre>
     * StringUtils.reverse(null)  = null
     * StringUtils.reverse("")    = ""
     * StringUtils.reverse("bat") = "tab"
     * </pre>
     *
     * @param str the String to reverse, may be null
     * @return the reversed String, {@code null} if null String input
     */
    public static String reverse(final String str) {
        if (str == null) {
            return null;
        }
        return new StringBuilder(str).reverse().toString();
    }

    /**
     * <p>裁剪字符串</p>
     *
     * <pre>
     * StringUtils.substring(null, *)   = null
     * StringUtils.substring("", *)     = ""
     * StringUtils.substring("abc", 0)  = "abc"
     * StringUtils.substring("abc", 2)  = "c"
     * StringUtils.substring("abc", 4)  = ""
     * StringUtils.substring("abc", -2) = "bc"
     * StringUtils.substring("abc", -4) = "abc"
     * </pre>
     *
     * @param str   the String to get the substring from, may be null
     * @param start the position to start from, negative means
     *              count back from the end of the String by this many characters
     * @return substring from start position, {@code null} if null String input
     */
    public static String substring(final String str, int start) {
        if (str == null) {
            return null;
        }

        // handle negatives, which means last n characters
        if (start < 0) {
            start = str.length() + start; // remember start is negative
        }

        if (start < 0) {
            start = 0;
        }
        if (start > str.length()) {
            return EMPTY;
        }

        return str.substring(start);
    }

    /**
     * <p>裁剪字符串</p>
     *
     * <pre>
     * StringUtils.substring(null, *, *)    = null
     * StringUtils.substring("", * ,  *)    = "";
     * StringUtils.substring("abc", 0, 2)   = "ab"
     * StringUtils.substring("abc", 2, 0)   = ""
     * StringUtils.substring("abc", 2, 4)   = "c"
     * StringUtils.substring("abc", 4, 6)   = ""
     * StringUtils.substring("abc", 2, 2)   = ""
     * StringUtils.substring("abc", -2, -1) = "b"
     * StringUtils.substring("abc", -4, 2)  = "ab"
     * </pre>
     *
     * @param str   the String to get the substring from, may be null
     * @param start the position to start from, negative means
     *              count back from the end of the String by this many characters
     * @param end   the position to end at (exclusive), negative means
     *              count back from the end of the String by this many characters
     * @return substring from start position to end position,
     */
    public static String substring(final String str, int start, int end) {
        if (str == null) {
            return null;
        }

        // handle negatives
        if (end < 0) {
            end = str.length() + end; // remember end is negative
        }
        if (start < 0) {
            start = str.length() + start; // remember start is negative
        }

        // check length next
        if (end > str.length()) {
            end = str.length();
        }

        // if start is greater than end, return ""
        if (start > end) {
            return EMPTY;
        }

        if (start < 0) {
            start = 0;
        }
        if (end < 0) {
            end = 0;
        }

        return str.substring(start, end);
    }

    /**
     * <p>字符串替换</p>
     *
     * <pre>
     * StringUtils.replace(null, *, *)        = null
     * StringUtils.replace("", *, *)          = ""
     * StringUtils.replace("any", null, *)    = "any"
     * StringUtils.replace("any", *, null)    = "any"
     * StringUtils.replace("any", "", *)      = "any"
     * StringUtils.replace("aba", "a", null)  = "aba"
     * StringUtils.replace("aba", "a", "")    = "b"
     * StringUtils.replace("aba", "a", "z")   = "zbz"
     * </pre>
     *
     * @param text         text to search and replace in, may be null
     * @param searchString the String to search for, may be null
     * @param replacement  the String to replace it with, may be null
     * @return the text with any replacements processed
     */
    public static String replace(final String text, final String searchString, final String replacement) {
        return replace(text, searchString, replacement, -1, false);
    }

    /**
     * <p>忽略大小写字符串替换</p>
     *
     * <pre>
     * StringUtils.replaceIgnoreCase(null, *, *)        = null
     * StringUtils.replaceIgnoreCase("", *, *)          = ""
     * StringUtils.replaceIgnoreCase("any", null, *)    = "any"
     * StringUtils.replaceIgnoreCase("any", *, null)    = "any"
     * StringUtils.replaceIgnoreCase("any", "", *)      = "any"
     * StringUtils.replaceIgnoreCase("aba", "a", null)  = "aba"
     * StringUtils.replaceIgnoreCase("abA", "A", "")    = "b"
     * StringUtils.replaceIgnoreCase("aba", "A", "z")   = "zbz"
     * </pre>
     *
     * @param text         text to search and replace in, may be null
     * @param searchString the String to search for (case insensitive), may be null
     * @param replacement  the String to replace it with, may be null
     * @return the text with any replacements processed
     */
    public static String replaceIgnoreCase(final String text, final String searchString, final String replacement) {
        return replace(text, searchString, replacement, -1, true);
    }

    /**
     * <p>字符串替换</p>
     *
     * <pre>
     * StringUtils.replace(null, *, *, *, false)         = null
     * StringUtils.replace("", *, *, *, false)           = ""
     * StringUtils.replace("any", null, *, *, false)     = "any"
     * StringUtils.replace("any", *, null, *, false)     = "any"
     * StringUtils.replace("any", "", *, *, false)       = "any"
     * StringUtils.replace("any", *, *, 0, false)        = "any"
     * StringUtils.replace("abaa", "a", null, -1, false) = "abaa"
     * StringUtils.replace("abaa", "a", "", -1, false)   = "b"
     * StringUtils.replace("abaa", "a", "z", 0, false)   = "abaa"
     * StringUtils.replace("abaa", "A", "z", 1, false)   = "abaa"
     * StringUtils.replace("abaa", "A", "z", 1, true)   = "zbaa"
     * StringUtils.replace("abAa", "a", "z", 2, true)   = "zbza"
     * StringUtils.replace("abAa", "a", "z", -1, true)  = "zbzz"
     * </pre>
     *
     * @param text         text to search and replace in, may be null
     * @param searchString the String to search for (case insensitive), may be null
     * @param replacement  the String to replace it with, may be null
     * @param max          maximum number of values to replace, or {@code -1} if no maximum
     * @param ignoreCase   if true replace is case insensitive, otherwise case sensitive
     * @return the text with any replacements processed
     */
    private static String replace(final String text, String searchString, final String replacement, int max, final boolean ignoreCase) {
        if (isEmpty(text) || isEmpty(searchString) || replacement == null || max == 0) {
            return text;
        }
        String searchText = text;
        if (ignoreCase) {
            searchText = text.toLowerCase();
            searchString = searchString.toLowerCase();
        }
        int start = 0;
        int end = searchText.indexOf(searchString, start);
        if (end == INDEX_NOT_FOUND) {
            return text;
        }
        final int replLength = searchString.length();
        int increase = replacement.length() - replLength;
        increase = increase < 0 ? 0 : increase;
        increase *= max < 0 ? 16 : max > 64 ? 64 : max;
        final StringBuilder buf = new StringBuilder(text.length() + increase);
        while (end != INDEX_NOT_FOUND) {
            buf.append(text, start, end).append(replacement);
            start = end + replLength;
            if (--max == 0) {
                break;
            }
            end = searchText.indexOf(searchString, start);
        }
        buf.append(text, start, text.length());
        return buf.toString();
    }

    /**
     * <p>替换一次</p>
     *
     * <pre>
     * StringUtils.replaceOnce(null, *, *)        = null
     * StringUtils.replaceOnce("", *, *)          = ""
     * StringUtils.replaceOnce("any", null, *)    = "any"
     * StringUtils.replaceOnce("any", *, null)    = "any"
     * StringUtils.replaceOnce("any", "", *)      = "any"
     * StringUtils.replaceOnce("aba", "a", null)  = "aba"
     * StringUtils.replaceOnce("aba", "a", "")    = "ba"
     * StringUtils.replaceOnce("aba", "a", "z")   = "zba"
     * </pre>
     *
     * @param text         text to search and replace in, may be null
     * @param searchString the String to search for, may be null
     * @param replacement  the String to replace with, may be null
     * @return the text with any replacements processed
     */
    public static String replaceOnce(final String text, final String searchString, final String replacement) {
        return replace(text, searchString, replacement, 1, false);
    }

    /**
     * <p>忽略大小写替换一次</p>
     *
     * <pre>
     * StringUtils.replaceOnceIgnoreCase(null, *, *)        = null
     * StringUtils.replaceOnceIgnoreCase("", *, *)          = ""
     * StringUtils.replaceOnceIgnoreCase("any", null, *)    = "any"
     * StringUtils.replaceOnceIgnoreCase("any", *, null)    = "any"
     * StringUtils.replaceOnceIgnoreCase("any", "", *)      = "any"
     * StringUtils.replaceOnceIgnoreCase("aba", "a", null)  = "aba"
     * StringUtils.replaceOnceIgnoreCase("aba", "a", "")    = "ba"
     * StringUtils.replaceOnceIgnoreCase("aba", "a", "z")   = "zba"
     * StringUtils.replaceOnceIgnoreCase("FoOFoofoo", "foo", "") = "Foofoo"
     * </pre>
     *
     * @param text         text to search and replace in, may be null
     * @param searchString the String to search for (case insensitive), may be null
     * @param replacement  the String to replace with, may be null
     * @return the text with any replacements processed
     */
    public static String replaceOnceIgnoreCase(final String text, final String searchString, final String replacement) {
        return replace(text, searchString, replacement, 1, true);
    }

    /**
     * 连接多个字符串为一个
     *
     * @param strs 字符串数组
     * @return 连接后的字符串
     */
    public static String concat(CharSequence... strs) {
        StringBuilder sb = new StringBuilder();
        for (CharSequence str : strs) {
            if (strs == null) {
                continue;
            }
            sb.append(str);
        }
        return sb.toString();
    }

    /**
     * 格式化文本, {} 表示占位符<br>
     * 此方法只是简单将占位符 {} 按照顺序替换为参数<br>
     * <p>
     * 例：<br>
     * 通常使用：format("this is {} for {}", "a", "b") =》 this is a for b
     *
     * @param template 文本模板，被替换的部分用 {} 表示
     * @param params   参数值
     * @return 格式化后的文本
     */
    public static String format(String template, Object... params) {
        if (params == null || params.length == 0 || isBlank(template)) {
            return template;
        }
        final int templateLength = template.length();

        StringBuilder sbuf = new StringBuilder(templateLength + 50);

        // 记录已经处理到的位置
        int handledPosition = 0;
        // 占位符所在位置
        int delimIndex;

        for (int argIndex = 0; argIndex < params.length; argIndex++) {
            delimIndex = template.indexOf(OBJ, handledPosition);
            // 转义符
            sbuf.append(template, handledPosition, delimIndex);
            sbuf.append(toString(params[argIndex]));
            handledPosition = delimIndex + 2;
        }
        sbuf.append(template, handledPosition, template.length());

        return sbuf.toString();
    }

    /**
     * <p>字符串比较</p>
     *
     * <pre>
     * StringUtils.compare(null, null)   = 0
     * StringUtils.compare(null , "a")   &lt; 0
     * StringUtils.compare("a", null)    &gt; 0
     * StringUtils.compare("abc", "abc") = 0
     * StringUtils.compare("a", "b")     &lt; 0
     * StringUtils.compare("b", "a")     &gt; 0
     * StringUtils.compare("a", "B")     &gt; 0
     * StringUtils.compare("ab", "abc")  &lt; 0
     * </pre>
     *
     * @param str1 the String to compare from
     * @param str2 the String to compare to
     * @return &lt; 0, 0, &gt; 0, if {@code str1}
     */
    public static int compare(final String str1, final String str2) {
        if (str1 == str2) {
            return 0;
        }
        if (str1 == null) {
            return -1;
        }
        if (str2 == null) {
            return 1;
        }
        return str1.compareTo(str2);
    }

    /**
     * <p>忽略大小写，比较字符串</p>
     *
     * <pre>
     * StringUtils.compareIgnoreCase(null, null)   = 0
     * StringUtils.compareIgnoreCase(null , "a")   &lt; 0
     * StringUtils.compareIgnoreCase("a", null)    &gt; 0
     * StringUtils.compareIgnoreCase("abc", "abc") = 0
     * StringUtils.compareIgnoreCase("abc", "ABC") = 0
     * StringUtils.compareIgnoreCase("a", "b")     &lt; 0
     * StringUtils.compareIgnoreCase("b", "a")     &gt; 0
     * StringUtils.compareIgnoreCase("a", "B")     &lt; 0
     * StringUtils.compareIgnoreCase("A", "b")     &lt; 0
     * StringUtils.compareIgnoreCase("ab", "ABC")  &lt; 0
     * </pre>
     *
     * @param str1 the String to compare from
     * @param str2 the String to compare to
     * @return &lt; 0, 0, &gt; 0, if {@code str1}
     */
    public static int compareIgnoreCase(final String str1, final String str2) {
        if (str1 == str2) {
            return 0;
        }
        if (str1 == null) {
            return -1;
        }
        if (str2 == null) {
            return 1;
        }
        return str1.compareToIgnoreCase(str2);
    }

    /**
     * <p>修改字符串的编码格式</p>
     *
     * <pre>
     * US-ASCII		ASCII 码，总共有 128 个，用一个字节的低 7 位表示，0~31 是控制字符如换行回车删除等；32~126 是打印字符，可以通过键盘输入并且能够显示出来。
     * ISO-8859-1	ISO 组织在 ASCII 码基础上又制定了一些列标准用来扩展 ASCII 编码，它们是 ISO-8859-1~ISO-8859-15，其中 ISO-8859-1 涵盖了大多数西欧语言字符，所有应用的最广泛。ISO-8859-1 仍然是单字节编码，它总共能表示 256 个字符。
     * GB2312		它的全称是《信息交换用汉字编码字符集 基本集》，它是双字节编码，总的编码范围是 A1-F7，其中从 A1-A9 是符号区，总共包含 682 个符号，从 B0-F7 是汉字区，包含 6763 个汉字。
     * GBK			全称叫《汉字内码扩展规范》，是国家技术监督局为 windows95 所制定的新的汉字内码规范，它的出现是为了扩展 GB2312，加入更多的汉字，它的编码范围是 8140~FEFE（去掉 XX7F）总共有 23940 个码位，它能表示 21003 个汉字，它的编码是和 GB2312 兼容的，也就是说用 GB2312 编码的汉字可以用 GBK 来解码，并且不会有乱码。
     * UTF-16		UTF-16 用两个字节来表示 Unicode 转化格式，这个是定长的表示方法，不论什么字符都可以用两个字节表示，两个字节是 16 个 bit，所以叫 UTF-16。UTF-16 表示字符非常方便，每两个字节表示一个字符，这个在字符串操作时就大大简化了操作，这也是 Java 以 UTF-16 作为内存的字符存储格式的一个很重要的原因。
     * UTF-16BE		16 位 UCS 转换格式，Big Endian（最低地址存放高位字节）字节顺序
     * UTF-16LE		16 位 UCS 转换格式，Little-endian（最高地址存放低位字节）字节顺序
     * UTF-8		8 位 UCS 转换格式,UTF-16 统一采用两个字节表示一个字符，虽然在表示上非常简单方便，但是也有其缺点，有很大一部分字符用一个字节就可以表示的现在要两个字节表示，存储空间放大了一倍，在现在的网络带宽还非常有限的今天，这样会增大网络传输的流量，而且也没必要。而 UTF-8 采用了一种变长技术，每个编码区域有不同的字码长度。不同类型的字符可以是由 1~6 个字节组成。
     * </pre>
     *
     * @param str        要转码的字符串
     * @param oldCharset 原字符集
     * @param newCharset 新字符集
     * @return 转码后的字符串
     * @throws UnsupportedEncodingException
     */
    public static String changeCharset(String str, String oldCharset, String newCharset) throws UnsupportedEncodingException {
        return new String(str.getBytes(oldCharset), newCharset);
    }

    /**
     * <p>将半角的符号转换成全角符号.(即英文字符转中文字符)</p>
     *
     * <pre>
     * StringUtils.full2Half("")			=""
     * StringUtils.full2Half("   ")			="   "
     * StringUtils.full2Half("\t")			="\t"
     * StringUtils.full2Half(null)			=""
     * StringUtils.full2Half("12343，liwy")	="12343,liwy"
     * StringUtils.full2Half("12１２＄liwy")	="1212$liwy"
     * StringUtils.full2Half("李文姚ｌｉｗｙ")	="李文姚liwy"
     * </pre>
     *
     * @param str 要转换的字符串
     * @return 转换后的字符串
     */
    public static String half2Full(String str) {
        if (isEmpty(str))
            return "";
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c >= 33 && c < 127) {
                sb.append((char) (c + 65248));
            } else {
                sb.append(str.charAt(i));
            }
        }
        return sb.toString();
    }

    /**
     * <p>将全角的符号转换成半角符号.(即中文字符转英文字符)</p>
     *
     * <pre>
     * StringUtils.half2Full("")			= ""
     * StringUtils.half2Full("   ")			= "   "
     * StringUtils.half2Full("\t")			= "\t"
     * StringUtils.half2Full(null)			= ""
     * StringUtils.half2Full("12343，liwy")	= "１２３４３，ｌｉｗｙ"
     * StringUtils.half2Full("12１２＄liwy")	= "１２１２＄ｌｉｗｙ"
     * StringUtils.half2Full("李文姚ｌｉｗｙ")	= "李文姚ｌｉｗｙ"
     * </pre>
     *
     * @param str 要转换的字符串
     * @return 转换后的字符串
     */
    public static String full2Half(String str) {
        if (isEmpty(str)) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c >= 65281 && c < 65375) {
                sb.append((char) (c - 65248));
            } else {
                sb.append(str.charAt(i));
            }
        }
        return sb.toString();
    }

    /**
     * <p>字符串分隔为数组</p>
     *
     * <pre>
     * StringUtils.split(null, *)         = null
     * StringUtils.split("", *)           = []
     * StringUtils.split("a.b.c", '.')    = ["a", "b", "c"]
     * StringUtils.split("a..b.c", '.')   = ["a", "b", "c"]
     * StringUtils.split("a:b:c", '.')    = ["a:b:c"]
     * StringUtils.split("a b c", ' ')    = ["a", "b", "c"]
     * </pre>
     *
     * @param str           the String to parse, may be null
     * @param separatorChar the character used as the delimiter
     * @return an array of parsed Strings, {@code null} if null String input
     */
    public static String[] split(final String str, final char separatorChar) {
        return split(str, separatorChar);
    }

    /**
     * <p>字符串分隔为数组</p>
     *
     * <pre>
     * StringUtils.split(null, *)         = null
     * StringUtils.split("", *)           = []
     * StringUtils.split("abc def", null) = ["abc", "def"]
     * StringUtils.split("abc def", " ")  = ["abc", "def"]
     * StringUtils.split("abc  def", " ") = ["abc", "def"]
     * StringUtils.split("ab:cd:ef", ":") = ["ab", "cd", "ef"]
     * </pre>
     *
     * @param str            the String to parse, may be null
     * @param separatorChars the characters used as the delimiters,
     *                       {@code null} splits on whitespace
     * @return an array of parsed Strings, {@code null} if null String input
     */
    public static String[] split(final String str, final String separatorChars) {

        if (str == null) {
            return null;
        }
        final int len = str.length();
        if (len == 0) {
            return new String[0];
        }
        final List<String> list = new ArrayList<>();
        int sizePlus1 = 1;
        int i = 0, start = 0;
        boolean match = false;
        boolean lastMatch = false;
        if (separatorChars == null) {
            // Null separator means use whitespace
            while (i < len) {
                if (Character.isWhitespace(str.charAt(i))) {
                    if (match) {
                        lastMatch = true;
                        list.add(str.substring(start, i));
                        match = false;
                    }
                    start = ++i;
                    continue;
                }
                lastMatch = false;
                match = true;
                i++;
            }
        } else if (separatorChars.length() == 1) {
            // Optimise 1 character case
            final char sep = separatorChars.charAt(0);
            while (i < len) {
                if (str.charAt(i) == sep) {
                    if (match) {
                        lastMatch = true;
                        list.add(str.substring(start, i));
                        match = false;
                    }
                    start = ++i;
                    continue;
                }
                lastMatch = false;
                match = true;
                i++;
            }
        } else {
            // standard case
            while (i < len) {
                if (separatorChars.indexOf(str.charAt(i)) >= 0) {
                    if (match) {
                        lastMatch = true;
                        list.add(str.substring(start, i));
                        match = false;
                    }
                    start = ++i;
                    continue;
                }
                lastMatch = false;
                match = true;
                i++;
            }
        }
        if (match && lastMatch) {
            list.add(str.substring(start, i));
        }
        return list.toArray(new String[list.size()]);
    }

    /**
     * <p>Join byte数组为字符串</p>
     *
     * <pre>
     * StringUtils.join(null, *)               = null
     * StringUtils.join([], *)                 = ""
     * StringUtils.join([null], *)             = ""
     * StringUtils.join([1, 2, 3], ';')  = "1;2;3"
     * StringUtils.join([1, 2, 3], null) = "123"
     * </pre>
     *
     * @param array     the array of values to join together, may be null
     * @param separator the separator character to use
     * @return the joined String, {@code null} if null array input
     */
    public static String join(final byte[] array, final char separator) {
        if (array == null) {
            return null;
        }
        final StringBuilder buf = new StringBuilder(array.length * 16);
        for (int i = 0; i < array.length; i++) {
            if (i > 0) {
                buf.append(separator);
            }
            buf.append(array[i]);
        }
        return buf.toString();
    }

    /**
     * <p>join char数组为字符串</p>
     *
     * <pre>
     * StringUtils.join(null, *)               = null
     * StringUtils.join([], *)                 = ""
     * StringUtils.join([null], *)             = ""
     * StringUtils.join([1, 2, 3], ';')  = "1;2;3"
     * StringUtils.join([1, 2, 3], null) = "123"
     * </pre>
     *
     * @param array     the array of values to join together, may be null
     * @param separator the separator character to use
     * @return the joined String, {@code null} if null array input
     */
    public static String join(final char[] array, final char separator) {
        if (array == null) {
            return null;
        }
        final StringBuilder buf = new StringBuilder(array.length * 16);
        for (int i = 0; i < array.length; i++) {
            if (i > 0) {
                buf.append(separator);
            }
            buf.append(array[i]);
        }
        return buf.toString();
    }

    /**
     * <p>join short数组为字符串</p>
     *
     * <pre>
     * StringUtils.join(null, *)               = null
     * StringUtils.join([], *)                 = ""
     * StringUtils.join([null], *)             = ""
     * StringUtils.join([1, 2, 3], ';')  = "1;2;3"
     * StringUtils.join([1, 2, 3], null) = "123"
     * </pre>
     *
     * @param array     the array of values to join together, may be null
     * @param separator the separator character to use
     * @return the joined String, {@code null} if null array input
     */
    public static String join(final short[] array, final char separator) {
        if (array == null) {
            return null;
        }
        final StringBuilder buf = new StringBuilder(array.length * 16);
        for (int i = 0; i < array.length; i++) {
            if (i > 0) {
                buf.append(separator);
            }
            buf.append(array[i]);
        }
        return buf.toString();
    }

    /**
     * <p>join int数组为字符串</p>
     *
     * <pre>
     * StringUtils.join(null, *)               = null
     * StringUtils.join([], *)                 = ""
     * StringUtils.join([null], *)             = ""
     * StringUtils.join([1, 2, 3], ';')  = "1;2;3"
     * StringUtils.join([1, 2, 3], null) = "123"
     * </pre>
     *
     * @param array     the array of values to join together, may be null
     * @param separator the separator character to use
     * @return the joined String, {@code null} if null array input
     */
    public static String join(final int[] array, final char separator) {
        if (array == null) {
            return null;
        }
        final StringBuilder buf = new StringBuilder(array.length * 16);
        for (int i = 0; i < array.length; i++) {
            if (i > 0) {
                buf.append(separator);
            }
            buf.append(array[i]);
        }
        return buf.toString();
    }

    /**
     * <p>join long数组为字符串</p>
     *
     * <pre>
     * StringUtils.join(null, *)               = null
     * StringUtils.join([], *)                 = ""
     * StringUtils.join([null], *)             = ""
     * StringUtils.join([1, 2, 3], ';')  = "1;2;3"
     * StringUtils.join([1, 2, 3], null) = "123"
     * </pre>
     *
     * @param array     the array of values to join together, may be null
     * @param separator the separator character to use
     * @return the joined String, {@code null} if null array input
     */
    public static String join(final long[] array, final char separator) {
        if (array == null) {
            return null;
        }
        final StringBuilder buf = new StringBuilder(array.length * 16);
        for (int i = 0; i < array.length; i++) {
            if (i > 0) {
                buf.append(separator);
            }
            buf.append(array[i]);
        }
        return buf.toString();
    }

    /**
     * <p>join float数组为字符串</p>
     *
     * <pre>
     * StringUtils.join(null, *)               = null
     * StringUtils.join([], *)                 = ""
     * StringUtils.join([null], *)             = ""
     * StringUtils.join([1, 2, 3], ';')  = "1;2;3"
     * StringUtils.join([1, 2, 3], null) = "123"
     * </pre>
     *
     * @param array     the array of values to join together, may be null
     * @param separator the separator character to use
     * @return the joined String, {@code null} if null array input
     */
    public static String join(final float[] array, final char separator) {
        if (array == null) {
            return null;
        }
        final StringBuilder buf = new StringBuilder(array.length * 16);
        for (int i = 0; i < array.length; i++) {
            if (i > 0) {
                buf.append(separator);
            }
            buf.append(array[i]);
        }
        return buf.toString();
    }

    /**
     * <p>join double数组为字符串</p>
     *
     * <pre>
     * StringUtils.join(null, *)               = null
     * StringUtils.join([], *)                 = ""
     * StringUtils.join([null], *)             = ""
     * StringUtils.join([1, 2, 3], ';')  = "1;2;3"
     * StringUtils.join([1, 2, 3], null) = "123"
     * </pre>
     *
     * @param array     the array of values to join together, may be null
     * @param separator the separator character to use
     * @return the joined String, {@code null} if null array input
     */
    public static String join(final double[] array, final char separator) {
        if (array == null) {
            return null;
        }
        final StringBuilder buf = new StringBuilder(array.length * 16);
        for (int i = 0; i < array.length; i++) {
            if (i > 0) {
                buf.append(separator);
            }
            buf.append(array[i]);
        }
        return buf.toString();
    }

    /**
     * <p>join Object数组为字符串</p>
     *
     * <pre>
     * StringUtils.join(null, *)               = null
     * StringUtils.join([], *)                 = ""
     * StringUtils.join([null], *)             = ""
     * StringUtils.join([1, 2, 3], ';')  = "1;2;3"
     * StringUtils.join([1, 2, 3], null) = "123"
     * </pre>
     *
     * @param array     the array of values to join together, may be null
     * @param separator the separator character to use
     * @return the joined String, {@code null} if null array input
     */
    public static String join(final Object[] array, final char separator) {
        return join(array, toString(separator));
    }

    /**
     * <p>join Object数组为字符串</p>
     *
     * <pre>
     * StringUtils.join(null, *)               = null
     * StringUtils.join([], *)                 = ""
     * StringUtils.join([null], *)             = ""
     * StringUtils.join([1, 2, 3], ';')  = "1;2;3"
     * StringUtils.join([1, 2, 3], null) = "123"
     * </pre>
     *
     * @param array     the array of values to join together, may be null
     * @param separator the separator character to use
     * @return the joined String, {@code null} if null array input
     */
    public static String join(final Object[] array, final String separator) {
        if (array == null) {
            return null;
        }
        final StringBuilder buf = new StringBuilder(array.length * 16);
        for (int i = 0; i < array.length; i++) {
            if (i > 0) {
                buf.append(separator);
            }
            buf.append(array[i]);
        }
        return buf.toString();
    }

    /**
     * <p>join iterable为字符串</p>
     *
     * @param iterable  the {@code Iterable} providing the values to join together, may be null
     * @param separator the separator character to use, null treated as ""
     * @return the joined String, {@code null} if null iterator input
     */
    public static String join(final Iterable<?> iterable, final String separator) {
        if (iterable == null) {
            return null;
        }
        return join(iterable.iterator(), separator);
    }

    /**
     * <p>join iterator为字符串</p>
     *
     * @param iterator  the {@code Iterator} of values to join together, may be null
     * @param separator the separator character to use, null treated as ""
     * @return the joined String, {@code null} if null iterator input
     */
    public static String join(final Iterator<?> iterator, final String separator) {
        if (iterator == null) {
            return null;
        }
        if (!iterator.hasNext()) {
            return EMPTY;
        }
        final Object first = iterator.next();
        if (!iterator.hasNext()) {
            return Objects.toString(first, EMPTY);
        }

        final StringBuilder buf = new StringBuilder(256); // Java default is 16, probably too small
        if (first != null) {
            buf.append(first);
        }

        while (iterator.hasNext()) {
            if (separator != null) {
                buf.append(separator);
            }
            final Object obj = iterator.next();
            if (obj != null) {
                buf.append(obj);
            }
        }
        return buf.toString();
    }

    // ------printObject()

    /**
     * 将对象转换为可读的字符串
     * 主要处理数组对象，Collection、Map的下级对象
     * 对于自定义对象主要是通过调用该对象重写的toString方法
     *
     * @param value
     * @return
     */
    public String toPrintString(Object value) {
        StringBuffer sb = new StringBuffer();
        appendInternal(sb, value);
        return sb.toString();
    }

    /**
     * 转换为String的核心逻辑
     *
     * @param buffer
     * @param value
     */
    private void appendInternal(final StringBuffer buffer, final Object value) {
        if (value == null) {
            buffer.append(NULL);
            return;

        }
        if (value instanceof Collection<?>) {
            appendDetail(buffer, (Collection<?>) value);
        } else if (value instanceof Map<?, ?>) {
            appendDetail(buffer, (Map<?, ?>) value);
        } else if (value instanceof long[]) {
            appendDetail(buffer, (long[]) value);
        } else if (value instanceof int[]) {
            appendDetail(buffer, (int[]) value);
        } else if (value instanceof short[]) {
            appendDetail(buffer, (short[]) value);
        } else if (value instanceof byte[]) {
            appendDetail(buffer, (byte[]) value);
        } else if (value instanceof char[]) {
            appendDetail(buffer, (char[]) value);
        } else if (value instanceof double[]) {
            appendDetail(buffer, (double[]) value);
        } else if (value instanceof float[]) {
            appendDetail(buffer, (float[]) value);
        } else if (value instanceof boolean[]) {
            appendDetail(buffer, (boolean[]) value);
        } else if (value.getClass().isArray()) {
            appendDetail(buffer, (Object[]) value);
        } else {
            appendDetail(buffer, value);
        }
    }

    private void appendDetail(final StringBuffer buffer, final Collection<?> coll) {
        buffer.append("[");
        Iterator<?> iter = coll.iterator();
        int i = 0;
        while (iter.hasNext()) {
            if (i > 0) {
                buffer.append(COMMA);
            }
            appendInternal(buffer, iter.next());
            i++;
        }
        buffer.append("]");
    }

    private void appendDetail(final StringBuffer buffer, final Map<?, ?> map) {
        buffer.append("{");
        Iterator<?> iter = map.entrySet().iterator();
        int i = 0;
        while (iter.hasNext()) {
            if (i > 0) {
                buffer.append(COMMA);
            }
            Map.Entry<?, ?> map1 = (Map.Entry<?, ?>) iter.next();
            appendInternal(buffer, map1.getKey());
            buffer.append("=");
            appendInternal(buffer, map1.getValue());
            i++;
        }
        buffer.append("}");
    }

    //long[]、int[]、short[]、byte[]等类型不能直接转换为Object[],所以要特殊处理
    private void appendDetail(final StringBuffer buffer, final long[] array) {

        buffer.append(BRACKET_START);
        for (int i = 0; i < array.length; i++) {
            if (i > 0) {
                buffer.append(COMMA);
            }
            appendDetail(buffer, array[i]);
        }
        buffer.append(BRACKET_END);
    }

    private void appendDetail(final StringBuffer buffer, final int[] array) {
        buffer.append(BRACKET_START);
        for (int i = 0; i < array.length; i++) {
            if (i > 0) {
                buffer.append(COMMA);
            }
            appendDetail(buffer, array[i]);
        }
        buffer.append(BRACKET_END);
    }

    private void appendDetail(final StringBuffer buffer, final short[] array) {
        buffer.append(BRACKET_START);
        for (int i = 0; i < array.length; i++) {
            if (i > 0) {
                buffer.append(COMMA);
            }
            appendDetail(buffer, array[i]);
        }
        buffer.append(BRACKET_END);
    }

    private void appendDetail(final StringBuffer buffer, final byte[] array) {
        buffer.append(BRACKET_START);
        for (int i = 0; i < array.length; i++) {
            if (i > 0) {
                buffer.append(COMMA);
            }
            appendDetail(buffer, array[i]);
        }
        buffer.append(BRACKET_END);
    }

    private void appendDetail(final StringBuffer buffer, final char[] array) {
        buffer.append(BRACKET_START);
        for (int i = 0; i < array.length; i++) {
            if (i > 0) {
                buffer.append(COMMA);
            }
            appendDetail(buffer, array[i]);
        }
        buffer.append(BRACKET_END);
    }

    private void appendDetail(final StringBuffer buffer, final double[] array) {
        buffer.append(BRACKET_START);
        for (int i = 0; i < array.length; i++) {
            if (i > 0) {
                buffer.append(COMMA);
            }
            appendDetail(buffer, array[i]);
        }
        buffer.append(BRACKET_END);
    }

    private void appendDetail(final StringBuffer buffer, final float[] array) {
        buffer.append(BRACKET_START);
        for (int i = 0; i < array.length; i++) {
            if (i > 0) {
                buffer.append(COMMA);
            }
            appendDetail(buffer, array[i]);
        }
        buffer.append(BRACKET_END);
    }

    private void appendDetail(final StringBuffer buffer, final boolean[] array) {
        buffer.append(BRACKET_START);
        for (int i = 0; i < array.length; i++) {
            if (i > 0) {
                buffer.append(COMMA);
            }
            appendDetail(buffer, array[i]);
        }
        buffer.append(BRACKET_END);
    }

    private void appendDetail(final StringBuffer buffer, final Object[] array) {
        buffer.append(BRACKET_START);
        for (int i = 0; i < array.length; i++) {
            final Object item = array[i];
            if (i > 0) {
                buffer.append(COMMA);
            }
            if (item == null) {
                buffer.append(NULL);

            } else {
                appendInternal(buffer, item);
            }
        }
        buffer.append(BRACKET_END);
    }

    private void appendDetail(final StringBuffer buffer, final Object value) {
        buffer.append(value);//如果要打印对象，请重新该对象的toPrintString方法
    }

    /**
     * 将驼峰式命名的字符串转换为下划线方式。如果转换前的驼峰式命名的字符串为空，则返回空字符串。<br>
     * 例如：
     * <pre>
     * HelloWorld=》hello_world
     * Hello_World=》hello_world
     * HelloWorld_test=》hello_world_test
     * </pre>
     *
     * @param str 转换前的驼峰式命名的字符串，也可以为下划线形式
     * @return 转换后下划线方式命名的字符串
     */
    public static String toUnderlineCase(CharSequence str) {
        return toSymbolCase(str, '_');
    }

    /**
     * 将驼峰式命名的字符串转换为使用符号连接方式。如果转换前的驼峰式命名的字符串为空，则返回空字符串。<br>
     *
     * @param str    转换前的驼峰式命名的字符串，也可以为符号连接形式
     * @param symbol 连接符
     * @return 转换后符号连接方式命名的字符串
     * @since 4.0.10
     */
    public static String toSymbolCase(CharSequence str, char symbol) {
        if (str == null) {
            return null;
        }

        final int length = str.length();
        final StringBuilder sb = new StringBuilder();
        char c;
        for (int i = 0; i < length; i++) {
            c = str.charAt(i);
            final Character preChar = (i > 0) ? str.charAt(i - 1) : null;
            if (Character.isUpperCase(c)) {
                //遇到大写字母处理
                final Character nextChar = (i < str.length() - 1) ? str.charAt(i + 1) : null;
                if (null != preChar && Character.isUpperCase(preChar)) {
                    //前一个字符为大写，则按照一个词对待
                    sb.append(c);
                } else if (null != nextChar && Character.isUpperCase(nextChar)) {
                    //后一个为大写字母，按照一个词对待
                    if (null != preChar && symbol != preChar) {
                        //前一个是非大写时按照新词对待，加连接符
                        sb.append(symbol);
                    }
                    sb.append(c);
                } else {
                    //前后都为非大写按照新词对待
                    if (null != preChar && symbol != preChar) {
                        //前一个非连接符，补充连接符
                        sb.append(symbol);
                    }
                    sb.append(Character.toLowerCase(c));
                }
            } else {
                if (sb.length() > 0 && Character.isUpperCase(sb.charAt(sb.length() - 1)) && symbol != c) {
                    //当结果中前一个字母为大写，当前为小写，说明此字符为新词开始（连接符也表示新词）
                    sb.append(symbol);
                }
                //小写或符号
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 将下划线方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。<br>
     * 例如：hello_world=》helloWorld
     *
     * @param name 转换前的下划线大写方式命名的字符串
     * @return 转换后的驼峰式命名的字符串
     */
    public static String toCamelCase(CharSequence name) {
        if (null == name) {
            return null;
        }

        String name2 = name.toString();
        if (name2.contains(UNDERLINE)) {
            final StringBuilder sb = new StringBuilder(name2.length());
            boolean upperCase = false;
            for (int i = 0; i < name2.length(); i++) {
                char c = name2.charAt(i);

                if (c == '_') {
                    upperCase = true;
                } else if (upperCase) {
                    sb.append(Character.toUpperCase(c));
                    upperCase = false;
                } else {
                    sb.append(Character.toLowerCase(c));
                }
            }
            return sb.toString();
        } else {
            return name2;
        }
    }

    /**
     * <p>省略超长的内容，使用"..."替换</p>
     *
     * <pre>
     * StringUtils.abbreviate(null, *)      = null
     * StringUtils.abbreviate("", 4)        = ""
     * StringUtils.abbreviate("abcdefg", 6) = "abc..."
     * StringUtils.abbreviate("abcdefg", 7) = "abcdefg"
     * StringUtils.abbreviate("abcdefg", 8) = "abcdefg"
     * StringUtils.abbreviate("abcdefg", 4) = "a..."
     * StringUtils.abbreviate("abcdefg", 3) = IllegalArgumentException
     * </pre>
     *
     * @param str
     * @param maxWidth
     * @return
     */
    public static String abbreviate(final String str, final int maxWidth) {
        final String defaultAbbrevMarker = "...";
        return abbreviate(str, defaultAbbrevMarker, maxWidth);
    }


    /**
     * <p>省略超长的内容，使用指定字符替换</p>
     *
     * <pre>
     * StringUtils.abbreviate(null, "...", *)      = null
     * StringUtils.abbreviate("abcdefg", null, *)  = "abcdefg"
     * StringUtils.abbreviate("", "...", 4)        = ""
     * StringUtils.abbreviate("abcdefg", ".", 5)   = "abcd."
     * StringUtils.abbreviate("abcdefg", ".", 7)   = "abcdefg"
     * StringUtils.abbreviate("abcdefg", ".", 8)   = "abcdefg"
     * StringUtils.abbreviate("abcdefg", "..", 4)  = "ab.."
     * StringUtils.abbreviate("abcdefg", "..", 3)  = "a.."
     * StringUtils.abbreviate("abcdefg", "..", 2)  = IllegalArgumentException
     * StringUtils.abbreviate("abcdefg", "...", 3) = IllegalArgumentException
     * </pre>
     *
     * @param str
     * @param abbrevMarker
     * @param maxWidth
     * @return
     */
    public static String abbreviate(final String str, final String abbrevMarker, final int maxWidth) {
        if (isEmpty(str) || isEmpty(abbrevMarker)) {
            return str;
        }

        final int abbrevMarkerLength = abbrevMarker.length();
        final int minAbbrevWidth = abbrevMarkerLength + 1;

        if (maxWidth < minAbbrevWidth) {
            throw new IllegalArgumentException(String.format("Minimum abbreviation width is %d", minAbbrevWidth));
        }
        if (str.length() <= maxWidth) {
            return str;
        }
        return str.substring(str.length() - (maxWidth - abbrevMarkerLength)) + abbrevMarker;
    }
}
