package com.example.gradecalculatorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class editGrades extends AppCompatActivity {
    CalculatorDatabaseHelper calculatorDatabaseHelper;
    String Criteria, WeightageTotal, TotalMarks, MarksObtained, WeightageAcheived, GradeID;
    String selectedCourseID, selectedCourseName, selectedCourseInstructor, instructorName1;
    Spinner spinnerUpgradeCriteria;
    Button btnUpgradeGrade;
    EditText editTextUpgradeCriteriaPercentage, editTextUpgradeTotalMarks, editTextUpgradeMarksObtained;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_grades);

        calculatorDatabaseHelper = new CalculatorDatabaseHelper(this);
        spinnerUpgradeCriteria = (Spinner) findViewById(R.id.spinnerUpgradeCriteria);
        btnUpgradeGrade = (Button) findViewById(R.id.btnUpgradeGrade);
        editTextUpgradeCriteriaPercentage = (EditText) findViewById(R.id.editTextUpgradeCriteriaPercentage);
        editTextUpgradeTotalMarks = (EditText) findViewById(R.id.editTextUpgradeTotalMarks);
        editTextUpgradeMarksObtained = (EditText) findViewById(R.id.editTextUpgradeMarksObtained);

        List<String> criteriaArrayList = calculatorDatabaseHelper.populateCriteriaInSpinner();
        ArrayAdapter<String> CriteriaArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, criteriaArrayList);
        CriteriaArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUpgradeCriteria.setAdapter(CriteriaArrayAdapter);
        spinnerUpgradeCriteria.setSelection(0, false);

        try {
            Criteria = getIntent().getExtras().getString("Criteria");
            WeightageTotal = getIntent().getExtras().getString("WeightageTotal");
            TotalMarks = getIntent().getExtras().getString("TotalMarks");
            MarksObtained = getIntent().getExtras().getString("MarksObtained");
            WeightageAcheived = getIntent().getExtras().getString("WeightageAcheived");
            GradeID = getIntent().getExtras().getString("GradeID");
            selectedCourseID = getIntent().getExtras().getString("selectedCourseID");
            selectedCourseName = getIntent().getExtras().getString("selectedCourseName");
            selectedCourseInstructor = getIntent().getExtras().getString("selectedCourseInstructor");
            instructorName1 = getIntent().getExtras().getString("instructorName1");

        } catch (Exception e) {

        }

        spinnerUpgradeCriteria.setSelection(criteriaArrayList.indexOf(Criteria));
        editTextUpgradeCriteriaPercentage.setText(WeightageTotal);
        editTextUpgradeTotalMarks.setText(TotalMarks);
        editTextUpgradeMarksObtained.setText(MarksObtained);

        btnUpgradeGrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                WeightageTotal = editTextUpgradeCriteriaPercentage.getText().toString().trim();
                if (WeightageTotal.isEmpty()) {
                    Toast.makeText(editGrades.this, "Criteria Percentage can't be Empty", Toast.LENGTH_SHORT).show();
                } else {
                    TotalMarks = editTextUpgradeTotalMarks.getText().toString().trim();
                    if (TotalMarks.isEmpty()) {
                        Toast.makeText(editGrades.this, "Total Marks can't be Empty", Toast.LENGTH_SHORT).show();
                    } else {
                        MarksObtained = editTextUpgradeMarksObtained.getText().toString().trim();
                        if (MarksObtained.isEmpty()) {
                            Toast.makeText(editGrades.this, "Marks Obtained can't be Empty", Toast.LENGTH_SHORT).show();
                        } else {
                            String gradeCriteriaID = calculatorDatabaseHelper.getGradeCriteriaID(Criteria);

                            Toast.makeText(editGrades.this, GradeID + "-" + gradeCriteriaID + "-" + MarksObtained + "-" + TotalMarks + "-" + WeightageTotal, Toast.LENGTH_SHORT).show();
                            calculatorDatabaseHelper.upgradeGrade(GradeID, gradeCriteriaID, MarksObtained, TotalMarks, WeightageTotal);
                            Log.d("gradeCriteriaID Updated", gradeCriteriaID);

                            Intent editCoursesIntent = new Intent(editGrades.this, editCourse.class);
                            Bundle myBundle = new Bundle();
                            myBundle.putString("selectedCourseID", selectedCourseID);
                            myBundle.putString("selectedCourseName", selectedCourseName);
                            myBundle.putString("selectedCourseInstructor", selectedCourseInstructor);
                            myBundle.putString("instructorName1", instructorName1);

                            editCoursesIntent.putExtras(myBundle);
                            startActivity(editCoursesIntent);
                        }
                    }
                }
            }
        });

        Toast.makeText(this, GradeID, Toast.LENGTH_SHORT).show();
    }
}