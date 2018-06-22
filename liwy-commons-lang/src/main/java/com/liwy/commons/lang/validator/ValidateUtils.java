package com.liwy.commons.lang.validator;

import com.liwy.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * <p>常用的校验工具类</p>
 *
 * <ul>
 *  <li><b>isMatch</b>
 *      - 是否匹配正则表达式</li>
 *  <li><b>isEmpty</b>
 *      - 校验对象是否为空</li>
 *  <li><b>isNumber</b>
 *      - 校验是否为数字</li>
 *  <li><b>isNoNegativeInt</b>
 *      - 校验是否为非负整数</li>
 *  <li><b>isGeneral</b>
 *      - 校验是否为英文字母数字下划线</li>
 *  <li><b>isChineseChar</b>
 *      - 校验中文字符(包括汉字和符号)</li>
 *  <li><b>isChinese</b>
 *      - 校验汉字</li>
 *  <li><b>isPostcode</b>
 *      - 校验邮编</li>
 *  <li><b>isMobile</b>
 *      - 校验手机号</li>
 *  <li><b>isTelephone</b>
 *      - 校验电话号</li>
 *  <li><b>isIP</b>
 *      - 校验IP地址</li>
 *  <li><b>isUrl</b>
 *      - 校验HTTP地址</li>
 *  <li><b>isEmail</b>
 *      - 校验邮箱</li>
 *  <li><b>isPlateNumber</b>
 *      - 校验中国车牌号</li>
 *  <li><b>isDate</b>
 *      - 验证yyyy-MM-dd格式的日期校验，已考虑平闰年</li>
 *  <li><b>isIdCard</b>
 *      - 校验身份证号</li>
 * </ul>
 *
 * @author liwy
 * @version v1.0.1
 */
