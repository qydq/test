package com.an.test.view.activity;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.an.test.R;
import com.an.test.view.BezierView;

import java.util.ArrayList;
import java.util.List;


public class MyBazierActivity extends AppCompatActivity {
    private BezierView mBezierView;
    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sst_activity_mybazier);
        mBezierView = (BezierView) findViewById(R.id.bezier);
        mEditText = (EditText) findViewById(R.id.editText);
        List<Point> pointList = new ArrayList<>();
        pointList.add(new Point(10, 200));
        pointList.add(new Point(110, 300));
        pointList.add(new Point(210, 100));
        pointList.add(new Point(310, 400));
        pointList.add(new Point(410, 100));
        pointList.add(new Point(510, 200));
        pointList.add(new Point(610, 500));
        mBezierView.setPointList(pointList);
        mBezierView.setLineSmoothness(0.16f);
        mBezierView.startAnimation(3000);
    }

    public void onClick(View v) {
        try {
            mBezierView.setLineSmoothness(Float.valueOf(mEditText.getText().toString()));
        } catch (NumberFormatException e) {
        }
        mBezierView.startAnimation(3000);
    }
}
