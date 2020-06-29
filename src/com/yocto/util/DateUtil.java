package com.yocto.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 说明：日期处理 创建人： 修改时间：2015年11月24日
 * 
 * @version
 */
public class DateUtil {

	private final static SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
	private final static SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
	private final static SimpleDateFormat sdfDays = new SimpleDateFormat("yyyyMMdd");
	private final static SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private final static SimpleDateFormat sdfTimes = new SimpleDateFormat("yyyyMMddHHmmss");

	public static final String LONG_PATTERN = "yyyy-MM-dd HH:mm:ss,SSS";
	public static final String NORMAL_PATTERN = "yyyy-MM-dd HH:mm:ss";
	public static final String SHORT_PATTERN_DATE = "yyyy-MM-dd";
	public static final String SIMPLE_PATTERN_DATE = "yyyyMMddHHmmsssss";
	public static final String PATH_PATTERN_DATE = "yyyy/MM/dd/";
	public static final String SUPER_LONG_PATTERN_DATE = "yyyyMMddHHmmssSSSSSSS";
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 获取YYYY格式
	 * 
	 * @return
	 */
	public static String getSdfTimes() {
		return sdfTimes.format(new Date());
	}

	/**
	 * 获取YYYY格式
	 * 
	 * @return
	 */
	public static String getYear() {
		return sdfYear.format(new Date());
	}

	/**
	 * 获取YYYY-MM-DD格式
	 * 
	 * @return
	 */
	public static String getDay() {
		return sdfDay.format(new Date());
	}

	/**
	 * 获取YYYYMMDD格式
	 * 
	 * @return
	 */
	public static String getDays() {
		return sdfDays.format(new Date());
	}

	/**
	 * 获取YYYY-MM-DD HH:mm:ss格式
	 * 
	 * @return
	 */
	public static String getTime() {
		return sdfTime.format(new Date());
	}

	/**
	 * @Title: compareDate
	 * @Description: TODO(日期比较，如果s>=e 返回true 否则返回false)
	 * @param s
	 * @param e
	 * @return boolean
	 * @throws
	 * @author
	 */
	public static boolean compareDate(String s, String e) {
		if (fomatDate(s) == null || fomatDate(e) == null) {
			return false;
		}
		return fomatDate(s).getTime() >= fomatDate(e).getTime();
	}

