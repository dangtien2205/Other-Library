package com.otherlibrary.tiplibrary;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.otherlibrary.tiplibrary.R;
import com.otherlibrary.tiplibrary.fragment.IntroductionFragment;
import java.util.ArrayList;
import me.relex.circleindicator.CircleIndicator;

public class IntroductionActivity extends AppCompatActivity {
    private ViewPager vpIntro;
    private TextView txtNext;
    private ArrayList<Integer> imageList;


    public static void startIntroduction(Context context,ArrayList<Integer> imageList){
        Intent intent=new Intent(context,IntroductionActivity.class);
        intent.putIntegerArrayListExtra("LIST",imageList);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);

        imageList=getIntent().getIntegerArrayListExtra("LIST");
        if(imageList.size()==0)
            finish();
        txtNext = (TextView) findViewById(R.id.txt_next);
        vpIntro = (ViewPager) findViewById(R.id.vp_intro);
        txtNext.setVisibility(View.GONE);
        vpIntro.setAdapter(new SamplePagerAdapter(getSupportFragmentManager()));
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(vpIntro);

        vpIntro.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == imageList.size()-1)
                    txtNext.setVisibility(View.VISIBLE);
                else
                    txtNext.setVisibility(View.GONE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        txtNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    public class SamplePagerAdapter extends FragmentPagerAdapter {
        Fragment f;

        public SamplePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            f = new IntroductionFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("IMAGE", imageList.get(position));
            f.setArguments(bundle);
            return f;
        }

        @Override
        public int getCount() {
            return imageList.size();
        }
    }
}
