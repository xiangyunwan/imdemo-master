package com.gf.platform.gfplatform.widget.chatkeyboard.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.gf.platform.gfplatform.R;
import com.gf.platform.gfplatform.base.BaseFaceFragment;
import com.gf.platform.gfplatform.entity.Emoji;
import com.gf.platform.gfplatform.util.Global;
import com.gf.platform.gfplatform.widget.chatkeyboard.adapter.EmojiAdapter;
import com.gf.platform.gfplatform.widget.chatkeyboard.listener.KeyBoardListener;

import java.util.ArrayList;
import java.util.List;

public class EmojiPageFragment extends BaseFaceFragment {

    private static final int ITEM_PAGE_COUNT = 24;
    private LinearLayout pagePointLayout;

    private Activity aty;
    private GridView[] allPageViews;
    private RadioButton[] pointViews;
    private KeyBoardListener mListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        aty = getActivity();
        View rootView = LayoutInflater.from(aty).inflate(R.layout.bjmgf_message_chat_frag_face, null);
        initWidget(rootView);
        return rootView;
    }

    private void initWidget(View rootView) {
        mPagerFace = (ViewPager) rootView.findViewById(R.id.frag_pager_face);
        pagePointLayout = (LinearLayout) rootView.findViewById(R.id.frag_point);

        int total = Global.EMOJIS.size();
        pages = total / ITEM_PAGE_COUNT
                + (total % ITEM_PAGE_COUNT == 0 ? 0 : 1);

        allPageViews = new GridView[pages];
        pointViews = new RadioButton[pages];

        for (int x = 0; x < pages; x++) {
            int start = x * ITEM_PAGE_COUNT;
            int end = (start + ITEM_PAGE_COUNT) > total ? total
                    : (start + ITEM_PAGE_COUNT);
            GridView view = new GridView(aty);
            List<Emoji> emojis = new ArrayList<>();
            emojis.addAll(Global.EMOJIS.subList(start, end - 1));
            Emoji delEmoji = new Emoji("-1", "del_normal", "删除", "");
            emojis.add(delEmoji);
            EmojiAdapter faceAdapter = new EmojiAdapter(getActivity(), emojis, mListener);
            view.setNumColumns(8);
            view.setHorizontalSpacing(1);
            view.setVerticalSpacing((int) getActivity().getResources().getDimension(R.dimen.gf_10dp));
            view.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
            view.setCacheColorHint(0);
            view.setPadding(2, 0, 2, 0);
            view.setBackgroundResource(android.R.color.transparent);
            view.setSelector(android.R.color.transparent);
            view.setVerticalScrollBarEnabled(false);
            view.setGravity(Gravity.CENTER);
            view.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT));
            view.setAdapter(faceAdapter);
            allPageViews[x] = view;

            RadioButton tip = new RadioButton(aty);
            tip.setBackgroundResource(R.drawable.selector_bg_tip);
            RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(
                    (int) getActivity().getResources().getDimension(R.dimen.gf_6dp), (int) getActivity().getResources().getDimension(R.dimen.gf_6dp));
            layoutParams.leftMargin = 2;
            pagePointLayout.addView(tip, layoutParams);
            if (x == 0) {
                tip.setChecked(true);
            }
            pointViews[x] = tip;
        }

        PagerAdapter facePagerAdapter = new FacePagerAdapter(allPageViews);
        mPagerFace.setAdapter(facePagerAdapter);
        mPagerFace.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int index) {
                pointViews[index].setChecked(true);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }

    public class FacePagerAdapter extends PagerAdapter {
        private final GridView[] gridViewList;

        public FacePagerAdapter(GridView[] gridViewList) {
            this.gridViewList = gridViewList;
        }

        @Override
        public int getCount() {
            return gridViewList.length;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView(gridViewList[arg1]);
        }

        @Override
        public Object instantiateItem(View arg0, int arg1) {
            ((ViewPager) arg0).addView(gridViewList[arg1]);
            return gridViewList[arg1];
        }
    }

    public void setKeyBoardListener(KeyBoardListener listener) {
        mListener = listener;
    }
}
