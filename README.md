PagerIndicator
==============

Customize your pager view with ease.


Usage
==============
1. You need to specify a selector id with `selector_id` attribute and at least a `HorizontalScrollView`.
2. `PagerIndicatorLayout` extendes from `RelativeLayout`, which means your can put your selector view wherever you want. 
3. You can use any view as your selector, or even a layout.
4. You can put decoration in `PagerIndicatorLayout` to style your pager indicator, for example, add a foreground gradient. Check `sample/` I have implmented it.
3. Typically you would specify a layout as a pager container in `HorizontalScrollView`, `LinearLayout` with `android:orientation="horizontal"` is easier. 
4. If you are using layout as pager container, DO NOT specify `android:layout_gravity`, otherwise it wont behave as expect.
5. set select callback with`PagerIndicatorLayout.setItemSelectListener(OnItemSelectListener listener)`
6. You can use it along with `ViewPager`. Check out `sample/` for all implementation.

Ideally a layout file would be like this:

    <com.royliao.pagerindicator.PagerIndicatorLayout
        android:id="@+id/lo_pi"
        style="@style/PagerIndicatorLayout"
        xmlns:pagerIndicator="http://schemas.android.com/apk/res-auto"
        pagerIndicator:selector_id="@+id/iv_selector">

        <HorizontalScrollView
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:scrollbarThumbHorizontal="@android:color/transparent">
            <!-- a container for pager, layout_gravity="center_horizontal" won't work as expect -->
            <LinearLayout
                android:id="@+id/lo_container"
                android:layout_width="wrap_content"
                android:layout_height="match_parent"
                android:gravity="center_vertical"
                android:orientation="horizontal">

                <TextView
                    style="@style/Pager"
                    android:text="Page1" />

                <TextView
                    style="@style/Pager"
                    android:text="Page2" />

                <TextView
                    style="@style/Pager"
                    android:text="Page3" />

                <TextView
                    style="@style/Pager"
                    android:text="Page4" />
            </LinearLayout>

        </HorizontalScrollView>
        <!-- selector usually is set after HorizontalScrollView so that it can be on it's top -->
        <ImageView
            android:id="@+id/iv_selector"
            android:layout_width="60dp"
            android:layout_height="match_parent"
            android:layout_centerInParent="true" />

    </com.royliao.pagerindicator.PagerIndicatorLayout>


<img src ="http://www.mediafire.com/convkey/5362/6iw446wcca6hteafg.jpg" /><img src ="http://www.mediafire.com/convkey/903b/j1qt3239h28d11ffg.jpg" /><img src ="http://www.mediafire.com/convkey/dfaa/j83o6dacd1erqh9fg.jpg" /><img src ="http://www.mediafire.com/convkey/45f5/a75vmg9l3cc2p2afg.jpg" /><img src ="http://www.mediafire.com/convkey/5789/qckb9b41ec938e9fg.jpg" /><img src ="http://www.mediafire.com/convkey/cccd/30fgz6ml1voco3zfg.jpg" />
