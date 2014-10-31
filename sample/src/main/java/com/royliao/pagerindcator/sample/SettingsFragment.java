package com.royliao.pagerindcator.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.royliao.pagerindicator.sample.R;

public class SettingsFragment extends Fragment {

    RadioGroup mRgSelectorStyle;
    CheckBox mCbPgrBgGradient;
    CheckBox mCbPgrBdrGradient;


    public static SettingsFragment create() {
        SettingsFragment fg = new SettingsFragment();
        Bundle args = new Bundle();
        fg.setArguments(args);
        return fg;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);
        mRgSelectorStyle = (RadioGroup) v.findViewById(R.id.rb_selector_style);
        RadioButton rbTransparent = (RadioButton) mRgSelectorStyle.findViewById(MainActivity.SelectorStyleCheckedButtonId);
        rbTransparent.setChecked(true);
        mRgSelectorStyle.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                MainActivity.SelectorStyleCheckedButtonId = mRgSelectorStyle.getCheckedRadioButtonId();
            }
        });


        mCbPgrBgGradient = (CheckBox) v.findViewById(R.id.cb_pager_bg_gradient);
        mCbPgrBgGradient.setChecked(MainActivity.IsPagerBackgroundGradient);
        mCbPgrBgGradient.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MainActivity.IsPagerBackgroundGradient = isChecked;
            }
        });

        mCbPgrBdrGradient = (CheckBox) v.findViewById(R.id.cb_pager_border_gradient);
        mCbPgrBdrGradient.setChecked(MainActivity.IsPagerBorderGradient);
        mCbPgrBdrGradient.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MainActivity.IsPagerBorderGradient = isChecked;
            }
        });

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
