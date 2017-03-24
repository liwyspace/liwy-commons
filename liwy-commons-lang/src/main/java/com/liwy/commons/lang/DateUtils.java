package com.liwy.commons.lang;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * Date:
 * @author liwy-psbc
 *
 */
public class DateUtils {
	
	/**
	 * 将指定格式的字符串转换为日期类型
	 * 
	 * @param dateStr 待转换的日期字符串
	 * @param pattern 转换模板
	 * @return 转换后的日期类型
	 */
	public static Date toDate(String dateStr, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		Date date = null;
		try {
			date = format.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 将日期类型转换为指定格式的日期字符串
	 * 
	 * @param date 待转换的日期类型
	 * @param pattern 转换规则
	 * @return 转换后的字符串
	 */
	public static String toStr(Date date, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}
	
    /**
     * 将 {@code Date} 类型转为  {@code Calendar}类型 
     * 
     * @param date 待转换的Date类型
     * @return 转换后的Calendar类型
     */
    public static Calendar toCalendar(final Date date) {
        final Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c;
    }
    
    /**
     * 将指定时区的{@code Date} 类型转为 {@code Calendar}类型
     * 
     * @param date 待转换的date类型
     * @param tz Date类型的时区
     * @return the created Calendar
     */
    public static Calendar toCalendar(final Date date, final TimeZone tz) {
    	final Calendar c = Calendar.getInstance(tz);
    	c.setTime(date);
    	return c;
    }
    
    /**
	 * 将日期类型转换为数据库时间戳
	 * @param date
	 * @return
	 */
	public static Timestamp toTimestamp(Date date) {
		Timestamp timestamp = new Timestamp(date.getTime());
		return timestamp;
	}
	
	/**
	 * 将指定日期字符串转换为数据库时间戳
	 * @param dateStr 带转换的日期字符串
	 * @param pattern 转换规则
	 * @return
	 */
	public static Timestamp toTimestamp(String dateStr, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		Date date = null;
		try {
			date = format.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Timestamp timestamp = new Timestamp(date.getTime());
		return timestamp;
	}
	
    //------获取指定日期的元素
	/**
	 * 获取指定日期的年
	 * @param date 指定的日期
	 * @return 返回的年份
	 */
	public static int getYear(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}
	
	/**
	 * 获取指定日期的月
	 * @param date 指定的日期
	 * @return 返回的月份
	 */
	public static int getMonth(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH)+1;
	}
	
	/**
	 * 获取指定日期的日
	 * @param date 指定的日期
	 * @return
	 */
	public static int getDate(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DATE);
	}
	
	/**
	 * 获取指定日期的星期
	 * @param date 指定的日期
	 * @return
	 */
	public static Week getWeek(Date date){
		Week week = null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int weekNum = calendar.get(Calendar.DAY_OF_WEEK);
		switch(weekNum) {
		case 1:
			week=Week.SUNDAY;  //星期日
			break;
		case 2:
			week=Week.MONDAY;  //星期一
			break;
		case 3:
			week=Week.TUESDAY;  //星期二
			break;
		case 4:
			week=Week.WEDNESDAY;  //星期三
			break;
		case 5:
			week=Week.THURSDAY;  //星期四
			break;
		case 6:
			week=Week.FRIDAY;  //星期五
			break;
		case 7:
			week=Week.SATURDAY;  //星期六
			break;
		}
		return week;
	}
	/**
	 * 星期的枚举类
	 * @author liwy-psbc
	 *
	 */
	public enum Week {

		MONDAY("星期一", "Monday", "Mon.", 1),
		TUESDAY("星期二", "Tuesday", "Tues.", 2),
		WEDNESDAY("星期三", "Wednesday", "Wed.", 3),
		THURSDAY("星期四", "Thursday","Thur.", 4),
		FRIDAY("星期五", "Friday", "Fri.", 5),
		SATURDAY("星期六","Saturday", "Sat.", 6),
		SUNDAY("星期日", "Sunday", "Sun.", 7);

		private String name_cn;
		private String name_en;
		private String name_enShort;
		private int number;

		private Week(String name_cn, String name_en, String name_enShort, int number) {
			this.name_cn = name_cn;
			this.name_en = name_en;
			this.name_enShort = name_enShort;
			this.number = number;
		}

		public String getChineseName() {
			return name_cn;
		}

		public String getName() {
			return name_en;
		}

		public String getShortName() {
			return name_enShort;
		}

		public int getNumber() {
			return number;
		}
	}
	
	/**
	 * 获取指定日期的小时
	 * @param date 指定的日期
	 * @return
	 */
	public static int getHour(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.HOUR);
	}
	
	/**
	 * 获取指定日期的小时-24小时制
	 * @param date
	 * @return
	 */
	public static int getHour24(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}
	
