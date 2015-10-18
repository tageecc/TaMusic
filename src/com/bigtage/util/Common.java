package com.bigtage.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Common {
	/**
	 * 验证是email格式是否合法
	 * 
	 * @param email
	 * @return 是否合法
	 */
	public static boolean isemail(String email) {
		boolean tag = true;
		final String pattern1 = "^([a-z0-9A-Z]+[-|//.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?//.)+[a-zA-Z]{2,}$";
		final Pattern pattern = Pattern.compile(pattern1);
		final Matcher mat = pattern.matcher(email);
		if (!mat.find()) {
			tag = false;
		}
		return tag;
	}

	/**
	 * 验证是否为手机号
	 * 
	 * @param str
	 * @return 是否合法
	 */
	public static boolean isNumer(String str) {
		Pattern pattern = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
		return pattern.matcher(str).matches();
	}

	/**
	 * 判断非空字符串
	 * 
	 * @param str
	 * @return 是否非空
	 */
	public static boolean isNull(String str) {
		return str != null && str.length() > 0;
	}
}
