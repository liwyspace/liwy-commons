package com.liwy.commons.lang;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>常用的字符串操作工具类</p>
 * 
 * <ul>
 *  <li><b>isEmpty</b>
 *      - 检查字符串是否为空</li>
 *  <li><b>isBlank</b>
 *      - 检查字符串是否为空白</li>
 *  <li><b>trim</b>
 *      - 删除前后空白</li>
 *  <li><b>unicodeEscaped</b>
 *      - 获取字符的unicode编码</li>
 *  <li><b>str2unicode</b>
 *      - 字符串转Unicode串</li>
 *  <li><b>unicode2str</b>
 *      - Unicode串转字符串</li>
 *  <li><b>changeCharset</b>
 *      - 改变字符串的字符集</li>
 *  <li><b>half2Full</b>
 *      - 改变字符串中半角字符为全角字符</li>
 *  <li><b>full2Half</b>
 *      - 改变字符串中全角字符为半角字符</li>
 * </ul>
 * 
 * <p>{@code StringUtils}类型定义与字符串处理相关的某些单词。</p>
 * 
 * <ul>
 *  <li>null - {@code null}</li>
 *  <li>empty - 一零长度的字符串 ({@code ""})</li>
 *  <li>space - 空字符 ({@code ' '}, char 32)</li>
 *  <li>whitespace - 定义的字符 {@link Character#isWhitespace(char)}</li>
 *  <li>trim - the characters &lt;= 32 as in {@link String#trim()}</li>
 * </ul>
 * 
 * <p>{@code StringUtils} handles {@code null} input Strings quietly.
 * 也就是说 {@code null} input will return {@code null}.
 * Where a {@code boolean} or {@code int} is being returned
 * details vary by method.</p>
 *
 * <p>A side effect of the {@code null} handling is that a
 * {@code NullPointerException} should be considered a bug in
 * {@code StringUtils}.</p>
 *
 * <p>方法在这个类中给出示例代码来解释它们的操作。
 * 符号 {@code *} 用于指示任何输入，包括 {@code null}.</p>
 *
 * <p>#线程安全的#</p>
 * 
 * @author liwy
 * @version 1.0
 */
public class StringUtils {
	/**
	 * The empty String {@code ""}.
	 * @since 2.0
	 */
	public static final String EMPTY = "";
	
	private static final char[] HEX_DIGITS = new char[] {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
	
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
     * <p>清除左右空白，如果为null则返回""</p>
     *
     * <pre>
     * StringUtils.trim(null)       = ""
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
        return str == null ? "" : str.trim();
    }

	// Delete
	//-----------------------------------------------------------------------
	/**
	 * <p>Deletes all whitespaces from a String as defined by
	 * {@link Character#isWhitespace(char)}.</p>
	 *
	 * <pre>
	 * StringUtils.deleteWhitespace(null)         = null
	 * StringUtils.deleteWhitespace("")           = ""
	 * StringUtils.deleteWhitespace("abc")        = "abc"
	 * StringUtils.deleteWhitespace("   ab  c  ") = "abc"
	 * </pre>
	 *
	 * @param str  the String to delete whitespace from, may be null
	 * @return the String without whitespaces, {@code null} if null String input
	 */
	public static String deleteWhitespace(final String str) {
		if (isEmpty(str)) {
			return str;
		}
		final int sz = str.length();
		final char[] chs = new char[sz];
		int count = 0;
		for (int i = 0; i < sz; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				chs[count++] = str.charAt(i);
			}
		}
		if (count == sz) {
			return str;
		}
		return new String(chs, 0, count);
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
        Pattern pattern = Pattern.compile("(\\\\u([0-9a-fA-F]{4}))");
        Matcher matcher = pattern.matcher(str);
        char ch;
        while (matcher.find()) {
            ch = (char) Integer.parseInt(matcher.group(2), 16);
            str = str.replace(matcher.group(1), ch + "");    
        }
        return str;
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
     * @param str 要转码的字符串
     * @param oldCharset 原字符集
     * @param newCharset 新字符集
     * @return	转码后的字符串
     * @throws UnsupportedEncodingException 
     */
    public static String changeCharset(String str, String oldCharset, String newCharset) throws UnsupportedEncodingException {
    	return new String(str.getBytes(oldCharset),newCharset);
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
			if (c >= 33 && c < 127)
				sb.append((char) (c + 65248));
			else
				sb.append(str.charAt(i));
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
		if (isEmpty(str))
			return "";
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c >= 65281 && c < 65375)
				sb.append((char) (c - 65248));
			else
				sb.append(str.charAt(i));
		}
		return sb.toString();
	}

	// ------printObject()

	/**
	 * 将对象转换为可读的字符串
	 * 主要处理数组对象，Collection、Map的下级对象
	 * 对于自定义对象主要是通过调用该对象重写的toString方法
	 * @param value
	 * @return
	 */
	public String toPrintString(Object value) {
		StringBuffer sb = new StringBuffer();
		appendInternal(sb,value);
		return sb.toString();
	}
	String arrayStart="[";
	String arraySeparator=",";
	String arrayEnd="]";
	String nullText="null";

	/**
	 * 转换为String的核心逻辑
	 * @param buffer
	 * @param value
	 */
	private void appendInternal(final StringBuffer buffer, final Object value) {
		if (value == null) {
			buffer.append(nullText);
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
		int i=0;
		while(iter.hasNext()) {
			if (i>0){
				buffer.append(arraySeparator);
			}
			appendInternal(buffer,iter.next());
			i++;
		}
		buffer.append("]");
	}
	private void appendDetail(final StringBuffer buffer, final Map<?, ?> map) {
		buffer.append("{");
		Iterator<?> iter = map.entrySet().iterator();
		int i=0;
		while(iter.hasNext()) {
			if (i>0){
				buffer.append(arraySeparator);
			}
			Map.Entry<?, ?> map1 = (Map.Entry<?, ?>) iter.next();
			appendInternal(buffer,map1.getKey());
			buffer.append("=");
			appendInternal(buffer,map1.getValue());
			i++;
		}
		buffer.append("}");
	}
	//long[]、int[]、short[]、byte[]等类型不能直接转换为Object[],所以要特殊处理
	private void appendDetail(final StringBuffer buffer, final long[] array) {

		buffer.append(arrayStart);
		for (int i = 0; i < array.length; i++) {
			if (i > 0) {
				buffer.append(arraySeparator);
			}
			appendDetail(buffer, array[i]);
		}
		buffer.append(arrayEnd);
	}
	private void appendDetail(final StringBuffer buffer, final int[] array) {
		buffer.append(arrayStart);
		for (int i = 0; i < array.length; i++) {
			if (i > 0) {
				buffer.append(arraySeparator);
			}
			appendDetail(buffer, array[i]);
		}
		buffer.append(arrayEnd);
	}
	private void appendDetail(final StringBuffer buffer, final short[] array) {
		buffer.append(arrayStart);
		for (int i = 0; i < array.length; i++) {
			if (i > 0) {
				buffer.append(arraySeparator);
			}
			appendDetail(buffer, array[i]);
		}
		buffer.append(arrayEnd);
	}
	private void appendDetail(final StringBuffer buffer, final byte[] array) {
		buffer.append(arrayStart);
		for (int i = 0; i < array.length; i++) {
			if (i > 0) {
				buffer.append(arraySeparator);
			}
			appendDetail(buffer, array[i]);
		}
		buffer.append(arrayEnd);
	}
	private void appendDetail(final StringBuffer buffer, final char[] array) {
		buffer.append(arrayStart);
		for (int i = 0; i < array.length; i++) {
			if (i > 0) {
				buffer.append(arraySeparator);
			}
			appendDetail(buffer, array[i]);
		}
		buffer.append(arrayEnd);
	}
	private void appendDetail(final StringBuffer buffer, final double[] array) {
		buffer.append(arrayStart);
		for (int i = 0; i < array.length; i++) {
			if (i > 0) {
				buffer.append(arraySeparator);
			}
			appendDetail(buffer, array[i]);
		}
		buffer.append(arrayEnd);
	}
	private void appendDetail(final StringBuffer buffer, final float[] array) {
		buffer.append(arrayStart);
		for (int i = 0; i < array.length; i++) {
			if (i > 0) {
				buffer.append(arraySeparator);
			}
			appendDetail(buffer, array[i]);
		}
		buffer.append(arrayEnd);
	}
	private void appendDetail(final StringBuffer buffer, final boolean[] array) {
		buffer.append(arrayStart);
		for (int i = 0; i < array.length; i++) {
			if (i > 0) {
				buffer.append(arraySeparator);
			}
			appendDetail(buffer, array[i]);
		}
		buffer.append(arrayEnd);
	}
	private void appendDetail(final StringBuffer buffer, final Object[] array) {
		buffer.append(arrayStart);
		for (int i = 0; i < array.length; i++) {
			final Object item = array[i];
			if (i > 0) {
				buffer.append(arraySeparator);
			}
			if (item == null) {
				buffer.append(nullText);

			} else {
				appendInternal(buffer, item);
			}
		}
		buffer.append(arrayEnd);
	}
	private void appendDetail(final StringBuffer buffer, final Object value) {
		buffer.append(value);//如果要打印对象，请重新该对象的toPrintString方法
	}





	public static final String UNDERLINE = "_";
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
	 * @param str 转换前的驼峰式命名的字符串，也可以为符号连接形式
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
					if(null != preChar && symbol != preChar) {
						//前一个是非大写时按照新词对待，加连接符
						sb.append(symbol);
					}
					sb.append(c);
				} else {
					//前后都为非大写按照新词对待
					if (null != preChar &&symbol != preChar) {
						//前一个非连接符，补充连接符
						sb.append(symbol);
					}
					sb.append(Character.toLowerCase(c));
				}
			} else {
				if(sb.length() > 0 && Character.isUpperCase(sb.charAt(sb.length() -1)) && symbol != c) {
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
}