	/**
	 * 获取指定日期的分钟
	 * @param date
	 * @return
	 */
	public static int getMinute(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MINUTE);
	}
	
	/**
	 * 获取指定日期的秒
	 * @param date
	 * @return
	 */
	public static int getSecond(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.SECOND);
	}
	
	/**
	 * 获取指定日期的毫秒
	 * @param date
	 * @return
	 */
	public static int getMillisecond(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MILLISECOND);
	}
	
	/**
	 * 获取指定日期当月的最大天数
	 * @param date
	 * @return
	 */
	public static int getMaxDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * 获取指定年月的最大天数
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getMaxDate(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month-1);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
	
	/**
     * <p>指定级别内的第几毫秒</p>
     * 
     * @param date the date to work with, not null
     * @param fragment the {@code Calendar} field part of date to calculate 
     * @return number of milliseconds within the fragment of date
     * @throws IllegalArgumentException if the date is <code>null</code> or
     * fragment is not supported
     * @since 2.4
     */
    public static long getFragmentInMilliseconds(final Date date, final int fragment) {
        return getFragment(date, fragment, TimeUnit.MILLISECONDS);    
    }
    
    /**
     * <p>指定级别内的第几秒</p>
     * 
     * @param date the date to work with, not null
     * @param fragment the {@code Calendar} field part of date to calculate 
     * @return number of seconds within the fragment of date
     * @throws IllegalArgumentException if the date is <code>null</code> or
     * fragment is not supported
     * @since 2.4
     */
    public static long getFragmentInSeconds(final Date date, final int fragment) {
        return getFragment(date, fragment, TimeUnit.SECONDS);
    }
    
    /**
     * <p>指定级别内的第几分</p>
     * 
     * @param date the date to work with, not null
     * @param fragment the {@code Calendar} field part of date to calculate 
     * @return number of minutes within the fragment of date
     * @throws IllegalArgumentException if the date is <code>null</code> or 
     * fragment is not supported
     * @since 2.4
     */
    public static long getFragmentInMinutes(final Date date, final int fragment) {
        return getFragment(date, fragment, TimeUnit.MINUTES);
    }
    
    /**
     * <p>指定级别内的第几小时</p>
     * 
     * @param date the date to work with, not null
     * @param fragment the {@code Calendar} field part of date to calculate 
     * @return number of hours within the fragment of date
     * @throws IllegalArgumentException if the date is <code>null</code> or 
     * fragment is not supported
     * @since 2.4
     */
    public static long getFragmentInHours(final Date date, final int fragment) {
        return getFragment(date, fragment, TimeUnit.HOURS);
    }
    
    /**
     * <p>指定级别内的第几天</p>
     * 
     * @param date the date to work with, not null
     * @param fragment the {@code Calendar} field part of date to calculate 
     * @return number of days  within the fragment of date
     * @throws IllegalArgumentException if the date is <code>null</code> or 
     * fragment is not supported
     * @since 2.4
     */
    public static long getFragmentInDays(final Date date, final int fragment) {
        return getFragment(date, fragment, TimeUnit.DAYS);
    }

    /**
     * <p>指定级别内的第几毫秒</p>
     * 
     * @param calendar the calendar to work with, not null
     * @param fragment the {@code Calendar} field part of calendar to calculate 
     * @return number of milliseconds within the fragment of date
     * @throws IllegalArgumentException if the date is <code>null</code> or 
     * fragment is not supported
     * @since 2.4
     */
  public static long getFragmentInMilliseconds(final Calendar calendar, final int fragment) {
    return getFragment(calendar, fragment, TimeUnit.MILLISECONDS);
  }
    /**
     * <p>指定级别内的第几秒</p>
     * 
     * @param calendar the calendar to work with, not null
     * @param fragment the {@code Calendar} field part of calendar to calculate 
     * @return number of seconds within the fragment of date
     * @throws IllegalArgumentException if the date is <code>null</code> or 
     * fragment is not supported
     * @since 2.4
     */
    public static long getFragmentInSeconds(final Calendar calendar, final int fragment) {
        return getFragment(calendar, fragment, TimeUnit.SECONDS);
    }
    
    /**
     * <p>指定级别内的第几分</p>
     * 
     * @param calendar the calendar to work with, not null
     * @param fragment the {@code Calendar} field part of calendar to calculate 
     * @return number of minutes within the fragment of date
     * @throws IllegalArgumentException if the date is <code>null</code> or 
     * fragment is not supported
     * @since 2.4
     */
    public static long getFragmentInMinutes(final Calendar calendar, final int fragment) {
        return getFragment(calendar, fragment, TimeUnit.MINUTES);
    }
    
    /**
     * <p>指定级别内的第几小时</p>
     *  
     * @param calendar the calendar to work with, not null
     * @param fragment the {@code Calendar} field part of calendar to calculate 
     * @return number of hours within the fragment of date
     * @throws IllegalArgumentException if the date is <code>null</code> or 
     * fragment is not supported
     * @since 2.4
     */
    public static long getFragmentInHours(final Calendar calendar, final int fragment) {
        return getFragment(calendar, fragment, TimeUnit.HOURS);
    }
    
    /**
     * <p>指定级别内的第几天</p>
     * 
     * @param calendar the calendar to work with, not null
     * @param fragment the {@code Calendar} field part of calendar to calculate 
     * @return number of days within the fragment of date
     * @throws IllegalArgumentException if the date is <code>null</code> or 
     * fragment is not supported
     * @since 2.4
     */
    public static long getFragmentInDays(final Calendar calendar, final int fragment) {
        return getFragment(calendar, fragment, TimeUnit.DAYS);
    }
    
    /**
     * <p>指定级别内的时间</p>
     * 
     * @param date the date to work with, not null
     * @param fragment the Calendar field part of date to calculate 
     * @param unit the time unit
     * @return number of units within the fragment of the date
     * @throws IllegalArgumentException if the date is <code>null</code> or 
     * fragment is not supported
     * @since 2.4
     */
    private static long getFragment(final Date date, final int fragment, final TimeUnit unit) {
        if(date == null) {
            throw  new IllegalArgumentException("The date must not be null");
        }
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return getFragment(calendar, fragment, unit);
    }

    /**
     * <p>指定级别内的时间</p>
     * 
     * @param calendar the calendar to work with, not null
     * @param fragment the Calendar field part of calendar to calculate 
     * @param unit the time unit
     * @return number of units within the fragment of the calendar
     * @throws IllegalArgumentException if the date is <code>null</code> or 
     * fragment is not supported
     * @since 2.4
     */
    private static long getFragment(final Calendar calendar, final int fragment, final TimeUnit unit) {
        if(calendar == null) {
            throw  new IllegalArgumentException("The date must not be null"); 
        }

        long result = 0;
        
        final int offset = (unit == TimeUnit.DAYS) ? 0 : 1;
        
        // Fragments bigger than a day require a breakdown to days
        switch (fragment) {
            case Calendar.YEAR:
                result += unit.convert(calendar.get(Calendar.DAY_OF_YEAR) - offset, TimeUnit.DAYS);
                break;
            case Calendar.MONTH:
                result += unit.convert(calendar.get(Calendar.DAY_OF_MONTH) - offset, TimeUnit.DAYS);
                break;
            default:
                break;
        }

        switch (fragment) {
            // Number of days already calculated for these cases
            case Calendar.YEAR:
            case Calendar.MONTH:
            
            // The rest of the valid cases
            case Calendar.DAY_OF_YEAR:
            case Calendar.DATE:
                result += unit.convert(calendar.get(Calendar.HOUR_OF_DAY), TimeUnit.HOURS);
                //$FALL-THROUGH$
            case Calendar.HOUR_OF_DAY:
                result += unit.convert(calendar.get(Calendar.MINUTE), TimeUnit.MINUTES);
                //$FALL-THROUGH$
            case Calendar.MINUTE:
                result += unit.convert(calendar.get(Calendar.SECOND), TimeUnit.SECONDS);
                //$FALL-THROUGH$
            case Calendar.SECOND:
                result += unit.convert(calendar.get(Calendar.MILLISECOND), TimeUnit.MILLISECONDS);
                break;
            case Calendar.MILLISECOND: break;//never useful
                default: throw new IllegalArgumentException("The fragment " + fragment + " is not supported");
        }
        return result;
    }
	
	//-----------日期的计算
	/**
	 * 获取num年前/后的日期
	 * @param date 指定的日期
	 * @param num 正数为后，负数为前
	 * @return
	 */
	public static Date addYear(Date date, int num) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, num);
		return calendar.getTime();
	}
	
	/**
	 * 获取num月前/后的日期
	 * @param date 指定的日期
	 * @param num 正数为后，负数为前
	 * @return
	 */
	public static Date addMonth(Date date, int num) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, num);
		return calendar.getTime();
	}
	
	/**
	 * 获取num日前/后的日期
	 * @param date 指定的日期
	 * @param num 正数为后，负数为前
	 * @return
	 */
	public static Date addDate(Date date, int num) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, num);
		return calendar.getTime();
	}
	
	/**
	 * 获取num周前/后的日期
	 * @param date 指定的日期
	 * @param num 正数为后，负数为前
	 * @return
	 */
	public static Date addWeek(Date date, int num) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.WEDNESDAY, num);
		return calendar.getTime();
	}
	
	/**
	 * 获取num小时前/后的日期
	 * @param date 指定的日期
	 * @param num 正数为后，负数为前
	 * @return
	 */
	public static Date addHour(Date date, int num) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR, num);
		return calendar.getTime();
	}
	
	/**
	 * 获取num分前/后的日期
	 * @param date 指定的日期
	 * @param num 正数为后，负数为前
	 * @return
	 */
	public static Date addMinute(Date date, int num) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, num);
		return calendar.getTime();
	}
	
	/**
	 * 获取num秒前/后的日期
	 * @param date 指定的日期
	 * @param num 正数为后，负数为前
	 * @return
	 */
	public static Date addSecond(Date date, int num) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.SECOND, num);
		return calendar.getTime();
	}
	
	/**
	 * 获取num毫秒前/后的日期
	 * @param date 指定的日期
	 * @param num 正数为后，负数为前
	 * @return
	 */
	public static Date addMillisecond(Date date, int num) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MILLISECOND, num);
		return calendar.getTime();
	}
	
	
	//-----获取两个日期差
	/**
	 * 计算两个日期相差的天数
	 * @param date
	 * @param odate
	 * @return
	 */
	public static long getIntervalDays(Date date, Date odate) {
		long num = date.getTime()-odate.getTime();
		return num/(1000*60*60*24);
	}
	
	/**
	 * 计算两个日期相差的小时
	 * @param date
	 * @param odate
	 * @return
	 */
	public static long getIntervalHours(Date date, Date odate) {
		long num = date.getTime()-odate.getTime();
		return num/(1000*60*60);
	}
	
	/**
	 * 计算两个日期相差的分钟
	 * @param date
	 * @param odate
	 * @return
	 */
	public static long getIntervalMinutes(Date date, Date odate) {
		long num = date.getTime()-odate.getTime();
		return num/(1000*60);
	}
	
	/**
	 * 计算两个日期相差的秒
	 * @param date
	 * @param odate
	 * @return
	 */
	public static long getIntervalSeconds(Date date, Date odate) {
		long num = date.getTime()-odate.getTime();
		return num/1000;
	}
	
	/**
	 * 计算两个日期相差的毫秒
	 * @param date
	 * @param odate
	 * @return
	 */
	public static long getIntervalMilliseconds(Date date, Date odate) {
		long num = date.getTime()-odate.getTime();
		return num;
	}
	
	/**
	 * 计算两个日期相差的时间
	 * @param date
	 * @param odate
	 * @return XX天XX小时XX分XX秒XX毫秒
	 */
	public static String getIntervalDate(Date date, Date odate) {
		long oD = 1000*60*60*24;
		long oH = 1000*60*60;
		long oM = 1000*60;
		long oS = 1000;
		
		long num = date.getTime()-odate.getTime();
		long day=num/oD;   
		long hour=num%oD/oH; 
		long min=num%oD%oH/oM;
		long s=num%oD%oH%oM/oS;  
		long ms=num%oD%oH%oM%oS;  
		return day+"天"+hour+"小时"+min+"分"+s+"秒"+ms+"毫秒";
	}
	
	
	 //-------比较日期
    /**
     * <p>判断两个Date是否是同一天</p>
     *
     * @param date1  第一个Date
     * @param date2  第二个Date
     * @return true 如果是同一天
     * @throws IllegalArgumentException 如果有Date是null
     */
    public static boolean isSameDay(final Date date1, final Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        final Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        final Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return isSameDay(cal1, cal2);
    }

    /**
     * <p>判断两个Calendar是否是同一天</p>
     * 
     * @param cal1 第一个Calendar
     * @param cal2 第二个Calendar
     * @return true 如果是同一天
     * @throws IllegalArgumentException 如果有calendar是 <code>null</code>
     */
    public static boolean isSameDay(final Calendar cal1, final Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }

    //-----------------------------------------------------------------------
    /**
     * <p>判断两个Date是否是同一时间</p>
     *
     * @param date1  第一个Date
     * @param date2  第二个Date
     * @return true 如果是同一时间
     * @throws IllegalArgumentException 如果有Date是null
     */
    public static boolean isSameInstant(final Date date1, final Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return date1.getTime() == date2.getTime();
    }

    /**
     * <p>判断两个Calendar是否是同一时间</p>
     *
     * @param cal1 第一个Calendar
     * @param cal2 第二个Calendar
     * @return true 如果是同一时间
     * @throws IllegalArgumentException 如果有calendar是 <code>null</code>
     */
    public static boolean isSameInstant(final Calendar cal1, final Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return cal1.getTime().getTime() == cal2.getTime().getTime();
    }

    /**
     * <p>判断两个Calendar是否代表两个相同的本地时间</p>
     *
     * @param cal1 第一个Calendar
     * @param cal2 第二个Calendar
     * @return true 如果是同一时间
     * @throws IllegalArgumentException 如果有calendar是 <code>null</code>
     */
    public static boolean isSameLocalTime(final Calendar cal1, final Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return cal1.get(Calendar.MILLISECOND) == cal2.get(Calendar.MILLISECOND) &&
                cal1.get(Calendar.SECOND) == cal2.get(Calendar.SECOND) &&
                cal1.get(Calendar.MINUTE) == cal2.get(Calendar.MINUTE) &&
                cal1.get(Calendar.HOUR_OF_DAY) == cal2.get(Calendar.HOUR_OF_DAY) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
                cal1.getClass() == cal2.getClass();
    }
    
    /**
     * 判断截取后的日期是否相等
     * 
     * @param cal1 the first calendar, not <code>null</code>
     * @param cal2 the second calendar, not <code>null</code>
     * @param field the field from {@code Calendar}
     * @return <code>true</code> if equal; otherwise <code>false</code>
     * @throws IllegalArgumentException if any argument is <code>null</code>
     * @see #truncate(Calendar, int)
     * @see #truncatedEquals(Date, Date, int)
     * @since 3.0
     */
    public static boolean truncatedEquals(final Calendar cal1, final Calendar cal2, final int field) {
        return truncatedCompareTo(cal1, cal2, field) == 0;
    }

    /**
     * 判断截取后的日期是否相等
     * 
     * @param date1 the first date, not <code>null</code>
     * @param date2 the second date, not <code>null</code>
     * @param field the field from {@code Calendar}
     * @return <code>true</code> if equal; otherwise <code>false</code>
     * @throws IllegalArgumentException if any argument is <code>null</code>
     * @see #truncate(Date, int)
     * @see #truncatedEquals(Calendar, Calendar, int)
     * @since 3.0
     */
    public static boolean truncatedEquals(final Date date1, final Date date2, final int field) {
        return truncatedCompareTo(date1, date2, field) == 0;
    }

    /**
     * 比较截取后的日期
     * 
     * @param cal1 the first calendar, not <code>null</code>
     * @param cal2 the second calendar, not <code>null</code>
     * @param field the field from {@code Calendar}
     * @return a negative integer, zero, or a positive integer as the first 
     * calendar is less than, equal to, or greater than the second.
     * @throws IllegalArgumentException if any argument is <code>null</code>
     * @see #truncate(Calendar, int)
     * @see #truncatedCompareTo(Date, Date, int)
     */
    public static int truncatedCompareTo(final Calendar cal1, final Calendar cal2, final int field) {
        final Calendar truncatedCal1 = truncate(cal1, field);
        final Calendar truncatedCal2 = truncate(cal2, field);
        return truncatedCal1.compareTo(truncatedCal2);
    }
    
    /**
     * 比较截取后的日期
     * 
     * @param date1 the first date, not <code>null</code>
     * @param date2 the second date, not <code>null</code>
     * @param field the field from <code>Calendar</code>
     * @return a negative integer, zero, or a positive integer as the first 
     * date is less than, equal to, or greater than the second.
     * @throws IllegalArgumentException if any argument is <code>null</code>
     * @see #truncate(Calendar, int)
     * @see #truncatedCompareTo(Date, Date, int)
     */
    public static int truncatedCompareTo(final Date date1, final Date date2, final int field) {
        final Date truncatedDate1 = truncate(date1, field);
        final Date truncatedDate2 = truncate(date2, field);
        return truncatedDate1.compareTo(truncatedDate2);
    }
    
    // -----当前日期的舍入值
    private enum ModifyType {TRUNCATE,ROUND,CEILING}
    
    /**
     * <p>获取离当前日期最接近的指定的字段(四舍五入)</p>
     *
     * @param date  the date to work with, not null
     * @param field  该参数来自于 {@code Calendar} 或 {@code SEMI_MONTH}
     * @return the different rounded date, not null
     * @throws ArithmeticException if the year is over 280 million
     */
    public static Date round(final Date date, final int field) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        final Calendar gval = Calendar.getInstance();
        gval.setTime(date);
        modify(gval, field, ModifyType.ROUND);
        return gval.getTime();
    }

    /**
     * <p>获取离当前日期最接近的指定的字段(四舍五入)</p>
     *
     * @param date  the date to work with, not null
     * @param field  the field from {@code Calendar} or <code>SEMI_MONTH</code>
     * @return the different rounded date, not null
     * @throws IllegalArgumentException if the date is <code>null</code>
     * @throws ArithmeticException if the year is over 280 million
     */
    public static Calendar round(final Calendar date, final int field) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        final Calendar rounded = (Calendar) date.clone();
        modify(rounded, field, ModifyType.ROUND);
        return rounded;
    }

    /**
     * <p>获取离当前日期最接近的指定的字段(四舍五入)</p>
     * 
     * @param date  the date to work with, either {@code Date} or {@code Calendar}, not null
     * @param field  the field from {@code Calendar} or <code>SEMI_MONTH</code>
     * @return the different rounded date, not null
     * @throws IllegalArgumentException if the date is <code>null</code>
     * @throws ClassCastException if the object type is not a {@code Date} or {@code Calendar}
     * @throws ArithmeticException if the year is over 280 million
     */
    public static Date round(final Object date, final int field) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        if (date instanceof Date) {
            return round((Date) date, field);
        } else if (date instanceof Calendar) {
            return round((Calendar) date, field).getTime();
        } else {
            throw new ClassCastException("Could not round " + date);
        }
    }

    /**
     * <p>截去指定字段后的日期，得到的日期(全部舍)</p>
     *
     * @param date  the date to work with, not null
     * @param field  the field from {@code Calendar} or <code>SEMI_MONTH</code>
     * @return the different truncated date, not null
     * @throws IllegalArgumentException if the date is <code>null</code>
     * @throws ArithmeticException if the year is over 280 million
     */
    public static Date truncate(final Date date, final int field) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        final Calendar gval = Calendar.getInstance();
        gval.setTime(date);
        modify(gval, field, ModifyType.TRUNCATE);
        return gval.getTime();
    }

    /**
     * <p>截去指定字段后的日期，得到的日期(全部舍)</p>
     * 
     * @param date  the date to work with, not null
     * @param field  the field from {@code Calendar} or <code>SEMI_MONTH</code>
     * @return the different truncated date, not null
     * @throws IllegalArgumentException if the date is <code>null</code>
     * @throws ArithmeticException if the year is over 280 million
     */
    public static Calendar truncate(final Calendar date, final int field) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        final Calendar truncated = (Calendar) date.clone();
        modify(truncated, field, ModifyType.TRUNCATE);
        return truncated;
    }

    /**
     * <p>截去指定字段后的日期，得到的日期(全部舍)</p>
     * 
     * @param date  the date to work with, either {@code Date} or {@code Calendar}, not null
     * @param field  the field from {@code Calendar} or <code>SEMI_MONTH</code>
     * @return the different truncated date, not null
     * @throws IllegalArgumentException if the date is <code>null</code>
     * @throws ClassCastException if the object type is not a {@code Date} or {@code Calendar}
     * @throws ArithmeticException if the year is over 280 million
     */
    public static Date truncate(final Object date, final int field) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        if (date instanceof Date) {
            return truncate((Date) date, field);
        } else if (date instanceof Calendar) {
            return truncate((Calendar) date, field).getTime();
        } else {
            throw new ClassCastException("Could not truncate " + date);
        }
    }
    
    /**
     * <p>获取当前日期的下一个指定的日期(全部入)</p>
     * 
     * @param date  the date to work with, not null
     * @param field  the field from {@code Calendar} or <code>SEMI_MONTH</code>
     * @return the different ceil date, not null
     * @throws IllegalArgumentException if the date is <code>null</code>
     * @throws ArithmeticException if the year is over 280 million
     */
    public static Date ceiling(final Date date, final int field) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        final Calendar gval = Calendar.getInstance();
        gval.setTime(date);
        modify(gval, field, ModifyType.CEILING);
        return gval.getTime();
    }

    /**
     * <p>获取当前日期的下一个指定的日期(全部入)</p>
     * 
     * @param date  the date to work with, not null
     * @param field  the field from {@code Calendar} or <code>SEMI_MONTH</code>
     * @return the different ceil date, not null
     * @throws IllegalArgumentException if the date is <code>null</code>
     * @throws ArithmeticException if the year is over 280 million
     */
    public static Calendar ceiling(final Calendar date, final int field) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        final Calendar ceiled = (Calendar) date.clone();
        modify(ceiled, field, ModifyType.CEILING);
        return ceiled;
    }

    /**
     * <p>获取当前日期的下一个指定的日期(全部入)</p>
     * 
     * @param date  the date to work with, either {@code Date} or {@code Calendar}, not null
     * @param field  the field from {@code Calendar} or <code>SEMI_MONTH</code>
     * @return the different ceil date, not null
     * @throws IllegalArgumentException if the date is <code>null</code>
     * @throws ClassCastException if the object type is not a {@code Date} or {@code Calendar}
     * @throws ArithmeticException if the year is over 280 million
     */
    public static Date ceiling(final Object date, final int field) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        if (date instanceof Date) {
            return ceiling((Date) date, field);
        } else if (date instanceof Calendar) {
            return ceiling((Calendar) date, field).getTime();
        } else {
            throw new ClassCastException("Could not find ceiling of for type: " + date.getClass());
        }
    }

    private static final int[][] fields = {
    		{Calendar.MILLISECOND},
            {Calendar.SECOND},
            {Calendar.MINUTE},
            {Calendar.HOUR_OF_DAY, Calendar.HOUR},
            {Calendar.DATE, Calendar.DAY_OF_MONTH, Calendar.AM_PM},
            {Calendar.MONTH},
            {Calendar.YEAR},
            {Calendar.ERA}};
    
    /**
     * <p>将Calander舍入</p>
     * 
     * @param val  要转换的Calander，不能为空
     * @param field  转换的级别
     * @param modType  转换类型 truncate, round or ceiling
     * @throws ArithmeticException 如果年大于 280 million
     */
    private static void modify(final Calendar val, final int field, final ModifyType modType) {
        if (val.get(Calendar.YEAR) > 280000000) {
            throw new ArithmeticException("Calendar value too large for accurate calculations");
        }
        if (field == Calendar.MILLISECOND) {
            return;
        }
        
        /*
         * 为了防止截取MDT时区时间到小时级别时出现，时区变为MST，及得到的时间比输入的时间晚了1个小时
         * Date date1 = new Date(1099206060000L);
         * System.out.println(date1);	//Sun Oct 31 01:01:00 MDT 2004
         * System.out.println(DateUtil.truncate(date1, Calendar.HOUR_OF_DAY));  //Sun Oct 31 01:00:00 MST 2004
         * 
         * 因为：即使新值与旧值相同，Calendar.set方法会重置一些字段（例如，DST_OFFSET）。
         * 所以在该方法最后我们需要在将新值设置为一个字段之前添加一个检查。如果此字段没有更改，请不要调用Calendar.set方法。
         * ----------start
         */
        final Date date = val.getTime();
        long time = date.getTime();
        boolean done = false;

        // truncate milliseconds
        final int millisecs = val.get(Calendar.MILLISECOND);
        if (ModifyType.TRUNCATE == modType || millisecs < 500) {
            time = time - millisecs;
        }
        if (field == Calendar.SECOND) {
            done = true;
        }

        // truncate seconds
        final int seconds = val.get(Calendar.SECOND);
        if (!done && (ModifyType.TRUNCATE == modType || seconds < 30)) {
            time = time - (seconds * 1000L);
        }
        if (field == Calendar.MINUTE) {
            done = true;
        }

        // truncate minutes
        final int minutes = val.get(Calendar.MINUTE);
        if (!done && (ModifyType.TRUNCATE == modType || minutes < 30)) {
            time = time - (minutes * 60000L);
        }
        
        // reset time
        if (date.getTime() != time) {
            date.setTime(time);
            val.setTime(date);
        }
        //--------end
        
        boolean roundUp = false; //是否为Up Round
        for (final int[] aField : fields) {
            for (final int element : aField) {
                if (element == field) {
                    if (modType == ModifyType.CEILING || modType == ModifyType.ROUND && roundUp) {
                        if (field == Calendar.AM_PM) {
                            //对特殊的舍入类型，进行特殊处理
                            if (val.get(Calendar.HOUR_OF_DAY) == 0) {
                                val.add(Calendar.HOUR_OF_DAY, 12);
                            } else {
                                val.add(Calendar.HOUR_OF_DAY, -12);
                                val.add(Calendar.DATE, 1);
                            }
                        } else {
                            //We need at add one to this field since the
                            //  last number causes us to round up
                            val.add(aField[0], 1);
                        }
                    }
                    return;
                }
            }
            
            int offset = 0;
            boolean offsetSet = false;
            //对特殊的舍入类型，进行特殊处理
            switch (field) {
                case Calendar.AM_PM:
                    if (aField[0] == Calendar.HOUR_OF_DAY) {
                        offset = val.get(Calendar.HOUR_OF_DAY);
                        if (offset >= 12) {
                            offset -= 12;
                        }
                        roundUp = offset >= 6;
                        offsetSet = true;
                    }
                    break;
                default:
                    break;
            }
            if (!offsetSet) {
                final int min = val.getActualMinimum(aField[0]);
                final int max = val.getActualMaximum(aField[0]);
                offset = val.get(aField[0]) - min; //偏移量
                roundUp = offset > ((max - min) / 2);
            }

            //为了防止MDT变为MST，所以当没有改变时，就不要使用set方法
            if (offset != 0) {
                val.set(aField[0], val.get(aField[0]) - offset);
            }
        }
        throw new IllegalArgumentException("The field " + field + " is not supported");
    }
    
    
    //-------遍历日期
    /**
     * 一周的范围, 星期日开始.
     */
    public static final int RANGE_WEEK_SUNDAY = 1;
    /**
     * 一周的范围, 星期一开始.
     */
    public static final int RANGE_WEEK_MONDAY = 2;
    /**
     * 一周的范围, 以输入的那一天开始.
     */
    public static final int RANGE_WEEK_RELATIVE = 3;
    /**
     * 一周的范围, 以输入那一天为一周的中间
     */
    public static final int RANGE_WEEK_CENTER = 4;
    /**
     * 一个月的范围, 从该月开始周到结束周，周日为一周的开始
     */
    public static final int RANGE_MONTH_SUNDAY = 5;
    /**
     * 一个月的范围, 从该月开始周到结束周，周一为一周的开始
     */
    public static final int RANGE_MONTH_MONDAY = 6;
    
    /**
     * <p>根据指定风格遍历Date或Calendar对象</p>
     *
     * @param focus  待遍历的对象
     * @param rangeStyle  遍历风格
     * @return the date 遍历的结果
     * @throws IllegalArgumentException if the date is <code>null</code>
     * @throws ClassCastException if the object type is not a {@code Date} or {@code Calendar}
     */
    public static Iterator<?> iterator(final Object focus, final int rangeStyle) {
        if (focus == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        if (focus instanceof Date) {
            return iterator((Date) focus, rangeStyle);
        } else if (focus instanceof Calendar) {
            return iterator((Calendar) focus, rangeStyle);
        } else {
            throw new ClassCastException("Could not iterate based on " + focus);
        }
    }
    
    /**
     * <p>根据指定风格遍历Date或Calendar对象</p>
     *
     * @param focus  待遍历的对象
     * @param rangeStyle  遍历风格
     * {@link DateUtils#RANGE_MONTH_SUNDAY}, 
     * {@link DateUtils#RANGE_MONTH_MONDAY},
     * {@link DateUtils#RANGE_WEEK_SUNDAY},
     * {@link DateUtils#RANGE_WEEK_MONDAY},
     * {@link DateUtils#RANGE_WEEK_RELATIVE},
     * {@link DateUtils#RANGE_WEEK_CENTER}
     * @return 遍历的结果
     * @throws IllegalArgumentException if the date is <code>null</code>
     * @throws IllegalArgumentException if the rangeStyle is invalid
     */
    public static Iterator<Calendar> iterator(final Date focus, final int rangeStyle) {
        if (focus == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        final Calendar gval = Calendar.getInstance();
        gval.setTime(focus);
        return iterator(gval, rangeStyle);
    }

    /**
     * <p>根据指定风格遍历Date或Calendar对象</p>
     *
     * @param focus  待遍历的对象
     * @param rangeStyle  遍历风格
     * {@link DateUtils#RANGE_MONTH_SUNDAY}, 
     * {@link DateUtils#RANGE_MONTH_MONDAY},
     * {@link DateUtils#RANGE_WEEK_SUNDAY},
     * {@link DateUtils#RANGE_WEEK_MONDAY},
     * {@link DateUtils#RANGE_WEEK_RELATIVE},
     * {@link DateUtils#RANGE_WEEK_CENTER}
     * @return 遍历的结果
     * @throws IllegalArgumentException if the date is <code>null</code>
     * @throws IllegalArgumentException if the rangeStyle is invalid
     */
    public static Iterator<Calendar> iterator(final Calendar focus, final int rangeStyle) {
        if (focus == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar start = null;
        Calendar end = null;
        int startCutoff = Calendar.SUNDAY; //星期日为开始
        int endCutoff = Calendar.SATURDAY; //星期六为结束
        switch (rangeStyle) {
            case RANGE_MONTH_SUNDAY:
            case RANGE_MONTH_MONDAY:
                start = truncate(focus, Calendar.MONTH); //截取日期到月级别
                end = (Calendar) start.clone();
                end.add(Calendar.MONTH, 1);
                end.add(Calendar.DATE, -1);
                if (rangeStyle == RANGE_MONTH_MONDAY) {
                    startCutoff = Calendar.MONDAY;//周一开始
                    endCutoff = Calendar.SUNDAY;//周日结束
                }
                break;
            case RANGE_WEEK_SUNDAY:
            case RANGE_WEEK_MONDAY:
            case RANGE_WEEK_RELATIVE:
            case RANGE_WEEK_CENTER:
                start = truncate(focus, Calendar.DATE);//截取日期到天级别
                end = truncate(focus, Calendar.DATE);
                switch (rangeStyle) {
                    case RANGE_WEEK_SUNDAY:
                        break;
                    case RANGE_WEEK_MONDAY:
                        startCutoff = Calendar.MONDAY;
                        endCutoff = Calendar.SUNDAY;
                        break;
                    case RANGE_WEEK_RELATIVE:
                        startCutoff = focus.get(Calendar.DAY_OF_WEEK);
                        endCutoff = startCutoff - 1;
                        break;
                    case RANGE_WEEK_CENTER:
                        startCutoff = focus.get(Calendar.DAY_OF_WEEK) - 3;
                        endCutoff = focus.get(Calendar.DAY_OF_WEEK) + 3;
                        break;
                    default:
                        break;
                }
                break;
            default:
                throw new IllegalArgumentException("The range style " + rangeStyle + " is not valid.");
        }
        if (startCutoff < Calendar.SUNDAY) {
            startCutoff += 7;
        }
        if (startCutoff > Calendar.SATURDAY) {
            startCutoff -= 7;
        }
        if (endCutoff < Calendar.SUNDAY) {
            endCutoff += 7;
        }
        if (endCutoff > Calendar.SATURDAY) {
            endCutoff -= 7;
        }
        while (start.get(Calendar.DAY_OF_WEEK) != startCutoff) {
            start.add(Calendar.DATE, -1);
        }
        while (end.get(Calendar.DAY_OF_WEEK) != endCutoff) {
            end.add(Calendar.DATE, 1);
        }
        return new DateIterator(start, end);
    }
    
    /**
     * <p>重写Iterator为DateIterator</p>
     */
    static class DateIterator implements Iterator<Calendar> {
        private final Calendar endFinal;
        private final Calendar spot;
        
        DateIterator(final Calendar startFinal, final Calendar endFinal) {
            super();
            this.endFinal = endFinal;
            spot = startFinal;
            spot.add(Calendar.DATE, -1);
        }

        @Override
        public boolean hasNext() {
            return spot.before(endFinal);
        }

        @Override
        public Calendar next() {
            if (spot.equals(endFinal)) {
                throw new NoSuchElementException();
            }
            spot.add(Calendar.DATE, 1);
            return (Calendar) spot.clone();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    
    
}
