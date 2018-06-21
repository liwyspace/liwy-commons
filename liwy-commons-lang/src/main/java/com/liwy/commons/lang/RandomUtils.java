package com.liwy.commons.lang;

import java.util.Random;
import java.util.UUID;

/**
 * <p>常用随机生成工具类</p>
 *
 * <ul>
 *  <li><b>nextBoolean</b>
 *      - 返回随机的布尔值</li>
 *  <li><b>nextBytes</b>
 *      - 创建随机字节数组</li>
 *  <li><b>nextInt</b>
 *      - 返回随机整数</li>
 *  <li><b>nextLong</b>
 *      - 随机的long型</li>
 *  <li><b>nextDouble</b>
 *      - 随机的double型</li>
 *  <li><b>nextFloat</b>
 *      - 随机的浮点类型</li>
 *  <li><b>random</b>
 *      - 随机的字符串</li>
 *  <li><b>randomAscii</b>
 *      - 随机ascii打印码字符串</li>
 *  <li><b>randomAlphabetic</b>
 *      - 随机字母串</li>
 *  <li><b>randomNumeric</b>
 *      - 随机数字串</li>
 *  <li><b>randomUUID</b>
 *      - 生成UUID</li>
 * </ul>
 *
 * @author liwy
 * @version v1.0.1
 */
public class RandomUtils {
	
	//---------------随机数值类型
	/**
	 * 随机方法使用的随机对象。这必须不是随机方法的局部，以便在同一毫秒内不返回相同的值。
	 */
	private static final Random RANDOM = new Random();
	
	/**
     * <p> 返回随机的布尔值</p>
     *
     * @return 随机的布尔值
     */
	public static boolean nextBoolean() {
		return RANDOM.nextBoolean();
	}
	
	/**
     * <p>创建随机字节数组</p>
     * 
     * @param count 返回数组的大小
     * @return 返回的随机字节数组
     * @throws IllegalArgumentException 如果 {@code count} 是错误的
     */
	public static byte[] nextBytes(final int count) {
		if (count < 0) {
            throw new IllegalArgumentException("Count cannot be negative.");
        }
        final byte[] result = new byte[count];
        RANDOM.nextBytes(result);
        return result;
	}
	
	/**
     * <p>返回范围内的随机整数</p>
     * 
     * @param startInclusive 可以返回的最小值必须是非负的
     * @param endExclusive 上限（不包括）
     * @throws IllegalArgumentException 如果 {@code startInclusive > endExclusive} 
     * 		或如果{@code startInclusive} 是否定的
     * @return 随机的整数
     */
    public static int nextInt(final int startInclusive, final int endExclusive) {
    	if(endExclusive<startInclusive) {
    		throw new IllegalArgumentException("Start value must be smaller or equal to end value.");
    	}
    	if(startInclusive<0) {
    		throw new IllegalArgumentException("Both range values must be non-negative.");
    	}

        if (startInclusive == endExclusive) {
            return startInclusive;
        }
        
        return startInclusive + RANDOM.nextInt(endExclusive - startInclusive);
    }

    /**
     * <p>返回一个0-最大值的随机整数 </p>
     *
     * @return 随机的整数
     * @see #nextInt(int, int)
     */
    public static int nextInt() {
        return nextInt(0, Integer.MAX_VALUE);
    }
	
    /**
     * <p>在指定范围内返回随机long型</p>
     * 
     * @param startInclusive 可以返回的最小值必须是非负的
     * @param endExclusive 上限（不包括）
     * @throws IllegalArgumentException
     *             如果 {@code startInclusive > endExclusive} 或如果
     *             {@code startInclusive} 是否定的
     * @return 随机的long
     */
    public static long nextLong(final long startInclusive, final long endExclusive) {
    	
    	if(endExclusive<startInclusive) {
    		throw new IllegalArgumentException("Start value must be smaller or equal to end value.");
    	}
    	if(startInclusive<0) {
    		throw new IllegalArgumentException("Both range values must be non-negative.");
    	}

        if (startInclusive == endExclusive) {
            return startInclusive;
        }

        return (long) nextDouble(startInclusive, endExclusive);
    }

    /**
     * <p>返回一个在0-最大的long型数据</p>
     *
     * @return 随机的long型
     * @see #nextLong(long, long)
     */
    public static long nextLong() {
        return nextLong(0, Long.MAX_VALUE);
    }
    
