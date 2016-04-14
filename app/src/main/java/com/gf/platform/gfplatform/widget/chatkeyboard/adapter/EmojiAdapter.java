package com.gf.platform.gfplatform.widget.chatkeyboard.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.gf.platform.gfplatform.R;
import com.gf.platform.gfplatform.entity.Emoji;
import com.gf.platform.gfplatform.widget.chatkeyboard.listener.KeyBoardListener;

import java.io.IOException;
import java.util.List;

public class EmojiAdapter extends BaseAdapter {

    private Context mContext = null;
    private float oldLocationX = -1;
    private float oldLocationY = -1;
    private KeyBoardListener mListener = null;
    private List<Emoji> listEmoji = null;

    public EmojiAdapter(Context context, List<Emoji> mDatas, KeyBoardListener listener) {
        mContext = context;
        mListener = listener;
        listEmoji = mDatas;
    }

    @Override
    public int getCount() {
        return listEmoji.size();
    }

    @Override
    public Object getItem(int position) {
        return listEmoji.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.bjmgf_message_chat_item_emoji, null);
            holder.itemIvEmoji = (ImageView)convertView.findViewById(R.id.bjmgf_message_chat_emoji_iv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        Emoji emoji = listEmoji.get(position);
        try {
            Bitmap mBitmap = BitmapFactory.decodeStream(mContext.getAssets().open("face/emoji/Emoji_" + emoji.name + ".png"));
            holder.itemIvEmoji.setImageBitmap(mBitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

        holder.itemIvEmoji.setBackgroundResource(R.drawable.bjmgf_message_chat_emoji);

        if (emoji.name.equals("del_normal")) {
            ViewGroup.LayoutParams lp = holder.itemIvEmoji.getLayoutParams();
            lp.width = (int)mContext.getResources().getDimension(R.dimen.gf_30dp);
            lp.height = (int)mContext.getResources().getDimension(R.dimen.gf_37dp);
            holder.itemIvEmoji.setLayoutParams(lp);
            holder.itemIvEmoji.setBackgroundColor(mContext.getResources().getColor(R.color.gf_white));
        }

        holder.itemIvEmoji.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    oldLocationX = event.getX();
                    oldLocationY = event.getY();
                    if (emoji.name.equals("del_normal")) {
                        holder.itemIvEmoji.setImageResource(R.mipmap.bjmgf_message_chat_del_face_selected);
                        return false;
                    }
                    v.setBackgroundResource(R.drawable.bjmgf_message_chat_emoji_selected);
                    break;
                case MotionEvent.ACTION_CANCEL:
                    if (emoji.name.equals("del_normal")) {
                        try {
                            holder.itemIvEmoji.setImageBitmap(BitmapFactory.decodeStream(mContext.getAssets().open("face/emoji/Emoji_" + emoji.name + ".png")));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return false;
                    }
                    v.setBackgroundResource(R.drawable.bjmgf_message_chat_emoji);
                    break;
                case MotionEvent.ACTION_UP:
                    //捕获单击事件
                    if (oldLocationX == event.getX() && oldLocationY == event.getY()) {
                        if (mListener != null) {
                            if (emoji.name.equals("del_normal")) {
                                mListener.selectedBackSpace(emoji);
                            } else {
                                mListener.selectedEmoji(emoji);
                            }
                        }
                    }
                    if (emoji.name.equals("del_normal")) {
                        try {
                            holder.itemIvEmoji.setImageBitmap(BitmapFactory.decodeStream(mContext.getAssets().open("face/emoji/Emoji_" + emoji.name + ".png")));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return false;
                    }
                    v.setBackgroundResource(R.drawable.bjmgf_message_chat_emoji);
                    break;
                default:
                    break;
            }
            return false;
        });
        return convertView;
    }

    class ViewHolder {
        ImageView itemIvEmoji;
    }
}
