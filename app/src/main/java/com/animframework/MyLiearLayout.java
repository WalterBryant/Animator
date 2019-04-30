package com.animframework;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.animator.R;

public class MyLiearLayout extends LinearLayout {
    public MyLiearLayout(Context context,   AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MyLayoutParams(getContext(), attrs);
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        MyLayoutParams p = (MyLayoutParams) params;
        if (!isDiscrollvable(p)) {
            super.addView(child, params);
        } else {
            MyFrameLayout frameLayout = new MyFrameLayout(getContext());
            frameLayout.addView(child);
            frameLayout.setmDiscrollveAlpha(p.mDiscrollveAlpha);
            frameLayout.setmDiscrollveFromBgColor(p.mDiscrollveFromBgColor);
            frameLayout.setmDiscrollveToBgColor(p.mDiscrollveToBgColor);
            frameLayout.setmDiscrollveScaleX(p.mDiscrollveScaleX);
            frameLayout.setmDisCrollveTranslation(p.mDiscrollveTranslation);
            super.addView(frameLayout, params);
        }
    }

    private boolean isDiscrollvable(MyLayoutParams p) {
        return p.mDiscrollveAlpha ||
                p.mDiscrollveScaleX ||
                p.mDiscrollveScaleY ||
                p.mDiscrollveTranslation != -1 ||
                (p.mDiscrollveFromBgColor != -1 &&
                        p.mDiscrollveToBgColor != -1);

    }

    public class MyLayoutParams extends LinearLayout.LayoutParams {

        public int mDiscrollveFromBgColor;//背景颜色变化开始值
        public int mDiscrollveToBgColor;//背景颜色变化结束值
        public int mDiscrollveTranslation;//平移值
        public boolean mDiscrollveAlpha;//是否需要透明度动画
        public boolean mDiscrollveScaleX;//是否需要x轴方向缩放
        public boolean mDiscrollveScaleY;//是否需要y轴方向缩放

        public MyLayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.DiscrollView_LayoutParams);
            mDiscrollveAlpha = a.getBoolean(R.styleable.DiscrollView_LayoutParams_discrollve_alpha, false);
            mDiscrollveScaleX = a.getBoolean(R.styleable.DiscrollView_LayoutParams_discrollve_scaleX,false);
            mDiscrollveScaleY = a.getBoolean(R.styleable.DiscrollView_LayoutParams_discrollve_scaleY, false);
            mDiscrollveTranslation = a.getInt(R.styleable.DiscrollView_LayoutParams_discrollve_translation, -1);
            mDiscrollveFromBgColor = a.getColor(R.styleable.DiscrollView_LayoutParams_discrollve_fromBgColor, -1);
            mDiscrollveToBgColor = a.getColor(R.styleable.DiscrollView_LayoutParams_discrollve_toBgColor, -1);
            a.recycle();
        }
    }
}
