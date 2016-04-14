package com.gf.platform.gfplatform.util;

import com.gf.platform.gfplatform.entity.Emoji;
import com.gf.platform.gfplatform.entity.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 通用数据
 * Created by sunhaoyang on 2016/2/22.
 */
public class Global {
    public static List<Emoji> EMOJIS = null;
    public static Map<String, String> EMOJISCODE = null;
    public static List<Message> MESSAGES = new ArrayList<>();
    public static boolean canBack = true;
}
