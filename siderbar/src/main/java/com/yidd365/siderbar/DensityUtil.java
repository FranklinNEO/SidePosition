package com.yidd365.siderbar;

import android.content.Context;

/**
 * Created by Neo on 16/4/8.
 */
public class DensityUtil {
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);

    }
}
