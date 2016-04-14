package com.gf.platform.gfplatform.base;

import android.support.v4.app.Fragment;
import android.view.View;

/**
 * 基类Fragment
 * Created by sunhaoyang on 2016/2/22.
 */
public class BaseFragment extends Fragment {

    protected View mView = null;

    public final <E extends View> E getView(int id) {
        try {
            return (E) mView.findViewById(id);
        } catch (ClassCastException ex) {
            throw ex;
        }
    }
}
