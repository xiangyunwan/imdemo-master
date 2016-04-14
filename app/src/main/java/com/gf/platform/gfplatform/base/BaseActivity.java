package com.gf.platform.gfplatform.base;

import android.app.Activity;
import android.view.View;

/**
 * 基类activity
 * Created by sunhaoyang on 2016/2/18.
 */
public class BaseActivity extends Activity {

    public final <E extends View> E getView(int id) {
        try {
            return (E) findViewById(id);
        } catch (ClassCastException ex) {
            throw ex;
        }
    }
}
