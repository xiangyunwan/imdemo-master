package com.gf.platform.gfplatform.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.gf.platform.gfplatform.R;
import com.gf.platform.gfplatform.adapter.HeaderAndFooterRecyclerViewAdapter;
import com.gf.platform.gfplatform.adapter.MessageAdapter;
import com.gf.platform.gfplatform.base.BaseFragment;
import com.gf.platform.gfplatform.entity.Message;
import com.gf.platform.gfplatform.util.Global;
import com.gf.platform.gfplatform.util.Util;
import com.gf.platform.gfplatform.widget.swipelayout.util.Attributes;

/**
 * 消息列表
 * Created by sunhaoyang on 2016/2/19.
 */
public class MessageFragment extends BaseFragment implements MessageAdapter.MsgAdapterListener {

    private RecyclerView rvMessage = null;
    private LinearLayoutManager mLayoutManager = null;
    private MessageAdapter adapter = null;
    private HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.bjmgf_message_fragment, container, false);
        init();
        return mView;
    }

    /**
     * 初始化
     */
    private void init() {
        rvMessage = getView(R.id.bjmgf_message_rv);
        mLayoutManager = new LinearLayoutManager(getActivity());
        rvMessage.setLayoutManager(mLayoutManager);
        Global.MESSAGES.clear();
        for (int i = 0; i < 20; i++) {
            Message m = new Message();
            m.setDate("星期三");
            m.setInfo("[惬意]");
            m.setNickName("火星来客" + i);
            m.setOldPosition(i);
            Global.MESSAGES.add(m);
        }
        adapter = new MessageAdapter(this, this);
        adapter.setMode(Attributes.Mode.Single);
        mHeaderAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(adapter);
        rvMessage.setAdapter(mHeaderAndFooterRecyclerViewAdapter);

        View vFooter = new View(getActivity());
        vFooter.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) getActivity().getResources().getDimension(R.dimen.gf_40dp)));
        vFooter.setBackgroundResource(R.color.gf_message_bg);
        Util.setFooterView(rvMessage, vFooter);

        View vHeader = View.inflate(getActivity(), R.layout.bjmgf_message_list_header, null);
        vHeader.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        RelativeLayout rlHeader = (RelativeLayout) vHeader.findViewById(R.id.bjmgf_message_head_notify_rl);
        vHeader.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    rlHeader.setBackgroundColor(getResources().getColor(R.color.gf_message_swipe_action_down));
                    break;
                case MotionEvent.ACTION_UP:
                    rlHeader.setBackgroundColor(getResources().getColor(R.color.gf_white));
                    break;
                case MotionEvent.ACTION_CANCEL:
                    rlHeader.setBackgroundColor(getResources().getColor(R.color.gf_white));
                    break;
                default:
                    break;
            }
            return true;
        });

        Util.setHeaderView(rvMessage, vHeader);
    }

    @Override
    public void OnMessageTop(int position) {
        Message msg = Global.MESSAGES.get(position);
        if (!msg.isTop()) {
            msg.setTop(true);
            Global.MESSAGES.remove(position);
            Global.MESSAGES.add(0, msg);
        } else {
            msg.setTop(false);
            Global.MESSAGES.remove(msg);
            Global.MESSAGES.add(msg.getOldPosition(), msg);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void OnMessageDel(int position) {
        Global.MESSAGES.remove(position);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}
