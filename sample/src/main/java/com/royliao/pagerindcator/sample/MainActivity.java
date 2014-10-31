package com.royliao.pagerindcator.sample;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.royliao.pagerindicator.sample.R;


public class MainActivity extends FragmentActivity {

    public static int SelectorStyleCheckedButtonId = R.id.rb_transparent;
    public static boolean IsPagerBackgroundGradient = true;
    public static boolean IsPagerBorderGradient = true;

    ArrayAdapter<DemoItem> mAdpt;
    ListView mLv;
    SettingsFragment mFgConfigs;

    FragmentManager mFgMgr;

    ImageView mIvSettings;

    final static String FRAGMENT_TAG = "FRAGMENT_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFgMgr = getSupportFragmentManager();

        //init settings button
        mIvSettings = (ImageView) findViewById(R.id.iv_settings);
        mIvSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFgMgr.popBackStack();
                mFgConfigs = SettingsFragment.create();
                mFgMgr.beginTransaction().add(android.R.id.content, mFgConfigs, FRAGMENT_TAG).addToBackStack(null).commit();
            }
        });


        mLv = (ListView) findViewById(R.id.lv);

        mAdpt = new ArrayAdapter<DemoItem>(this, android.R.layout.simple_list_item_1, android.R.id.text1, DemoItem.values());
        mLv.setAdapter(mAdpt);

        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                int selectorBgResId = -1;

                switch (SelectorStyleCheckedButtonId) {
                    case R.id.rb_bottom_border:
                        selectorBgResId = R.drawable.bg_selector_border_bottom_light_blue;
                        break;
                    case R.id.rb_rectangle:
                        selectorBgResId = R.drawable.bg_selector_rect_light_blue_bottom_gap;
                        break;
                    default:
                        break;
                }

                int gradientMode;
                if (IsPagerBackgroundGradient && IsPagerBorderGradient)
                    gradientMode = NormalPagerFragment.GRADIENT_MODE_BACKGROUND_BORDER;
                else if (IsPagerBackgroundGradient)
                    gradientMode = NormalPagerFragment.GRADIENT_MODE_BACKGROUND;
                else if (IsPagerBorderGradient)
                    gradientMode = NormalPagerFragment.GRADIENT_MODE_BORDER;
                else
                    gradientMode = NormalPagerFragment.GRADIENT_MODE_NONE;

                DemoItem demo = DemoItem.values()[position];
                switch (demo) {
                    case TextPagerSelectorInMiddle:
                        NormalPagerFragment fg = NormalPagerFragment.create(R.layout.fragment_text_pager_defined_selector_in_mid,
                            selectorBgResId, R.drawable.bg_gradient_mid, gradientMode);
                        mFgMgr.beginTransaction().add(android.R.id.content, fg, FRAGMENT_TAG).addToBackStack(null).commit();
                        break;
                    case TextPagerSelectorOnLeft:
                        fg = NormalPagerFragment.create(R.layout.fragment_text_pager_defined_selector_on_left, selectorBgResId,
                            R.drawable.bg_gradient_left, gradientMode);
                        mFgMgr.beginTransaction().add(android.R.id.content, fg, FRAGMENT_TAG).addToBackStack(null).commit();
                        break;
                    case AddTextPager:
                        fg = AddTextPagerInCodeFragment.create(R.layout.fragment_no_pager_defined, selectorBgResId,
                            R.drawable.bg_gradient_mid, gradientMode);
                        mFgMgr.beginTransaction().add(android.R.id.content, fg, FRAGMENT_TAG).addToBackStack(null).commit();
                        break;
                    case IconPager:
                        fg = NormalPagerFragment.create(R.layout.fragment_img_pager_defined, selectorBgResId,
                            R.drawable.bg_gradient_mid, gradientMode);
                        mFgMgr.beginTransaction().add(android.R.id.content, fg, FRAGMENT_TAG).addToBackStack(null).commit();
                        break;

                    case StyledPager1:
                        StyledPager1Fragment styledPgrFg = StyledPager1Fragment.create();
                        mFgMgr.beginTransaction().add(android.R.id.content, styledPgrFg, FRAGMENT_TAG).addToBackStack(null).commit();
                        break;
                    case StyledPager2:
                        StyledPager2Fragment styledPgr2Fg = StyledPager2Fragment.create();
                        mFgMgr.beginTransaction().add(android.R.id.content, styledPgr2Fg, FRAGMENT_TAG).addToBackStack(null).commit();
                        break;
                    default:
                        break;
                }

            }
        });

    }


    public static enum DemoItem {
        TextPagerSelectorInMiddle("Text pager, selector in middle"),
        TextPagerSelectorOnLeft("Text pager, selector on left"),
        AddTextPager("Add text pager programmatically"),
        IconPager("Icon pager"),
        StyledPager1("Styled pager 1"),
        StyledPager2("Styled pager 2");

        String text;

        DemoItem(String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }


}
