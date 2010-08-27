package com.risetek.operation.platform.base.shared;

/**
 * <p>
 * FieldVerifier validates that the name the user enters is valid.
 * </p>
 */
public class FieldVerifier {

	
	public static boolean isValidName(String name) {
		if (name == null) {
			return false;
		}
		return name.length() > 3;
	}
	
	/**
	 * @Description: 公共验证不能为空的字段
	 * @param text
	 * @return  参数 
	 * @return String 返回类型
	 */
	public static String commValidity(String text, String title) {
		if (null == text || "".equals(text)) {
			return title + "不能为空！";
		}
		return null;
	}
	
	/**
	 * 日期比较函数 little小于或等于big日期返回true
	 * @param little 小日期
	 * @param big 大日期
	 * @return 小日期小于大日期返回true，否则返回false
	 */
	public static boolean lessOrEqualThanDate(String little, String big) {
		String[] littleArray = little.split("-");
		String[] bigArray = big.split("-");
		int yearLittle = Integer.parseInt(littleArray[0]);
		int monthLittle = Integer.parseInt(littleArray[1]);
		int dayLittle = Integer.parseInt(littleArray[2]);

		int yearBig = Integer.parseInt(bigArray[0]);
		int monthBig = Integer.parseInt(bigArray[1]);
		int dayBig = Integer.parseInt(bigArray[2]);
		if (yearBig > yearLittle ? true : false) {
			return true;
		} else if (yearBig == yearLittle ? true : false) {
			if (monthBig > monthLittle ? true : false) {
				return true;
			} else if (monthBig == monthLittle ? true : false) {
				if (dayBig > dayLittle ? true : false) {
					return true;
				} else if (dayBig == dayLittle ? true : false) {
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}
}
