package com.an.test.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

import com.an.test.utils.Utils;

/**
 * Created by qydda on 2017/4/11.
 */

public class Bezier2 extends View {
    private static String TAG = "Bezier2";

    private Paint mPaint;
    private int centerX, centerY;

    private PointF start, end, center, control1, control2, control3;
    private boolean mode = true;

    private String data = "FA D8 1D 06 1B 19 B0 86 BC 9E 8A AA C2 80 9F 19 82 87 80 F9 E6 A7 C6 D5 84 88 F4 85 E2 C1 94 85";

    private Context mContext;


    public Bezier2(Context context) {
        this(context, null);
        this.mContext = context;
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(8);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setTextSize(60);

        start = new PointF(0, 0);
        end = new PointF(0, 0);
        center = new PointF(0, 0);
        control1 = new PointF(0, 0);
        control2 = new PointF(0, 0);
        control3 = new PointF(0, 0);
    }

    public Bezier2(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initPaint();
    }

    public void setMode(boolean mode) {
        this.mode = mode;
    }

    private int[] unpack(String data) {
        int[] bytes = Utils.hexStringToInt(data);
        return bytes;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w / 2;
        centerY = h / 2;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawStatic(canvas);
//        drawReal(canvas);
    }

    private void drawStatic(Canvas canvas) {
        Path path = new Path();

        start.x = 0;
        start.y = centerY;

        center.x = centerX;
        center.y = centerY - 300;

        end.x = centerX * 2;
        end.y = centerY;


        control1.x = centerX / 2;
        control1.y = centerY - 300;

        control2.x = 2 * center.x - control1.x;
        control2.y = centerY - 300;

        control3.x = control2.x;
        control3.y = centerY - 120;


        // 绘制数据点和控制点
        mPaint.setColor(Color.GRAY);
        mPaint.setStrokeWidth(20);
        canvas.drawPoint(start.x, start.y, mPaint);
        canvas.drawPoint(end.x, end.y, mPaint);
        canvas.drawPoint(center.x, center.y, mPaint);

        canvas.drawPoint(control1.x, control1.y, mPaint);
        canvas.drawPoint(control2.x, control2.y, mPaint);
        canvas.drawPoint(control3.x, control3.y, mPaint);

        //绘制曲线
        path.moveTo(start.x, start.y);
        mPaint.setStrokeWidth(8);
        mPaint.setColor(Color.RED);
//        path.cubicTo(control1.x, control1.y, control2.x, control2.y, center.x, center.y);

        path.quadTo(control1.x, control1.y, center.x, center.y);

        canvas.drawPath(path, mPaint);

        path.moveTo(center.x, center.y);

//        path.quadTo(control2.x, control2.y, end.x, end.y);

        path.cubicTo(control2.x, control2.y, control3.x, control3.y, end.x, end.y);

        canvas.drawPath(path, mPaint);
    }

    private void drawReal(Canvas canvas) {
        Path path = new Path();
        String tData = data.replaceAll(" ", "");
        int[] unpack = unpack(tData.substring(36, 48));
        data = "FA D8 1D 06 1B 19 B0 86 BC 9E 8A AA C2 80 9F 19 82 87 80 F9 E6 A7 C6 D5 84 88 F4 85 E2 C1 94 85";
        String tData1 = data.replaceAll(" ", "");

        int[] unpack1 = unpack(tData1.substring(52, tData1.length()));


        int startPiy = centerY + unpack[0];
        int startPix = 0;

        int centerPiy = centerY + unpack[1];
        int centerPix = (centerX * 2 / 3) * 2;

        int endPiy = centerY + unpack[2];
        int endPix = (centerX * 2 / 3) * 3;

        start.x = startPix;
        start.y = startPiy;

        center.x = endPix;
        center.y = endPiy;

        end.x = endPix;
        end.y = endPiy;

        control1.x = (centerPix + startPix) / 2;
        control1.y = centerPiy;

        control2.x = (endPix + centerPix) / 2 + centerPix;
        control2.y = centerPiy;

        // 绘制数据点和控制点
        mPaint.setColor(Color.GRAY);


        mPaint.setStrokeWidth(20);
        canvas.drawPoint(start.x, start.y, mPaint);
        canvas.drawPoint(end.x, end.y, mPaint);
        canvas.drawPoint(center.x, center.y, mPaint);

        canvas.drawPoint(control1.x, control1.y, mPaint);
        canvas.drawPoint(control2.x, control2.y, mPaint);

        mPaint.setStrokeWidth(4);
        canvas.drawLine(start.x, start.y, control1.x, control1.y, mPaint);
        canvas.drawLine(control1.x, control1.y, control2.x, control2.y, mPaint);
        canvas.drawLine(control2.x, control2.y, end.x, end.y, mPaint);

        path.moveTo(start.x, start.y);

        mPaint.setStrokeWidth(8);
        mPaint.setColor(Color.RED);
        path.cubicTo(control1.x, control1.y, control2.x, control2.y, end.x, end.y);
        canvas.drawPath(path, mPaint);
    }

}