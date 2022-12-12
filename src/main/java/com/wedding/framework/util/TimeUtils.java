package com.wedding.framework.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class TimeUtils {
	public static String getNowTime() {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
	}

	public static String getNowTimeMili() {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
	}

	public static String getNow() {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
	}

	public static String getZoneDateTime(String time) {
		final String oldFormat = "yyyyMMddHHmmss";
		final String newFormat = "yyyy-MM-dd HH:mm:ss";

		SimpleDateFormat sdf = new SimpleDateFormat(oldFormat);
		Date d = null;
		try {
			d = sdf.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sdf.applyPattern(newFormat);
		return sdf.format(d).toString();
	}

	public static int compareYear(LocalDateTime date1, LocalDateTime date2) {
		LocalDateTime dayDate1 = date1.truncatedTo(ChronoUnit.YEARS);
		LocalDateTime dayDate2 = date2.truncatedTo(ChronoUnit.YEARS);
		int compareResult = dayDate1.compareTo(dayDate2);
		return compareResult;
	}

	public static int compareMonth(LocalDateTime date1, LocalDateTime date2) {
		LocalDateTime dayDate1 = date1.truncatedTo(ChronoUnit.MONTHS);
		LocalDateTime dayDate2 = date2.truncatedTo(ChronoUnit.MONTHS);
		int compareResult = dayDate1.compareTo(dayDate2);
		return compareResult;
	}

	public static int compareDays(LocalDateTime date1, LocalDateTime date2) {
		LocalDateTime dayDate1 = date1.truncatedTo(ChronoUnit.DAYS);
		LocalDateTime dayDate2 = date2.truncatedTo(ChronoUnit.DAYS);
		int compareResult = dayDate1.compareTo(dayDate2);
		return compareResult;
	}

	public static int compareHour(LocalDateTime date1, LocalDateTime date2) {
		LocalDateTime dayDate1 = date1.truncatedTo(ChronoUnit.HOURS);
		LocalDateTime dayDate2 = date2.truncatedTo(ChronoUnit.HOURS);
		int compareResult = dayDate1.compareTo(dayDate2);
		return compareResult;
	}

	public static int compareMinute(LocalDateTime date1, LocalDateTime date2) {
		LocalDateTime dayDate1 = date1.truncatedTo(ChronoUnit.MINUTES);
		LocalDateTime dayDate2 = date2.truncatedTo(ChronoUnit.MINUTES);
		int compareResult = dayDate1.compareTo(dayDate2);
//		System.out.println("=== 시간 단위 비교 ===");
//		System.out.println("date1.truncatedTo(ChronoUnit.HOURS) : " + dayDate1);
//		System.out.println("date2.truncatedTo(ChronoUnit.HOURS) : " + dayDate2);
//		System.out.println("결과 : " + compareResult);
		return compareResult;
	}
}
