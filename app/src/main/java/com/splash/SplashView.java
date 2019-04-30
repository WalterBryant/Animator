package com.splash;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

public class SplashView extends View {
    private ValueAnimator mAnimator;
    private float mRotationRadius = 90;
    private float mCircleRadius = 18;

    private int[] mCircleColors;

    private int mSplashBgColor = Color.WHITE;

    private long mRotationDuration = 1200;

    private float mHoleRadius = 0f;
    private float mCurrentRotationAngle;
    private float mCurrentRotationRadius = mRotationRadius;
    private Paint mPaint = new Paint();
    private Paint mPaintBackground = new Paint();

    private float mCenterX;
    private float mCenterY;
    private float mDiagonalDist;


    public SplashView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        mCircleColors = context.getResources().getIntArray(android.R.array.imProtocols);

        mPaint.setAntiAlias(true);
        mPaintBackground.setAntiAlias(true);
        mPaintBackground.setStyle(Paint.Style.STROKE);
        mPaintBackground.setColor(mSplashBgColor);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterX = w / 2f;
        mCenterY = h / 2f;
        mDiagonalDist = (float) (Math.sqrt((w * w + h * h)) / 2f);
    }

    public void splashDisapper() {
        if (mState != null && mState instanceof RotateState) {
            RotateState rotateState = (RotateState) mState;
            rotateState.cancel();
            post(new Runnable() {
                @Override
                public void run() {
                    mState = new MergingState();
                }
            });
        }
    }

    private SplashState mState = null;

    private abstract class SplashState {
        public abstract void drawState(Canvas canvas);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mState == null) {
            //开启一个旋转动画
            mState = new RotateState();
        }
        //调用绘制方法
        mState.drawState(canvas);
    }

    private class RotateState extends SplashState {

        public RotateState() {
            mAnimator = ValueAnimator.ofFloat(0f, (float) (Math.PI * 2));
            mAnimator.setInterpolator(new LinearInterpolator());
            mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mCurrentRotationAngle = (float) animation.getAnimatedValue();
                    invalidate();
                }
            });
            mAnimator.setDuration(mRotationDuration);
            mAnimator.setRepeatCount(ValueAnimator.INFINITE);
            mAnimator.start();
        }

        public void cancel() {
            mAnimator.cancel();
        }

        @Override
        public void drawState(Canvas canvas) {
            drawBackground(canvas);
            drawCircles(canvas);
        }
    }

    private class MergingState extends SplashState {
        public MergingState() {
            mAnimator = ValueAnimator.ofFloat(0, mRotationRadius);
            mAnimator.setDuration(mRotationDuration);
            mAnimator.setInterpolator(new OvershootInterpolator(10f));
            mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mCurrentRotationRadius = (float) animation.getAnimatedValue();
                    invalidate();
                }
            });
            mAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mState = new ExpandState();
                }
            });
            mAnimator.reverse();
        }

        @Override
        public void drawState(Canvas canvas) {
            drawBackground(canvas);
            drawCircles(canvas);
        }
    }

    private class ExpandState extends SplashState {

        public ExpandState() {
            mAnimator = ValueAnimator.ofFloat(mCircleRadius, mDiagonalDist);
            mAnimator.setDuration(mRotationDuration);
            mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mHoleRadius = (float) animation.getAnimatedValue();
                    invalidate();
                }
            });
            mAnimator.start();
        }

        @Override
        public void drawState(Canvas canvas) {
            drawBackground(canvas);
        }
    }

    private void drawCircles(Canvas canvas) {
        float rotationAngle = (float) (2 * Math.PI / mCircleColors.length);
        for (int i = 0; i < mCircleColors.length; i++) {
            double angle = i * rotationAngle + mCurrentRotationAngle;
            float cx = (float) (mCurrentRotationRadius * Math.cos(angle) + mCenterX);
            float cy = (float) (mCurrentRotationRadius * Math.sin(angle) + mCenterY);
            mPaint.setColor(mCircleColors[i]);
            canvas.drawCircle(cx, cy, mCircleRadius, mPaint);
        }
    }

    private void drawBackground(Canvas canvas) {
        if (mHoleRadius > 0f) {
            float strokeWidth = mDiagonalDist - mHoleRadius;
            mPaintBackground.setStrokeWidth(strokeWidth);
            float radius = mHoleRadius + strokeWidth / 2;
            canvas.drawCircle(mCenterX, mCenterY, radius, mPaintBackground);
        } else {
            canvas.drawColor(mSplashBgColor);
        }
    }
}
