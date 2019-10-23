package com.winterchen.utilCommon;


import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期工具类
 * 
 */
public class DateUtils {

	private static final String	DEFAULT_CONVERT_PATTERN	= "yyyyMMddHHmmssSSS";

	/**
	 * 获取当前时间字符串(默认格式:yyyyMMddHHmmssSSS)  
	 */
	public static String getCurrentTimeStrDefault() {
		return getCurrentTimeStr(DEFAULT_CONVERT_PATTERN);
	}

	/**
	 * 获取指定时间字符串(默认格式:yyyyMMddHHmmssSSS)
	 */
	public static String getTimeStrDefault(Date date) {
		if (null == date) return "";

		SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_CONVERT_PATTERN);
		return dateFormat.format(date);
	}

	/**
	 * 获取当前时间字符串
	 *
	 * @param pattern
	 *            转换格式
	 */
	public static String getCurrentTimeStr(String pattern) {
		if (StringUtils.isBlank(pattern)) return "";

		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.format(new Date());
	}

	/**
	 * 获取指定时间字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String getTimeStr(Date date, String pattern) {
		if (null == date || StringUtils.isBlank(pattern)) return "";

		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.format(date);
	}

	/**
	 * 判断时间字符串是否为默认格式
	 * 
	 * @param dateTimeStr
	 */
	public static boolean isValidDefaultFormat(String dateTimeStr) {
		if (StringUtils.isBlank(dateTimeStr)) return false;

		SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_CONVERT_PATTERN);
		try {
			dateFormat.parse(dateTimeStr);
			return true;  
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 		日期加  完整
	 * @param date  原日期
	 * @param addTime  数目
	 * @param type  1:年  2：月  3：周  4：日
	 * @return
	 * hcl
	 */

	public static Date addTime(Date date,int addTime,String type){
		GregorianCalendar gc =new GregorianCalendar();
		gc.setTime(date);
		if(type.equals("1")){ 
			gc.add(1,+addTime);
		}else if(type.equals("2")){
			gc.add(2,+addTime);
		}else if(type.equals("3")){
			gc.add(3,+addTime);
		}else if(type.equals("4")){
			gc.add(5,+addTime);
		}
		gc.set(gc.get(Calendar.YEAR),gc.get(Calendar.MONTH),gc.get(Calendar.DATE));
		return gc.getTime();

	}
	
	/**
	 * 		日期减  完整
	 * @param date  原日期
	 * @param addTime  数目
	 * @param type  1:年  2：月  3：周  4：日
	 * @return
	 * hcl
	 */
	
	public static String subtractTimeall(Date date,int addTime,String type){
		GregorianCalendar gc =new GregorianCalendar();
		gc.setTime(date);
		if(type.equals("1")){
			gc.add(1,-addTime);
		}else if(type.equals("2")){
			gc.add(2,-addTime);
		}else if(type.equals("3")){
			gc.add(3,-addTime);
		}else if(type.equals("4")){
			gc.add(5,-addTime);
		}
		
		gc.set(gc.get(Calendar.YEAR),gc.get(Calendar.MONTH),gc.get(Calendar.DATE));
		return dateToChinese2(gc.getTime());
		
	}
	public static Date subtractTimeall2(Date date,int addTime,String type){
		GregorianCalendar gc =new GregorianCalendar();
		gc.setTime(date);
		if(type.equals("1")){
			gc.add(1,-addTime);
		}else if(type.equals("2")){
			gc.add(2,-addTime);
		}else if(type.equals("3")){
			gc.add(3,-addTime);
		}else if(type.equals("4")){
			gc.add(5,-addTime);
		}
		
		gc.set(gc.get(Calendar.YEAR),gc.get(Calendar.MONTH),gc.get(Calendar.DATE));
		return gc.getTime();
		
	}
	
	/**
	 * 把日期转换成相应格式的字符串
	 * @author lipengfei
	 * @param date
	 * @return String
	 * @throws
	 */
	public static String dateToStr(Date date, String dateFormat) {

		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		return format.format(date);
	}
	public static String dateToChinese2(Date date) {

		return dateToStr(date, "yyyy-MM-dd HH:mm:ss");
	}
	public static String dateToChinese3(Date date) {

		return dateToStr(date, "HH:mm:ss");
	}
	public static String dateToChinese1(Date date) {

		return dateToStr(date, "yyyy-MM-dd");
	}
	public static String dateToChinese(Date date) {

		return dateToStr(date, "yyyy年MM月dd日HH时mm分ss秒");
	}

	public static String getCurrentDate() {

		return dateToStr(new Date(), "yyyy-MM-dd");
	}
	
	public static String getCurrentDate2(Date date) {

		return dateToStr(new Date(), "yyyyMMdd");
	}

	public static String getCurrentTime() {

		return dateToStr(new Date(), "HH:mm:ss");
	}

	public static String getCurrentDate(String dateFormat) {

		return dateToStr(new Date(), dateFormat);
	}

	public static String getYearFromDate(Date date) {

		return dateToStr(date, "yyyy");
	}

	public static String getMonthFromDate(Date date) {

		return dateToStr(date, "MM");
	}

	public static String getDayFromDate(Date date) {

		return dateToStr(date, "dd");
	}

	public static String getHourFromDate(Date date) {

		return dateToStr(date, "HH");
	}

	public static String getMinuteFromDate(Date date) {

		return dateToStr(date, "mm");
	}

	public static String getSecondFromDate(Date date) {

		return dateToStr(date, "ss");
	}

	public static String getMillisecondFromDate(Date date) {

		return dateToStr(date, "SSS");
	}
	public static String calculateTime(Date date1, Date date2) {
		StringBuffer result = new StringBuffer();
		long between = (date1.getTime() - date2.getTime()) / 1000;// 除以1000是为了转换成秒
		long days = between / (24 * 3600);
		long hours = between % (24 * 3600) / 3600;
		long minutes = between % 3600 / 60;
		long seconds = between % 60 / 60;
		result.append(days).append("天").append(hours).append("小时").append(minutes).append("分").append(seconds)
				.append("秒");
		return result.toString();
	}

	public static Date calculateDate(Date date, long days, long hours, long minutes, long seconds) {
		long between = (days * 24 * 3600 + hours * 3600 + minutes * 60 + seconds) * 1000;
		long newTime = date.getTime() + between;
		return new Date(newTime);
	}

	public static boolean compareDate(Date date1, Date date2) {
		return date1.getTime() > date2.getTime();
	}

	public static boolean compareDate1(Date date1, Date date2) {
		return date1.getTime() < date2.getTime();
	}

	public static Date compareDate2(Date date1) {
		Date date2=new Date();
		
		if(date1.getTime() > date2.getTime()){
			return date1;
		}else{
			return null;
		}
		 
	}
	/**
     * 通过时间秒毫秒数判断两个时间的间隔
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDaysByMillisecond(Date date1,Date date2)
    {
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
        return days;
    }
    /**
	 * 日期转星期
	 *
	 * @param datetime
	 * @return
	 */
	public static String dateToWeek(String datetime) {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		String[] weekDays = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
		Calendar cal = Calendar.getInstance(); // 获得一个日历
		Date datet = null;
		try {
			datet = f.parse(datetime);
			cal.setTime(datet);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
		if (w < 0)
			w = 0;
		return weekDays[w];
	}
	
	public static String dateToWeek(Date dt) {
	    String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(dt);
	    int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
	    if (w < 0)
	        w = 0;
	    return weekDays[w];
	}
	/**
	 * 判断日期是否过期
	 * 
	 * @return
	 */
	public static boolean isExpired(Date startDate, int days) {
		Date dueDate = calculateDate(startDate, days, 0, 0, 0);
		System.out.println(dateToChinese(dueDate));
		return compareDate(dueDate, new Date());
	}
	/**
     * 判断时间是不是今天
     * @param date
     * @return    是返回true，不是返回false
     */
    public static boolean isNow(Date date) {
        //当前时间
        Date now = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        //获取今天的日期
        String nowDay = sf.format(now);
        //对比的时间
        String day = sf.format(date);
        return day.equals(nowDay);
    }

	public static Date returnDateFromString(String s) {
		Date d = null;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
			d = formatter.parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d;
	}

	/**
	 * 时间工具类
	 **/
	public static String getFormatDate(Date date, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}

	/**
	 * 计算年龄
	 * */
	public static int getUserAgeByBirthday(Date date){
		/*if(date != null){
			return Integer.parseInt(getFormatDate(new Date(),"yyyy"))-Integer.parseInt(getFormatDate(date,"yyyy"));
		}
		return 0;*/
		if (date == null) {
			throw new IllegalArgumentException(
					"The birthDay can not be null!");
		}
		Calendar cal = Calendar.getInstance();
		if (cal.before(date)) {
			throw new IllegalArgumentException(
					"The birthDay is before Now.It's unbelievable!");
		}
		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH);
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
		cal.setTime(date);

		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH);
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

		int age = yearNow - yearBirth;

		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				if (dayOfMonthNow < dayOfMonthBirth) age--;
			} else {
				age--;
			}
		}
		return age;
	}

}
