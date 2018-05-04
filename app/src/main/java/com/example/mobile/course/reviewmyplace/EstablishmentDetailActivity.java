package com.example.mobile.course.reviewmyplace;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.mobile.course.reviewmyplace.helper.FragmentDetail;
import com.example.mobile.course.reviewmyplace.helper.FragmentReview;

import java.util.ArrayList;
import java.util.List;

public class EstablishmentDetailActivity extends AppCompatActivity {

    //Initialize component use in layout
    TextView textview_EstablishmentName = null;
    TextView textView_Location = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_establishment_detail);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager_1);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        // Add Fragments to adapter one by one
        adapter.addFragment(new FragmentReview(), "REVIEW", 1);
        adapter.addFragment(new FragmentDetail(), "DETAIL", 1);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout_main);
        tabLayout.setupWithViewPager(viewPager);
    }


    //Change Between Tabs
    class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title, int EstablishmentID) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    //OnClick Method for Show All Reviews
    public void showAllReview(View view) {
    }

    //OnClick Method for Create New Establishment
    public void createReview(View view) {

    }
}
