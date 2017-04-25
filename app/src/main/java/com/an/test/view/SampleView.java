package com.an.test.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.an.base.utils.ytips.SizeUtils;


/**
 * Created by qydda on 2017/4/10.
 */

public class SampleView extends View {

    private Paint mPaint;

    private int centerX, centerY;

    private PointF start, end, control;

    private int mWidth;
    private int mHeight;
    private Context mContext;

    public SampleView(Context context) {
        super(context);
        this.mContext = context;
        initPaint();

    }

    public SampleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initPaint();
    }

    // 2.初始化画笔
    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(8);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setTextSize(60);

        start = new PointF(0, 0);
        end = new PointF(0, 0);
        control = new PointF(0, 0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w / 2;
        centerY = h / 2;

        // 初始化数据点和控制点的位置
        start.x = centerX - 400;
        start.y = centerY;

        end.x = centerX + 400;
        end.y = centerY;

        control.x = centerX;
        control.y = centerY - 100;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 根据触摸位置更新控制点，并提示重绘
        control.x = event.getX();
        control.y = event.getY();

        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 绘制数据点和控制点
        mPaint.setColor(Color.GRAY);
        mPaint.setStrokeWidth(20);
        canvas.drawPoint(start.x, start.y, mPaint);//数据点，开始的位置
        canvas.drawPoint(end.x, end.y, mPaint);//数据点，结束的位置
        canvas.drawPoint(control.x, control.y, mPaint);//控制点，弯曲的程度

        // 绘制辅助线
        mPaint.setStrokeWidth(4);
        canvas.drawLine(start.x, start.y, control.x, control.y, mPaint);

        canvas.drawLine(end.x, end.y, control.x, control.y, mPaint);

        for (int i = 0; i < mWidth; i = i + 50) {
            canvas.drawLine(0, SizeUtils.dp2Px(mContext, i), mWidth, SizeUtils.dp2Px(mContext, i), mPaint);//x
        }

        for (int i = 0; i < mWidth; i = i + 20) {
            canvas.drawLine(SizeUtils.dp2Px(mContext, i), 0, SizeUtils.dp2Px(mContext, i), mHeight, mPaint);//y
        }
        // 绘制贝塞尔曲线
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(8);

        Path path = new Path();

        path.moveTo(start.x, start.y);

        path.quadTo(control.x, control.y, end.x, end.y);

//        path.cubicTo(control1.x, control1.y, control2.x, control2.y, end.x, end.y);

        canvas.drawPath(path, mPaint);
    }

}
