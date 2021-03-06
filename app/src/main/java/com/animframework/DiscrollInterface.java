package com.animframework;

public interface DiscrollInterface {

    /**
     * 当滑动的时候调用该方法，用来控制里面的控件执行相应的动画
     * @param ratio 动画执行的百分比，（child view 画出来的距离百分比）
     */
    void onDiscroll(float ratio);

    /**
     * 重置动画 -- 让view所有的属性都恢复到原来的样子
     */
    void onResetDiscroll();
}
