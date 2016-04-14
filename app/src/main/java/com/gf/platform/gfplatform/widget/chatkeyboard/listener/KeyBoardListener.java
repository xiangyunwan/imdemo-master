package com.gf.platform.gfplatform.widget.chatkeyboard.listener;


import com.gf.platform.gfplatform.entity.Emoji;
import com.gf.platform.gfplatform.entity.Face;

public interface KeyBoardListener {

    void selectedFace(Face face);

    void selectedEmoji(Emoji emoji);
    
    void selectedBackSpace(Emoji back);
}
