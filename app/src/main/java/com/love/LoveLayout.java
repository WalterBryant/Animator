package com.love;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.animator.R;

import java.util.Random;

public class LoveLayout extends RelativeLayout {

    private Interpolator line = new LinearInterpolator();//线性
    private Interpolator acc = new AccelerateInterpolator();//加速
    private Interpolator dce = new DecelerateInterpolator();//减速
    private Interpolator accdec = new AccelerateDecelerateInterpolator();//先加速后减速
    private Interpolator[] interpolators ;

    Drawable[] drawables = new Drawable[3];
    private Random random = new Random();
    private int dHeight;
    private int dWidth;
    private LayoutParams params;
    private int mWidth;
    private int mHeight;

    public LoveLayout(Context context) {
        super(context);
        init();
    }

    public LoveLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoveLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        interpolators = new Interpolator[4];
        interpolators[0] = line;
        interpolators[1] = acc;
        interpolators[2] = dce;
        interpolators[3] = accdec;

        drawables[0] = getResources().getDrawable(R.mipmap.red);
        drawables[1] = getResources().getDrawable(R.mipmap.yellow);
        drawables[2] = getResources().getDrawable(R.mipmap.blue);

        dWidth = drawables[0].getIntrinsicWidth();
        dHeight = drawables[0].getIntrinsicHeight();
        params = new LayoutParams(dWidth, dHeight);

        params.addRule(CENTER_HORIZONTAL);
        params.addRule(ALIGN_PARENT_BOTTOM);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }

    public void addLoveIcon() {
        final ImageView iv = new ImageView(getContext());
        iv.setImageDrawable(drawables[random.nextInt(3)]);
        iv.setLayoutParams(params);
        addView(iv);
        AnimatorSet set = getAnimator(iv);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                removeView(iv);
            }
        });
        set.start();
    }

    private AnimatorSet getAnimator(ImageView iv) {
        ObjectAnimator alpha = ObjectAnimator.ofFloat(iv,"alpha", 0.3f, 1f);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(iv, "scaleX", 0.3f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(iv, "scaleY", 0.3f, 1f);

        AnimatorSet enter = new AnimatorSet();
        enter.setDuration(600);
        enter.playTogether(alpha, scaleX, scaleY);

        ValueAnimator bezierAnimator = getBezierValueAnimator(iv);

        AnimatorSet set = new AnimatorSet();
        set.playSequentially(enter, bezierAnimator);
        set.setInterpolator(interpolators[random.nextInt(4)]);
        set.setTarget(iv);
        return set;
    }

    private ValueAnimator getBezierValueAnimator(final ImageView iv) {
        PointF pointF0 = new PointF((mWidth-dWidth)/2, mHeight-dHeight);
        PointF pointF3 = new PointF(random.nextInt(mWidth), 10);
        PointF pointF1 = getPointF(1);
        PointF pointF2 = getPointF(2);

        BeaierEvaluator evaluator = new BeaierEvaluator(pointF1, pointF2);

        ValueAnimator animator = ValueAnimator.ofObject(evaluator, pointF0, pointF3);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF = (PointF) animation.getAnimatedValue();
                iv.setX(pointF.x);
                iv.setY(pointF.y);
                iv.setAlpha(1-animation.getAnimatedFraction());
            }
        });
        animator.setDuration(4000);
        return animator;
    }

    private PointF getPointF(int i) {
        PointF pointF = new PointF();
        pointF.x = random.nextInt(mWidth);
        if (i == 1) {
            pointF.y = random.nextInt(mHeight / 2) + mHeight / 2;
        } else {
            pointF.y = random.nextInt(mHeight / 2);
        }
        return pointF;
    }
}
