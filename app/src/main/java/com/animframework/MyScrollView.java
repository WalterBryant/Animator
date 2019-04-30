package com.animframework;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

public class MyScrollView extends ScrollView {

    private MyLiearLayout mContent;

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mContent = (MyLiearLayout) getChildAt(0);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        View first = mContent.getChildAt(0);
        first.getLayoutParams().height = getHeight();
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        int scrollViewHeight = getHeight();
        for (int i=0; i<mContent.getChildCount();i++) {
            View child = mContent.getChildAt(i);
            int childHeight = child.getHeight();
            if (!(child instanceof DiscrollInterface)) {
                continue;
            }

            DiscrollInterface discrollInterface = (DiscrollInterface) child;
            int childTop = child.getTop();

            int absoluteTop = childTop - t;
            if (absoluteTop <= scrollViewHeight) {
                int visibleGap = scrollViewHeight - absoluteTop;

                float ratio = visibleGap / (float)childHeight;
                discrollInterface.onDiscroll(clamp(ratio, 1f, 0f));
            } else {
                discrollInterface.onResetDiscroll();
            }
        }
    }

    //三个数中的中间大小的一个数
    public static float clamp(float value, float max, float min) {
        return Math.max(Math.min(value, max), min);
    }
}
