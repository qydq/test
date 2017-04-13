package com.an.test.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qydda on 2017/4/11.
 */

public class BezierBase extends View {
    private float lineSmoothness = 0.2f;//曲线光滑指数
    private List<Point> mPointList;//数据
    private Paint mPaint;//画笔
    private Path mAssistPath;
    private float defYAxis = 700f;//阴影控制区域坐标
    private float defXAxis = 10f;
    private float drawScale = 1f;
    private Path mPath;
    private PathMeasure mPathMeasure;

    private int centerX, centerY;

    private PointF start, end, center, control1, control2, control3;
    private boolean mode = true;

    private String data = "FA D8 1D 06 1B 19 B0 86 BC 9E 8A AA C2 80 9F 19 82 87 80 F9 E6 A7 C6 D5 84 88 F4 85 E2 C1 94 85";

    private Context mContext;


    public BezierBase(Context context) {
        super(context);
        this.mContext = context;
        initPaint();
    }

    public BezierBase(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initPaint();
    }

    public void setPointList(List<Point> pointList) {
        mPointList.clear();
        mPointList = pointList;
        measurePath();
    }

    public void setLineSmoothness(float lineSmoothness) {
        if (lineSmoothness != this.lineSmoothness) {
            this.lineSmoothness = lineSmoothness;
            measurePath();
            postInvalidate();
        }
        startAnimation(3000);
    }

    public void setDrawScale(float drawScale) {
        this.drawScale = drawScale;
        postInvalidate();
    }

    public void startAnimation(long duration) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "drawScale", 0f, 1f);
        animator.setDuration(duration);
        animator.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        centerX = MeasureSpec.getSize(widthMeasureSpec) / 2;

    }

    @Override
    protected void onDraw(Canvas canvas) {
//        drawStatic(canvas);
//        drawReal(canvas);
        Log.d("point", "--yy@@--removeItem--centerx--" + getWidth());
        drawBezier(canvas);
    }

    /**
     * 绘制阴影
     *
     * @param canvas
     * @param path
     * @param pos
     */
    private void drawShadowArea(Canvas canvas, Path path, float[] pos) {
        path.lineTo(pos[0], defYAxis);
        path.lineTo(defXAxis, defYAxis);
        path.close();
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(0x88CCCCCC);
        canvas.drawPath(path, paint);
    }

    /**
     * 绘制点
     *
     * @param canvas
     * @param pos
     */
    private void drawPoint(Canvas canvas, final float[] pos) {
        Paint redPaint = new Paint();
        redPaint.setColor(Color.RED);
        redPaint.setStrokeWidth(3);
        redPaint.setStyle(Paint.Style.FILL);
        for (Point point : mPointList) {
            if (point.x > pos[0]) {
                break;
            }
            canvas.drawCircle(point.x, point.y, 10, redPaint);
        }
    }

    private PathEffect getPathEffect(float length) {
        return new DashPathEffect(new float[]{length * drawScale, length}, 0);
    }

    private void drawBezier(Canvas canvas) {
        if (mPointList == null)
            return;
        //measurePath();
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(3);
        paint.setStyle(Paint.Style.STROKE);
        //绘制辅助线
//        canvas.drawPath(mAssistPath, paint);

        paint.setColor(Color.GREEN);
        Path dst = new Path();
        dst.rLineTo(0, 0);
        float distance = mPathMeasure.getLength() * drawScale;
        if (mPathMeasure.getSegment(0, distance, dst, true)) {
            //绘制线
            canvas.drawPath(dst, paint);
            float[] pos = new float[2];
            mPathMeasure.getPosTan(distance, pos, null);
            //绘制阴影
//            drawShadowArea(canvas, dst, pos);
            //绘制点
            drawPoint(canvas, pos);
        }
        /*greenPaint.setPathEffect(getPathEffect(mPathMeasure.getLength()));
        canvas.drawPath(mPath, greenPaint);*/
        //mPath.reset();adb shell screenrecord --bit-rate 2000000 /sdcard/test.mp4
    }
    private void measurePath() {
        mPath = new Path();
        mAssistPath = new Path();
        float prePreviousPointX = Float.NaN;
        float prePreviousPointY = Float.NaN;
        float previousPointX = Float.NaN;
        float previousPointY = Float.NaN;

        float currentPointX = Float.NaN;
        float currentPointY = Float.NaN;
        float nextPointX;//下一个点x坐标
        float nextPointY;//下一个点y坐标

        final int lineSize = mPointList.size();
        for (int valueIndex = 0; valueIndex < lineSize; ++valueIndex) {
            if (Float.isNaN(currentPointX)) {
                Point point = mPointList.get(valueIndex);
                currentPointX = point.x;
                currentPointY = point.y;
            }
            if (Float.isNaN(previousPointX)) {
                //是否是第一个点
                if (valueIndex > 0) {
                    Point point = mPointList.get(valueIndex - 1);
                    previousPointX = point.x;
                    previousPointY = point.y;
                } else {
                    //是的话就用当前点表示上一个点
                    previousPointX = currentPointX;
                    previousPointY = currentPointY;
                }
            }

            if (Float.isNaN(prePreviousPointX)) {
                //是否是前两个点
                if (valueIndex > 1) {
                    Point point = mPointList.get(valueIndex - 2);
                    prePreviousPointX = point.x;
                    prePreviousPointY = point.y;
                } else {
                    //是的话就用当前点表示上上个点
                    prePreviousPointX = previousPointX;
                    prePreviousPointY = previousPointY;
                }
            }

            // 判断是不是最后一个点了
            if (valueIndex < lineSize - 1) {
                Point point = mPointList.get(valueIndex + 1);
                nextPointX = point.x;
                nextPointY = point.y;
            } else {
                //是的话就用当前点表示下一个点
                nextPointX = currentPointX;
                nextPointY = currentPointY;
            }

            if (valueIndex == 0) {
                // 将Path移动到开始点
                mPath.moveTo(currentPointX, currentPointY);
                mAssistPath.moveTo(currentPointX, currentPointY);
            } else {
                // 求出控制点坐标
                final float pAx = previousPointX + lineSmoothness * (currentPointX - prePreviousPointX);
                final float pAy = previousPointY + lineSmoothness * (currentPointY - prePreviousPointY);

                final float pBx = currentPointX - lineSmoothness * (nextPointX - previousPointX);
                final float pBy = currentPointY - lineSmoothness * (nextPointY - previousPointY);

                //画出曲线
                mPath.cubicTo(pAx, pAy, pBx, pBy, currentPointX, currentPointY);

                //将控制点保存到辅助路径上
                mAssistPath.lineTo(pAx, pAy);
                mAssistPath.lineTo(pBx, pBy);
                mAssistPath.lineTo(currentPointX, currentPointY);
            }

            // 更新值,
            prePreviousPointX = previousPointX;
            prePreviousPointY = previousPointY;
            previousPointX = currentPointX;
            previousPointY = currentPointY;
            currentPointX = nextPointX;
            currentPointY = nextPointY;
        }
        mPathMeasure = new PathMeasure(mPath, false);
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(8);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setTextSize(60);

        start = new PointF(0, 0);
        end = new PointF(0, 0);
        mPointList = new ArrayList<>();

        center = new PointF(0, 0);
        control1 = new PointF(0, 0);
        control2 = new PointF(0, 0);
        control3 = new PointF(0, 0);

        mPointList.add(new Point(10, 200));
        mPointList.add(new Point(800, 200));
    }


    public void setMode(boolean mode) {
        this.mode = mode;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w / 2;
        centerY = h / 2;
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



}