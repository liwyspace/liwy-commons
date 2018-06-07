package com.liwy.commons.lang.math;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MathUtils {
	
	/**
	 * 将double类型转换为字符串，并防止出现科学记数法
	 * @param x
	 * @return
	 */
	public static String double2str(double x) {
		BigDecimal bx = new BigDecimal(Double.toString(x));
		return bx.toString();
	}
	
	/**
	 * 格式化double类型
	 * @param x
	 * @param format
	 * 		0	数字，如果不存在则显示为0
	 * 		#	数字，如果不存在则显示为空
	 * 		.	小数点
	 * 		,	分隔符
	 * 		E	科学计数法
	 * 		%	百分符
	 * 		具体参数写法可参考DecimalFormat的参数模式
	 * @return
	 */
	public static String formatDouble(double x, String format) {
		DecimalFormat df = new DecimalFormat(format);
		return df.format(x);
	}
	
	/**
	 * 准确的加法
	 * 
	 * 例如：
	 * 0.1+0.2=0.30000000000000004
	 * 1+(-0.9)=0.09999999999999998
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public static double add(double x, double y) {
		BigDecimal bx = new BigDecimal(Double.toString(x));
		BigDecimal by = new BigDecimal(Double.toString(y));
		return bx.add(by).doubleValue();
	}
	
	public static double add(String x, String y) {
		BigDecimal bx = new BigDecimal(x);
		BigDecimal by = new BigDecimal(y);
		return bx.add(by).doubleValue();
	}
	
	/**
	 * 精确减法
	 * 
	 * 例如：
	 * 0.1-(-0.2)=0.30000000000000004
	 * 1-0.9=0.09999999999999998
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public static double subtract(double x, double y) {
		BigDecimal bx = new BigDecimal(Double.toString(x));
		BigDecimal by = new BigDecimal(Double.toString(y));
		return bx.subtract(by).doubleValue();
	}
	
	public static double subtract(String x, String y) {
		BigDecimal bx = new BigDecimal(x);
		BigDecimal by = new BigDecimal(y);
		return bx.subtract(by).doubleValue();
	}
	
	/**
	 * 精确乘法
	 * 
	 * 例如：
	 * 6.9*7=48.300000000000004
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public static double multiply(double x, double y) {
		BigDecimal bx = new BigDecimal(Double.toString(x));
		BigDecimal by = new BigDecimal(Double.toString(y));
		return bx.multiply(by).doubleValue();
	}
	
	public static double multiply(String x, String y) {
		BigDecimal bx = new BigDecimal(x);
		BigDecimal by = new BigDecimal(y);
		return bx.multiply(by).doubleValue();
	}
	
	/**
	 * 精确除法
	 * 
	 * 例如：
	 * 123.3/100=1.2329999999999999
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public static double divide(double x, double y) {
		BigDecimal bx = new BigDecimal(Double.toString(x));
		BigDecimal by = new BigDecimal(Double.toString(y));
		return bx.divide(by).doubleValue();
	}
	
	public static double divide(String x, String y) {
		BigDecimal bx = new BigDecimal(x);
		BigDecimal by = new BigDecimal(y);
		return bx.divide(by).doubleValue();
	}
	
	/**
	 * 进入，远离零
	 * @param x
	 * @param n
	 * @return
	 */
	public static double round_up(double x,int n) {
		BigDecimal bx = new BigDecimal(Double.toString(x));
		return bx.setScale(n, BigDecimal.ROUND_UP).doubleValue();
	}
	
	/**
	 * 舍去，接近零
	 * @param x
	 * @param n
	 * @return
	 */
	public static double round_down(double x,int n) {
		BigDecimal bx = new BigDecimal(Double.toString(x));
		return bx.setScale(n, BigDecimal.ROUND_DOWN).doubleValue();
	}
	
	/**
	 * 四舍五入
	 * @param x
	 * @param n
	 * @return
	 */
	public static double round_halfUp(double x,int n) {
		BigDecimal bx = new BigDecimal(Double.toString(x));
		return bx.setScale(n, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	/**
	 * 五舍六入
	 * @param x
	 * @param n
	 * @return
	 */
	public static double round_halfDown(double x,int n) {
		BigDecimal bx = new BigDecimal(Double.toString(x));
		return bx.setScale(n, BigDecimal.ROUND_HALF_DOWN).doubleValue();
	}
	
	/**
	 * 银行家舍入，四舍六入五考虑，五后非零就进一，五后为零看奇偶，五前为偶应舍去，五前为奇要进一
	 * @param x
	 * @param n
	 * @return
	 */
	public static double round_halfEven(double x,int n) {
		BigDecimal bx = new BigDecimal(Double.toString(x));
		return bx.setScale(n, BigDecimal.ROUND_HALF_EVEN).doubleValue();
	}
	
	/**
	 * 正入负舍，接近正无穷
	 * @param x
	 * @param n
	 * @return
	 */
	public static double round_ceiling(double x,int n) {
		BigDecimal bx = new BigDecimal(Double.toString(x));
		return bx.setScale(n, BigDecimal.ROUND_CEILING).doubleValue();
	}
	
	/**
	 * 正舍负入，接近负无穷
	 * @param x
	 * @param n
	 * @return
	 */
	public static double round_floor(double x,int n) {
		BigDecimal bx = new BigDecimal(Double.toString(x));
		return bx.setScale(n, BigDecimal.ROUND_FLOOR).doubleValue();
	}
	
	/**
	 * 十进制转换为其他进制字符串
	 * @param i
	 * @param radix
	 * 		2	二进制
	 * 		8	八进制
	 * 		16	十六进制
	 * 
	 * @return
	 */
	public static String toString(int i, int radix) {
		return Integer.toString(i, radix);
	}
	
	/**
	 * 其他进制字符串转换为十进制整数
	 * @param s
	 * @param radix
	 * @return
	 */
	public static int valeOf(String s, int radix) {
		return Integer.valueOf(s, radix);
	}
	
	/**
	 * 生成一个0到n的随机数，包括0不包括n
	 * @param n
	 * @return
	 */
	public static int random(int n) {
		Random random = new Random();
		return random.nextInt(n);
	}
	
	/**
	 * 生成一个32位的随机ID
	 * @return
	 */
	public static String randomUUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}
	
	/**
	 * 是否是整数或是浮点数,但默认-05.15这种也认为是正确的格式
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) {
		Pattern p = Pattern.compile("^([-+]?\\d+)(\\.\\d+)?$");
		Matcher m = p.matcher(str);
		return m.matches();
	}
	
	/**
	 * 计算简单四则表达式，通过逆波兰式的算法；只支持'+','-','*','/','(',')'六个运算符
	 * @param str
	 * @return
	 */
	public static String calculate(String str) {
		List<String> list = parseRPExpress(str);
		return calculateRPExpress(list);
	}
	
	/**
	 * 预处理表达式，去除空白字符和在正负号'-'，'+'前加'0'
	 * @param express
	 * @return
	 */
	private static String adjustExpress(String express) {
		express = express.replaceAll("\\s", "");
		if(express.startsWith("-")||express.startsWith("+")) {
			express = "0" + express;
		}
		express = express.replaceAll("\\(\\-", "(0-");
		express = express.replaceAll("\\(\\+", "(0+");
		return express;
	}
	
	/**
	 * 将中缀表达式转换为逆波兰表达式
	 * @param str
	 * @return
	 */
	public static List<String> parseRPExpress(String str) {
		Stack<String> stack = new Stack<String>();//存放操作符的栈
		List<String> returnList = new ArrayList<String>(); //返回逆波兰表达式
		String positions = "+-*/()";
		
		StringTokenizer st = new StringTokenizer(adjustExpress(str), positions, true);
		while(st.hasMoreElements()) {
			String token = st.nextToken();
			 //如果是操作数直接输出
			if(positions.indexOf(token)<0) {
				returnList.add(token);
			} else {
				//如果栈为空或元素为“(”或栈顶元素为“(”,直接入栈
				if(stack.empty() || "(".equals(token) || "(".equals(stack.peek())) {
					stack.push(token);
				//如果元素为“)”,从栈顶输出元素直到遇到“(”,并丢弃“(”
				} else if(")".equals(token)) {
					while (!stack.empty()) {
						String top = stack.pop();
						if(!"(".equals(top)) {
							returnList.add(top);
						} else {
							break;
						}
					}
				//如果元素小于等于栈顶元素，从栈顶输出元素直到该元素大于栈顶元素或栈顶元素为“(”,最后将元素入栈
				} else if(positions.indexOf(token)<=positions.indexOf(stack.peek())) {
					while (!stack.empty() && !"(".equals(stack.peek()) && positions.indexOf(token)<=positions.indexOf(stack.peek())) {
						returnList.add(stack.pop());
					}
					stack.push(token);
				//如果元素大于栈顶元素，直接入栈
				} else {
					stack.push(token);
				}
			}
		}
		//将栈中剩余的元素输出
		while (!stack.empty()) {
			returnList.add(stack.pop());
		}
		return returnList;
	}
	
	/**
	 * 计算逆波兰表达式
	 * @param revPolish
	 * @return
	 */
	public static String calculateRPExpress(List<String> revPolish) {
		Stack<String> numberStack = new Stack<String>();  //存放操作数的栈
		for(int i=0;i<revPolish.size();i++) {
			String poi = revPolish.get(i);
			if(isNumber(poi)) {
				numberStack.push(poi);
			} else {
				BigDecimal tempNumber1 = new BigDecimal(numberStack.pop());
				BigDecimal tempNumber2 = new BigDecimal(numberStack.pop());
				BigDecimal tempNumber = new BigDecimal("0");
				if ("+".equals(poi)) {
					tempNumber = tempNumber2.add(tempNumber1);
				} else if ("-".equals(poi)) {
					tempNumber = tempNumber2.subtract(tempNumber1);
				} else if ("*".equals(poi)) {
					tempNumber = tempNumber2.multiply(tempNumber1);
				} else if ("/".equals(poi)) {
					tempNumber = tempNumber2.divide(tempNumber1);
				}
				numberStack.push(tempNumber.toPlainString());
			}
		}
		return numberStack.pop();
	}
	
	/**
	 * 人民币金额小写转大写，最大支持千亿级
	 * @param num
	 * @return
	 */
	public static String numToCny(double num) {
		String digit[] = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
		String unit[] = {"仟","佰","拾","亿","仟","佰","拾","万","仟","佰","拾","元","角","分"};
		if(num==0) {
			return "";
		}
		String numStr = formatDouble(num, "#0.00");
		numStr = numStr.replaceAll("\\.|-", "");
		String returnStr = "";
		for(int i=0;i<numStr.length();i++) {
			int index = Integer.parseInt(Character.toString(numStr.charAt(i)));
			returnStr += digit[index]+unit[unit.length-numStr.length()+i];
		}
		returnStr = returnStr.replaceAll("零(仟|佰|拾|角)", "零");
		returnStr = returnStr.replaceAll("零+", "零");
		returnStr = returnStr.replaceAll("零(万|亿|元)", "$1");
		returnStr = returnStr.replaceAll("亿万", "亿");
		returnStr = returnStr.replaceAll("^元零?|零分", "");
		returnStr = returnStr.replaceAll("元$", "元整");
		return (num<0?"负":"") + returnStr;
	}
}
