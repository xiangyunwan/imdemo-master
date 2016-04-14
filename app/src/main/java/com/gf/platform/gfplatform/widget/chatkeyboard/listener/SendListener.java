package com.gf.platform.gfplatform.widget.chatkeyboard.listener;

import com.gf.platform.gfplatform.entity.Face;

public interface SendListener {

    void sendMsg(String text);

    void sendExpression(Face face);
}
