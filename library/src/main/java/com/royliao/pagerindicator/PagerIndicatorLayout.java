package com.royliao.pagerindicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;


public class PagerIndicatorLayout extends RelativeLayout {

    onItemSelectListener mListener;

    public interface onItemSelectListener {

        void onSelected(View v, int position);
    }

    public void setItemSelectListener(onItemSelectListener listener) {
        this.mListener = listener;
    }

    int mSelectorId = 0;
    View mVSelector;


    public View getSelector() {
        return mVSelector;
    }

    int mSelectorCenterX;

    HorizontalScrollView mHsv;

    View mVContainer;
    ViewGroup mLoContainer;

    View mSelectedView;

    public View getSelectedView() {
        return mSelectedView;
    }

    int mSelectedNewSPosition = -1;
    int mSelectedPosition = -1;

    public int getSelectedPosition() {
        return mSelectedPosition;
    }

    double mLastVelocity = 0;

    private final static int DELTA_PIXES_TO_CHANGE = 500;

    private VelocityTracker mVelocityTracker = null;


    public PagerIndicatorLayout(Context context, int selectorId) {
        super(context);
        mSelectorId = selectorId;
        init(null);
    }

    public PagerIndicatorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public PagerIndicatorLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.PagerIndicator);
            mSelectorId = a.getResourceId(R.styleable.PagerIndicator_selector_id, 0);
            if (mSelectorId == 0)
                throw new RuntimeException("You must specify the selector id");
            a.recycle();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (BuildConfig.DEBUG)
            Log.d("debug", "onWindowFocusChanged");
    }

    public void setSelectedPosition(int position) {
        mSelectedNewSPosition = position;
        syncSelectedPosition();
    }

    private void syncSelectedPosition() {

        if (mLoContainer == null)
            return;

        mSelectedPosition = mSelectedNewSPosition;

        if (mSelectedPosition < 0 || mSelectedPosition > mLoContainer.getChildCount())
            return;

        mSelectedView = mLoContainer.getChildAt(mSelectedPosition);

        alignSelectedViewCenterToSelectorCenter();

        if (mListener != null)
            mListener.onSelected(mSelectedView, mSelectedPosition);
    }

    private void alignSelectedViewCenterToSelectorCenter() {
        if (mSelectedView == null || mHsv == null)
            return;

        int[] loc = new int[2];
        mSelectedView.getLocationInWindow(loc);
        int itemCenter = loc[0] + mSelectedView.getWidth() / 2;
        int itemCenterToSelectorCenter = mSelectorCenterX - itemCenter;
        if (itemCenterToSelectorCenter != 0)
            mHsv.smoothScrollBy(itemCenterToSelectorCenter * -1, 0);
        if (BuildConfig.DEBUG)
            Log.d("debug", "alignSelectedViewCenterToSelectorCenter#itemCenterToSelectorCenter: " + itemCenterToSelectorCenter);
    }

    boolean mNeedSynced = false;

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (BuildConfig.DEBUG)
            Log.d("debug", "onLayout#changed: " + changed);

        if (mNeedSynced) {
            if (mSelectedPosition != mSelectedNewSPosition)
                syncSelectedPosition();
            mNeedSynced = false;
        }

        if (!changed) {
            return;
        }

        if (mVSelector == null) {
            mVSelector = findViewById(mSelectorId);
            if (mVSelector == null)
                throw new RuntimeException("Could not find selector view");
        }

        int[] selectorLoc = new int[2];
        mVSelector.getLocationInWindow(selectorLoc);
        mSelectorCenterX = selectorLoc[0] + mVSelector.getWidth() / 2;

        if (mHsv == null) {
            //find out HorizontalScrollView
            for (int i = 0; i < getChildCount(); i++) {
                View v = getChildAt(i);
                if (v instanceof HorizontalScrollView) {
                    mHsv = (HorizontalScrollView) v;
                    mVContainer = mHsv.getChildAt(0);
                    break;
                }
            }
            if (mHsv == null)
                throw new RuntimeException("There is no HorizontalScrollView in your layout");
        }

        //Container is not a ViewGroup possibly
        if (mLoContainer == null && mVContainer instanceof ViewGroup) {
            mLoContainer = (ViewGroup) mVContainer;
            mLoContainer.setPadding(0, 0, 0, 0);

            initChildViewClickEvent();
            initTouchListener();
        }

        if (mLoContainer != null && mLoContainer.getChildCount() > 0) {

            int selectorLeftToCenterX = mVSelector.getLeft() + mVSelector.getWidth() / 2;
            int selectorRightToCenterX = getWidth() - mVSelector.getRight() + mVSelector.getWidth() / 2;

            View firstView = mLoContainer.getChildAt(0);

            //int[] loc = new int[2];
            //firstView.getLocationInWindow(loc);

            //MarginLayoutParams mgLp;

            //mgLp = (MarginLayoutParams) firstView.getLayoutParams();
            //int margin = mgLp.leftMargin;
            int firstViewLeftToCenterX = firstView.getLeft() + firstView.getWidth() / 2;
            int leftPadding = selectorLeftToCenterX - firstViewLeftToCenterX;
            if (leftPadding < 0)
                leftPadding = 0;

            View lastView = mLoContainer.getChildAt(mLoContainer.getChildCount() - 1);

            //loc = new int[2];
            //lastView.getLocationInWindow(loc);
            //mgLp = (MarginLayoutParams) lastView.getLayoutParams();
            //margin = mgLp.rightMargin;
            //int lastViewWidth = lastView.getWidth() / 2;
            int lastViewRightToCenterX = mLoContainer.getWidth() - lastView.getRight() + lastView.getWidth() / 2;
            int rightPadding = selectorRightToCenterX - lastViewRightToCenterX;
            if (rightPadding < 0)
                rightPadding = 0;

            mLoContainer.setPadding(leftPadding, 0, rightPadding, 0);

            mNeedSynced = true;

        }

    }

    public static final String ARGS_SELECTED_NEW_POSITION = "ARGS_SELECTED_NEW_POSITION";
    public static final String ARGS_INSTANCE_STATE = "ARGS_INSTANCE_STATE";

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARGS_INSTANCE_STATE, super.onSaveInstanceState());
        bundle.putInt(ARGS_SELECTED_NEW_POSITION, mSelectedNewSPosition);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        //begin boilerplate code so parent classes can restore state
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            state = bundle.getParcelable(ARGS_INSTANCE_STATE);
            this.mSelectedNewSPosition = bundle.getInt(ARGS_SELECTED_NEW_POSITION);
        }
        super.onRestoreInstanceState(state);
    }

    private void initChildViewClickEvent() {
        if (mLoContainer == null)
            return;

        for (int i = 0; i < mLoContainer.getChildCount(); i++) {

            View v = mLoContainer.getChildAt(i);

            final int finalI = i;
            v.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mListener == null)
                        return;

                    mSelectedPosition = finalI;
                    mSelectedView = v;

                    alignSelectedViewCenterToSelectorCenter();
                    mListener.onSelected(v, finalI);

                }
            });
        }

    }


    private void initTouchListener() {
        if (mHsv == null)
            return;

        mHsv.setOnTouchListener(

            new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int index = event.getActionIndex();
                    int pointerId = event.getPointerId(index);

                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            break;
                        case MotionEvent.ACTION_MOVE:
                            if (mVelocityTracker == null)
                                mVelocityTracker = VelocityTracker.obtain();
                            // Add a user's movement to the tracker.
                            mVelocityTracker.addMovement(event);
                            mVelocityTracker.computeCurrentVelocity(1000);
                            mLastVelocity = mVelocityTracker.getXVelocity(pointerId);
                            //Log.d("debug", String.valueOf(mLastVelocity));
                            break;

                        case MotionEvent.ACTION_UP:

                            if (mLoContainer == null || mListener == null)
                                break;

                            View selectedView = null;
                            int selectedItemPos = -1;
                            int selectedItemCenterToSelectorCenter = 0;

                            for (int i = 0; i < mLoContainer.getChildCount(); i++) {
                                View item = mLoContainer.getChildAt(i);

                                int[] loc = new int[2];
                                item.getLocationInWindow(loc);
                                int itemCenter = loc[0] + item.getWidth() / 2;

                                int itemCenterToSelectorCenter = mSelectorCenterX - itemCenter;
                                if (selectedView == null || Math.abs(itemCenterToSelectorCenter) < Math.abs(
                                    selectedItemCenterToSelectorCenter)) {
                                    selectedView = item;
                                    selectedItemPos = i;
                                    selectedItemCenterToSelectorCenter = itemCenterToSelectorCenter;
                                }
                            }

                            if (selectedView != null && selectedView != mSelectedView) {
                                mSelectedPosition = selectedItemPos;
                                mSelectedView = selectedView;
                                mHsv.smoothScrollBy(selectedItemCenterToSelectorCenter * -1, 0);
                                mListener.onSelected(mSelectedView, selectedItemPos);
                                return true;
                            }


                            if (Math.abs(mLastVelocity) > DELTA_PIXES_TO_CHANGE) {
                                int i = mLoContainer.indexOfChild(mSelectedView);

                                if (mLastVelocity < 0) { //slide to right
                                    if (i != mLoContainer.getChildCount() - 1) {
                                        selectedItemPos = i + 1;
                                        selectedView = mLoContainer.getChildAt(selectedItemPos);
                                    }
                                } else if (mLastVelocity > 0) {
                                    if (i != 0) {
                                        selectedItemPos = i - 1;
                                        selectedView = mLoContainer.getChildAt(selectedItemPos);
                                    }
                                }
                            }

                            //reset
                            mLastVelocity = 0;

                            try {
                                mVelocityTracker.recycle();
                                mVelocityTracker = null;
                            } catch (Exception e) {
                                mVelocityTracker = null;
                                e.printStackTrace();
                            }


                            if (selectedView != null) {
                                mSelectedPosition = selectedItemPos;
                                mSelectedView = selectedView;
                                int[] loc = new int[2];
                                mSelectedView.getLocationInWindow(loc);
                                int itemCenter = loc[0] + mSelectedView.getWidth() / 2;
                                int itemCenterToSelectorCenter = mSelectorCenterX - itemCenter;
                                mHsv.smoothScrollBy(itemCenterToSelectorCenter * -1, 0);
                                mListener.onSelected(mSelectedView, selectedItemPos);
                                return true;
                            }

                            break;

                        default:
                            break;
                    }

                    return false;
                }
            }

        );
    }


}
