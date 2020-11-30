package com.example.gradecalculatorapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.TooManyListenersException;

public class addSemester extends AppCompatActivity {

    CalculatorDatabaseHelper calculatorDatabaseHelper;
    Spinner spinnerSemester,spinnerCourse;
    TextView textViewShowSelectedCourses;
    ArrayList<Integer> mUserItems = new ArrayList<>();
    Button SubmitChangesButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_semester);

        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(addSemester.this);
        final SharedPreferences.Editor editor= sharedPreferences.edit();

        calculatorDatabaseHelper = new CalculatorDatabaseHelper(this);
        String SessionStudentID = sharedPreferences.getString("SessionStudentID","0");
        SubmitChangesButton = (Button) findViewById(R.id.SubmitChangesButton);
        spinnerSemester = (Spinner)findViewById(R.id.spinnerSemester);
//        spinnerCourse = (Spinner)findViewById(R.id.spinnerCourse);

        textViewShowSelectedCourses = findViewById(R.id.textViewShowSelectedCourses);
        List<String> semesterArrayList = calculatorDatabaseHelper.populateSemestersInSpinner();
        ArrayAdapter<String> SemesterArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, semesterArrayList);
        Button btnCourse;

        SemesterArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSemester.setAdapter(SemesterArrayAdapter);
        spinnerSemester.setSelection(0, false);
//        spinnerCourse.setSelection(0, false);

        List<String> courseArrayList = calculatorDatabaseHelper.getStudentCourses(SessionStudentID);
        String[] listItems = new String[courseArrayList.size()];
        for(int i = 0;i<listItems.length;i++){
            listItems[i] = courseArrayList.get(i);
        }
        boolean[] checkedItems = new boolean[listItems.length];
        ArrayList<Integer> mUserItems = new ArrayList<>();

            btnCourse = findViewById(R.id.btnCourses);
            btnCourse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder newBuilder = new AlertDialog.Builder(addSemester.this);
                    newBuilder.setTitle("Select The Subjects");
                    newBuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                            if (isChecked) {
                                mUserItems.add(position);
                            } else {
                                mUserItems.remove((Integer.valueOf(position)));
                            }
                        }
                    });
                    newBuilder.setCancelable(false);
                    newBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            String item = "";
                            for (int i = 0; i < mUserItems.size(); i++) {
                                item = item + listItems[mUserItems.get(i)];
                                if (i != mUserItems.size() - 1) {
                                    item = item + ",";
                                }
                            }
                            textViewShowSelectedCourses.setText(item);
                        }
                    });

                    newBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });

                    newBuilder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            for (int i = 0; i < checkedItems.length; i++) {
                                checkedItems[i] = false;
                                mUserItems.clear();
                                textViewShowSelectedCourses.setText("");
                            }
                        }
                    });

                    AlertDialog dialogBox = newBuilder.create();
                    dialogBox.show();


                    Log.d("The Selected Courses ",textViewShowSelectedCourses.getText().toString());
                }
            });

        SubmitChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String selectedSemester = spinnerSemester.getSelectedItem().toString();
                if(selectedSemester != "Select Semester"){
                    String[] coursesArray = textViewShowSelectedCourses.getText().toString().split(",");
                    String[] selectedCourseID = new String[coursesArray.length];
                    for(int j = 0; j<coursesArray.length;j++){
                        String[] splitCourseArray =  coursesArray[j].split(" - ");
                        selectedCourseID[j] = splitCourseArray[0];
                    }
                    calculatorDatabaseHelper.setStudentCourseValue(selectedSemester,selectedCourseID, SessionStudentID);

                    Toast.makeText(addSemester.this, "Courses Saved Successfully", Toast.LENGTH_SHORT).show();
                    Intent homeIntent = new Intent(addSemester.this, homePage.class);
                    startActivity(homeIntent);
                }else{
                    Toast.makeText(addSemester.this, "Semester was not selected", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}