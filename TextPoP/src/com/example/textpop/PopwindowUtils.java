package com.example.textpop;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PopwindowUtils {

	private PopupWindow mPopupWindow;
	private View btnView;
	private View view;
	private RelativeLayout rl_content;
	private PopOnClickListener callback;
	private TextView titleTextView;
	private Activity activity;

	private View.OnKeyListener backlistener = new View.OnKeyListener() {
		@Override
		public boolean onKey(View view, int i, KeyEvent keyEvent) {
			if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
				if (i == KeyEvent.KEYCODE_BACK) { // 表示按返回键 时的操作
					closePopupWindow();
					return true;
				} // 后退
				return false; // 已处理
			}
			return false;
		}
	};

	/**
	 * 
	 * @param btnView
	 *            被点击的控件
	 * @param activity
	 *            上下文activity
	 * @param contentView
	 *            引用view
	 */
	public PopwindowUtils(View btnView, Activity activity, View contentView,
			boolean choseDefault) {
		super();
		view = LayoutInflater.from(activity).inflate(R.layout.popwindow_item,
				null);
		rl_content = (RelativeLayout) view.findViewById(R.id.rl_content);
		if (contentView != null) {
			rl_content.addView(contentView);
		}
		titleTextView = (TextView) view.findViewById(R.id.pop_tv_title);
		if (!choseDefault) {
			titleTextView.setVisibility(View.GONE);
			view.findViewById(R.id.pop_btn_passive).setVisibility(View.GONE);
			view.findViewById(R.id.pop_btn_positive).setVisibility(View.GONE);
		}

		this.btnView = btnView;
		this.activity = activity;
	}

	/**
	 * 弹出窗体
	 */
	private void showPopwindow() {

		WindowManager.LayoutParams params = activity.getWindow()
				.getAttributes();
		mPopupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		mPopupWindow.setFocusable(true);
		mPopupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);

		mPopupWindow.showAtLocation(btnView, Gravity.RIGHT | Gravity.BOTTOM, 0,
				0);
		// 设置返回键监听
		view.setFocusable(true);
		view.setFocusableInTouchMode(true);
		view.setOnKeyListener(backlistener);
		mPopupWindow.setTouchable(true);
		params.alpha = 0.6f;

		activity.getWindow().setAttributes(params);

		view.findViewById(R.id.pop_btn_positive).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						callback.PositiveOnclick();
					}
				});

		view.findViewById(R.id.pop_btn_passive).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						callback.PassiveOnclick();
					}
				});

		view.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				closePopupWindow();
				return false;
			}
		});
	}

	/**
	 * 关闭窗体
	 */
	private void closePopupWindow() {
		if (mPopupWindow != null && mPopupWindow.isShowing()) {
			mPopupWindow.dismiss();
			mPopupWindow = null;
			WindowManager.LayoutParams params = activity.getWindow()
					.getAttributes();
			params.alpha = 1f;
			activity.getWindow().setAttributes(params);
		}
	}

	/**
	 * 获取窗体实例
	 */
	public void getPopupWindow() {

		if (null != mPopupWindow) {
			closePopupWindow();
			return;
		} else {
			showPopwindow();
		}
	}

	/**
	 * 设置popwindow标题
	 * 
	 * @param string
	 *            标题内容
	 */
	public void setTitleText(String string) {
		this.titleTextView.setText(string);
	}

	/**
	 * 设置popwindow背景色
	 * 
	 * @param color
	 *            标题背景颜色
	 */
	public void setTitleBackgroundResource(int color) {
		this.titleTextView.setBackgroundResource(color);
	}

	/**
	 * 设置popwindow标题字体颜色
	 * 
	 * @param color
	 *            字体颜色
	 */
	public void setTitleTextcolor(int color) {
		this.titleTextView.setTextColor(color);
	}

	/**
	 * 设置popwindow标题字体大小
	 * 
	 * @param size
	 *            字体大小
	 */
	public void setTitleTextsize(int size) {
		this.titleTextView.setTextSize(dip2px(activity, size));
	}

	public void setPopOnClickListener(PopOnClickListener callback) {
		this.callback = callback;
	}

	public void setChoseDefault() {

	}

	/**
	 * popwindow下部按钮回调函数
	 * 
	 * @author weizhenhua
	 * 
	 */
	public interface PopOnClickListener {
		public void PassiveOnclick();

		public void PositiveOnclick();
	}

	/**
	 * 从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}
}
