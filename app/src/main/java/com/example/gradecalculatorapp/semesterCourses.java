package com.example.gradecalculatorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class semesterCourses extends AppCompatActivity {
    String semesterInformation = "";
    String SessionStudentID;
    CalculatorDatabaseHelper calculatorDatabaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semester_courses);

        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(semesterCourses.this);
        final SharedPreferences.Editor editor= sharedPreferences.edit();

        calculatorDatabaseHelper = new CalculatorDatabaseHelper(this);
        try{
            semesterInformation = getIntent().getExtras().getString("semesterInformation");
//            Log.d("In semesterInformation",semesterInformation);
        }catch (Exception e){

        }
        String[] semsterCourseIntentArray = semesterInformation.split(" ");
        SessionStudentID = sharedPreferences.getString("SessionStudentID","0");

        String seasonFromSemsterCourseIntent = semsterCourseIntentArray[0];
        String yearFromSemsterCourseIntent = semsterCourseIntentArray[1];
        ArrayList<String> studentCourseInfo = calculatorDatabaseHelper.getStudentCourseInfo(SessionStudentID,seasonFromSemsterCourseIntent,yearFromSemsterCourseIntent);
        ListView listViewSemesterCourses = findViewById(R.id.listViewSemesterCourses);
        final semesterCoursesAdapter semesterCoursesAdapter = new semesterCoursesAdapter(studentCourseInfo,semesterCourses.this,SessionStudentID);
        listViewSemesterCourses.setAdapter(semesterCoursesAdapter);


    }
}