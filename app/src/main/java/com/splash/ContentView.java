package com.splash;

import android.content.Context;
import android.widget.ImageView;

import com.animator.R;

public class ContentView extends ImageView {
    public ContentView(Context context) {
        super(context);
        setImageResource(R.mipmap.content);
    }
}
