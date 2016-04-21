package com.qz.qizhi.kevin.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qz.qizhi.R;
import com.qz.qizhi.widget.SpecialCalendar;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日历gridview中的每一个item显示的textview
 *
 * @author lmw
 */
public class CalendarAdapter extends BaseAdapter {
    private boolean isLeapyear = false; // 是否为闰年
    private int daysOfMonth = 0; // 某月的天数
    private int dayOfWeek = 0; // 具体某一天是星期几
    private int lastDaysOfMonth = 0; // 上一个月的总天数
    private Context context;
    private String[] dayNumber = new String[42]; // 一个gridview中的日期存入此数组中
    private boolean[] hasPlan = new boolean[42];
    private boolean[] isLate = new boolean[42];// 标记日志是否延时填写
    // private static String week[] = {"周日","周一","周二","周三","周四","周五","周六"};
    private SpecialCalendar sc = null;
    // private LunarCalendar lc = null;
    private Resources res = null;
    private Drawable drawable = null;

    private String currentYear = "";
    private String currentMonth = "";
    private String currentDay = "";

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private int currentFlag = -1; // 用于标记当天
    private int selectFlag = -1; // 用于标记选中天
    private int[] schDateTagFlag = null; // 存储当月所有的日程日期

    private String showYear = ""; // 用于在头部显示的年份
    private String showMonth = ""; // 用于在头部显示的月份
    private String showDay = ""; // 用于在头部显示的日期
    private String animalsYear = "";
    private String leapMonth = ""; // 闰哪一个月
    private String cyclical = ""; // 天干地支
    // 系统当前时间
    private String sysDate = "";
    private String sys_year = "";
    private String sys_month = "";
    private String sys_day = "";

    public CalendarAdapter() {
        Date date = new Date();
        sysDate = sdf.format(date); // 当期日期
        sys_year = sysDate.split("-")[0];
        sys_month = sysDate.split("-")[1];
        sys_day = sysDate.split("-")[2];

    }

    public CalendarAdapter(Context context, Resources rs, int jumpMonth,
                           int jumpYear, int year_c, int month_c, int day_c) {
        this();
        this.context = context;
        sc = new SpecialCalendar();
        // lc = new LunarCalendar();
        this.res = rs;

        int stepYear = year_c + jumpYear;
        int stepMonth = month_c + jumpMonth;
        if (stepMonth > 0) {
            // 往下一个月滑动
            if (stepMonth % 12 == 0) {
                stepYear = year_c + stepMonth / 12 - 1;
                stepMonth = 12;
            } else {
                stepYear = year_c + stepMonth / 12;
                stepMonth = stepMonth % 12;
            }
        } else {
            // 往上一个月滑动
            stepYear = year_c - 1 + stepMonth / 12;
            stepMonth = stepMonth % 12 + 12;
            if (stepMonth % 12 == 0) {

            }
        }

        currentYear = String.valueOf(stepYear);
        ; // 得到当前的年份
        currentMonth = String.valueOf(stepMonth); // 得到本月
        // （jumpMonth为滑动的次数，每滑动一次就增加一月或减一月）
        currentDay = String.valueOf(day_c); // 得到当前日期是哪天

        getCalendar(Integer.parseInt(currentYear),
                Integer.parseInt(currentMonth));

    }