public class ValidateUtils {
    /** 数字 */
    public final static String NUMBERS = "^([-+]?\\d+)(\\.\\d+)?$";
    /** 非负整数 */
    public final static String NONEGATIVEINT = "^\\d+$";
    /** 英文字母 、数字和下划线 */
    public final static String GENERAL = "^\\w+$";
    /** 中文字符 */
    public final static String CHINESECHAR = "^[\u0391-\uFFE5]+$";
    /** 汉字 */
    public final static String CHINESE = "^[\u4e00-\u9fa5]+$";
    /** 中国邮编 */
    public final static String POSTCODE = "^\\d{6}$";
    /** 手机号 */
    public final static String MOBILE = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(16[6])|(17[0,1,3,5-8])|(18[0-9])|(19[8,9]))\\d{8}$";
    /** 电话 */
    public final static String TELEPHONE = "(^[0-9]{3,4}-[0-9]{7,8}-[0-9]{3,4}$)|(^[0-9]{3,4}-[0-9]{7,8}$)|(^[0-9]{7,8}-[0-9]{3,4}$)|(^[0-9]{7,15}$)";
    /** IP地址 */
    public final static String IPADDRES = "((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)";
    /** HTTP 地址 */
    public final static String HTTPURL = "(https://|http://)?([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
    /** 邮件，符合RFC 5322规范，正则来自：http://emailregex.com/ */
    public final static String EMAIL = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    /** 中国车牌号码 */
    public final static String PLATENUMBER = "^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$";
    /** yyyy-MM-dd格式的日期校验，已考虑平闰年 */
    public final static String DATE = "^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$";

	/**
     * 正则验证方法
     * 
     * @param regStr 正则表达式
     * @param str 待验证的字符串
     * @return
     */
    public static boolean isMatch(String regStr, String str) {
        if (StringUtils.isEmpty(regStr)) {
            return false;
        }
        if(str == null) {
            return false;
        }
        Pattern pattern = Pattern.compile(regStr);
        return isMatch(pattern, str);
    }

    public static boolean isMatch(Pattern pattern, String content) {
        if (content == null || pattern == null) {
            return false;
        }
        return pattern.matcher(content).matches();
    }

    /**
     * 验证是否为空<br>
     * 对于String类型判定是否为empty(null 或 "")<br>
     *
     * @param value 值
     * @return 是否为空
     * @return 是否为空
     */
    public static boolean isEmpty(Object value) {
        return (null == value || (value instanceof String && StringUtils.isEmpty((String) value)));
    }
	
	/**
	 * 校验是否为数字
	 * @param number
	 * @return
	 */
	public static boolean isNumber(String number) {
		return isMatch(NUMBERS,number);
	}
	
	/**
	 * 校验是否为非负整数
	 * @param number
	 * @return
	 */
	public static boolean isNoNegativeInt(String number) {
		return isMatch(NONEGATIVEINT,number);
	}

    /**
     * 验证是否为英文字母 、数字和下划线
     *
     * @param value 值
     * @return 是否为英文字母 、数字和下划线
     */
    public static boolean isGeneral(String value) {
        return isMatch(GENERAL, value);
    }
	
	/**
     * 判断中文字符(包括汉字和符号)
     * 
     * @param text
     * @return
     * @author jiqinlin
     */
    public final static boolean isChineseChar(String text){
        return isMatch(CHINESECHAR, text);
    }
    
    /**
     * 匹配汉字
     * 
     * @param text
     * @return
     * @author jiqinlin
     */
    public final static boolean isChinese(String text){
        return isMatch(CHINESE, text);
    }

    /**
     * 验证输入邮政编号
     *
     * @param str 待验证的字符串
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean isPostcode(String str) {
        return isMatch(POSTCODE, str);
    }

    /**
     * 手机验证
     *
     * @param mobile
     * @return
     */
    public static boolean isMobile(String mobile) {
        return isMatch(MOBILE, mobile);
    }

    /**
     * 电话验证
     *
     * @param Tel
     * @return
     */
    public static boolean isTelephone(String Tel) {
        return isMatch(TELEPHONE, Tel);
    }

    /**
     * 验证IP地址
     *
     * @param str 待验证的字符串
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean isIP(String str) {
        return isMatch(IPADDRES, str);
    }

    /**
     * 验证网址Url
     *
     * @param str 待验证的字符串
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean isUrl(String str) {
        return isMatch(HTTPURL, str);
    }

    /**
     * 邮箱验证
     *
     * @param mail
     * @return
     */
    public static boolean isEmail(String mail) {
        Pattern pattern = Pattern.compile(EMAIL, Pattern.CASE_INSENSITIVE);
        return isMatch(pattern, mail);
    }

    /**
     * 中国车牌号验证
     *
     * @param number
     * @return boolean
     */
    public static boolean isPlateNumber(String number) {
        return isMatch(PLATENUMBER, number);
    }

    /**
     * 验证yyyy-MM-dd格式的日期校验，已考虑平闰年
     */
    public static boolean isDate(String input) {
        return isMatch(DATE, input);
    }

    /**
     * 身份证验证
     * @param idcard
     * @return
     */
    public static boolean isIdCard(String idcard) {
        HashMap<Integer, String> area = new HashMap<Integer, String>();
        area.put(11, "北京");
        area.put(12, "天津");
        area.put(13, "河北");
        area.put(14, "山西");
        area.put(15, "内蒙古");
        area.put(21, "辽宁");
        area.put(22, "吉林");
        area.put(23, "黑龙江");
        area.put(31, "上海");
        area.put(32, "江苏");
        area.put(33, "浙江");
        area.put(34, "安徽");
        area.put(35, "福建");
        area.put(36, "江西");
        area.put(37, "山东");
        area.put(41, "河南");
        area.put(42, "湖北");
        area.put(43, "湖南");
        area.put(44, "广东");
        area.put(45, "广西");
        area.put(46, "海南");
        area.put(50, "重庆");
        area.put(51, "四川");
        area.put(52, "贵州");
        area.put(53, "云南");
        area.put(54, "西藏");
        area.put(61, "陕西");
        area.put(62, "甘肃");
        area.put(63, "青海");
        area.put(64, "宁夏");
        area.put(65, "新疆");
        area.put(71, "台湾");
        area.put(81, "香港");
        area.put(82, "澳门");
        area.put(91, "国外");
        if(StringUtils.isBlank(idcard)) return false;
        if(area.get(Integer.parseInt(idcard.substring(0, 2)))==null) return false;
        if(!(idcard.length()==15||idcard.length()==18)) return false;
        if(idcard.length()==15){
            //老身份证
            int year = Integer.parseInt(idcard.substring(2,6))+1900;
            String ereg;
            if (year % 4 == 0||(year% 100 == 0 &&year % 4 == 0 )){
                ereg="^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$";//测试出生日期的合法性
             }else{
                 ereg="^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$";//测试出生日期的合法性
             }
             if(isMatch(ereg, idcard))
                 return true;
             else
                 return false;
              
        }else if(idcard.length()==18){
            //新省份证
             //18位身份号码检测
             //出生日期的合法性检查
             //闰年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))
             //平年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))
            int year = Integer.parseInt(idcard.substring(2,6))+1900;
            String ereg;
             if (year % 4 == 0 ||(year % 100 == 0 && year%4 == 0 )){
                 ereg="^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$";//闰年出生日期的合法性正则表达式
             }else{
                 ereg="^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$";//平年出生日期的合法性正则表达式
             }
             if(isMatch(ereg, idcard)){//测试出生日期的合法性
             //计算校验位
                 int[] idcards = new int[18];
                 for (int i = 0; i < idcard.length(); i++) {
                    idcards[i]=Integer.parseInt(idcard.substring(i, i+1));
                }
                 int S = (idcards[0] + idcards[10]) * 7
                 + (idcards[1] + idcards[11]) * 9
                 + (idcards[2] + idcards[12]) * 10
                 + (idcards[3] + idcards[13]) * 5
                 + (idcards[4] + idcards[14]) * 8
                 + (idcards[5] + idcards[15]) * 4
                 + (idcards[6] + idcards[16]) * 2
                 + idcards[7] * 1
                 + idcards[8] * 6
                 + idcards[9] * 3 ;
                 int Y = S % 11;
                 String M = "F";
                 String JYM = "10X98765432";
                 M = JYM.substring(Y,Y+1);//判断校验位
                 if(M.equalsIgnoreCase(String.valueOf(idcards[17])))
                         return true; //检测ID的校验位
                 else
                     return false;
             }
             else
                 return false;
        }
        return false;
    }
}
