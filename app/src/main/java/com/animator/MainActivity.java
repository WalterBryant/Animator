package com.animator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private View firstView;
    private View secondView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstView = findViewById(R.id.first);
        secondView = findViewById(R.id.second);
        button = findViewById(R.id.bt);
    }

    public void startFirstAnim(View view) {
        //显示first view ：1、透明度动画，2、缩放动画，3、翻转动画
        //透明度动画
        ObjectAnimator firstAlphaAnim= ObjectAnimator.ofFloat(firstView, "alpha", 1.0f, 0.7f);
        firstAlphaAnim.setDuration(300);
        //旋转动画1
        ObjectAnimator firstRotationXAnim = ObjectAnimator.ofFloat(firstView, "rotationX", 0f, 20f);
        firstRotationXAnim.setDuration(300);
        //再旋转回来
        ObjectAnimator firstResumeRotationXAnim = ObjectAnimator.ofFloat(firstView, "rotationX", 20f, 0f);
        firstResumeRotationXAnim.setDuration(300);
        firstResumeRotationXAnim.setStartDelay(300);//延迟第一次旋转动画的时间，

        //缩放动画
        ObjectAnimator firstScaleXAnim = ObjectAnimator.ofFloat(firstView, "ScaleX", 1.0f, 0.8f);
        firstScaleXAnim.setDuration(300);
        ObjectAnimator firstScaleYAnim = ObjectAnimator.ofFloat(firstView, "ScaleY", 1.0f, 0.8f);
        firstScaleYAnim.setDuration(300);

        //由于缩放造成距离顶部有一个距离，需要平移
        ObjectAnimator firstTranslationYAnim = ObjectAnimator.ofFloat(firstView, "translationY", 0f, -0.1f*firstView.getHeight());
        firstTranslationYAnim.setDuration(300);

        //第二个view和第一个view动画同时开始执行
        ObjectAnimator secondTranslationYAnim = ObjectAnimator.ofFloat(secondView, "translationY", secondView.getHeight(), 0f);
        secondTranslationYAnim.setDuration(300);
        secondTranslationYAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                secondView.setVisibility(View.VISIBLE);
                button.setClickable(false);
            }
        });

        AnimatorSet set = new AnimatorSet();
        set.playTogether(firstScaleXAnim, firstScaleYAnim, firstAlphaAnim, firstRotationXAnim, firstResumeRotationXAnim, firstTranslationYAnim,secondTranslationYAnim);
        set.start();
    }
}
