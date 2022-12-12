package com.wedding.framework.util;

public class ConvertUtils {

	public static String byteArrayToHexString(byte[] bytes) {

		StringBuilder sb = new StringBuilder();

		for (byte b : bytes) {

			sb.append(String.format("%02X", b & 0xff));
		}

		return sb.toString();
	}

	public static String byteArrayToHexString(byte[] bytes, int len) {

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < len; i++) {
			sb.append(String.format("%02X", bytes[i] & 0xff));
		}
		return sb.toString();
	}

	public static byte[] hexStringToByteArray(String s) {
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
		}
		return data;
	}

	public static String stringToHex(String s) {
		String result = "";

		for (int i = 0; i < s.length(); i++) {
			result += String.format("%02X", (int) s.charAt(i));
		}

		return result;
	}
	
	public static char[] byteToCharArray(byte[] data) {
		char[] chars = new char[data.length / 2];
		for (int i = 0; i < chars.length; i++) {
			chars[i] = (char) (((data[i * 2] & 0xff) << 8) + (data[(i * 2 + 1)] & 0xff));
		}
		return chars;
	}
}
