package com.gf.platform.gfplatform.util;

import android.util.Log;

/**
 * Created by sunhaoyang on 2016/3/17.
 */
public class LogProxy {

    private static String TAG = LogProxy.class.getSimpleName();

    public static void i(String log) {
        Log.i(TAG, log);
    }
}
