package com.gf.platform.gfplatform;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.gf.platform.gfplatform.adapter.TabFragmentAdapter;
import com.gf.platform.gfplatform.base.BaseFragmentActivity;
import com.gf.platform.gfplatform.fragment.ContactFragment;
import com.gf.platform.gfplatform.fragment.ExploreFragment;
import com.gf.platform.gfplatform.fragment.GameFragment;
import com.gf.platform.gfplatform.fragment.MessageFragment;
import com.gf.platform.gfplatform.util.EmojiUtil;
import com.gf.platform.gfplatform.util.Global;
import com.gf.platform.gfplatform.util.Util;
import com.gf.platform.gfplatform.widget.circleimageview.CircleImageView;
import com.gf.platform.gfplatform.widget.customviewpager.CustomViewPager;
import com.gf.platform.gfplatform.widget.slidemenu.SlidingMenu;

import java.util.ArrayList;
import java.util.List;

public class MainTabActivity extends BaseFragmentActivity {

    private CircleImageView faceIv = null;
    private TabLayout tbMain = null;
    private CustomViewPager vpMain = null;
    private TabFragmentAdapter adapter = null;
    private SlidingMenu sm = null;
    private float oldLocationX = 0;
    private float oldLocationY = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        faceIv = getView(R.id.bjmgf_main_face_iv);
        tbMain = getView(R.id.bjmgf_main_tab);
        vpMain = getView(R.id.bjmgf_main_vp);
        sm = getView(R.id.bjmgf_main_menu);

        sm.setBackgroundDrawable(new BitmapDrawable(Util.doBlur(BitmapFactory.decodeResource(getResources(), R.mipmap.demo_face), 15, false)));

        tbMain.setTabMode(TabLayout.MODE_FIXED);

        Fragment fMessage = new MessageFragment();
        Fragment fGame = new GameFragment();
        Fragment fContact = new ContactFragment();
        Fragment fExplore = new ExploreFragment();
        List<Fragment> list = new ArrayList<>();
        list.add(fGame);
        list.add(fMessage);
        list.add(fContact);
        list.add(fExplore);
        adapter = new TabFragmentAdapter(getSupportFragmentManager(), list);
        vpMain.setAdapter(adapter);
        tbMain.setupWithViewPager(vpMain);

        resetTab();
        faceIv.setOnClickListener(v -> {
            sm.toggle();
        });

        tbMain.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                resetTabImg();
                setTabImg(tab.getPosition(), tab);
                vpMain.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                resetTabImg();
                setTabImg(tab.getPosition(), tab);
                vpMain.setCurrentItem(tab.getPosition());
            }
        });

        tbMain.getTabAt(1).select();

        Global.EMOJIS = EmojiUtil.parseEmoji(this);
        Global.EMOJISCODE = EmojiUtil.parseEmojiCode(this);

    }

    private void setTabImg(int index, TabLayout.Tab t) {
        switch (index) {
            case 0:
                ((ImageView) t.getCustomView().findViewById(R.id.bjmgf_main_tab_iv)).setImageResource(R.mipmap.bjmgf_main_tab_game_selected);
                break;
            case 1:
                ((ImageView) t.getCustomView().findViewById(R.id.bjmgf_main_tab_iv)).setImageResource(R.mipmap.bjmgf_main_tab_message_selected);
                break;
            case 2:
                ((ImageView) t.getCustomView().findViewById(R.id.bjmgf_main_tab_iv)).setImageResource(R.mipmap.bjmgf_main_tab_contact_selected);
                break;
            case 3:
                ((ImageView) t.getCustomView().findViewById(R.id.bjmgf_main_tab_iv)).setImageResource(R.mipmap.bjmgf_main_tab_explore_selected);
                break;
            default:
                break;
        }
    }

    private void resetTabImg() {
        int tabSize = tbMain.getTabCount();
        for (int i = 0; i < tabSize; i++) {
            TabLayout.Tab t = tbMain.getTabAt(i);
            switch (i) {
                case 0:
                    ((ImageView) t.getCustomView().findViewById(R.id.bjmgf_main_tab_iv)).setImageResource(R.mipmap.bjmgf_main_tab_game);
                    break;
                case 1:
                    ((ImageView) t.getCustomView().findViewById(R.id.bjmgf_main_tab_iv)).setImageResource(R.mipmap.bjmgf_main_tab_message);
                    break;
                case 2:
                    ((ImageView) t.getCustomView().findViewById(R.id.bjmgf_main_tab_iv)).setImageResource(R.mipmap.bjmgf_main_tab_contact);
                    break;
                case 3:
                    ((ImageView) t.getCustomView().findViewById(R.id.bjmgf_main_tab_iv)).setImageResource(R.mipmap.bjmgf_main_tab_explore);
                    break;
                default:
                    break;
            }
        }
    }

    private void resetTab() {
        int tabSize = tbMain.getTabCount();
        for (int i = 0; i < tabSize; i++) {
            TabLayout.Tab t = tbMain.getTabAt(i);
            switch (i) {
                case 0:
                    t.setCustomView(Util.getTabView(this, R.mipmap.bjmgf_main_tab_game));
                    break;
                case 1:
                    t.setCustomView(Util.getTabView(this, R.mipmap.bjmgf_main_tab_message));
                    break;
                case 2:
                    t.setCustomView(Util.getTabView(this, R.mipmap.bjmgf_main_tab_contact));
                    break;
                case 3:
                    t.setCustomView(Util.getTabView(this, R.mipmap.bjmgf_main_tab_explore));
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            oldLocationX = ev.getX();
            oldLocationY = ev.getY();
        } else if (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_CANCEL) {
            if (oldLocationX == ev.getX() && oldLocationY == ev.getY()) {
                if (sm.isOpen()) {
                    sm.toggle();
                    return true;
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }
}
