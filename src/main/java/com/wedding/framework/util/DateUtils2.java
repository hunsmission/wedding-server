package com.wedding.framework.util;

import java.text.ParseException;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.springframework.format.datetime.DateFormatter;
import org.springframework.util.ObjectUtils;

public class DateUtils2 {
	public static class DatePattern {
		public static final String yyyy = "yyyy";
		public static final String yyyyMM = "yyyyMM";
		public static final String yyyyMMdd = "yyyyMMdd";
		public static final String yyyyMMddHH = "yyyyMMddHH";
		public static final String yyyyMMddHHmm = "yyyyMMddHHmm";
		public static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";
		public static final String yyyyMMddHHmmssSSS = "yyyyMMddHHmmssSSS";
		public static final String MM = "MM";
		public static final String dd = "dd";
		public static final String HH = "HH";
		public static final String yyyy_MM_dd = "yyyy-MM-dd";
		public static final String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
		public static final String yyyy_MM_dd_HH_mm_ss_SSS = "yyyy-MM-dd HH:mm:ss.SSS";
		public static final String dd_MM = "dd/MM";
		public static final String YYYYww = "YYYYww";
		public static final String ww_YYYY = "ww YYYY";
	}

	public static class Zone {
		public static final String GMT = "GMT";
		public static final String SYS = ZoneId.systemDefault().getId();
	}

	private static final WeekFields DEFAULT_WEEK_FIELDS = WeekFields.of(DayOfWeek.SUNDAY, 4);

	private static final String PROP_INSTANT = "DateUtils2.instant";

	/**
	 * Get Instant<br>
	 * If, this method is already called, the same value of previous one will be returned.<br>
	 * Otherwise, it will returns new Instant<br>
	 * @return
	 */
	public static Instant getInstant() {
		return ThreadUtils.getProp(PROP_INSTANT, () -> newInstant());
	}

	/**
	 * New Instant<br>
	 * It returns Now Instant.
	 * @return
	 */
	public static Instant newInstant() {
		Instant now = Instant.now();
		ThreadUtils.setProp(PROP_INSTANT, now);
		return now;
	}

	/**
	 * Convert from Time String to Instant
	 * @param timeStr	Time String
	 * @param pattern	Time String Pattern
	 * @param zoneId	Time String TimeZone
	 * @return
	 */
	public static Instant toInstant(String timeStr, String pattern, String zoneId) {
		return toInstant(timeStr, pattern, zoneId, null);
	}

	/**
	 * Convert from Time String to Instant<br>
	 * If timeStr is empty, Default Value will be returned.<br>
	 * @param timeStr		Time String
	 * @param pattern		Time String Pattern
	 * @param zoneId		Time String TimeZone
	 * @param defaultValue	Default Value
	 * @return
	 */
	public static Instant toInstant(String timeStr, String pattern, String zoneId, Instant defaultValue) {
		if (ObjectUtils.isEmpty(timeStr)) {
			return defaultValue;
		}

		LogicUtils.assertNotEmpty(pattern, "pattern");
		LogicUtils.assertNotEmpty(zoneId, "zoneId");

		Instant instant;
		try {
			Date date = getDateFormatter(pattern, zoneId).parse(timeStr, Locale.US);
			instant = Instant.ofEpochMilli(date.getTime());
		} catch (ParseException e) {
			instant = getDateTimeFormatter(pattern, zoneId).parse(timeStr, Instant::from);
		}
		return instant;
	}

	/**
	 * It calculate plus Months at Instant
	 * @param instant	The Source Instant
	 * @param months	The months for calculating plus
	 * @param zoneId	Based TimeZone for recognize the Instant Value
	 * @return
	 */
	public static Instant plusMonths(Instant instant, int months, String zoneId) {
		LogicUtils.assertNotEmpty(zoneId, "zoneId");

		ZoneId zone = ZoneId.of(zoneId);
		LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
		Instant value = localDateTime.plusMonths(months).atZone(zone).toInstant();
		return value;
	}

	/**
	 * Get Time String<br>
	 * If, this method is already called, the same value of previous one will be returned.<br>
	 * Otherwise, it will returns new Time String<br>
	 * @param pattern	Time String Pattern
	 * @param zoneId	TimeZone
	 * @return
	 */
	public static String getString(String pattern, String zoneId) {
		LogicUtils.assertNotEmpty(pattern, "pattern");
		LogicUtils.assertNotEmpty(zoneId, "zoneId");

		Instant now = getInstant();
		return format(now, pattern, zoneId);
	}

	/**
	 * New Time String
	 * @param pattern	Time String Pattern
	 * @param zoneId	TimeZone
	 * @return
	 */
	public static String newString(String pattern, String zoneId) {
		LogicUtils.assertNotEmpty(pattern, "pattern");
		LogicUtils.assertNotEmpty(zoneId, "zoneId");

		Instant now = newInstant();
		return format(now, pattern, zoneId);
	}

