package com.threed;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.animator.R;

public class ThreeDActivity extends AppCompatActivity {

    private ViewPager vp;
    private int[] layouts = {
            R.layout.welcome1,
            R.layout.welcome2,
            R.layout.welcome3
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three_d);

        vp = findViewById(R.id.vp);

        WelcomePagerAdapter adapter = new WelcomePagerAdapter(getSupportFragmentManager());
        vp.setPageTransformer(true, new WelcomepagerTransformer());
        vp.setAdapter(adapter);
    }

    class WelcomePagerAdapter extends FragmentPagerAdapter {

        public WelcomePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment = new TranslateFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("layoutId", layouts[i]);
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
