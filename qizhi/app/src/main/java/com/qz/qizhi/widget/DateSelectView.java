package com.qz.qizhi.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.qz.qizhi.R;
import com.qz.qizhi.kevin.adapter.CalendarAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

/**
 * 
 * @author Administrator
 *
 */
public class DateSelectView {

	Context mContext;
	// 将日期返回给textView
	TextView textView;
	View showAtLocation;

	public DateSelectView(Context context, TextView textView, View showAtLocation) {
		mContext = context;
		this.textView = textView;
		this.showAtLocation = showAtLocation;
	}

	PopupWindow pw;
	SpecialCalendar specialCalendar;

	List<View> viewList;
	int curIndex;
	ViewPager mViewPager;
	TextView tvDate;
	boolean isClose;

	public void showDatePw() {
		jumpMonth = 0;
		setCurrentdate(); 
		specialCalendar = new SpecialCalendar();
		View view = LayoutInflater.from(mContext).inflate(R.layout.layout_popupwindow_date, null);
		pw = new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		pw.setOutsideTouchable(false);
		pw.setBackgroundDrawable(new BitmapDrawable());
		pw.setFocusable(true);
		mViewPager = (ViewPager) view.findViewById(R.id.viewpage);
		tvDate = (TextView) view.findViewById(R.id.tvDate);
		curIndex = 498;
		viewList = new ArrayList<View>();
		viewList.add(LayoutInflater.from(mContext).inflate(R.layout.layout_gridview, null));
		viewList.add(LayoutInflater.from(mContext).inflate(R.layout.layout_gridview, null));
		viewList.add(LayoutInflater.from(mContext).inflate(R.layout.layout_gridview, null));
		mViewPager.setAdapter(mPagerAdapter = new MyPagerAdapter(viewList));
		mViewPager.setOnPageChangeListener(new MyOnPageChangeListener());
		mViewPager.setCurrentItem(curIndex);

		pw.showAtLocation(showAtLocation, Gravity.TOP, 0, 0);
		pw.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				// TODO Auto-generated method stub
				if (!isClose){
					textView.setText(calV.getSelectDate());
				}
				isClose = true;
			}
		});
		isClose = false;
		view.findViewById(R.id.btClose).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (calV.getSelectFlag()==-1){
					isClose = true;
				}
				pw.dismiss();
			}
		});

	}

	public boolean isClose() {
		return isClose;
	}

	public void setClose(boolean isClose) {
		this.isClose = isClose;
	}

	/**
	 * 页卡切换监听
	 */
	public class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageSelected(int position) {
			if (curIndex > position) {// 向左
				// dayIndex--;
				jumpMonth--;
			} else if (curIndex < position) {// 向右
				// dayIndex++;
				jumpMonth++;
			}
			curIndex = position;
			calV = new CalendarAdapter(mContext, mContext.getResources(), jumpMonth, jumpYear, year_c, month_c, day_c);
			gridView = (GridView) viewList.get(mViewPager.getCurrentItem() % 3);
			gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
			gridView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					// TODO Auto-generated method stub
//					if (calV.isThisMonth(position)) {
						calV.setSelectFlag(position);
						calV.notifyDataSetChanged();
						pw.dismiss();
//					}
					// Constants.showToast(calV.getSelectDate());
				}
			});
			gridView.setAdapter(calV);
			setTvDate(calV.getShowYear() + "/" + calV.getShowMonth());
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageScrollStateChanged(int position) {

		}
	}

	private int year_c = 0;
	private int month_c = 0;
	private int day_c = 0;
	private String currentDate = "";
	private static int jumpMonth = 0;
	private static int jumpYear = 0;
	private CalendarAdapter calV = null;
	private GridView gridView;

	public void setCurrentdate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		TimeZone gmt = TimeZone.getTimeZone("GMT+08:00");
		format.setTimeZone(gmt);
		currentDate = format.format(Calendar.getInstance().getTime());
		year_c = Integer.parseInt(currentDate.split("-")[0]);
		month_c = Integer.parseInt(currentDate.split("-")[1]);
		day_c = Integer.parseInt(currentDate.split("-")[2]);
		calV = new CalendarAdapter(mContext, mContext.getResources(), jumpMonth, jumpYear, year_c, month_c, day_c);
	}

	public void setDate(String date) {
		currentDate = date;
		year_c = Integer.parseInt(currentDate.split("-")[0]);
		month_c = Integer.parseInt(currentDate.split("-")[1]);
		day_c = Integer.parseInt(currentDate.split("-")[2]);
	}

	public void setTvDate(String tvDate) {
		this.tvDate.setText(tvDate);
	}

	/**
	 * ViewPager适配器
	 */
	MyPagerAdapter mPagerAdapter;

	private class MyPagerAdapter extends PagerAdapter {
		public List<View> mListViews;

		public MyPagerAdapter(List<View> mListViews) {
			this.mListViews = mListViews;
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			// ((ViewPager) arg0).removeView(mListViews.get(arg1%3));
		}

		@Override
		public void finishUpdate(View arg0) {
		}

		@Override
		public int getCount() {
			return 1000;// mListViews.size();
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			if (((ViewPager) arg0).getChildCount() == 3) {
				((ViewPager) arg0).removeView(mListViews.get(arg1 % 3));
			}
			((ViewPager) arg0).addView(mListViews.get(arg1 % 3), 0);

			return mListViews.get(arg1 % 3);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == (arg1);
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
		}
	}

	public int getYear_c() {
		return Integer.valueOf(calV.getSelectDate().split("-")[0]);
	}


	public int getMonth_c() {
		return Integer.valueOf(calV.getSelectDate().split("-")[1]);
	}


	public int getDay_c() {
		return Integer.valueOf(calV.getSelectDate().split("-")[2]);
	}

	
	
}