	/**
	 * Format Instant -> Time String by Pattern and Zone
	 * @param instant	Instant to Format
	 * @param pattern	Time String Pattern
	 * @param zoneId	TimeZone
	 * @return
	 */
	public static String format(Instant instant, String pattern, String zoneId) {
		return format(instant, pattern, zoneId, null);
	}

	/**
	 * Format Instant -> Time String by Pattern and Zone<br>
	 * If Instant is null, Default Value will be returned.<br>
	 * @param instant	Instant to Format
	 * @param pattern	Time String Pattern
	 * @param zoneId	TimeZone
	 * @param defaultValue
	 * @return
	 */
	public static String format(Instant instant, String pattern, String zoneId, String defaultValue) {
		if (instant == null) {
			return defaultValue;
		}
		LogicUtils.assertNotEmpty(pattern, "pattern");
		LogicUtils.assertNotEmpty(zoneId, "zoneId");

		// How Dirty!!
		if (pattern.contains("w")) {

//			return DateUtils.getYearWeek(instant, DatePattern.yyyyMMddHHmmss);

			ZoneId zone = ZoneId.of(zoneId);
			LocalDateTime localDate = LocalDateTime.ofInstant(instant, zone);
			int weekOfYear = localDate.get(DEFAULT_WEEK_FIELDS.weekOfWeekBasedYear());
			int weekOfYear2 = localDate.get(WeekFields.SUNDAY_START.weekOfWeekBasedYear());

			if (weekOfYear != weekOfYear2) {
				int year = localDate.get(DEFAULT_WEEK_FIELDS.weekBasedYear());
				int year2 = localDate.get(WeekFields.SUNDAY_START.weekBasedYear());
				localDate.get(DEFAULT_WEEK_FIELDS.weekOfWeekBasedYear());
				Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(zone));
//				cal.setMinimalDaysInFirstWeek(4);
				cal.setTimeInMillis(instant.toEpochMilli());
				if (year != year2) {
					cal.set(Calendar.YEAR, year);
				}
				cal.set(Calendar.WEEK_OF_YEAR, weekOfYear);
				instant = cal.toInstant();
			}
		}

		String str = getDateTimeFormatter(pattern, zoneId).format(instant);
		return str;
	}

	private static DateFormatter getDateFormatter(String pattern, String zoneId) {
		DateFormatter formatter = new DateFormatter(pattern);
		formatter.setTimeZone(TimeZone.getTimeZone(zoneId));
		return formatter;
	}

	private static DateTimeFormatter getDateTimeFormatter(String pattern, String zoneId) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern).withZone(ZoneId.of(zoneId));
		return formatter;
	}

	/**
	 * Convert Time String From Pattern -> To Pattern
	 * @param timeStr		Time String
	 * @param fromPattern	From Pattern
	 * @param toPattern		To Pattern
	 * @return
	 */
	public static String toOtherPattern(String timeStr, String fromPattern, String toPattern) {
		if (ObjectUtils.isEmpty(timeStr)) {
			return timeStr;
		}
		LogicUtils.assertNotEmpty(fromPattern, "fromPattern");
		LogicUtils.assertNotEmpty(toPattern, "toPattern");
		Instant instant = toInstant(timeStr, fromPattern, Zone.GMT);
		String str = format(instant, toPattern, Zone.GMT);
		return str;
	}

	/**
	 * Convert Time String From TimeZone -> To TimeZone
	 * @param timeStr		Time String
	 * @param pattern		Time String Pattern
	 * @param fromZoneId	From Zone
	 * @param toZoneId		To Zone
	 * @return
	 */
	public static String toOtherZone(String timeStr, String pattern, String fromZoneId, String toZoneId) {
		if (ObjectUtils.isEmpty(timeStr)) {
			return timeStr;
		}

		LogicUtils.assertNotEmpty(pattern, "pattern");
		LogicUtils.assertNotEmpty(fromZoneId, "fromZoneId");
		LogicUtils.assertNotEmpty(toZoneId, "toZoneId");

		Instant instant = toInstant(timeStr, pattern, fromZoneId);
		String str = format(instant, pattern, toZoneId);
		return str;
	}

	/**
	 * Get Week Of Year
	 * @param timeStr	Time String
	 * @param pattern	Time String Pattern
	 * @return
	 */
	public static int getWeekOfYear(String timeStr, String pattern) {
		if (ObjectUtils.isEmpty(timeStr)) {
			return 0;
		}
		LogicUtils.assertNotEmpty(pattern, "pattern");

		LocalDateTime localDate = LocalDateTime.parse(timeStr, getDateTimeFormatter(pattern, Zone.GMT));
		int weekOfYear = localDate.get(DEFAULT_WEEK_FIELDS.weekOfWeekBasedYear());
		return weekOfYear;
	}

}
