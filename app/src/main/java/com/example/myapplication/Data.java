package com.example.myapplication;

import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TimerTask;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Data implements Runnable {
    Thread t;
    TextView tw;
    String url;
//    ArrayList<RollArray> rollArrays;
    RollArray rollArrays;
    Data(String url) {
        t = new Thread(this);
//        this.tw = tw;
        this.url = url;
//        this.rollArrays = new ArrayList<>();
        this.rollArrays = new RollArray();
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
//            RollArray rollArray = new RollArray();
//            if (rollArrays.isEmpty())
//                rollArray = new RollArray();
//            else
//                rollArray = rollArrays.get(
//                        rollArrays.size()-1);
            rollArrays.clear();
            for (int i = jsonArr.length()-1; i > 0 ; i--) {
                JSONObject obj = jsonArr.getJSONObject(i);
//                if (rollArray.isEmpty())
//                    rollArray.setDate_beg_born(obj.getString("date_beg_born"));
//                else if (!rollArray.getDate_beg_born().equals(obj.getString("date_beg_born"))) {
//                    rollArray.calcAvg();
//                    rollArrays.add(rollArray);
//                    rollArray = new RollArray(obj.getString("date_beg_born"));
//                }
//                else if (rollArray.getRollInd(rollArray.size()-1).id == obj.getInt("id")){
//                    continue;
//                }
                rollArrays.addRoll(new Roll(
                        obj.getString("attr_id"),
                        obj.getString("date_beg_born"),
                        obj.getString("e_born_unit"),
                        obj.getString("e_born_unit_code"),
                        obj.getString("id"),
                        obj.getString("m_unit_id"),
                        obj.getString("offsets"),
                        obj.getString("title"),
                        obj.getString("vavg")));
            }
//            if (!rollArray.isEmpty()) {
//                rollArray.calcAvg();
//                rollArrays.add(rollArray);
//            }
            rollArrays.calcAvg();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
