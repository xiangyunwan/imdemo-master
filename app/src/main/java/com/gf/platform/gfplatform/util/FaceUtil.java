package com.gf.platform.gfplatform.util;

import android.os.Environment;

import com.gf.platform.gfplatform.entity.Face;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 表情工具类
 * Created by sunhaoyang on 2016/2/29.
 */
public class FaceUtil {

    /**
     * 将face json转换
     *
     * @param name
     * @return
     */
    public static List<Face> parseFace(String name) {
        List<Face> list = new ArrayList<>();
        boolean flag = true;
        try {
            String json = Util.getFileFromSD(Environment.getExternalStorageDirectory() + File.separator + "chat" + File.separator + name + File.separator + name + ".json");
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                Face face = new Face(item.getString("ID"), item.getString("Name"), item.getString("Show"), "", Environment.getExternalStorageDirectory() + File.separator + "chat" + File.separator + name + File.separator + name + "_" + item.getString("Name") + ".png", flag);
                list.add(face);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }


}
