package com.example.mydraggable;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.widget.TextView;

public class MyTextView extends TextView {
	Paint mPaint;
	
	private void _init(){
		// 描画準備
		mPaint = new Paint();
		mPaint.setColor(0xFFDD0000); // 赤
		mPaint.setStrokeWidth(5);
		mPaint.setAntiAlias(true);
		mPaint.setStyle(Style.STROKE);
	}
	public MyTextView(Context context) {
		super(context);
		_init();
	}
	public MyTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		_init();
	}	
	public MyTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		_init();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// デフォルト描画
		super.onDraw(canvas);
		
		// 枠描画
		int w = getWidth();
        int h = getHeight();
        canvas.drawRect(0, 0, w, h, mPaint);
	}
	
	
	

}
