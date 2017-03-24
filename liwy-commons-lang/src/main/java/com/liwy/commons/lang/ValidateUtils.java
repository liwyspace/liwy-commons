package com.liwy.commons.lang;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateUtils {
	/**
     * 正则验证方法
     * 
     * @param regStr 正则表达式
     * @param str 待验证的字符串
     * @return
     */
    public static boolean match(String regStr, String str) {
        Pattern pattern = Pattern.compile(regStr);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
    
    /**
     * <p>校验是否为空字符串 ("") 或 null.</p>
     *
     * <pre>
     * StringUtil.isEmpty(null)      = true
     * StringUtil.isEmpty("")        = true
     * StringUtil.isEmpty(" ")       = false
     * StringUtil.isEmpty("bob")     = false
     * StringUtil.isEmpty("  bob  ") = false
     * StringUtil.isEmpty("\n") = false
     * StringUtil.isEmpty("\t") = false
     * </pre>
     *
     * @param cs
     * @return
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
     * StringUtil.isBlank("bob")     = false
     * StringUtil.isBlank("  bob  ") = false
     * StringUtil.isBlank("\n")       = true
     * StringUtil.isBlank("\t")       = true
     * </pre>
     *
     * @param cs
     * @return
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
	 * 校验是否为数字
	 * @param number
	 * @return
	 */
	public static boolean isNumber(String number) {
		String regstr = "^([-+]?\\d+)(\\.\\d+)?$";
		return match(regstr,number);
	}
	
	/**
	 * 校验是否为非负整数
	 * @param number
	 * @return
	 */
	public static boolean isNoNegativeInt(String number) {
		String regstr = "^\\d+$";
		return match(regstr,number);
	}
	
	/**
     * 判断中文字符(包括汉字和符号)
     * 
     * @param text
     * @return
     * @author jiqinlin
     */
    public final static boolean isChineseChar(String text){
        return match(text, "^[\u0391-\uFFE5]+$");
    }
    
    /**
     * 匹配汉字
     * 
     * @param text
     * @return
     * @author jiqinlin
     */
    public final static boolean isChinese(String text){
        return match(text, "^[\u4e00-\u9fa5]+$");
    }
    
    //-------------------------特定字符串验证
	
	/**
     * 手机验证
     * 
     * @param mobile
     * @return
     */
    public static boolean isMobile(String mobile) {
        String regstr = "^(13[4,5,6,7,8,9]|15[0,8,9,1,7]|188|187)\\d{8}$";
        return match(regstr, mobile);
    }
    
    /**
     * 电话验证
     * 
     * @param Tel
     * @return
     */
    public static boolean isTelephone(String Tel) {
        String regstr = "(^[0-9]{3,4}-[0-9]{7,8}-[0-9]{3,4}$)|(^[0-9]{3,4}-[0-9]{7,8}$)|(^[0-9]{7,8}-[0-9]{3,4}$)|(^[0-9]{7,15}$)";
        return match(regstr, Tel);
    }
    
    /**
     * 邮箱验证
     * 
     * @param mail
     * @return
     */
    public static boolean isEmail(String mail) {
        String regstr = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        return match(regstr, mail);
    }
    
    /**
     * 验证输入邮政编号
     * 
     * @param str 待验证的字符串
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean isPostcode(String str) {
	    String regex = "^\\d{6}$";
	    return match(regex, str);
    }
    
    /**
     * 验证IP地址
     * 
     * @param str 待验证的字符串
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean isIP(String str) {
	    String num = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";
	    String regex = "^" + num + "\\." + num + "\\." + num + "\\." + num + "$";
	    return match(regex, str);
    }
    
    /**
     * 验证网址Url
     * 
     * @param str 待验证的字符串
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean isUrl(String str) {
	    String regex = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
	    return match(regex, str);
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
             if(match(ereg, idcard)) 
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
             if(match(ereg, idcard)){//测试出生日期的合法性
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
