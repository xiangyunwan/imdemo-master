package com.gf.platform.gfplatform.entity;

/**
 * emoji表情
 * Created by sunhaoyang on 2016/2/24.
 */
public class Emoji {

    public String id;
    public String name;
    public String show;
    public String code;
    public String value;

    public Emoji() {
    }

    public Emoji(String id, String name, String show, String code) {
        this.id = id;
        this.name = name;
        this.show = show;
        this.code = code;
    }

    public Emoji(String id, String name, String show, String code, String value) {
        this.id = id;
        this.name = name;
        this.show = show;
        this.code = code;
        this.value = value;
    }
}
