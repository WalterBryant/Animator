package com.splash;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.animator.R;

public class SplashActivity extends AppCompatActivity {

    private FrameLayout mMainView;
    private SplashView splashView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainView = new FrameLayout(this);
        ContentView contentView = new ContentView(this);
        mMainView.addView(contentView);
        splashView = new SplashView(this);
        mMainView.addView(splashView);

        setContentView(mMainView);

        //后台开始加载数据
        startLoadData();
    }

    Handler handler = new Handler();

    private void startLoadData() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //数据加载完毕，进入主界面--->开启后面的两个动画
                splashView.splashDisapper();
            }
        }, 1000);//延迟时间
    }
}
