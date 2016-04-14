package com.gf.platform.gfplatform.widget.chatkeyboard.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout.LayoutParams;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.gf.platform.gfplatform.R;
import com.gf.platform.gfplatform.base.BaseFaceFragment;
import com.gf.platform.gfplatform.entity.Face;
import com.gf.platform.gfplatform.util.FaceUtil;
import com.gf.platform.gfplatform.widget.chatkeyboard.adapter.FaceAdapter;
import com.gf.platform.gfplatform.widget.chatkeyboard.listener.KeyBoardListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FacePageFragment extends BaseFaceFragment {

    private static final int ITEM_PAGE_COUNT = 10;
    private LinearLayout pagePointLayout;
    private Activity aty;
    private GridView[] allPageViews;
    private RadioButton[] pointViews;
    private KeyBoardListener listener;
    private String folderPath = "";

    //当前fragment(一个表情分类)所包含的全部表情
    private List<Face> datas;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        aty = getActivity();
        View rootView = LayoutInflater.from(aty).inflate(R.layout.bjmgf_message_chat_frag_face, null);
        initWidget(rootView);
        return rootView;
    }

    protected void initWidget(View rootView) {
        String name = folderPath.subSequence(folderPath.lastIndexOf("/") + 1, folderPath.length()) + "";
        if (folderPath == null || folderPath.trim().length() == 0) {
            folderPath = "";
        }
        File folder = new File(folderPath);
        if (folder.isDirectory()) {
            datas = FaceUtil.parseFace(name);
        } else {
            datas = new ArrayList<>(0);
        }
        mPagerFace = (ViewPager) rootView.findViewById(R.id.frag_pager_face);
        pagePointLayout = (LinearLayout) rootView.findViewById(R.id.frag_point);

        int total = datas.size();
        pages = total / ITEM_PAGE_COUNT
                + (total % ITEM_PAGE_COUNT == 0 ? 0 : 1);

        allPageViews = new GridView[pages];
        pointViews = new RadioButton[pages];

        for (int x = 0; x < pages; x++) {
            int start = x * ITEM_PAGE_COUNT;
            int end = (start + ITEM_PAGE_COUNT) > total ? total
                    : (start + ITEM_PAGE_COUNT);
            final List<Face> itemDatas = datas.subList(start, end);
            GridView view = new GridView(aty);
            FaceAdapter faceAdapter = new FaceAdapter(getActivity(), itemDatas);
            view.setAdapter(faceAdapter);

            view.setNumColumns(5);
            view.setBackgroundColor(Color.TRANSPARENT);
            view.setHorizontalSpacing(1);
            view.setVerticalSpacing(1);
            view.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
            view.setCacheColorHint(0);
            view.setVerticalScrollBarEnabled(false);
            view.setSelector(new ColorDrawable(Color.TRANSPARENT));
            view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));
            view.setGravity(Gravity.CENTER);

            view.setOnItemClickListener((parent, v, position, id) -> {
                if (listener != null) {
                    listener.selectedFace(itemDatas.get(position));
                }
            });
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
        mPagerFace.setOnPageChangeListener(new OnPageChangeListener() {

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
        this.listener = listener;
    }

    public void setPath(String path) {
        folderPath = path;
    }
}