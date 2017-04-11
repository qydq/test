package com.an.test.view.activity;

import android.graphics.Bitmap;
import android.widget.LinearLayout;

import com.an.base.utils.DUtilsBitmap;
import com.an.base.utils.ytips.FastBlurUtil;
import com.an.base.view.ParallaxActivity;
import com.an.test.R;
import com.an.test.view.Bezier2;
import com.an.test.view.LineGraphicView;
import com.an.test.view.SampleView;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

public class LineGraphicActivity extends ParallaxActivity {
    private LinearLayout scrollView;
    @ViewInject(R.id.linegraphicView)
    private LineGraphicView lineGraphicView;
//    private SampleView lineGraphicView;
    private ArrayList<Double> yList;


    @Override
    public void initView() {

//        SampleView view = new SampleView(mContext);
//        setContentView(view);

        Bezier2 view = new Bezier2(mContext);
        setContentView(view);

       /* setContentView(R.layout.sst_activity_linegraphic);
        Bitmap oBitmap = DUtilsBitmap.INSTANCE.ReadBitmapById(mContext, R.drawable.yy_drawable_bg_yezi);
        Bitmap sBitmap = DUtilsBitmap.INSTANCE.zoomBitmap(oBitmap, 20);
        Bitmap bitmap = FastBlurUtil.doBlur(sBitmap, 9, false);

        scrollView = (LinearLayout) findViewById(R.id.activity_main);
        lineGraphicView = (LineGraphicView) findViewById(R.id.linegraphicView);

        scrollView.setBackground(DUtilsBitmap.INSTANCE.bitmapToDrawable(bitmap));
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
        lineGraphicView.setData(yList, xRawDatas, 8, 2);*/

    }
}
