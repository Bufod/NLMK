package com.example.myapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    LineChart chart;
    Data data;
    public String url = "http://13.69.157.123/get-last-data";
    Timer timer;
    MyTimerTask myTimerTask;
    private NotificationManager nm;
    private final int NOTIFICATION_ID = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nm = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        chart = (LineChart) findViewById(R.id.Chart);
        data = new Data(url);
        timer = new Timer();
        myTimerTask  = new MyTimerTask();
        timer.schedule(myTimerTask, 0, 1000);
    }
    private void drawLineChart(Data data){
        chart.clear();
        Description description = new Description();
        description.setText("Динамика");
        chart.setDescription(description);
        ArrayList<Entry>  yData = new ArrayList<>();
//        ArrayList<Entry>  yDataWar = new ArrayList<>();
        for(int i = 0; i < data.rollArrays.size(); i++){
//            yData.add(new Entry(i, (float)data.rollArrays.get(i).getAvg()));
            float vavg = (float)data.rollArrays.getRollInd(i).vavg;
//            if(vavg < 123)
//                yDataWar.add(new Entry(i, vavg));
//            else
                yData.add(new Entry(i, vavg));
        }

        ArrayList<String> xData = new ArrayList<>();
        for(int i = 0; i < data.rollArrays.size(); i++){
//            xData.add(data.rollArrays.get(i).getDate_beg_born());
            xData.add(String.valueOf(data.rollArrays.getRollInd(i).id));
            if (data.rollArrays.getRollInd(i).vavg < 123)
                showNotif();
        }

        LineDataSet lineDataSet1 = new LineDataSet(yData, "Ср.Скорость");
        lineDataSet1.setColor(Color.argb(225, 233, 137, 11));
        lineDataSet1.setLineWidth(3);
        lineDataSet1.setCircleColor(Color.BLACK);
        lineDataSet1.setCircleHoleColor(Color.WHITE);

//        LineDataSet lineDataSet2 = new LineDataSet(yDataWar, "Ср.Скорость");
//        lineDataSet1.setCircleColor(Color.BLACK);
//        lineDataSet1.setCircleHoleColor(Color.WHITE);

        LineData lineData = new LineData(lineDataSet1);
        lineData.setValueTextSize(13f);
        lineData.setValueTextColor(Color.BLACK);
        lineData.setDrawValues(false);
        chart.setData(lineData);
        XAxis x = chart.getXAxis();
        x.setValueFormatter(new IndexAxisValueFormatter(xData));
        x.setPosition(XAxis.XAxisPosition.BOTTOM);
        x.setLabelCount(100);
        x.setLabelRotationAngle(-80);
        chart.invalidate();

    }
    class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            data.t.start();
            try {
                data.t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for(int i = 0; i < data.rollArrays.size(); i++)
//                System.out.println(data.rollArrays.get(i).size() + " " + data.rollArrays.get(i).getAvg());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    drawLineChart(data);
                }
            });
        }
    }

    public void showNotif(){
        Notification.Builder builder = new Notification.Builder(getApplicationContext());
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE);
        builder
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker("Критичная скорость")
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setContentTitle("Уведомление")
                .setContentText("Скорость ниже 123");

        Notification notification = builder.build();
        nm.notify(NOTIFICATION_ID, notification);
    }
}