    /**
     * <p>在指定范围内返回随机double类型</p>
     * 
     * @param startInclusive 可以返回的最小值必须是非负的
     * @param endInclusive 上限（含）
     * @throws IllegalArgumentException
     *             如果 {@code startInclusive > endInclusive} 或如果
     *             {@code startInclusive} 是否定的
     * @return 随机的double型数据
     */
    public static double nextDouble(final double startInclusive, final double endInclusive) {
    	if(endInclusive<startInclusive) {
    		throw new IllegalArgumentException("Start value must be smaller or equal to end value.");
    	}
    	if(startInclusive<0) {
    		throw new IllegalArgumentException("Both range values must be non-negative.");
    	}
        if (startInclusive == endInclusive) {
            return startInclusive;
        }
        return startInclusive + ((endInclusive - startInclusive) * RANDOM.nextDouble());
    }
    
    /**
     * <p>返回一个随机的double型数据在0 - 最大值 </p>
     *
     * @return 随机的double型
     * @see #nextDouble(double, double)
     */
    public static double nextDouble() {
        return nextDouble(0, Double.MAX_VALUE);
    }
    
    /**
     * <p>返回指定范围内的随机float类型数据。</p>
     * 
     * @param startInclusive 可以返回的最小值必须是非负的
     * @param endInclusive 上限（含）
     * @throws IllegalArgumentException
     *             如果 {@code startInclusive > endInclusive} 或如果
     *             {@code startInclusive} 是否定的
     * @return 随机的float类型
     */
    public static float nextFloat(final float startInclusive, final float endInclusive) {
    	if(endInclusive<startInclusive) {
    		throw new IllegalArgumentException("Start value must be smaller or equal to end value.");
    	}
    	if(startInclusive<0) {
    		throw new IllegalArgumentException("Both range values must be non-negative.");
    	}
        if (startInclusive == endInclusive) {
            return startInclusive;
        }
        return startInclusive + ((endInclusive - startInclusive) * RANDOM.nextFloat());
    }

    /**
     * <p>返回一个随机的浮点类型在0-最大值</p>
     *
     * @return 随机的浮点类型
     * @see #nextFloat()
     */
    public static float nextFloat() {
        return nextFloat(0, Float.MAX_VALUE);
    }
    
    
    
    //---------------随机字符类型
    /**
     * <p>创建一个随机字符串，其长度是指定的字符数。</p>
     * <p>字符将从所有字符集中选择。</p>
     * 
     * @param count 创建随机字符串的长度
     * @return 随机的字符串
     */
    public static String random(final int count) {
        return random(count, false, false);
    }
    
    /**
     * <p>创建一个随机字符串，其长度是指定的字符数。</p>
     *
     * <p>将从字符串指定的字符集中选择字符，不能为空。
     * 如果为NULL，则使用所有字符集。</p>
     *
     * @param count  创建随机字符串的长度
     * @param chars  包含要使用的字符集的字符串，可能是null，但不能是empty
     * @return 随机的字符串
     * @throws IllegalArgumentException 如果 {@code count} &lt; 0 或者字符串是empty.
     */
    public static String random(final int count, final String chars) {
        if (chars == null) {
            return random(count, 0, 0, false, false, null, RANDOM);
        }
        return random(count, chars.toCharArray());
    }

    /**
     * <p>创建一个随机字符串，其长度是指定的字符数。</p>
     *
     * <p>字符将从指定的字符集中选择。</p>
     *
     * @param count  创建随机字符串的长度
     * @param chars  包含要使用的字符集的字符数组可能是空的
     * @return 随机的字符串
     * @throws IllegalArgumentException if {@code count} &lt; 0.
     */
    public static String random(final int count, final char... chars) {
        if (chars == null) {
            return random(count, 0, 0, false, false, null, RANDOM);
        }
        return random(count, 0, chars.length, false, false, chars, RANDOM);
    }
    
    /**
     * 随机ascii打印码字符串
     * 
     * <pre>
     * StringUtil.randomAscii(12)	=	j\W:}},wbN~=
     * StringUtil.randomAscii(6)	=	<K#5]h
     * </pre>
     * 
     * @param count
     * @return
     */
    public static String randomAscii(final int count) {
        return random(count, 32, 127, false, false);
    }
    
    /**
     * 范围内随机长度的随机ascii字符串
     * @param minLengthInclusive
     * @param maxLengthExclusive
     * @return
     */
    public static String randomAscii(final int minLengthInclusive, final int maxLengthExclusive) {
        return randomAscii(RandomUtils.nextInt(minLengthInclusive, maxLengthExclusive));
    }
    /**
     * 随机字母串
     * 
     * <pre>
     * StringUtil.randomAlpha(12)	=	eIQpoPxMDSUR
     * StringUtil.randomAlpha(6)	=	puCMiK
     * </pre>
     * 
     * @param count
     * @return
     */
    public static String randomAlphabetic(final int count) {
        return random(count, true, false);
    }
    public static String randomAlphabetic(final int minLengthInclusive, final int maxLengthExclusive) {
        return randomAlphabetic(RandomUtils.nextInt(minLengthInclusive, maxLengthExclusive));
    }
    
