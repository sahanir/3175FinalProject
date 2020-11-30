package com.example.gradecalculatorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class editCourse extends AppCompatActivity {
    Spinner spinnerInstructor,spinnerCriteria;
    String selectedCourseID, selectedCourseName, selectedCourseInstructor,criteriaPercentage,totalMarks,marksObtained;
    String SessionStudentID;
    String instructorName1;
    CalculatorDatabaseHelper calculatorDatabaseHelper;
    Button btnSaveCriteria;
    EditText editTextCriteriaPercentage,editTextTotalMarks,editTextMarksObtained;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);

        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(editCourse.this);
        final SharedPreferences.Editor editor= sharedPreferences.edit();

        spinnerInstructor = (Spinner)findViewById(R.id.spinnerInstructor);
        spinnerCriteria = (Spinner)findViewById(R.id.spinnerCriteria);
        btnSaveCriteria = (Button)findViewById(R.id.btnSaveCriteria);

        editTextCriteriaPercentage= (EditText)findViewById(R.id.editTextCriteriaPercentage);
        editTextTotalMarks= (EditText)findViewById(R.id.editTextTotalMarks);
        editTextMarksObtained= (EditText)findViewById(R.id.editTextMarksObtained);

        SessionStudentID = sharedPreferences.getString("SessionStudentID","0");

        calculatorDatabaseHelper = new CalculatorDatabaseHelper(this);

        try{
            selectedCourseID = getIntent().getExtras().getString("textViewCourseID");
            selectedCourseName = getIntent().getExtras().getString("textViewCourseName");
            selectedCourseInstructor = getIntent().getExtras().getString("textViewInstructor");
            instructorName1 = getIntent().getExtras().getString("instructorName");
        }catch (Exception e){

        }

        List<String> instructorArrayList = calculatorDatabaseHelper.populateInstructorInSpinner();
        int positionInstructor = instructorArrayList.indexOf(instructorName1);
//        Log.d("instructorArrayList",instructorArrayList.toString());
        List<String> criteriaArrayList = calculatorDatabaseHelper.populateCriteriaInSpinner();
        ArrayAdapter<String> InstructorArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, instructorArrayList);
        ArrayAdapter<String> CriteriaArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, criteriaArrayList);

        InstructorArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerInstructor.setAdapter(InstructorArrayAdapter);
        spinnerInstructor.setSelection(0, false);
        spinnerInstructor.setSelection(positionInstructor);

        CriteriaArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCriteria.setAdapter(CriteriaArrayAdapter);
        spinnerCriteria.setSelection(0, false);

        ArrayList<String> gradesInfo = calculatorDatabaseHelper.getGrades(SessionStudentID,selectedCourseID);
//        Log.d("gradesInfo",gradesInfo.toString());
        ListView listViewCriteria = findViewById(R.id.listViewCriteria);
        final editCourseAdapter editCourseAdapter = new editCourseAdapter(gradesInfo);
        listViewCriteria.setAdapter(editCourseAdapter);

        listViewCriteria.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0) {
                    Intent editGradesIntent = new Intent(editCourse.this, editGrades.class);
                    Bundle myBundle = new Bundle();
                    myBundle.putString("Criteria", (String) ((TextView) view.findViewById(R.id.textViewCriteriaValue)).getText());
                    myBundle.putString("WeightageTotal", (String) ((TextView) view.findViewById(R.id.textViewWeightageValue)).getText());
                    myBundle.putString("TotalMarks", (String) ((TextView) view.findViewById(R.id.textViewTotalMarksValue)).getText());
                    myBundle.putString("MarksObtained", (String) ((TextView) view.findViewById(R.id.textViewMarksObtainedValue)).getText());
                    myBundle.putString("WeightageAcheived", (String) ((TextView) view.findViewById(R.id.textViewAcheivedPercentageValue)).getText());
                    myBundle.putString("GradeID", (String) ((TextView) view.findViewById(R.id.textViewGradeID)).getText());
                    myBundle.putString("selectedCourseID", selectedCourseID);
                    myBundle.putString("selectedCourseName", selectedCourseName);
                    myBundle.putString("selectedCourseInstructor", selectedCourseInstructor);
                    myBundle.putString("instructorName1", instructorName1);

                    editGradesIntent.putExtras(myBundle);
                    startActivity(editGradesIntent);
                }
            }
        });



        btnSaveCriteria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                criteriaPercentage = editTextCriteriaPercentage.getText().toString().trim();
                if(criteriaPercentage.isEmpty()){
                    Toast.makeText(editCourse.this, "Criteria Percentage can't be Empty", Toast.LENGTH_SHORT).show();
                }else{
                    totalMarks = editTextTotalMarks.getText().toString().trim();
                    if(totalMarks.isEmpty()){
                        Toast.makeText(editCourse.this, "Total Marks can't be Empty", Toast.LENGTH_SHORT).show();
                    }else{
                        marksObtained = editTextMarksObtained.getText().toString().trim();
                        if(marksObtained.isEmpty()){
                            Toast.makeText(editCourse.this, "Marks Obtained can't be Empty", Toast.LENGTH_SHORT).show();
                        }else {
                            calculatorDatabaseHelper.insertGrades(SessionStudentID,selectedCourseID,
                                    spinnerInstructor.getSelectedItem().toString(),
                                    spinnerCriteria.getSelectedItem().toString(),criteriaPercentage,
                                    totalMarks,marksObtained);
                            ArrayList<String> gradesInfo = calculatorDatabaseHelper.getGrades(SessionStudentID,selectedCourseID);
//                            Toast.makeText(editCourse.this, gradesInfo.toString(), Toast.LENGTH_SHORT).show();
                            ListView listViewCriteria = findViewById(R.id.listViewCriteria);
                            final editCourseAdapter editCourseAdapter = new editCourseAdapter(gradesInfo);
                            listViewCriteria.setAdapter(editCourseAdapter);
                        }
                    }
                }

            }
        });




    }
}