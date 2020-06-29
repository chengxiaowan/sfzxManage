package com.yocto.util;

import java.util.Random;

public class RandomUtil {
	private static Random random = new Random();

	public static void main(String args[]) {
		System.out.println(Tools.getRandomNum());
	}

	public static String getRandomValue(int m) {
		String code = "";
		for (int i = 0; i < m; i++) {
			int num = random.nextInt(9);
			code += num + "";
		}
		return code;
	}
}