    public static String randomAlphanumeric(final int count) {
        return random(count, true, true);
    }
    public static String randomAlphanumeric(final int minLengthInclusive, final int maxLengthExclusive) {
        return randomAlphanumeric(RandomUtils.nextInt(minLengthInclusive, maxLengthExclusive));
    }
    public static String randomGraph(final int count) {
        return random(count, 33, 126, false, false);
    }
    public static String randomGraph(final int minLengthInclusive, final int maxLengthExclusive) {
        return randomGraph(RandomUtils.nextInt(minLengthInclusive, maxLengthExclusive));
    }
    /**
     * 随机数字串
     * 
     * <pre>
     * StringUtil.randomNumeric(12)	=	684901171952
     * StringUtil.randomNumeric(6)	=	005598
     * </pre>
     * 
     * @param count
     * @return
     */
    public static String randomNumeric(final int count) {
        return random(count, false, true);
    }
    
    public static String randomNumeric(final int minLengthInclusive, final int maxLengthExclusive) {
        return randomNumeric(RandomUtils.nextInt(minLengthInclusive, maxLengthExclusive));
    }
    
    public static String randomPrint(final int minLengthInclusive, final int maxLengthExclusive) {
        return randomPrint(RandomUtils.nextInt(minLengthInclusive, maxLengthExclusive));
    }
    
    public static String randomPrint(final int count) {
        return random(count, 32, 126, false, false);
    }
    
    
    /**
     * 获取随机字符串
     * 
     * @param count	字符串长度
     * @param letters 显示字母
     * @param numbers 显示数字
     * @return 随机字符串
     */
    public static String random(final int count, final boolean letters, final boolean numbers) {
        return random(count, 0, 0, letters, numbers);
    }
    
    /**
     * 获取随机字符串
     * @param count	字符串长度
     * @param start unicode开始范围
     * @param end  unicode结束范围
     * @param letters 显示字母
     * @param numbers 显示数字
     * @return 随机字符串
     */
    public static String random(int count, int start, int end, boolean letters, boolean numbers) {
    	return random(count, start, end, letters, numbers, null, RANDOM);
    }
    
    /**
     * 获取随机字符串
     * 
     * @param count	字符串长度
     * @param start unicode开始范围
     * @param end  unicode结束范围
     * @param letters 显示字母
     * @param numbers 显示数字
     * @param chars
     * @param random
     * @return 随机的字符串
     */
	public static String random(int count, int start, int end, final boolean letters, final boolean numbers,
			final char[] chars, final Random random) {
		if (count == 0) {
			return "";
		} else if (count < 0) {
			throw new IllegalArgumentException("Requested random string length " + count + " is less than 0.");
		}
		if (chars != null && chars.length == 0) {
			throw new IllegalArgumentException("The chars array must not be empty");
		}

		if (start == 0 && end == 0) {
			if (chars != null) {
				end = chars.length;
			} else {
				if (!letters && !numbers) {
					end = Integer.MAX_VALUE;
				} else {
					end = 'z' + 1;
					start = ' ';
				}
			}
		} else {
			if (end <= start) {
				throw new IllegalArgumentException(
						"Parameter end (" + end + ") must be greater than start (" + start + ")");
			}
		}

		final char[] buffer = new char[count];
		final int gap = end - start;

		while (count-- != 0) {
			char ch;
			if (chars == null) {
				ch = (char) (random.nextInt(gap) + start);
			} else {
				ch = chars[random.nextInt(gap) + start];
			}
			if (letters && Character.isLetter(ch) || numbers && Character.isDigit(ch) || !letters && !numbers) {
				if (ch >= 56320 && ch <= 57343) {
					if (count == 0) {
						count++;
					} else {
						// low surrogate, insert high surrogate after putting it
						// in
						buffer[count] = ch;
						count--;
						buffer[count] = (char) (55296 + random.nextInt(128));
					}
				} else if (ch >= 55296 && ch <= 56191) {
					if (count == 0) {
						count++;
					} else {
						// high surrogate, insert low surrogate before putting
						// it in
						buffer[count] = (char) (56320 + random.nextInt(128));
						count--;
						buffer[count] = ch;
					}
				} else if (ch >= 56192 && ch <= 56319) {
					// private high surrogate, no effing clue, so skip it
					count++;
				} else {
					buffer[count] = ch;
				}
			} else {
				count++;
			}
		}
		return new String(buffer);
	}

	/**
	 * 生成UUID
	 *
	 * @param
	 * @return java.lang.String
	 */
    public static String randomUUID() {
        return UUID.randomUUID().toString();
    }
    
}
