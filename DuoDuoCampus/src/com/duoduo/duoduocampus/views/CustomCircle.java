package com.duoduo.duoduocampus.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.duoduo.duoduocampus.R;
import com.duoduo.duoduocampus.utils.DisplayUtil;

//更新那块蛋疼的小圆点
public class CustomCircle extends View{
	
	private Paint paint;
	private int oneDip;
	

	public CustomCircle(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		paint=new Paint();
		paint.setColor(getResources().getColor(
				R.color.pink));
	}
	
	public CustomCircle(Context context, AttributeSet attrs) {
		super(context, attrs);
		paint=new Paint();
		paint.setColor(getResources().getColor(
				R.color.grey));
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		oneDip = DisplayUtil.dip2px(1,
				getResources().getDisplayMetrics().density);
		canvas.drawCircle((float)6*oneDip,(float)10*oneDip,(float)3*oneDip,paint);
	}
}
