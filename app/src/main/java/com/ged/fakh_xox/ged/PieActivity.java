package com.ged.fakh_xox.ged;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;

public class PieActivity extends AppCompatActivity {
    private static String TAG="PieActivity";
    private String IP="http://10.0.2.2:18080/ged-web/rest/reclamation/";
    private int[] yData={};
    private String[] xData={"Treated","Untreated","InProgress"};
    PieChart pieChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie);
        Log.d(TAG, "onCreate: Starting to create Chart");
        pieChart=(PieChart) findViewById(R.id.idPieChart);
        pieChart.setContentDescription("Filtre des Reclamations par Etats");
        pieChart.setRotationEnabled(true);
        pieChart.setHoleRadius(25f);
        pieChart.setDrawEntryLabels(true);
        pieChart.setTransparentCircleAlpha(0);

        addDataSet();

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Log.d(TAG, "onValueSelected: Value Selected from Chart");
                Log.d(TAG, "onValueSelected: "+e.toString());
                Log.d(TAG, "onValueSelected: "+h.toString());

                int pos1=e.toString().indexOf("(sum): ");
                String nbr = e.toString().substring(pos1 +7 );

                for(int i=0; i<yData.length;i++){
                    if(yData[i]==Float.parseFloat(nbr)){
                        pos1=i;
                        break;
                    }
                }
                String rec=xData[pos1 +1];
                Toast.makeText(PieActivity.this, "Reclamation" + rec +"\n" + "Total" + nbr,Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected() {

            }
        });
    }

    private void addDataSet() {
        Log.d(TAG, "addDataSet started");
        ArrayList<PieEntry>yEntrys = new ArrayList<>();
        ArrayList<String>xEntrys=new ArrayList<>();

        for(int i=0; i<yData.length; i++) {
            yEntrys.add(new PieEntry(yData[i], i));
        }

            for(int i=0; i<xData.length; i++){
            xEntrys.add(xData[i]);

        }
        PieDataSet pieDataSet= new PieDataSet(yEntrys,"Etat des Reclamations");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);


        //AddColors
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.BLUE);
        colors.add(Color.GREEN);
        colors.add(Color.RED);

        //Legend
        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);


        PieData pieData=new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }
}
