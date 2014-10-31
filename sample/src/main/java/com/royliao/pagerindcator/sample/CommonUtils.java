package com.royliao.pagerindcator.sample;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

public class CommonUtils {

    public static int convertDipToPx(Context context, int dp) {
        Resources r = context.getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
        return (int) px;
    }
}
