package com.royliao.pagerindcator.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.royliao.pagerindicator.sample.R;


public class TestFragment extends Fragment {

    ViewGroup mLoPager;
    ImageView mIvSelector;
    ViewGroup mLoContainer;

    ViewPager mViewPager;
    PagerAdapter mPgrAdpt;
    View mVRoot;
    FragmentManager mFgMgr;


    public static TestFragment create() {
        TestFragment fg = new TestFragment();
        Bundle args = new Bundle();
        fg.setArguments(args);
        return fg;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mVRoot = inflater.inflate(R.layout.fragment_test, container, false);

        mLoPager = (ViewGroup) mVRoot.findViewById(R.id.lo_pager);

        mLoContainer = (ViewGroup) mLoPager.findViewById(R.id.lo_container);


        mViewPager = (ViewPager) mVRoot.findViewById(R.id.vp);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {

            }
        });


        return mVRoot;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mFgMgr = getChildFragmentManager();


        mPgrAdpt = new PagerAdapter(mFgMgr);
        mViewPager.setAdapter(mPgrAdpt);

    }


    public class PagerAdapter extends FragmentPagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return MessageFragment.create(getPageTitle(position).toString());
        }

        @Override
        public int getCount() {
            return mLoContainer.getChildCount();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Page" + (position + 1);
        }

    }


}
