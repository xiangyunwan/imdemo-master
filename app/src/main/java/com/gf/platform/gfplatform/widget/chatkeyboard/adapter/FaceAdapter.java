package com.gf.platform.gfplatform.widget.chatkeyboard.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gf.platform.gfplatform.R;
import com.gf.platform.gfplatform.entity.Face;
import com.gf.platform.gfplatform.util.Util;

import java.util.List;

public class FaceAdapter extends BaseAdapter {
    private List<Face> listFace = null;
    private Context context = null;


    public FaceAdapter(Context context, List<Face> mDatas) {
        listFace = mDatas;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listFace.size();
    }

    @Override
    public Object getItem(int position) {
        return listFace.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.bjmgf_message_chat_item_face, null);
            holder.view = (ImageView) convertView.findViewById(R.id.bjmgf_message_chat_face_iv);
            holder.tv = (TextView) convertView.findViewById(R.id.bjmgf_message_chat_face_tv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        Face data = listFace.get(position);
        if (data.isShow) {
            holder.tv.setText(data.show);
        } else {
            holder.tv.setVisibility(View.GONE);
        }
        try {
            Bitmap bitmap = Util.getImageThumbnail(data.path, (int)context.getResources().getDimension(R.dimen.gf_30dp), (int)context.getResources().getDimension(R.dimen.gf_30dp));
            holder.view.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    class ViewHolder {
        ImageView view;
        TextView tv;
    }
}