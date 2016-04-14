package com.gf.platform.gfplatform.widget.chatkeyboard.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.widget.ImageView;

import com.gf.platform.gfplatform.R;
import com.gf.platform.gfplatform.base.BaseFaceFragment;
import com.gf.platform.gfplatform.widget.chatkeyboard.ChatKeyBoard;
import com.gf.platform.gfplatform.widget.chatkeyboard.PagerSlidingTabStrip;

import java.util.List;

public class FaceCategroyAdapter extends FragmentStatePagerAdapter implements
        PagerSlidingTabStrip.IconTabProvider {
    private final int sMode;
    private Context mContext = null;
    //每个item表示一个folder绝对路径，每个folder中存放的是一套face图片
    private List<String> datas;
    private List<BaseFaceFragment> listFragment;

    public FaceCategroyAdapter(Context context, FragmentManager fm, int mode, List<BaseFaceFragment> listFragment) {
        super(fm);
        sMode = mode;
        mContext = context;
        this.listFragment = listFragment;
    }

    @Override
    public void setPageIcon(int position, ImageView image) {
        switch (position) {
            case 0:
                image.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.bjmgf_message_chat_emoji));
                break;
            case 1:
                image.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.bjmgf_message_chat_meng));
                break;
            case 2:
                image.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.bjmgf_message_chat_palace));
                break;
            case 3:
                image.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.bjmgf_message_chat_pigge));
                break;
            case 4:
                image.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.bjmgf_message_chat_wzf));
                break;
            default:
                image.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.bjmgf_message_chat_emoji));
                break;
        }
    }

    @Override
    public int getCount() {
        if (sMode == ChatKeyBoard.LAYOUT_TYPE_FACE) {
            return listFragment.size();
        } else {
            return 1;
        }
    }

    @Override
    public Fragment getItem(int position) {
        Fragment f = null;
        if (sMode == ChatKeyBoard.LAYOUT_TYPE_FACE) {
            f = listFragment.get(position);
        }
        return f;
    }
}