package com.qz.qizhi.widget;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SpecialCalendar {

    private int daysOfMonth = 0; // 某月的天数
    private int dayOfWeek = 0; // 具体某一天是星期几
    private static String week[] = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };

    // 判断是否为闰年
    public boolean isLeapYear(int year) {
        if (year % 100 == 0 && year % 400 == 0) {
            return true;
        } else if (year % 100 != 0 && year % 4 == 0) {
            return true;
        }
        return false;
    }

    // 得到某月有多少天数
    public int getDaysOfMonth(boolean isLeapyear, int month) {
        switch (month) {
        case 1:
        case 3:
        case 5:
        case 7:
        case 8:
        case 10:
        case 12:
            daysOfMonth = 31;
            break;
        case 4:
        case 6:
        case 9:
        case 11:
            daysOfMonth = 30;
            break;
        case 2:
            if (isLeapyear) {
                daysOfMonth = 29;
            } else {
                daysOfMonth = 28;
            }

        }
        return daysOfMonth;
    }

    // 指定某年中的某月的第一天是星期几
    public int getWeekdayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, 1);
        dayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;
        return dayOfWeek;
    }

    public String getWeekdayOfMonth(int day) {

        return week[day];
    }

    @SuppressWarnings("deprecation")
    public int getWeekDay(int y, int m, int d) {

        Date date = new Date();
        date.setYear(y);
        date.setMonth(m + 1);
        date.setDate(d);
        return date.getDay() + 1;
    }

    public String getWeek(int index) {
        String week = null;
        switch (index) {
        case 1:
            week = "周日";
            break;
        case 2:
            week = "周一";
            break;
        case 3:
            week = "周二";
            break;
        case 4:
            week = "周三";
            break;
        case 5:
            week = "周四";
            break;
        case 6:
            week = "周五";
            break;
        case 7:
            week = "周六";
            break;
        }
        return week;
    }

    public String preOrNextDay(String currentDate, int i) {
        if (null == currentDate || currentDate.length() == 0) {
            return " ";
        }
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 参数为你要格式化时间日期的模式
        Date date = null;
        try {
            date = df.parse(currentDate);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            c.setTime(date); // 设置当前日期
            c.add(Calendar.DATE, i); // 日期加1天
            // c.add(Calendar.DATE, -1); //日期减1天
            date = c.getTime();

            return sdf.format(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return " ";
    }

}