	/**
	 * 格式化日期
	 * 
	 * @return
	 */
	public static Date fomatDate(String date) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return fmt.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 校验日期是否合法
	 * 
	 * @return
	 */
	public static boolean isValidDate(String s) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			fmt.parse(s);
			return true;
		} catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return false;
		}
	}

	/**
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static int getDiffYear(String startTime, String endTime) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			// long aa=0;
			int years = (int) (((fmt.parse(endTime).getTime() - fmt.parse(startTime).getTime()) / (1000 * 60 * 60 * 24)) / 365);
			return years;
		} catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return 0;
		}
	}

	/**
	 * <li>功能描述：时间相减得到天数
	 * 
	 * @param beginDateStr
	 * @param endDateStr
	 * @return long
	 * @author Administrator
	 */
	public static long getDaySub(String beginDateStr, String endDateStr) {
		long day = 0;
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
		java.util.Date beginDate = null;
		java.util.Date endDate = null;

		try {
			beginDate = format.parse(beginDateStr);
			endDate = format.parse(endDateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
		// System.out.println("相隔的天数="+day);

		return day;
	}

	/**
	 * 得到n天之后的日期
	 * 
	 * @param days
	 * @return
	 */
	public static String getAfterDayDate(String days) {
		int daysInt = Integer.parseInt(days);

		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
		Date date = canlendar.getTime();

		SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = sdfd.format(date);

		return dateStr;
	}

	/**
	 * 得到n天之后是周几
	 * 
	 * @param days
	 * @return
	 */
	public static String getAfterDayWeek(String days) {
		int daysInt = Integer.parseInt(days);
		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
		Date date = canlendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("E");
		String dateStr = sdf.format(date);
		return dateStr;
	}

	public static void main(String[] args) {
		System.out.println(getDays());
		System.out.println(getAfterDayWeek("3"));
	}

	/**
	 * 格式化日期显示格式yyyy-MM-dd
	 * 
	 * @param sdate
	 *            原始日期格式
	 * @return yyyy-MM-dd格式化后的日期显示
	 */
	public static String dateFormat(String sdate) {
		return dateFormat(sdate, "yyyy-MM-dd");
	}

	/**
	 * 格式化日期显示格式
	 * 
	 * @param sdate
	 *            原始日期格式
	 * @param format
	 *            格式化后日期格式
	 * @return 格式化后的日期显示
	 */
	public static String dateFormat(String sdate, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		java.sql.Date date = java.sql.Date.valueOf(sdate);
		String dateString = formatter.format(date);

		return dateString;
	}

	/**
	 * 求两个日期相差天数
	 * 
	 * @param sd
	 *            起始日期，格式yyyy-MM-dd
	 * @param ed
	 *            终止日期，格式yyyy-MM-dd
	 * @return 两个日期相差天数
	 */
	public static long getIntervalDays(String sd, String ed) {
		return ((java.sql.Date.valueOf(ed)).getTime() - (java.sql.Date.valueOf(sd)).getTime()) / (3600 * 24 * 1000);
	}

	/**
	 * 起始年月yyyy-MM与终止月yyyy-MM之间跨度的月数
	 * 
	 * @return int
	 */
	public static int getInterval(String beginMonth, String endMonth) {
		int intBeginYear = Integer.parseInt(beginMonth.substring(0, 4));
		int intBeginMonth = Integer.parseInt(beginMonth.substring(beginMonth.indexOf("-") + 1));
		int intEndYear = Integer.parseInt(endMonth.substring(0, 4));
		int intEndMonth = Integer.parseInt(endMonth.substring(endMonth.indexOf("-") + 1));

		return ((intEndYear - intBeginYear) * 12) + (intEndMonth - intBeginMonth) + 1;
	}

	public static Date getDate(String sDate, String dateFormat) {
		SimpleDateFormat fmt = new SimpleDateFormat(dateFormat);
		ParsePosition pos = new ParsePosition(0);

		return fmt.parse(sDate, pos);
	}

	/**
	 * 取得当前日期的年份，以yyyy格式返回.
	 * 
	 * @return 当年 yyyy
	 */
	public static String getCurrentYear() {
		return getFormatCurrentTime("yyyy");
	}

	/**
	 * 取得当前日期的月份，以MM格式返回.
	 * 
	 * @return 当前月份 MM
	 */
	public static String getCurrentMonth() {
		return getFormatCurrentTime("MM");
	}

	/**
	 * 取得当前日期的天数，以格式"dd"返回.
	 * 
	 * @return 当前月中的某天dd
	 */
	public static String getCurrentDay() {
		return getFormatCurrentTime("dd");
	}

	/**
	 * 取得当期日期的年，月，以格式"yyyy-MM"返回
	 * 
	 * @return
	 */
	public static String getCurrentYM() {
		return getFormatCurrentTime("yyyy-MM");
	}

	/**
	 * 返回当前时间字符串。
	 * <p>
	 * 格式：yyyy-MM-dd
	 * 
	 * @return String 指定格式的日期字符串.
	 */
	public static String getCurrentDate() {
		return getFormatDateTime(new Date(), "yyyy-MM-dd");
	}

	/**
	 * 返回给定时间字符串。
	 * <p>
	 * 格式：yyyy-MM-dd
	 * 
	 * @param date
	 *            日期
	 * @return String 指定格式的日期字符串.
	 */
	public static String getFormatDate(Date date) {
		return getFormatDateTime(date, "yyyy-MM-dd");
	}

	/**
	 * 返回当前时间字符串。
	 * <p>
	 * 格式：yyyy-MM-dd HH:mm:ss
	 * 
	 * @return String 指定格式的日期字符串.
	 */
	public static String getCurrentTime() {
		return getFormatDateTime(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 返回给定时间字符串。
	 * <p>
	 * 格式：yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 *            日期
	 * @return String 指定格式的日期字符串.
	 */
	public static String getFormatTime(Date date) {
		return getFormatDateTime(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 根据给定的格式，返回时间字符串。
	 * <p>
	 * 格式参照类描绘中说明.
	 * 
	 * @param format
	 *            日期格式字符串
	 * @return String 指定格式的日期字符串.
	 */
	public static String getFormatCurrentTime(String format) {
		return getFormatDateTime(new Date(), format);
	}

	/**
	 * 根据给定的格式与时间(Date类型的)，返回时间字符串<br>
	 * 
	 * @param date
	 *            指定的日期
	 * @param format
	 *            日期格式字符串
	 * @return String 指定格式的日期字符串.
	 */
	public static String getFormatDateTime(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date).trim();
	}

	/**
	 * 取得指定年月日的日期对象.
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月注意是从1到12
	 * @param day
	 *            日
	 * @return 一个java.util.Date()类型的对象
	 */
	public static Date getDateObj(int year, int month, int day) {
		Calendar c = new GregorianCalendar();
		c.set(year, month - 1, day);
		return c.getTime();
	}

	/**
	 * 取得指定分隔符分割的年月日的日期对象.
	 * 
	 * @param args
	 *            格式为"yyyy-MM-dd"
	 * @param split
	 *            时间格式的间隔符，例如“-”，“/”
	 * @return 一个java.util.Date()类型的对象
	 */
	public static Date getDateObj(String args, String split) {
		String[] temp = args.split(split);
		int year = new Integer(temp[0]).intValue();
		int month = new Integer(temp[1]).intValue();
		int day = new Integer(temp[2]).intValue();
		return getDateObj(year, month, day);
	}

	public static String formatDate(Date theDate) {
		Locale locale = Locale.CHINA;
		String dateString = "";
		if (theDate == null)
			return "";
		try {
			Calendar cal = Calendar.getInstance(locale);
			// cal.setFirstDayOfWeek(Calendar.SUNDAY);
			cal.setTime((Date) theDate);

			// DateFormat dateFormatter = DateFormat.getDateTimeInstance(DateFormat.LONG,DateFormat.MEDIUM,locale);
			java.text.SimpleDateFormat dateFormatter = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", locale);
			dateString = dateFormatter.format(cal.getTime());
			// System.out.println(dateString);
			// System.out.println(cal.get(Calendar.YEAR));
			// System.out.println(cal.get(cal.DAY_OF_WEEK));
		} catch (Exception e) {
			System.out.println("test result:" + e.getMessage());
		} finally {

		}
		return dateString;
	}

	/**
	 * 取得给定字符串描述的日期对象，描述模式采用pattern指定的格式.
	 * 
	 * @param dateStr
	 *            日期描述
	 * @param pattern
	 *            日期模式
	 * @return 给定字符串描述的日期对象。
	 */
	public static Date getDateFromString(String dateStr, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date resDate = null;
		try {
			resDate = sdf.parse(dateStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resDate;
	}

	/**
	 * 取得当前Date对象.
	 * 
	 * @return Date 当前Date对象.
	 */
	public static Date getDateObj() {
		Calendar c = new GregorianCalendar();
		return c.getTime();
	}

	/**
	 * 
	 * @return 当前月份有多少天；
	 */
	public static int getDaysOfCurMonth() {
		int curyear = new Integer(getCurrentYear()).intValue(); // 当前年份
		int curMonth = new Integer(getCurrentMonth()).intValue();// 当前月份
		int mArray[] = new int[] { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		// 判断闰年的情况 ，2月份有29天；
		if ((curyear % 400 == 0) || ((curyear % 100 != 0) && (curyear % 4 == 0))) {
			mArray[1] = 29;
		}
		return mArray[curMonth - 1];
		// 如果要返回下个月的天数，注意处理月份12的情况，防止数组越界；
		// 如果要返回上个月的天数，注意处理月份1的情况，防止数组越界；
	}

	/**
	 * 根据制定的年月 返回当前月份有多少天
	 * 
	 * @param yyyy
	 *            -mm
	 * @return
	 */
	public static int getDaysOfCurMonth(final String time) {
		String[] timeArray = time.split("-");
		int curyear = new Integer(timeArray[0]).intValue(); // 当前年份
		int curMonth = new Integer(timeArray[1]).intValue();// 当前月份
		int mArray[] = new int[] { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		// 判断闰年的情况 ，2月份有29天；
		if ((curyear % 400 == 0) || ((curyear % 100 != 0) && (curyear % 4 == 0))) {
			mArray[1] = 29;
		}
		if (curMonth == 12) {
			return mArray[0];
		}
		return mArray[curMonth - 1];
		// 如果要返回下个月的天数，注意处理月份12的情况，防止数组越界；
		// 如果要返回上个月的天数，注意处理月份1的情况，防止数组越界；
	}

	/**
	 * 返回制定为年度为year月度为month的月份内，第weekOfMonth个星期的第dayOfWeek天。<br>
	 * 00 00 00 01 02 03 04 <br>
	 * 05 06 07 08 09 10 11<br>
	 * 12 13 14 15 16 17 18<br>
	 * 19 20 21 22 23 24 25<br>
	 * 26 27 28 29 30 31 <br>
	 * 2006年的第一个周的1到7天为：05 06 07 01 02 03 04 <br>
	 * 2006年的第二个周的1到7天为：12 13 14 08 09 10 11 <br>
	 * 2006年的第三个周的1到7天为：19 20 21 15 16 17 18 <br>
	 * 2006年的第四个周的1到7天为：26 27 28 22 23 24 25 <br>
	 * 2006年的第五个周的1到7天为：02 03 04 29 30 31 01 。本月没有就自动转到下个月了。
	 * 
	 * @param year
	 *            形式为yyyy <br>
	 * @param month
	 *            形式为MM,参数值在[1-12]。<br>
	 * @param weekOfMonth
	 *            在[1-6],因为一个月最多有6个周。<br>
	 * @param dayOfWeek
	 *            数字在1到7之间，包括1和7。1表示星期天，7表示星期六<br>
	 *            -6为星期日-1为星期五，0为星期六 <br>
	 * @return <type>int</type>
	 */
	public static int getDayofWeekInMonth(String year, String month, String weekOfMonth, String dayOfWeek) {
		Calendar cal = new GregorianCalendar();
		// 在具有默认语言环境的默认时区内使用当前时间构造一个默认的 GregorianCalendar。
		int y = new Integer(year).intValue();
		int m = new Integer(month).intValue();
		cal.clear();// 不保留以前的设置
		cal.set(y, m - 1, 1);// 将日期设置为本月的第一天。
		cal.set(Calendar.DAY_OF_WEEK_IN_MONTH, new Integer(weekOfMonth).intValue());
		cal.set(Calendar.DAY_OF_WEEK, new Integer(dayOfWeek).intValue());
		// System.out.print(cal.get(Calendar.MONTH)+" ");
		// System.out.print("当"+cal.get(Calendar.WEEK_OF_MONTH)+"\t");
		// WEEK_OF_MONTH表示当天在本月的第几个周。不管1号是星期几，都表示第一个周
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 根据指定的年、月、日返回当前是星期几。1表示星期天、2表示星期一、7表示星期六。
	 * 
	 * @param year
	 * @param month
	 *            month是从1开始的12结束
	 * @param day
	 * @return
	 */
	public static int getDayOfWeek(String year, String month, String day) {
		Calendar cal = new GregorianCalendar(new Integer(year).intValue(), new Integer(month).intValue() - 1, new Integer(day).intValue());
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 返回制定日期所在的周是一年中的第几个周。<br>
	 * created by wangmj at 20060324.<br>
	 * 
	 * @param year
	 * @param month
	 *            范围1-12<br>
	 * @param day
	 * @return int
	 */
	public static int getWeekOfYear(String year, String month, String day) {
		Calendar cal = new GregorianCalendar();
		cal.clear();
		cal.set(new Integer(year).intValue(), new Integer(month).intValue() - 1, new Integer(day).intValue());
		return cal.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * 取得给定日期加上一定天数后的日期对象.
	 * 
	 * @param date
	 *            给定的日期对象
	 * @param amount
	 *            需要添加的天数，如果是向前的天数，使用负数就可以.
	 * @return Date 加上一定天数以后的Date对象.
	 */
	public static Date getDateAdd(Date date, int amount) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(GregorianCalendar.DATE, amount);
		return cal.getTime();
	}

	public static String getDateAddMonth(String date, int amount, String format) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			Calendar cd = Calendar.getInstance();
			cd.setTime(sdf.parse(date));
			// cd.add(Calendar.DATE, 1);// 增加一天
			cd.add(Calendar.MONTH, amount);// 增加一个月
			return sdf.format(cd.getTime());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 取得给定日期加上一定天数后的日期对象.
	 * 
	 * @param date
	 *            给定的日期对象
	 * @param amount
	 *            需要添加的天数，如果是向前的天数，使用负数就可以.
	 * @param format
	 *            输出格式.
	 * @return Date 加上一定天数以后的Date对象.
	 */
	public static String getFormatDateAdd(Date date, int amount, String format) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(GregorianCalendar.DATE, amount);
		return getFormatDateTime(cal.getTime(), format);
	}

	/**
	 * 获得当前日期固定间隔天数的日期，如前60天dateAdd(-60)
	 * 
	 * @param amount
	 *            距今天的间隔日期长度，向前为负，向后为正
	 * @param format
	 *            输出日期的格式.
	 * @return java.lang.String 按照格式输出的间隔的日期字符串.
	 */
	public static String getFormatCurrentAdd(int amount, String format) {

		Date d = getDateAdd(new Date(), amount);

		return getFormatDateTime(d, format);
	}

	/**
	 * 取得给定格式的昨天的日期输出
	 * 
	 * @param format
	 *            日期输出的格式
	 * @return String 给定格式的日期字符串.
	 */
	public static String getFormatYestoday(String format) {
		return getFormatCurrentAdd(-1, format);
	}

	/**
	 * 返回指定日期的前一天。<br>
	 * 
	 * @param sourceDate
	 * @return
	 */
	public static String getYestoday(String sourceDate, String format) {
		return getFormatDateAdd(getDateFromString(sourceDate, format), -1, format);
	}

	/**
	 * 返回明天的日期，<br>
	 * 
	 * @return
	 */
	public static String getFormatTomorrow(String format) {
		return getFormatCurrentAdd(1, format);
	}

	/**
	 * 返回指定日期的后一天。<br>
	 * 
	 * @param sourceDate
	 * @return
	 */
	public static String getFormatDateTommorrow(String sourceDate, String format) {
		return getFormatDateAdd(getDateFromString(sourceDate, format), 1, format);
	}

	public static String getCurrentDateString(String dateFormat) {
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		sdf.setTimeZone(TimeZone.getDefault());

		return sdf.format(cal.getTime()).trim();
	}

	/**
	 * 获取当前日期
	 * 
	 * @param format
	 * @return
	 */
	/*
	 * public static java.util.Date getCurDateByFormat(String format) { Date
	 * newdate = new Date(System.currentTimeMillis()); SimpleDateFormat sdf =
	 * new SimpleDateFormat(format); String strDate = sdf.format(newdate);
	 * java.util.Date date = null; try { date = sdf.parse(strDate); } catch
	 * (ParseException ex) { // log.error("转化字符串到日期出错" + ex); } return date; }
	 */
	/**
	 * 返回当前时间串 格式:yyMMddhhmmss,在上传附件时使用
	 * 
	 * @return String
	 */
	public static String getCurDate() {
		GregorianCalendar gcDate = new GregorianCalendar();
		int year = gcDate.get(GregorianCalendar.YEAR);
		int month = gcDate.get(GregorianCalendar.MONTH) + 1;
		int day = gcDate.get(GregorianCalendar.DAY_OF_MONTH);
		int hour = gcDate.get(GregorianCalendar.HOUR_OF_DAY);
		int minute = gcDate.get(GregorianCalendar.MINUTE);
		int sen = gcDate.get(GregorianCalendar.SECOND);
		String y;
		String m;
		String d;
		String h;
		String n;
		String s;
		y = new Integer(year).toString();

		if (month < 10) {
			m = "0" + new Integer(month).toString();
		} else {
			m = new Integer(month).toString();
		}

		if (day < 10) {
			d = "0" + new Integer(day).toString();
		} else {
			d = new Integer(day).toString();
		}

		if (hour < 10) {
			h = "0" + new Integer(hour).toString();
		} else {
			h = new Integer(hour).toString();
		}

		if (minute < 10) {
			n = "0" + new Integer(minute).toString();
		} else {
			n = new Integer(minute).toString();
		}

		if (sen < 10) {
			s = "0" + new Integer(sen).toString();
		} else {
			s = new Integer(sen).toString();
		}

		return "" + y + m + d + h + n + s;
	}

	/**
	 * 根据给定的格式，返回时间字符串。
	 * 
	 * @param format
	 * @return
	 */
	public static String getCurTimeByFormat(String format) {
		Date newdate = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(newdate);
	}

	/**
	 * if(curDate>time) return true否则return false
	 * 
	 * @return
	 */
	public static boolean getDiff(Date curDate, Date time) {
		boolean diff = false;
		if (curDate.getTime() - time.getTime() > 0)
			diff = true;

		return diff;
	}

	/**
	 * 获取两个时间串时间的差值，单位为秒
	 * 
	 * @param startTime
	 *            开始时间 yyyy-MM-dd HH:mm:ss
	 * @param endTime
	 *            结束时间 yyyy-MM-dd HH:mm:ss
	 * @return 两个时间的差值(秒)
	 */
	public static long getDiff(String startTime, String endTime) {
		long diff = 0;
		try {
			Date startDate = simpleDateFormat.parse(startTime);
			Date endDate = simpleDateFormat.parse(endTime);
			diff = startDate.getTime() - endDate.getTime();
			diff = diff / 1000;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return diff;
	}

	/**
	 * 获取小时/分钟/秒
	 * 
	 * @param second
	 *            秒
	 */
	public static String getHour(long second) {
		long hour = second / 60 / 60;
		long minute = (second - hour * 60 * 60) / 60;
		long sec = (second - hour * 60 * 60) - minute * 60;

		return hour + "小时" + minute + "分钟" + sec + "秒";

	}

	/**
	 * 获取 天，时，分，秒
	 * 
	 * @param second
	 *            秒
	 * @return
	 */
	public static String getBetweenLength(long second) {
		long day = second / (60 * 60 * 24);
		long hour = (second - day * 60 * 60 * 24) / (60 * 60);
		long minute = ((second - day * 60 * 60 * 24) - hour * 60 * 60) / 60;
		long sec = ((second - day * 60 * 60 * 24) - hour * 60 * 60) - minute * 60;

		if (day == 0 && hour != 0) {
			return hour + "小时" + minute + "分" + sec + "秒";
		}
		if (day == 0 && hour == 0 && minute != 0) {
			return minute + "分" + sec + "秒";
		}
		if (day == 0 && hour == 0 && minute == 0) {
			return sec + "秒";
		}
		return day + "天" + hour + "小时" + minute + "分" + sec + "秒";
	}

	/**
	 * 返回指定时间字符串。
	 * <p>
	 * 格式：yyyy-MM-dd HH:mm:ss
	 * 
	 * @return String 指定格式的日期字符串.
	 */
	public static String getDateTime(long microsecond) {
		return getFormatDateTime(new Date(microsecond), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 返回当前时间加实数小时后的日期时间。
	 * <p>
	 * 格式：yyyy-MM-dd HH:mm:ss
	 * 
	 * @return Float 加几实数小时.
	 */
	public static String getDateByAddFltHour(float flt) {
		int addMinute = (int) (flt * 60);
		Calendar cal = new GregorianCalendar();
		cal.setTime(new Date());
		cal.add(GregorianCalendar.MINUTE, addMinute);
		return getFormatDateTime(cal.getTime(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 返回指定时间加实数小时后的日期时间。
	 * <p>
	 * 格式：yyyy-MM-dd HH:mm:ss
	 * 
	 * @return Float 加几实数小时.
	 */
	public static String getDateByAddFltHour(String dateStr, float flt) {
		int addMinute = (int) (flt * 60);
		Calendar cal = new GregorianCalendar();
		cal.setTime(getDateFromString(dateStr, "yyyy-MM-dd HH:mm:ss"));
		cal.add(GregorianCalendar.MINUTE, addMinute);
		return getFormatDateTime(cal.getTime(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 返回指定时间减实数小时后的日期时间。
	 * <p>
	 * 格式：yyyy-MM-dd HH:mm:ss
	 * 
	 * @return Float 减几实数小时.
	 */
	public static String getDateByMinusFltHour(String dateStr, float flt) {
		int addMinute = -(int) (flt * 60);
		Calendar cal = new GregorianCalendar();
		cal.setTime(getDateFromString(dateStr, "yyyy-MM-dd HH:mm:ss"));
		cal.add(GregorianCalendar.MINUTE, addMinute);
		return getFormatDateTime(cal.getTime(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 返回指定时间减实数分钟后的日期时间。
	 * <p>
	 * 格式：yyyy-MM-dd HH:mm:ss
	 * 
	 * @return int 减几实数小时.
	 */
	public static String getDateByMinusFlt(String dateStr, int flt) {
		int addMinute = -(int) (flt);
		Calendar cal = new GregorianCalendar();
		cal.setTime(getDateFromString(dateStr, "yyyy-MM-dd HH:mm:ss"));
		cal.add(GregorianCalendar.MINUTE, addMinute);
		return getFormatDateTime(cal.getTime(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 返回指定时间加指定小时数后的日期时间。
	 * <p>
	 * 格式：yyyy-MM-dd HH:mm:ss
	 * 
	 * @return 时间.
	 */
	public static String getDateByAddHour(String datetime, int minute) {
		String returnTime = null;
		Calendar cal = new GregorianCalendar();
		Date date;
		try {
			date = simpleDateFormat.parse(datetime);
			cal.setTime(date);
			cal.add(GregorianCalendar.MINUTE, minute);
			returnTime = getFormatDateTime(cal.getTime(), "yyyy-MM-dd HH:mm:ss");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return returnTime;

	}

	/**
	 * 获取两个时间串时间的差值，单位为小时
	 * 
	 * @param startTime
	 *            开始时间 yyyy-MM-dd HH:mm:ss
	 * @param endTime
	 *            结束时间 yyyy-MM-dd HH:mm:ss
	 * @return 两个时间的差值(小时)
	 */
	public static int getIntHour(String endTime, String startTime) {
		long diff = 0;
		try {
			Date endDate = simpleDateFormat.parse(endTime);
			Date startDate = simpleDateFormat.parse(startTime);
			diff = endDate.getTime() - startDate.getTime();
			diff = diff / (1000 * 60 * 60);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new Long(diff).intValue();
	}

	/**
	 * 获取两个时间串时间的差值，单位为小时
	 * 
	 * @param startTime
	 *            开始时间 yyyy-MM-dd HH:mm:ss
	 * @param endTime
	 *            结束时间 yyyy-MM-dd HH:mm:ss
	 * @return 两个时间的差值(小时)
	 */
	public static float getDiffHour(String endTime, String startTime) {
		float diff = 0F;
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date endDate = ft.parse(endTime);
			Date startDate = ft.parse(startTime);
			diff = endDate.getTime() - startDate.getTime();
			diff = diff / (1000 * 60 * 60);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return diff;
	}

	/**
	 * 获取两个时间类型的时间的差值，单位为小时
	 * 
	 * @param startTime
	 *            开始时间 yyyy-MM-dd HH:mm:ss
	 * @param endTime
	 *            结束时间 yyyy-MM-dd HH:mm:ss
	 * @return 两个时间的差值(秒)
	 */
	public static int getDiffHour(Date startTime, Date endTime) {
		long diff = 0;
		diff = endTime.getTime() - startTime.getTime();
		diff = diff / (1000 * 60 * 60);
		return new Long(diff).intValue();
	}

	/**
	 * 生成格式化日期，为了进行日期比较
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Date getDateFromString(String dateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date resDate = null;
		try {
			resDate = sdf.parse(dateStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resDate;
	}

	/**
	 * 获取和当前时间相隔几天的时间串
	 * 
	 * @param days
	 *            相隔天数
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String getDateByCurrent(int days) {
		Calendar cal = new GregorianCalendar();
		cal.add(Calendar.DATE, days);
		return simpleDateFormat.format(cal.getTime());
	}

	/**
	 * 返回指定时间加实数小时后的日期时间。(如果小时数为<=0那么返回当前时间curDate)
	 */
	public static Date getDateByAddFltHour(Date date, float flt, Date curDate) {
		if (TextUtil.isNotNull(flt)) {
			String dateStr = DateUtil.getFormatTime(date);
			return getDate(getDateByAddFltHour(dateStr, flt), "yyyy-MM-dd HH:mm:ss");
		}
		return curDate;
	}

	public static Date getDateAddTime(Date date, int days, int hours, int minutes, int seconds) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(GregorianCalendar.DATE, days);
		cal.add(GregorianCalendar.HOUR_OF_DAY, hours);
		cal.add(GregorianCalendar.MINUTE, minutes);
		cal.add(GregorianCalendar.SECOND, seconds);

		return cal.getTime();
	}

	/**
	 * 日期转换为字符串
	 * 
	 * @param pattern
	 * @param date
	 * @return
	 */
	public static String date2Str(String pattern, Date date) {
		if (null != date) {
			return new SimpleDateFormat(pattern).format(date);
		} else {
			return "";
		}
	}

	/**
	 * 将时间转换成说明
	 * 
	 * @param dateString
	 * @return
	 * @throws ParseException
	 */
	public String getTimeStr(String dateString) throws Exception {
		try {
			String str = "几分钟前";
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date now = new Date();
			java.util.Date pDate = df.parse(dateString);
			long l = now.getTime() - pDate.getTime();
			long day = l / (24 * 60 * 60 * 1000);
			long hour = (l / (60 * 60 * 1000) - day * 24);
			long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
			long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
			if (hour == 0) {
				if (min == 0) {
					str = "刚刚";
				} else {
					str = min + "分钟前";
				}
			}
			if (hour > 0 && hour < 2) {
				str = "1小时前";
			}
			if (hour < 24 && hour > 1) {
				str = hour + "小时前";
			}
			if (day < 30 && day > 0) {
				str = day + "天前";
			}
			if (day > 30 && day <= 60) {
				str = "1个月前";
			}
			if (day > 60 && day <= 90) {
				str = "2个月前";
			}
			if (day > 90) {
				str = "3个月前";
			}
			return str;
		} catch (Exception e) {
			throw e;
		}
	}
}
