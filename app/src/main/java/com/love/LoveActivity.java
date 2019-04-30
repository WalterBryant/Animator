package com.love;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.animator.R;

public class LoveActivity extends AppCompatActivity {

    private LoveLayout loveLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_love);

        loveLayout = findViewById(R.id.love_layout);

    }

    public void start(View view) {
        loveLayout.addLoveIcon();
    }

}
