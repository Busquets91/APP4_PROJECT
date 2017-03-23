package com.example.alexispointurier.app4_eolienne;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class Home extends AppCompatActivity {

    LineGraphSeries<DataPoint> series;
    GraphView graph;

    protected void generateGraph(GraphView g , int power){
        g.removeAllSeries();
        double x,y;
        x = 0;
        series = new LineGraphSeries<DataPoint>();
        for(int i = 0; i<500; i++){
            x += 0.1;
            y = Math.sin(x/power);
            series.appendData(new DataPoint(x,y), true, 500);
        }
        g.addSeries(series);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Graph
        graph = (GraphView)findViewById(R.id.graphPower);
        generateGraph(graph, 3);

        //SeekBar
        SeekBar seekBar = (SeekBar)findViewById(R.id.powerChange);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                generateGraph(graph, progress+1);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
