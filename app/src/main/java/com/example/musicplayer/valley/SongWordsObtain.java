package com.example.musicplayer.valley;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.musicplayer.bean.Lyric;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author ywww
 * @date 2020-11-22 15:42
 */
public class SongWordsObtain extends Application {
    private RequestQueue mQueue;
    private Context context;
    private Lyric lyric;
    private String url = "http://116.62.109.242:3000/lyric?id=";
    public SongWordsObtain(Context context,int id){
        this.context = context;
        this.url = this.url + id;
        initRequestQueue();
    }


    /**
     * 成功调用的监听器
     */
    private class JsonListener implements Response.Listener<JSONObject>{

        @Override
        public void onResponse(JSONObject response) {
            Gson gson = new Gson();
            try {
                JSONObject jsonObject = response.getJSONObject("lrc");
                lyric = gson.fromJson(String.valueOf(jsonObject),Lyric.class);
                Log.d("TAG_SONG_WORD",lyric.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 失败后调用的监听器
     */
    private class StrErrListener implements Response.ErrorListener{

        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d("TAG_songWordsObtain",error.toString());
        }
    }

    private void initRequestQueue() {
        this.mQueue = Volley.newRequestQueue(context);
    }

    /**
     * get请求
     */
    public void startGetJson() {
        // 默认情况下设成null为get方法,否则为post方法。
        JsonObjectRequest srReq = new JsonObjectRequest(url, null,
                new SongWordsObtain.JsonListener(), new SongWordsObtain.StrErrListener());
        // 控制是否缓存
        srReq.setShouldCache(false);
        mQueue.add(srReq);
    }
}
