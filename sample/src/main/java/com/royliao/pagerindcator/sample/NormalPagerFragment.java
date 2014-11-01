package com.royliao.pagerindcator.sample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.royliao.pagerindicator.PagerIndicatorLayout;
import com.royliao.pagerindicator.sample.R;


public class NormalPagerFragment extends Fragment {

    private PagerIndicatorLayout mPagerIndicatorLayout;
    private ImageView mIvSelector;
    private ViewGroup mLoContainer;

    private ViewPager mViewPager;
    private PagerAdapter mPgrAdpt;
    private View mVRoot;
    private FragmentManager mFgMgr;

    private ImageView mIvGradient;


    private static final String ARGS_LAYOUT_RES_ID = "ARGS_LAYOUT_RES_ID";
    private int mLayoutResId;

    private static final String ARGS_SELECTOR_BG_RES_ID = "ARGS_SELECTOR_BG_RES_ID";
    private int mSelectorBgResId;

    private static final String ARGS_GRADIENT_RES_ID = "ARGS_GRADIENT_RES_ID";
    private int mGradientResId = -1;


    private static final String ARGS_GRADIENT_MODE = "ARGS_GRADIENT_MODE";
    public final static int GRADIENT_MODE_NONE = 0;
    public final static int GRADIENT_MODE_BACKGROUND_BORDER = 1;
    public final static int GRADIENT_MODE_BORDER = 2;
    public final static int GRADIENT_MODE_BACKGROUND = 3;
    private int mGradientMode;

    private static final String ARGS_ADD_PAGERS = "ARGS_ADD_PAGERS";
    boolean mAddPagers = false;

    public static NormalPagerFragment create(int layoutResId, int selectorBgResId, int gradientResId, int gradientMode) {
        NormalPagerFragment fg = new NormalPagerFragment();
        Bundle args = new Bundle();
        fg.setArguments(args);
        args.putInt(ARGS_LAYOUT_RES_ID, layoutResId);
        args.putInt(ARGS_SELECTOR_BG_RES_ID, selectorBgResId);
        args.putInt(ARGS_GRADIENT_RES_ID, gradientResId);
        args.putInt(ARGS_GRADIENT_MODE, gradientMode);
        return fg;
    }


    public static NormalPagerFragment createToAddPagers(int layoutResId, int selectorBgResId, int gradientResId, int gradientMode) {
        NormalPagerFragment fg = NormalPagerFragment.create(layoutResId, selectorBgResId, gradientResId, gradientMode);
        Bundle args = fg.getArguments();
        args.putBoolean(ARGS_ADD_PAGERS, true);
        return fg;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //load saved args
        mLayoutResId = getArguments().getInt(ARGS_LAYOUT_RES_ID, -1);
        if (mLayoutResId == -1)
            return null;
        mSelectorBgResId = getArguments().getInt(ARGS_SELECTOR_BG_RES_ID, -1);
        mGradientResId = getArguments().getInt(ARGS_GRADIENT_RES_ID);
        mGradientMode = getArguments().getInt(ARGS_GRADIENT_MODE);
        mAddPagers = getArguments().getBoolean(ARGS_ADD_PAGERS);

        mVRoot = inflater.inflate(mLayoutResId, container, false);

        //init pager indicator layout
        mPagerIndicatorLayout = (PagerIndicatorLayout) mVRoot.findViewById(R.id.pi);
        mPagerIndicatorLayout.setItemSelectListener(new PagerIndicatorLayout.OnItemSelectListener() {
            @Override
            public void onSelected(View v, int position) {
                mViewPager.setCurrentItem(position);
            }
        });

        mLoContainer = (ViewGroup) mPagerIndicatorLayout.findViewById(R.id.lo_container);

        //init selector
        mIvSelector = (ImageView) mPagerIndicatorLayout.findViewById(R.id.iv_selector);
        if (mSelectorBgResId != -1)
            mIvSelector.setBackgroundResource(mSelectorBgResId);
        else
            mIvSelector.setBackgroundColor(getResources().getColor(android.R.color.transparent));

        //init gradient
        mIvGradient = (ImageView) mPagerIndicatorLayout.findViewById(R.id.iv_gradient);
        if (mGradientResId != -1)
            mIvGradient.setImageResource(mGradientResId);
        switch (mGradientMode) {
            case GRADIENT_MODE_NONE:
                mIvGradient.setVisibility(View.GONE);
                break;
            case GRADIENT_MODE_BACKGROUND_BORDER:

                break;
            case GRADIENT_MODE_BACKGROUND:
                mIvGradient.setPadding(0, 0, 0, CommonUtils.convertDipToPx(getActivity(), 1));
                break;
            case GRADIENT_MODE_BORDER:
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    CommonUtils.convertDipToPx(getActivity(), 1));
                lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                mIvGradient.setLayoutParams(lp);
                break;
            default:
                break;
        }


        //handle view pager
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

    private void addTextPagers() {
        for (int i = 0; i < 4; i++) {
            TextView tv = new TextView(getActivity());
            int pd = CommonUtils.convertDipToPx(getActivity(), 15);
            tv.setPadding(pd, 0, pd, 0);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(16);
            tv.setTextColor(Color.WHITE);
            tv.setText("Pager" + (i + 1));
            mLoContainer.addView(tv, lp);
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mFgMgr = getChildFragmentManager();
        mPgrAdpt = new PagerAdapter(mFgMgr);

        if (mAddPagers)
            addTextPagers();

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
