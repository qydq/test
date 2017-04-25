package com.an.test;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.TextView;

import com.an.base.utils.YbitmapUtils;
import com.an.base.utils.ytips.FastBlurUtils;
import com.an.base.view.ParallaxActivity;
import com.an.base.view.activity.AnPicDetailsActivity;
import com.an.base.view.widget.YshrinkScrollView;
import com.an.test.view.activity.BaseBezierActivity;
import com.an.test.view.activity.MyBazierActivity;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

public class MainActivity extends ParallaxActivity implements View.OnClickListener {
    @ViewInject(R.id.activity_main)
    private YshrinkScrollView scrollView;
    @ViewInject(R.id.tv)
    private TextView tv;
    private TextView tvBeiSar;
    private TextView tvBeiPic;

    @Override
    public void initView() {
        setContentView(R.layout.activity_main);
        scrollView = (YshrinkScrollView) findViewById(R.id.activity_main);
        tv = (TextView) findViewById(R.id.tv);
        tvBeiSar = (TextView) findViewById(R.id.tvBeiSar);
        tvBeiPic = (TextView) findViewById(R.id.tvBeiPic);

        Bitmap oBitmap = YbitmapUtils.INSTANCE.ReadBitmapById(mContext, R.drawable.yy_drawable_bg_yezi);
        Bitmap sBitmap = YbitmapUtils.INSTANCE.zoomBitmap(oBitmap, 20);
        Bitmap bitmap = FastBlurUtils.doBlur(sBitmap, 9, false);
        scrollView.setBackground(YbitmapUtils.INSTANCE.bitmapToDrawable(bitmap));
        tv.setOnClickListener(this);
        tvBeiSar.setOnClickListener(this);
        tvBeiPic.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv:
                startActivity(new Intent(mContext, BaseBezierActivity.class));
                break;
            case R.id.tvBeiSar:
                startActivity(new Intent(mContext, MyBazierActivity.class));
                break;
            case R.id.tvBeiPic:
                Intent intent = new Intent(mContext, AnPicDetailsActivity.class);
//                ArrayList<String> mDatas = new ArrayList<>();
//                mDatas.add("https://www.pic.bul.com/adsfasdf.jpg");
//                intent.putStringArrayListExtra("images", mDatas);
                intent.putExtra("position", 0);
                intent.putExtra("locationX", 100);
                intent.putExtra("locationY", 16);
//                intent.putExtra("width", 400);
//                intent.putExtra("height", 300);
                intent.putExtra("isZoom", false);
//                intent.putExtra("drawableId", R.drawable.yy_drawable_bg_yezi);
                intent.putExtra("absPath", "sdcard/an_ytips/picture/shiluohua.jpg");
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
