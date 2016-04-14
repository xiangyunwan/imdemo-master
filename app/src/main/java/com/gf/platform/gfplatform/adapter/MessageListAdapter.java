package com.gf.platform.gfplatform.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gf.platform.gfplatform.R;
import com.gf.platform.gfplatform.entity.Message;
import com.gf.platform.gfplatform.widget.circleimageview.CircleImageView;
import com.gf.platform.gfplatform.widget.emojitextview.EmojiTextView;

import java.util.List;

/**
 * Created by sunhaoyang on 2016/3/2.
 */
public class MessageListAdapter extends BaseAdapter {

    private Context mContext = null;
    private List<Message> mList = null;
    private LayoutInflater inflater;

    public MessageListAdapter(Context context, List<Message> list) {
        mContext = context;
        mList = list;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).isFlag() ? 1 : 0;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        Message msg = mList.get(position);
        if (convertView == null) {
            holder = new ViewHolder();
            if (msg.isFlag()) {
                convertView = View.inflate(mContext, R.layout.bjmgf_message_chat_list_msg_info_me_item, null);
            } else {
                convertView = View.inflate(mContext, R.layout.bjmgf_message_chat_list_msg_info_you_item, null);
            }
            holder.tvChat = (EmojiTextView)convertView.findViewById(R.id.bjmgf_message_chat_msg_item_tv);
            holder.tvTime = (TextView)convertView.findViewById(R.id.bjmgf_message_chat_time_tv);
            holder.ivFace = (CircleImageView)convertView.findViewById(R.id.bjmgf_message_chat_msg_item_face_iv);
            holder.ivChat = (ImageView)convertView.findViewById(R.id.bjmgf_message_chat_msg_item_expression);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        if (mList.get(position).getExpression() != null) {
            holder.ivChat.setImageBitmap(mList.get(position).getExpression());
            holder.tvChat.setVisibility(View.GONE);
            holder.ivChat.setVisibility(View.VISIBLE);
        } else {
            holder.tvChat.setText(msg.getInfo());
            holder.ivChat.setVisibility(View.GONE);
            holder.tvChat.setVisibility(View.VISIBLE);
        }
        holder.tvTime.setText(msg.getDate());

        return convertView;
    }

    static class ViewHolder {
        TextView tvTime;
        EmojiTextView tvChat;
        ImageView ivChat;
        CircleImageView ivFace;
    }
}
