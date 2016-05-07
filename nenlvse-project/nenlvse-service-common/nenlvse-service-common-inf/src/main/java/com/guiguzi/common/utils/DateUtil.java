package com.guiguzi.common.utils;


import org.apache.http.impl.cookie.DateParseException;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

/**
 * Created by kqy on 2015/5/10.
 */
public class DateUtil {

    /**
     * 获取文件名 当前时间戳+随机数
     *
     * @return
     */
    public static String getTimestampForFileId() {
        return System.currentTimeMillis() + "" + new Random().nextInt(10000);
    }

    /**
     * 将指定字符串转换成日期
     *
     * @param date        String 日期字符串
     * @param datePattern String 日期格式
     * @return Date
     */
    public static Date getFormatDate(String date, String datePattern) {
        SimpleDateFormat sd = new SimpleDateFormat(datePattern);
        return sd.parse(date, new java.text.ParsePosition(0));
    }


    /**
     * 将指定字符串转换成日期
     *
     * @param date        String 日期字符串
     * @return Date
     */
    public static Date getFormatDate8(String date) throws DateParseException {
        if(date == null || date.length() == 0) return null;
        date = date.replaceAll("[-\\s/:]+","");

        if(date.length()<8) throw new DateParseException("日期格式错误");
        if(date.length()>8) date = date.substring(0,8);

        SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
        return sd.parse(date, new java.text.ParsePosition(0));
    }


    /**
     * 将指定日期对象转换成格式化字符串
     *
     * @param date        Date XML日期对象
     * @param datePattern String 日期格式
     * @return String
     */
    public static String getFormattedString(Date date, String datePattern) {
        SimpleDateFormat sd = new SimpleDateFormat(datePattern);

        return sd.format(date);
    }

    /**
     * 将指定XML日期对象转换成格式化字符串
     *
     * @param xmlDate     Date XML日期对象
     * @param datePattern String 日期格式
     * @return String
     */
    public static String getFormattedString(XMLGregorianCalendar xmlDate,
                                            String datePattern) {
        SimpleDateFormat sd = new SimpleDateFormat(datePattern);

        Calendar calendar = xmlDate.toGregorianCalendar();

        return sd.format(calendar.getTime());
    }

    /**
     * 将指定XML日期对象转换成日期对象
     *
     * @param xmlDate     Date XML日期对象
     * @return Date
     */
    public static Date xmlGregorianCalendar2Date(XMLGregorianCalendar xmlDate) {
        return xmlDate.toGregorianCalendar().getTime();
    }

    public static String getThisYear() {
        // 获得当前日期
        Calendar cldCurrent = Calendar.getInstance();
        // 获得年月日
        String strYear = String.valueOf(cldCurrent.get(Calendar.YEAR));
        return strYear;
    }

    public static XMLGregorianCalendar convert2XMLCalendar(Calendar calendar) {
        try {
            DatatypeFactory dtf = DatatypeFactory.newInstance();
            return dtf.newXMLGregorianCalendar(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH) + 1,
                    calendar.get(Calendar.DAY_OF_MONTH),
                    calendar.get(Calendar.HOUR),
                    calendar.get(Calendar.MINUTE),
                    calendar.get(Calendar.SECOND),
                    calendar.get(Calendar.MILLISECOND),
                    calendar.get(Calendar.ZONE_OFFSET) / (1000 * 60));

        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date getNowDate(String dateformat) {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);// 可以方便地修改日期格式
        String dateString = dateFormat.format(now);
        SimpleDateFormat sd = new SimpleDateFormat(dateformat);
        Date date = sd.parse(dateString, new java.text.ParsePosition(0));
        return date;
    }

    // 获取当天时间
    public static java.sql.Timestamp getNowTime(String dateformat) {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);// 可以方便地修改日期格式
        String dateString = dateFormat.format(now);
        SimpleDateFormat sd = new SimpleDateFormat(dateformat);
        Date dateFormt = sd.parse(dateString, new java.text.ParsePosition(0));
        java.sql.Timestamp dateTime = new java.sql.Timestamp(dateFormt
                .getTime());

        return dateTime;
        // return hehe;
    }

    // 获取指定时间
    public static java.sql.Timestamp getNowNewTime(String date, String dateformat) {
        //Date   now   =   new   Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);//可以方便地修改日期格式
        dateFormat.parse(date, new java.text.ParsePosition(0));

        //  String  dateString= dateFormat.format(date);
        Date dateFormt = dateFormat.parse(date, new java.text.ParsePosition(0));
        java.sql.Timestamp dateTime = new java.sql.Timestamp(dateFormt.getTime());


        return dateTime;
    }

    /**
     * @param tdate 含有yyyy-MM-dd'T'hh:mm:ss.SSS格式的时间转换.
     * @return
     */
    public static String getTFormatString(String tdate) {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        String str = "";
        try {
            Date date = format1.parse(tdate);
            SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            str = format2.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static void main(String[] args) {
        SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
        String date = "20110202";
        System.out.println(sd.parse(date, new java.text.ParsePosition(0)));
        System.out.println(getBefore2HourDate());
    }

    //获取当前时间前2个小时的时间。
    public static String getBefore2HourDate() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.HOUR_OF_DAY, -2); // 目前時間加3小時
        return df.format(c.getTime());

    }

    /**
     * @param time1 当前时间
     * @param time2 比较时间
     * @return 如果time1比time2大gap分钟，则返回true;
     */
    public static boolean compareDateTime(Date time1, Date time2, int gap) {
        return time1.getTime() - time2.getTime() > gap * 60 * 1000;
    }


    public static Date DateAddOne(Date time) {
        Calendar   calendar   =   new GregorianCalendar();
        calendar.setTime(time);
        calendar.add(calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动
        return calendar.getTime();
    }
}
