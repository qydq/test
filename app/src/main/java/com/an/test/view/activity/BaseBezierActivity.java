package com.an.test.view.activity;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.LinearLayout;

import com.an.base.utils.YbitmapUtils;
import com.an.base.utils.ytips.FastBlurUtils;
import com.an.base.view.ParallaxActivity;
import com.an.test.R;
import com.an.test.view.BezierBase;
import com.an.test.view.LineGraphicView;
import com.an.test.view.SampleView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class BaseBezierActivity extends ParallaxActivity {
    private LinearLayout scrollView;
    private LineGraphicView lineGraphicView;
    private SampleView sampleView;
    private ArrayList<Double> yList;
    //    private BezierBase bezier;
    private BezierBase bezier;
    private Timer mTimer = new Timer(true);
    private TimerTask timerTask;


    @Override
    public void initView() {
        setContentView(R.layout.sst_activity_basebezier);
        Bitmap oBitmap = YbitmapUtils.INSTANCE.ReadBitmapById(mContext, R.drawable.yy_drawable_bg_yezi);
        Bitmap sBitmap = YbitmapUtils.INSTANCE.zoomBitmap(oBitmap, 20);
        Bitmap bitmap = FastBlurUtils.doBlur(sBitmap, 9, false);
        scrollView = (LinearLayout) findViewById(R.id.activity_main);
        scrollView.setBackground(YbitmapUtils.INSTANCE.bitmapToDrawable(bitmap));

        bezier = (BezierBase) findViewById(R.id.bezier);
//        lineGraphicView = (LineGraphicView) findViewById(R.id.linegraphicView);
//        sampleView = (SampleView) findViewById(R.id.sampleView);

        List<Point> pointList = new ArrayList<>();

//        pointList.add(new Point(10, 200));
//        pointList.add(new Point(110, 300));
//        pointList.add(new Point(210, 100));
//        pointList.add(new Point(310, 400));
//        pointList.add(new Point(410, 100));
//        pointList.add(new Point(510, 200));
//        pointList.add(new Point(610, 500));
//        pointList.add(new Point(710, 600));
//        pointList.add(new Point(810, 200));
//        pointList.add(new Point(910, 450));
//        pointList.add(new Point(925, 150));


//        pointList.add(new Point(10, 200));
//        pointList.add(new Point(110, 200));
//        pointList.add(new Point(210, 200));
//        pointList.add(new Point(310, 200));
//        pointList.add(new Point(410, 200));
//        pointList.add(new Point(510, 200));
//        pointList.add(new Point(610, 200));
//        pointList.add(new Point(710, 200));
//        pointList.add(new Point(810, 200));
//        pointList.add(new Point(910, 200));
//        pointList.add(new Point(984, 200));
//        bezier.setPointList(pointList);


        bezier.setLineSmoothness(0.16f);
//        timerTask = new TimerTask() {
//            @Override
//            public void run() {
//                handler.sendEmptyMessage(10);
//            }
//        };
//
//        mTimer.schedule(timerTask, 1000,10000);
//        initGraphicData();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            bezier.startAnimation(10000);
            Log.d(TAG, "--yy@@--timerTask--timer--" + System.currentTimeMillis() / 1000);
        }
    };

    private void initGraphicData() {
        yList = new ArrayList<>();
        yList.add(2.103);
        yList.add(4.05);
        yList.add(6.60);
        yList.add(3.08);
        yList.add(4.32);
        yList.add(2.0);
        yList.add(5.0);

        ArrayList<String> xRawDatas = new ArrayList<>();
        xRawDatas.add("05-19");
        xRawDatas.add("05-20");
        xRawDatas.add("05-21");
        xRawDatas.add("05-22");
        xRawDatas.add("05-23");
        xRawDatas.add("05-24");
        xRawDatas.add("05-25");
        xRawDatas.add("05-26");
        lineGraphicView.setData(yList, xRawDatas, 8, 2);
    }
}
