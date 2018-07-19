package com.util;

import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;

import cn.mrdear.pay.util.DateUtil;

public final class RandomGenretorUtils {
	
	public static String generateSerialNumber(String goodId) {
		
		return goodId + DateUtil.formatDateyyyyMMddHHmmsss(new Date())
				+ RandomStringUtils.randomNumeric(4);
		
	}
	
	public static void main(String[] args) {
		
		System.out.println(RandomGenretorUtils.generateSerialNumber("21000374"));
		
	}
}
