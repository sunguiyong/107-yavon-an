package com.zt.yavon.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class RegexUtils {
	/**
	 * 手机号验证
	 * 
	 * @param str
	 * @return 验证通过返回true
	 */
	public static boolean isMobile(String str) {
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		p = Pattern.compile("^[1][1,3,4,5,8][0-9]{9}$"); // 验证手机号
		m = p.matcher(str);
		b = m.matches();
		return b;
	}

	/**
	 * 电话号码验证
	 * 
	 * @param str
	 * @return 验证通过返回true
	 */
	public static boolean isPhone(String str) {
		Pattern p1 = null, p2 = null;
		Matcher m = null;
		boolean b = false;
		p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$"); // 验证带区号的
		p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$"); // 验证没有区号的
		if (str.length() > 9) {
			m = p1.matcher(str);
			b = m.matches();
		} else {
			m = p2.matcher(str);
			b = m.matches();
		}
		return b;
	}

	/**
	 * 验证邮箱
	 * 
	 * @param email
	 * @return
	 */
	public static boolean checkEmail(String email) {
		boolean flag = false;
		try {
			String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(email);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
	
	/**
	 * 短信验证码(6位数字)
	 */
	public static boolean checkMsgCode(String code) {
		boolean flag = false;
		try {
			String check = "^[0-9]{6}";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(code);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
	
	/**
	 * 验证日期格式
	 * @param date yyyy-mm-dd
	 * @return
	 */
	public static boolean checkDate(String date) {
		if (date == null) return false;
		try {
			String check = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(date);
			if (!matcher.matches()) return false;
			
			String data[] = date.split("-");
			int year = Integer.parseInt(data[0]);
			int month = Integer.parseInt(data[1]);
			int day = Integer.parseInt(data[2]);
			boolean isLeapYear = (year % 4 == 0 && year % 100 != 0)
					|| (year % 400 == 0);
			if (!(month >= 1 && month <= 12)) return false;
			if (!(day >= 1 && day <= 31)) return false;
			
			if (month == 2) {
				int maxDay = isLeapYear ? 29 : 28;
				if (!(day >= 1 && day <= maxDay)) return false;
			}
			
			if (day == 31) {
				boolean isBigMonth = 
						month == 1 ||
						month == 3 ||
						month == 5 ||
						month == 7 ||
						month == 8 ||
						month == 10 ||
						month == 12;
				return isBigMonth;
			}
			
			return true;
		} catch (Exception e) {
			return false;
		}
	} 
	
	/**
	 * 是否是Url地址
	 * @param str
	 * @return
	 */
	public static boolean isURL(String str){
		boolean flag = false;
        try {
        	//转换为小写
            str = str.toLowerCase();
            String check = "^((https|http|ftp|rtsp|mms)?://)"  
                    + "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" //ftp的user@  
                   + "(([0-9]{1,3}\\.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184  
                     + "|" // 允许IP和DOMAIN（域名） 
                     + "([0-9a-z_!~*'()-]+\\.)*" // 域名- www.  
                     + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\\." // 二级域名  
                    + "[a-z]{2,6})" // first level domain- .com or .museum  
                    + "(:[0-9]{1,4})?" // 端口- :80  
                    + "((/?)|" // a slash isn't required if there is no file name  
                    + "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";  
            Pattern regex = Pattern.compile(check);
    		Matcher matcher = regex.matcher(str);
    		flag = matcher.matches();
        } catch (Exception e) {
        	flag = false;
        }
        
		return flag;
	}
	
	/**
	 * 是否是汉字
	 * @param str
	 * @return
	 */
	public static boolean isChineseCharacter(String str) {
		String check = "[\\u4e00-\\u9fa5]+";
		Pattern regex = Pattern.compile(check);
		Matcher matcher = regex.matcher(str);
		return matcher.matches();
	}
	
	/**
	 * 是否是银行卡号 <br>
	 * Luhn算法来验证:<br>
	 * 1、从卡号最后一位数字开始，逆向将奇数位(1、3、5等等)相加。<br>
	 * 2、从卡号最后一位数字开始，逆向将偶数位数字，先乘以2（如果乘积为两位数，则将其减去9），再求和。<br>
	 * 3、将奇数位总和加上偶数位总和，结果应该可以被10整除。<br>
	 * @param number
	 * @return
	 */
	public static boolean isBankerNumber(String number) {
		if (number == null ) return false;
		if (number.length() != 16 && number.length() != 19) return false;
		if (!number.matches("\\d+")) return false;
		
		char digits[] = number.toCharArray();
		int len = number.length();
		int numSum = 0;
		for(int i = len - 1,j = 1; i >= 0; i--,j++) {
			int value = digits[i] - '0';
			if (j % 2 == 0) {
				value *= 2;
				if (value > 9) value -= 9;
			}
			numSum += value;
		}
		return numSum % 10 == 0;
	}
}
