package com.threed;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.animator.R;

public class WelcomepagerTransformer implements ViewPager.PageTransformer {

    @Override
    public void transformPage(@NonNull View view, float position) {
        if (position < 1 && position > -1) {
            ViewGroup viewGroup = view.findViewById(R.id.rl);
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childView = viewGroup.getChildAt(i);
                float factor = (float) (Math.random() * 3);
                if (childView.getTag() == null) {
                    childView.setTag(factor);
                } else {
                    factor = (float) childView.getTag();
                }

                childView.setTranslationX(factor * childView.getWidth() * position);
            }


            //效果1
//			view.setScaleX(1-Math.abs(position));
//			view.setScaleY(1-Math.abs(position));
            //效果2
//			view.setScaleX(Math.max(0.9f,1-Math.abs(position)));
//			view.setScaleY(Math.max(0.9f,1-Math.abs(position)));
            //效果3 3D翻转
//			view.setPivotX(position<0f?view.getWidth():0f);//左边页面：0~-1；右边的页面：1~0
//			view.setPivotY(view.getHeight()*0.5f);
//			view.setRotationY(position*45f);//0~90度
            //效果4 3D内翻转
//			view.setPivotX(position<0f?view.getWidth():0f);//左边页面：0~-1；右边的页面：1~0
//			view.setPivotY(view.getHeight()*0.5f);
//			view.setRotationY(-position*45f);//0~90度

            view.setPivotX(view.getWidth() * 0.5f);//左边页面：0~-1；右边的页面：1~0
            view.setPivotY(view.getHeight() * 0.5f);
            view.setRotationY(-position * 45f);//0~90度
        }
    }
}
