package com.gf.platform.gfplatform.entity;

/**
 * 表情
 * Created by sunhaoyang on 2016/2/29.
 */
public class Face {

    public String id;
    public String name;
    public String show;
    public String value;
    public String path;
    public boolean isShow;

    public Face(String id, String name, String show, String value, String path, boolean isShow) {
        this.id = id;
        this.name = name;
        this.show = show;
        this.value = value;
        this.path = path;
        this.isShow = isShow;
    }
}
