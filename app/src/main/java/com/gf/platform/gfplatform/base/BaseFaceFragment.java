package com.gf.platform.gfplatform.base;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

/**
 * 基类Face fragment
 * Created by sunhaoyang on 2016/3/11.
 */
public class BaseFaceFragment extends Fragment {

    protected ViewPager mPagerFace = null;
    protected int pages = 0;

    public ViewPager getViewPager() {
        return mPagerFace;
    }

    public int getPage() {
        return pages;
    }
}