    public CalendarAdapter(Context context, Resources rs, int year, int month,
                           int day) {
        this();
        this.context = context;
        sc = new SpecialCalendar();
        // lc = new LunarCalendar();
        this.res = rs;
        currentYear = String.valueOf(year);
        ; // 得到跳转到的年份
        currentMonth = String.valueOf(month); // 得到跳转到的月份
        currentDay = String.valueOf(day); // 得到跳转到的天

        getCalendar(Integer.parseInt(currentYear),
                Integer.parseInt(currentMonth));

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return dayNumber.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.item_calendar, null);
        }
        RelativeLayout layout = (RelativeLayout) convertView
                .findViewById(R.id.layout_bg);
        TextView textView = (TextView) convertView.findViewById(R.id.tvtext);
        String d = dayNumber[position].split("\\.")[0];
        SpannableString sp = new SpannableString(d);
        /*
		 * sp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0,
		 * d.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); sp.setSpan(new
		 * RelativeSizeSpan(1.2f) , 0, d.length(),
		 * Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // sp.setSpan(new
		 * ForegroundColorSpan(Color.MAGENTA), 14, 16,
		 * Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
		 */
        textView.setText(sp);
        textView.setTextColor(0xFFadd5ee);

        // if(position<7){
        // //设置周
        // textView.setTextColor(Color.WHITE);
        // textView.setBackgroundColor(color.search_txt_color);
        // textView.setTextSize(14);
        // }
        if (position < daysOfMonth + dayOfWeek && position >= dayOfWeek) {
            // 当前月信息显示
            textView.setTextColor(Color.WHITE);// 当月字体
            drawable = res.getDrawable(R.color.colorBlue);
        }
        if (schDateTagFlag != null && schDateTagFlag.length > 0) {
            for (int i = 0; i < schDateTagFlag.length; i++) {
                if (schDateTagFlag[i] == position) {
                    // 设置日程标记背景
                    // textView.setBackgroundResource(R.drawable.mark);
                }
            }
        }
        if (currentFlag == position) {
            // 设置当天的背景
            drawable = res.getDrawable(R.color.colorSelectDateText);
            layout.setBackgroundDrawable(drawable);
            textView.setTextColor(0xFFFFFFFF);
        } else if (selectFlag == position) {
            drawable = res.getDrawable(R.color.white);
            textView.setTextColor(Color.BLACK);
            layout.setBackgroundDrawable(drawable);
        } else if (position < daysOfMonth + dayOfWeek && position >= dayOfWeek) {
            // 当前月信息显示
            textView.setTextColor(Color.WHITE);// 当月字体
            drawable = res.getDrawable(R.color.colorBlue);
            layout.setBackgroundDrawable(drawable);
        } else {
            textView.setTextColor(0xFFadd5ee);
            drawable = res.getDrawable(R.color.colorBlue);
            layout.setBackgroundDrawable(drawable);
        }
        return convertView;
    }

    // 得到某年的某月的天数且这月的第一天是星期几
    public void getCalendar(int year, int month) {
        isLeapyear = sc.isLeapYear(year); // 是否为闰年
        daysOfMonth = sc.getDaysOfMonth(isLeapyear, month); // 某月的总天数
        dayOfWeek = sc.getWeekdayOfMonth(year, month); // 某月第一天为星期几
        lastDaysOfMonth = sc.getDaysOfMonth(isLeapyear, month - 1); // 上一个月的总天数
        Log.d("DAY", isLeapyear + " ======  " + daysOfMonth
                + "  ============  " + dayOfWeek + "  =========   "
                + lastDaysOfMonth);
        getweek(year, month);
    }

    // 将一个月中的每一天的值添加入数组dayNuber中
    private void getweek(int year, int month) {
        int j = 1;
        int flag = 0;
        String lunarDay = "";

        // 得到当前月的所有日程日期(这些日期需要标记)

        for (int i = 0; i < dayNumber.length; i++) {
            // 周一
            // if(i<7){
            // dayNumber[i]=week[i]+"."+" ";
            // }
            if (i < dayOfWeek) { // 前一个月

                int temp = lastDaysOfMonth - dayOfWeek + 1;
                // lunarDay = lc.getLunarDate(year, month-1, temp+i,false);
                dayNumber[i] = (temp + i) + "." + lunarDay;

            } else if (i < daysOfMonth + dayOfWeek) { // 本月
                String day = String.valueOf(i - dayOfWeek + 1); // 得到的日期
                // lunarDay = lc.getLunarDate(year, month, i-dayOfWeek+1,false);
                dayNumber[i] = i - dayOfWeek + 1 + "." + lunarDay;
                // 对于当前月才去标记当前日期
                if (Integer.valueOf(sys_year) == year
                        && Integer.valueOf(sys_month) == month
                        && Integer.valueOf(sys_day) == Integer.valueOf(day)) {
                    // 标记当前日期
                    currentFlag = i;
                }
                setShowYear(String.valueOf(year));
                setShowMonth(String.valueOf(month));
                setShowDay(String.valueOf(day));
                // setAnimalsYear(lc.animalsYear(year));
                // setLeapMonth(lc.leapMonth ==
                // 0?"":String.valueOf(lc.leapMonth));
                // setCyclical(lc.cyclical(year));
            } else { // 下一个月
                // lunarDay = lc.getLunarDate(year, month+1, j,false);
                dayNumber[i] = j + "." + lunarDay;
                j++;
            }
        }

        String abc = "";
        for (int i = 0; i < dayNumber.length; i++) {
            abc = abc + dayNumber[i] + ":";
        }
        Log.d("DAYNUMBER", abc);

    }

    public void matchScheduleDate(int year, int month, int day) {

    }

    /**
     * 点击每一个item时返回item中的日期
     *
     * @param position
     * @return
     */
    public String getDateByClickItem(int position) {
        return dayNumber[position];
    }

    /**
     * 在点击gridView时，得到这个月中第一天的位置
     *
     * @return
     */
    public int getStartPositon() {
        return dayOfWeek + 7;
    }

    public int getThisMonthStartPosition() {
        return dayOfWeek;
    }

    /**
     * 在点击gridView时，得到这个月中最后一天的位置
     *
     * @return
     */
    public int getEndPosition() {
        return (dayOfWeek + daysOfMonth + 7) - 1;
    }

    public int getThisMonthEndPosition() {
        return dayOfWeek + daysOfMonth - 1;
    }

    public String getShowYear() {
        return showYear;
    }

    public void setShowYear(String showYear) {
        this.showYear = showYear;
    }

    public void setShowDay(String showDay) {
        this.showDay = showDay;
    }

    public String getShowMonth() {
        return showMonth;
    }

    public String getShowDay() {
        return showDay;
    }

    public String getSelectDate() {
        if (selectFlag == -1) {
            selectFlag = currentFlag;
        }
        String month = getShowMonth();
        String year = getShowYear();
        String day = getDateByClickItem(selectFlag).replace(".", "");
        int selectMonth = Integer.valueOf(month);
        if (selectFlag < getThisMonthStartPosition()) {
            selectMonth -= 1;
            if (selectMonth == 0) {
                selectMonth = 12;
                year = String.valueOf(Integer.valueOf(year) - 1);
            }
        } else if (selectFlag > getThisMonthEndPosition()) {
            selectMonth += 1;
            if (selectMonth == 13) {
                selectMonth = 1;
                year = String.valueOf(Integer.valueOf(year) + 1);
            }
        }
        String monthString;
        if (String.valueOf(selectMonth).length() < 2) {
            monthString = "0" + selectMonth;
        } else {
            monthString = String.valueOf(selectMonth);
        }
        if (day.length() < 2) {
            day = "0" + day;
        }
        return year + "年" + monthString + "月" + day + "日";
    }

    public String getSelectDateByPosition(int position) {
        if (position == -1) {
            return "";
        }
        String month = getShowMonth();
        String year = getShowYear();
        String day = getDateByClickItem(position).replace(".", "");
        int selectMonth = Integer.valueOf(month);
        if (position < getThisMonthStartPosition()) {
            selectMonth -= 1;
            if (selectMonth == 0) {
                selectMonth = 12;
                year = String.valueOf(Integer.valueOf(year) - 1);
            }
        } else if (position > getThisMonthEndPosition()) {
            selectMonth += 1;
            if (selectMonth == 13) {
                selectMonth = 1;
                year = String.valueOf(Integer.valueOf(year) + 1);
            }
        }
        String monthString;
        if (String.valueOf(selectMonth).length() < 2) {
            monthString = "0" + selectMonth;
        } else {
            monthString = String.valueOf(selectMonth);
        }
        if (day.length() < 2) {
            day = "0" + day;
        }
        return year + "-" + monthString + "-" + day;
    }

    public String getMonthFristDate() {
        String month = getShowMonth();
        String year = getShowYear();
        String day = getDateByClickItem(dayOfWeek).replace(".", "");
        int preMonth = Integer.valueOf(month);
        if (Integer.valueOf(day) > 1) {
            preMonth -= 1;
            if (preMonth == 0) {
                preMonth = 12;
                year = String.valueOf(Integer.valueOf(year) - 1);
            }
        }
        return year + "-" + preMonth + "-" + day;
    }

    public String getMonthEndDate() {
        String month = getShowMonth();
        String year = getShowYear();
        String day = getDateByClickItem(dayOfWeek + daysOfMonth - 1).replace(
                ".", "");
        int nextMonth = Integer.valueOf(month);
        // if (Integer.valueOf(day)>=1) {
        // nextMonth += 1;
        // if (nextMonth == 13) {
        // nextMonth = 1;
        // year = String.valueOf(Integer.valueOf(year)+1);
        // }
        // }
        return year + "-" + nextMonth + "-" + day;
    }

    public void setShowMonth(String showMonth) {
        this.showMonth = showMonth;
    }

    public String getAnimalsYear() {
        return animalsYear;
    }

    public void setAnimalsYear(String animalsYear) {
        this.animalsYear = animalsYear;
    }

    public String getLeapMonth() {
        return leapMonth;
    }

    public void setLeapMonth(String leapMonth) {
        this.leapMonth = leapMonth;
    }

    public String getCyclical() {
        return cyclical;
    }

    public void setCyclical(String cyclical) {
        this.cyclical = cyclical;
    }

    public void setSelectFlag(int flag) {
        this.selectFlag = flag;
    }

    public int getSelectFlag() {
        return this.selectFlag;
    }

    public void setHasPlan(boolean[] hasPlan) {
        this.hasPlan = hasPlan;
    }

    public void setLate(boolean[] isLate) {
        this.isLate = isLate;
    }
}
