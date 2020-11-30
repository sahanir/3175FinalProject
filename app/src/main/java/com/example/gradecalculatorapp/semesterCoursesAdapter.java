package com.example.gradecalculatorapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class semesterCoursesAdapter extends BaseAdapter {
    List<String> studentCourseInfo;
    Context context;
    CalculatorDatabaseHelper calculatorDatabaseHelper;
    String SessionStudentID;

    public semesterCoursesAdapter(List<String> studentCourseInfo, semesterCourses semesterCourses, String SessionStudentID) {
        this.studentCourseInfo = studentCourseInfo;
        this.context = semesterCourses;
        this.SessionStudentID= SessionStudentID;
    }
    @Override
    public int getCount() {
        return studentCourseInfo.size();
    }

    @Override
    public Object getItem(int i) {
        return studentCourseInfo.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        calculatorDatabaseHelper = new CalculatorDatabaseHelper(context);
        if (view == null){
            LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
            view = layoutInflater.inflate(R.layout.semestercourseslayout,viewGroup,false);
        }
        TextView textViewCourseName = view.findViewById(R.id.textViewInstructorName);
        TextView textViewCourseID = view.findViewById(R.id.textViewCourseID);
        TextView textViewGrade = view.findViewById(R.id.textViewCriteriaValue);
        TextView textViewGPA = view.findViewById(R.id.textViewWeightageValue);
        TextView textViewInstructor = view.findViewById(R.id.textViewTotalMarksValue);



        String[] displayingIntentSplit = studentCourseInfo.get(i).split(" ; ");
        Double percentage = calculatorDatabaseHelper.getPercentage(SessionStudentID,displayingIntentSplit[0]);
        String[] gradeGPA = calculatorDatabaseHelper.getGradeGPA(percentage);
        Log.d("gradeGPA",gradeGPA.toString());

        Log.d("Inside Adapter",percentage+"-"+gradeGPA[1]+"-"+gradeGPA[0]);
        String instructorName = calculatorDatabaseHelper.getInstructorName(displayingIntentSplit[2]);
        textViewCourseName.setText(displayingIntentSplit[1]);
        textViewCourseID.setText(displayingIntentSplit[0]);
        textViewGrade.setText(gradeGPA[1]);
        textViewGPA.setText(gradeGPA[0]);
        textViewInstructor.setText(instructorName);

        Button btnCriteria = view.findViewById(R.id.btnCriteria);

        btnCriteria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editCourseIntent = new Intent(context, editCourse.class);
                Bundle myBundle = new Bundle();
                myBundle.putString("textViewCourseID",displayingIntentSplit[0]);
                myBundle.putString("textViewCourseName",displayingIntentSplit[1]);
                myBundle.putString("textViewInstructor",displayingIntentSplit[2]);
                myBundle.putString("instructorName",textViewInstructor.getText().toString());
                editCourseIntent.putExtras(myBundle);
                context.startActivity(editCourseIntent);
            }

        });
        return view;
    }
}
