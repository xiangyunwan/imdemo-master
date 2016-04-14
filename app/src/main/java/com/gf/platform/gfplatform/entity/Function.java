package com.gf.platform.gfplatform.entity;

import android.graphics.Bitmap;

/**
 * 功能
 * Created by sunhaoyang on 2016/3/3.
 */
public class Function {

    public String name;
    public Bitmap pic;
    public int resId;

    public Function () {}

    public Function(String name, int resId) {
        this.resId = resId;
        this.name = name;
    }

    public Function(String name, Bitmap pic) {
        this.pic = pic;
        this.name = name;
    }

    public Function(String name, Bitmap pic, int resId) {
        this.name = name;
        this.pic = pic;
        this.resId = resId;
    }
}
