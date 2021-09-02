package com.metclue.app.tutorial_screens.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.eftimoff.viewpagertransformers.DefaultTransformer;
import com.metclue.app.R;
import com.metclue.app.tutorial_screens.fragments.TutorialFragment;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

public class TutorialActivity extends AppCompatActivity {

    ViewPager viewPagerTutorial;
    DotsIndicator dotsIndicator;
    ViewPagerTutorialAdapter viewPagerTutorialAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        viewPagerTutorial = findViewById(R.id.viewPagerTutorial);
        dotsIndicator = findViewById(R.id.dotsIndicator);

        viewPagerTutorialAdapter = new ViewPagerTutorialAdapter(getSupportFragmentManager(), 3);
        viewPagerTutorial.setAdapter(viewPagerTutorialAdapter);
        dotsIndicator.setViewPager(viewPagerTutorial);
//        viewPagerTutorial.setPageTransformer(true, new DefaultTransformer());


    }









    public class ViewPagerTutorialAdapter extends FragmentStatePagerAdapter {


        public ViewPagerTutorialAdapter(@NonNull FragmentManager fm, int tabcount) {
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