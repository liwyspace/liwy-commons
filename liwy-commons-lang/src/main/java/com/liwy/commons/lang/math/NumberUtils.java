package com.liwy.commons.lang.math;

import com.liwy.commons.lang.StringUtils;

/**
 * <b>模块：</b> <br/>
 * <b>名称：</b> <br/>
 * <b>描述：</b> <br/>
 * <b>作者：</b> wenyao02.li <br/>
 * <b>创建时间：</b> 2018/6/7 16:35 <br/>
 * <b>版本：</b> V1.0 <br/>
 */
public class NumberUtils {

    /**
     * <p>检查字符串是否为有效的Java number。</p>
     *
     * <p>有效数字包括十六进制，用<code>0x</code>或<code>0X</code>限定、八进制数字、科学计数法和用类型限定符标记的数字（例如123L）。</p>
     *
     * <p>已0开头的费十六进制字符串视为八进制字符串。因此字符串<code>09</code>将返回false，因为9不是一个有效的八进制。
     * 但是<code>0</code>强被视为十进制</p>
     *
     * <p><code>null</code> 和空白 {@code String} 将返回
     * <code>false</code>.</p>
     */
    public static boolean isCreatable(final String str) {
        if (StringUtils.isEmpty(str)) {
            return false;
        }
        final char[] chars = str.toCharArray();
        int sz = chars.length;
        boolean hasExp = false;
        boolean hasDecPoint = false;
        boolean allowSigns = false;
        boolean foundDigit = false;
        // deal with any possible sign up front
        final int start = chars[0] == '-' || chars[0] == '+' ? 1 : 0;
        if (sz > start + 1 && chars[start] == '0' && !StringUtils.isContains(str, '.')) { // leading 0, skip if is a decimal number
            if (chars[start + 1] == 'x' || chars[start + 1] == 'X') { // leading 0x/0X
                int i = start + 2;
                if (i == sz) {
                    return false; // str == "0x"
                }
                // checking hex (it can't be anything else)
                for (; i < chars.length; i++) {
                    if ((chars[i] < '0' || chars[i] > '9')
                            && (chars[i] < 'a' || chars[i] > 'f')
                            && (chars[i] < 'A' || chars[i] > 'F')) {
                        return false;
                    }
                }
                return true;
            } else if (Character.isDigit(chars[start + 1])) {
                // leading 0, but not hex, must be octal
                int i = start + 1;
                for (; i < chars.length; i++) {
                    if (chars[i] < '0' || chars[i] > '7') {
                        return false;
                    }
                }
                return true;
            }
        }
        sz--; // don't want to loop to the last char, check it afterwords
        // for type qualifiers
        int i = start;
        // loop to the next to last char or to the last char if we need another digit to
        // make a valid number (e.g. chars[0..5] = "1234E")
        while (i < sz || i < sz + 1 && allowSigns && !foundDigit) {
            if (chars[i] >= '0' && chars[i] <= '9') {
                foundDigit = true;
                allowSigns = false;

            } else if (chars[i] == '.') {
                if (hasDecPoint || hasExp) {
                    // two decimal points or dec in exponent
                    return false;
                }
                hasDecPoint = true;
            } else if (chars[i] == 'e' || chars[i] == 'E') {
                // we've already taken care of hex.
                if (hasExp) {
                    // two E's
                    return false;
                }
                if (!foundDigit) {
                    return false;
                }
                hasExp = true;
                allowSigns = true;
            } else if (chars[i] == '+' || chars[i] == '-') {
                if (!allowSigns) {
                    return false;
                }
                allowSigns = false;
                foundDigit = false; // we need a digit after the E
            } else {
                return false;
            }
            i++;
        }
        if (i < chars.length) {
            if (chars[i] >= '0' && chars[i] <= '9') {
                // no type qualifier, OK
                return true;
            }
            if (chars[i] == 'e' || chars[i] == 'E') {
                // can't have an E at the last byte
                return false;
            }
            if (chars[i] == '.') {
                if (hasDecPoint || hasExp) {
                    // two decimal points or dec in exponent
                    return false;
                }
                // single trailing decimal point after non-exponent is ok
                return foundDigit;
            }
            if (!allowSigns
                    && (chars[i] == 'd'
                    || chars[i] == 'D'
                    || chars[i] == 'f'
                    || chars[i] == 'F')) {
                return foundDigit;
            }
            if (chars[i] == 'l'
                    || chars[i] == 'L') {
                // not allowing L with an exponent or decimal point
                return foundDigit && !hasExp && !hasDecPoint;
            }
            // last character is illegal
            return false;
        }
        // allowSigns is true iff the val ends in 'E'
        // found digit it to make sure weird stuff like '.' and '1E-' doesn't pass
        return !allowSigns && foundDigit;
    }

    /**
     * <p>检查给定字符串是否是可解析的数字.</p>
     *
     * <p>可解析数字包括可被如下方法解析的： {@link Integer#parseInt(String)},
     * {@link Long#parseLong(String)}, {@link Float#parseFloat(String)} or
     * {@link Double#parseDouble(String)}.</p>
     *
     * <p>十六进制和科学计数法是不可被解析的</p>
     */
    public static boolean isParsable(final String str) {
        if (StringUtils.isEmpty(str)) {
            return false;
        }
        if (str.charAt(str.length() - 1) == '.') {
            return false;
        }
        if (str.charAt(0) == '-') {
            if (str.length() == 1) {
                return false;
            }
            return withDecimalsParsing(str, 1);
        }
        return withDecimalsParsing(str, 0);
    }

    private static boolean withDecimalsParsing(final String str, final int beginIdx) {
        int decimalPoints = 0;
        for (int i = beginIdx; i < str.length(); i++) {
            final boolean isDecimalPoint = str.charAt(i) == '.';
            if (isDecimalPoint) {
                decimalPoints++;
            }
            if (decimalPoints > 1) {
                return false;
            }
            if (!isDecimalPoint && !Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
