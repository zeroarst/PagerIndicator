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
import android.widget.TextView;

import com.royliao.pagerindicator.PagerIndicatorLayout;
import com.royliao.pagerindicator.sample.R;


public class StyledPager2Fragment extends Fragment {

    PagerIndicatorLayout mPagerIndicatorLayout;
    ImageView mIvSelector;
    ViewGroup mLoContainer;

    ViewPager mViewPager;
    PagerAdapter mPgrAdpt;
    View mVRoot;
    FragmentManager mFgMgr;


    public static StyledPager2Fragment create() {
        StyledPager2Fragment fg = new StyledPager2Fragment();
        Bundle args = new Bundle();
        fg.setArguments(args);
        return fg;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mVRoot = inflater.inflate(R.layout.fragment_styled_pager2, container, false);

        mPagerIndicatorLayout = (PagerIndicatorLayout) mVRoot.findViewById(R.id.pi);
        mPagerIndicatorLayout.setItemSelectListener(new PagerIndicatorLayout.OnItemSelectListener() {
            @Override
            public void onSelected(View v, int position) {
                if (mViewPager.getCurrentItem() != position)
                    mViewPager.setCurrentItem(position);
            }
        });

        mLoContainer = (ViewGroup) mPagerIndicatorLayout.findViewById(R.id.lo_container);

        mIvSelector = (ImageView) mPagerIndicatorLayout.findViewById(R.id.iv_selector);
        mIvSelector.setBackgroundColor(getResources().getColor(android.R.color.transparent));

        mViewPager = (ViewPager) mVRoot.findViewById(R.id.vp);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if (mPagerIndicatorLayout.getSelectedPosition() != position)
                    mPagerIndicatorLayout.setSelectedPosition(position);
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
            ViewGroup layout = (ViewGroup) mLoContainer.getChildAt(position);
            TextView tv = (TextView) layout.getChildAt(1);
            return tv.getText();
        }

    }


}
