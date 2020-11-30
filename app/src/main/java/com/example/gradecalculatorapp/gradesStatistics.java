package com.example.gradecalculatorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

public class gradesStatistics extends AppCompatActivity {
    ArrayList<String> studentsGradesInfor;
//    ArrayList<String> label;
    CalculatorDatabaseHelper calculatorDatabaseHelper;
    String SessionStudentID;
    DataPoint[] data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades_statistics);

        BarChart barChart = findViewById(R.id.graph);
        
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(gradesStatistics.this);
        final SharedPreferences.Editor editor= sharedPreferences.edit();
        SessionStudentID = sharedPreferences.getString("SessionStudentID","0");

        calculatorDatabaseHelper = new CalculatorDatabaseHelper(this);

        studentsGradesInfor = calculatorDatabaseHelper.getStats(SessionStudentID);
        Log.d("studentsGradesInfor",studentsGradesInfor.toString());
        ArrayList<BarEntry> values = new ArrayList<>();

        for(int i=0; i<studentsGradesInfor.size(); i++){
            String[] details = studentsGradesInfor.get(i).split(" ; ");
            values.add(new BarEntry(i, (float) Double.parseDouble(details[1])));
            Log.d("CheckGet", String.valueOf(studentsGradesInfor.get(i)) + " - " +values.get(i));
        }

        BarDataSet barDataSet = new BarDataSet(values,"Courses");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);

        BarData barData = new BarData(barDataSet);


        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("");
        barChart.animateY(2000);
    }
}