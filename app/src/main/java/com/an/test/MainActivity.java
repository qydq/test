package com.an.test;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.TextView;

import com.an.base.utils.DUtilsBitmap;
import com.an.base.utils.ytips.FastBlurUtil;
import com.an.base.view.ParallaxActivity;
import com.an.base.view.widget.WIoScrollView;
import com.an.test.view.activity.BaseBezierActivity;

import org.xutils.view.annotation.ViewInject;

public class MainActivity extends ParallaxActivity implements View.OnClickListener {
    @ViewInject(R.id.activity_main)
    private WIoScrollView scrollView;
    @ViewInject(R.id.tv)
    private TextView tv;
    private TextView tvBeiSar;

    @Override
    public void initView() {
        setContentView(R.layout.activity_main);
        scrollView = (WIoScrollView) findViewById(R.id.activity_main);
        tv = (TextView) findViewById(R.id.tv);
        tvBeiSar = (TextView) findViewById(R.id.tvBeiSar);

        Bitmap oBitmap = DUtilsBitmap.INSTANCE.ReadBitmapById(mContext, R.drawable.yy_drawable_bg_yezi);
        Bitmap sBitmap = DUtilsBitmap.INSTANCE.zoomBitmap(oBitmap, 20);
        Bitmap bitmap = FastBlurUtil.doBlur(sBitmap, 9, false);
        scrollView.setBackground(DUtilsBitmap.INSTANCE.bitmapToDrawable(bitmap));
        tv.setOnClickListener(this);
        tvBeiSar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv:
                startActivity(new Intent(mContext, BaseBezierActivity.class));
                break;
            case R.id.tvBeiSar:
                startActivity(new Intent(mContext, com.an.test.view.activity.MainActivity.class));
                break;
            default:
                break;
        }
    }
}
