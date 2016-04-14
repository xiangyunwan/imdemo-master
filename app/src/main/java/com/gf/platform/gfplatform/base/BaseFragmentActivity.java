package com.gf.platform.gfplatform.base;

import android.support.v4.app.FragmentActivity;
import android.view.View;

/**
 * 基类fragmentactivity
 * Created by sunhaoyang on 2016/2/23.
 */
public class BaseFragmentActivity extends FragmentActivity {

    public final <E extends View> E getView(int id) {
        try {
            return (E) findViewById(id);
        } catch (ClassCastException ex) {
            throw ex;
        }
    }
}
