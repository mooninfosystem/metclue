package com.metclue.app.onboarding_screens.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.metclue.app.R;
import com.metclue.app.auth.LoginActivity;
import com.metclue.app.onboarding_screens.fragments.TutorialFragment;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

public class OnboardingActivity extends AppCompatActivity {

    ViewPager viewPagerOnboarding;
    DotsIndicator dotsIndicator;
    ViewPagerOnboardingAdapter viewPagerOnboardingAdapter;
    TextView tvNext;
    int pagerPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        viewPagerOnboarding = findViewById(R.id.viewPagerTutorial);
        dotsIndicator = findViewById(R.id.dotsIndicator);
        tvNext = findViewById(R.id.tvNext);

        viewPagerOnboardingAdapter = new ViewPagerOnboardingAdapter(getSupportFragmentManager(), 3);
        viewPagerOnboarding.setAdapter(viewPagerOnboardingAdapter);
        dotsIndicator.setViewPager(viewPagerOnboarding);
//        viewPagerTutorial.setPageTransformer(true, new DefaultTransformer());

        tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OnboardingActivity.this, LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            }
        });

        viewPagerOnboarding.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pagerPosition = position;
                if (position == 3){
                    tvNext.setText("Next");
                } else {
                    tvNext.setText("Skip");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    public class ViewPagerOnboardingAdapter extends FragmentStatePagerAdapter {


        public ViewPagerOnboardingAdapter(@NonNull FragmentManager fm, int tabcount) {
            super(fm, tabcount);

        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    TutorialFragment tab1 = new TutorialFragment();
                    return tab1;
                case 1:
                    TutorialFragment tab2 = new TutorialFragment();
                    return tab2;
                case 2:
                    TutorialFragment tab3 = new TutorialFragment();
                    return tab3;
                case 3:
                    TutorialFragment tab4 = new TutorialFragment();
                    return tab4;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 4;
        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}