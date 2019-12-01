package com.example.myapplication;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        for(int i = 0; i < data.rollArrays.size(); i++){
//            yData.add(new Entry(i, (float)data.rollArrays.get(i).getAvg()));
            yData.add(new Entry(i, (float)data.rollArrays.getRollInd(i).vavg));
        }

        ArrayList<String> xData = new ArrayList<>();
        for(int i = 0; i < data.rollArrays.size(); i++){
//            xData.add(data.rollArrays.get(i).getDate_beg_born());
            xData.add(String.valueOf(data.rollArrays.getRollInd(i).id));
        }

        LineDataSet lineDataSet = new LineDataSet(yData, "Ср.Скорость");
        lineDataSet.setColor(Color.argb(225, 233, 137, 11));
        lineDataSet.setLineWidth(3);

        LineData lineData = new LineData(lineDataSet);
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
}