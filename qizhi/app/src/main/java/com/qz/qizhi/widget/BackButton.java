package com.qz.qizhi.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import com.qz.qizhi.R;


public class BackButton extends Button {
	@SuppressWarnings("deprecation")
	public BackButton(final Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// setImageResource(R.drawable.selector_common_title_back);
		 setBackgroundResource(R.mipmap.icon_back);
		// setBackground(getResources().getDrawable(R.drawable.btn_back));
		setGravity(Gravity.CENTER);
		setTextColor(Color.WHITE);
		this.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
//				KeyEvent keyEvent = new KeyEvent(KeyEvent.ACTION_DOWN,KeyEvent.KEYCODE_BREAK);
//						dispatchKeyEvent(keyEvent);
				((Activity) context).finish();
			}
		});
	}

	public BackButton(Context context, AttributeSet attrs) {

		this(context, attrs, 0);
	}

	public BackButton(Context context) {
		this(context, null);
	}
}
