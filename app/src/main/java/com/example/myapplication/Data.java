package com.example.myapplication;

import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Data implements Runnable {
    Thread t;
    TextView tw;
    String url;

    Data(TextView tw, String url) {
        t = new Thread(this);
        this.tw = tw;
        this.url = url;
    }

    @Override
    public void run() {
        try {
            runSite();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void runSite() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String ans = response.body().string();
            JSONArray jsonArr = new JSONArray(ans);
            for (int i = 0; i < jsonArr.length(); ++i) {
                JSONObject obj = jsonArr.getJSONObject(i);
                System.out.println(obj.getString("date_beg_born"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
