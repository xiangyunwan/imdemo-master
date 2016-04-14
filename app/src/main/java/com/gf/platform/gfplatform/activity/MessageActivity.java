package com.gf.platform.gfplatform.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gf.platform.gfplatform.R;
import com.gf.platform.gfplatform.adapter.MessageListAdapter;
import com.gf.platform.gfplatform.base.BaseFragmentActivity;
import com.gf.platform.gfplatform.entity.Face;
import com.gf.platform.gfplatform.entity.Message;
import com.gf.platform.gfplatform.util.EmojiUtil;
import com.gf.platform.gfplatform.util.Global;
import com.gf.platform.gfplatform.util.LogProxy;
import com.gf.platform.gfplatform.util.Util;
import com.gf.platform.gfplatform.widget.chatkeyboard.ChatKeyBoard;
import com.gf.platform.gfplatform.widget.chatkeyboard.listener.SendListener;
import com.gf.platform.gfplatform.widget.dropdownlistview.DropDownListView;
import com.gf.platform.gfplatform.widget.swipeback.SwipeBackActivityHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * im界面
 * Created by sunhaoyang on 2016/2/23.
 */
public class MessageActivity extends BaseFragmentActivity implements DropDownListView.OnRefreshListenerHeader {
    //标题
    private TextView tvTitle = null;
    //返回按钮
    private RelativeLayout rlBack = null;
    //表情键盘
    private ChatKeyBoard boxInput = null;
    //对话列表
    private DropDownListView lvMsg = null;
    //对话列表适配器
    private MessageListAdapter adapter = null;
    //消息集合
    private List<Message> list = new ArrayList<>();
    private Handler mHandler = null;
    //滑动返回并且关闭当前页面
    private SwipeBackActivityHelper helper = new SwipeBackActivityHelper();
    //修改BUG：如果不在handler中操作，会导致无法正确置底
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            lvMsg.smoothScrollToPosition(list.size());
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bjmgf_message_activity);

        //初始化滑动关闭页面
        helper.setDebuggable(true)
                .setEdgeMode(false)
                .setParallaxMode(true)
                .setParallaxRatio(3)
                .setNeedBackgroundShadow(false)
                .init(this);

        tvTitle = getView(R.id.bjmgf_message_chat_title_tv);
        rlBack = getView(R.id.bjmgf_message_chat_back);
        boxInput = getView(R.id.bjmgf_message_chat_keyboard);
        lvMsg = getView(R.id.bjmgf_message_chat_listview);

        //获取当前消息的索引
        int index = getIntent().getIntExtra("index", 0);
        boxInput.setMessageIndex(index);
        tvTitle.setText(Global.MESSAGES.get(index).getNickName());

        //如果草稿不为空，则显示草稿
        if (Global.MESSAGES.get(index).getDraft().trim().length() > 0) {
            boxInput.getEditText().setText(EmojiUtil.convert(this, Global.MESSAGES.get(index).getDraft()));
        }

        rlBack.setOnClickListener(v -> {
            finish();
        });

        //键盘设置监听
        //发送消息，发送表情
        boxInput.setListener(new SendListener() {
            @Override
            public void sendMsg(String text) {
                LogProxy.i("text = " + text);
                Message msg = new Message("帅的一般", text, "22:22", "", true, false);
                list.add(msg);
                msg = new Message("一般的帅", text, "22:23", "", false, false);
                list.add(msg);
                adapter.notifyDataSetChanged();
                handler.sendEmptyMessage(0);
            }

            @Override
            public void sendExpression(Face face) {
                Message msg = new Message("帅的一般", "", "15:15", "", true, Util.getImageThumbnail(face.path, 200, 200), false);
                list.add(msg);
                msg = new Message("一般的帅", "", "15:20", "", false, Util.getImageThumbnail(face.path, 200, 200), false);
                list.add(msg);
                adapter.notifyDataSetChanged();
                lvMsg.requestFocus();
                handler.sendEmptyMessage(0);
            }
        });

        for (int i = 0; i < 10; i++) {
            Message m = new Message("火星海盗" + i, "你好火星海盗火星海盗火星海盗火星海盗火星海盗火星海盗火星海盗火星海盗火星海盗火星海盗火星海盗火星海盗火星海盗火星海盗火星海盗火星海盗火星海盗火星海盗火星海盗火星海盗火星海盗火星海盗火星海盗火星海盗火星海盗火星海盗火星海盗" + i, "11:10", "", true, false);
            list.add(m);
        }
        adapter = new MessageListAdapter(this, list);
        lvMsg.setAdapter(adapter);
        lvMsg.setOnRefreshListenerHead(this);
        //将消息置底
        lvMsg.setSelection(list.size() - 1);
        //设置触摸监听
        lvMsg.setOnTouchListener(getOnTouchListener());
        //设置光标处于最后
        boxInput.getEditText().setSelection(boxInput.getEditText().getText().length());
    }

    @Override
    public void onRefresh() {
        List<Message> oldMsg = new ArrayList<>();
        mHandler = new Handler(getMainLooper()) {
            @Override
            public void handleMessage(android.os.Message msg) {
                super.handleMessage(msg);
                adapter.notifyDataSetChanged();
                if (!lvMsg.isMove()) {
                    lvMsg.setSelectionFromTop(oldMsg.size() + 1, (int) getResources().getDimension(R.dimen.gf_40dp));
                }
                lvMsg.onRefreshCompleteHeader();
            }
        };
        for (int i = 0; i < 3; i++) {
            if (i == 2) {
                Message m = new Message("帅的一般", "帅的一般般帅的一般般帅的一般般帅的一般般帅的一般般帅的一般般帅的一般般帅的一般般帅的一般般帅的一般般帅的一般般" + i, "10:22", "", true, false);
                oldMsg.add(m);
                continue;
            }
            Message m = new Message("帅的一般", "帅的一般般" + i, "10:22", "", true, false);
            oldMsg.add(m);
        }
        list.addAll(0, oldMsg);
        mHandler.sendEmptyMessageDelayed(0, 1000);
    }

    /**
     * 若软键盘或表情键盘弹起，点击上端空白处应该隐藏输入法键盘
     *
     * @return 会隐藏输入法键盘的触摸事件监听器
     */
    private View.OnTouchListener getOnTouchListener() {
        return (v, event) -> {
            boxInput.hideExpressionLayout();
            Util.hideKeyboard(MessageActivity.this);
            boxInput.hideFunctionLayout();
            return false;
        };
    }

    @Override
    public void onBackPressed() {
        helper.finish();
    }

    @Override
    public void finish() {
        Util.hideKeyboard(this);
        super.finish();
    }
}
