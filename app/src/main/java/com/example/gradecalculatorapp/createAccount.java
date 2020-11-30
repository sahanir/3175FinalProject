package com.example.gradecalculatorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class createAccount extends AppCompatActivity {

    CalculatorDatabaseHelper calculatorDatabaseHelper;

    EditText editTextStudentID,editTextStudentEmailAddress,editTextStudentName,editTextPassword,editTextPassword2;
    Spinner spinnerProgram,spinnerStream,spinnerMiniStream;
    Button CreateAccountSubmitButton,loginSubmitButton;
    int StudentID;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.+[a-z]+";
    String StudentName,StudentPassword,StudentPassword2,StudentEmail,StudentProgramID = "",StudentStreamID = "",StudentMiniStreamID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        calculatorDatabaseHelper = new CalculatorDatabaseHelper(this);

        editTextStudentID = (EditText)findViewById(R.id.editTextLoginStudentID);
        editTextStudentEmailAddress = (EditText)findViewById(R.id.editTextStudentEmailAddress);
        editTextStudentName = (EditText)findViewById(R.id.editTextStudentName);
        editTextPassword = (EditText)findViewById(R.id.editTextLoginPassword);
        editTextPassword2 = (EditText)findViewById(R.id.editTextPassword2);

        spinnerProgram = (Spinner)findViewById(R.id.spinnerProgram);
        spinnerStream = (Spinner)findViewById(R.id.spinnerStream);
        spinnerMiniStream = (Spinner)findViewById(R.id.spinnerMiniStream);

        spinnerProgram.setSelection(0, false);
        spinnerStream.setSelection(0, false);
        spinnerMiniStream.setSelection(0, false);

        CreateAccountSubmitButton =(Button)findViewById(R.id.CreateAccountSubmitButton);
        loginSubmitButton =(Button)findViewById(R.id.loginSubmitButton);

        List<String> programArrayList = calculatorDatabaseHelper.populateProgramsInSpinner();
        ArrayAdapter<String> ProgramArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, programArrayList);
        ProgramArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProgram.setAdapter(ProgramArrayAdapter);

        spinnerProgram.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedProgram = adapterView.getItemAtPosition(i).toString();
//                Toast.makeText(createAccount.this, ""+selectedProgram, Toast.LENGTH_SHORT).show();
                StudentProgramID = selectedProgram.substring(0,selectedProgram.indexOf(' '));
                if(selectedProgram.equals("CSISPBD (COMPUTER AND INFORMATION SYSTEMS (POST-BACCALAUREATE DIPLOMA))")){

                    spinnerStream.setVisibility(View.VISIBLE);
                    List<String> streamArrayList = calculatorDatabaseHelper.populateStreamsInSpinner();
                    ArrayAdapter<String> StreamArrayAdapter = new ArrayAdapter<String>(createAccount.this, android.R.layout.simple_spinner_item, streamArrayList);
                    StreamArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerStream.setAdapter(StreamArrayAdapter);
                    spinnerStream.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            String selectedStream = adapterView.getItemAtPosition(i).toString();

//                            Toast.makeText(createAccount.this, ""+selectedStream, Toast.LENGTH_SHORT).show();

                            if(selectedStream.equals("DASTREAM (DATA ANALYTICS)")){
                                StudentStreamID = selectedStream.substring(0,selectedStream.indexOf(' '));
                                spinnerMiniStream.setVisibility(View.VISIBLE);
                                List<String> MinistreamArrayList = calculatorDatabaseHelper.populateMiniStreamsInSpinner();
                                ArrayAdapter<String> MinistreamArrayAdapter = new ArrayAdapter<String>(createAccount.this, android.R.layout.simple_spinner_item, MinistreamArrayList);
                                MinistreamArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinnerMiniStream.setAdapter(MinistreamArrayAdapter);

                                spinnerMiniStream.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        String selectedMiniStream = adapterView.getItemAtPosition(i).toString();
                                        StudentMiniStreamID = selectedMiniStream.substring(0,selectedMiniStream.indexOf(' '));
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {
                                        StudentMiniStreamID = "CSISMS";
                                    }
                                });
                            }else{
                                StudentStreamID = "ETSTREAM";
                                StudentMiniStreamID = "N/A";
                                spinnerMiniStream.setVisibility(View.INVISIBLE);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parentView) {
                            if(StudentProgramID.equals("CSISPBD")){
                                StudentStreamID="ETSTREAM";
                            }
                            // your code here
                            spinnerMiniStream.setVisibility(View.INVISIBLE);
                        }

                    });
                }else{
                    StudentStreamID = "N/A";
                    StudentMiniStreamID = "N/A";
                    spinnerStream.setVisibility(View.INVISIBLE);
                    spinnerMiniStream.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                if(StudentProgramID.equals("") || StudentProgramID.equals("Select")){
                    StudentProgramID="CSISDIP";
                    if(StudentStreamID.equals("") || StudentStreamID.equals("Select")){
                        StudentStreamID="N/A";
                        if(StudentMiniStreamID.equals("") || StudentMiniStreamID.equals("Select")){
                            StudentMiniStreamID="N/A";
                        }
                    }else{
                        StudentStreamID="ETSTREAM";
                    }
                }
                // your code here
                spinnerStream.setVisibility(View.INVISIBLE);
                spinnerMiniStream.setVisibility(View.INVISIBLE);
            }

        });

        CreateAccountSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String studentIDString = editTextStudentID.getText().toString();
                if (studentIDString.length() == 9){
                    StudentID = Integer.parseInt(studentIDString);
                    if(editTextStudentEmailAddress.getText().toString().matches(emailPattern) && editTextStudentEmailAddress.getText().toString().length() > 0){
                        StudentEmail = editTextStudentEmailAddress.getText().toString();
                        if(editTextStudentName.getText().toString().length() > 0){
                            StudentName = editTextStudentName.getText().toString();
                            if((editTextPassword.getText().toString().equals(editTextPassword2.getText().toString())) && editTextPassword.getText().toString().length() >= 8){
                                StudentPassword = editTextPassword.getText().toString();

                                if(StudentProgramID.equals("") || StudentProgramID.equals("Select")) {
                                    StudentProgramID = "CSISDIP";
                                    if (StudentStreamID.equals("") || StudentStreamID.equals("Select")) {
                                        StudentStreamID = "N/A";
                                        if (StudentMiniStreamID.equals("") || StudentMiniStreamID.equals("Select")) {
                                            StudentMiniStreamID = "N/A";
                                        }
                                    }
                                }
                                Toast.makeText(createAccount.this, StudentID + "\n" +StudentEmail + "\n" +StudentName + "\n" +StudentPassword + "\n" +StudentProgramID + "\n" +StudentStreamID + "\n" +StudentMiniStreamID , Toast.LENGTH_SHORT).show();
                                calculatorDatabaseHelper.createAccountInsertStudentData(StudentID,StudentPassword,StudentName,StudentEmail,StudentProgramID,StudentStreamID,StudentMiniStreamID);
                                Intent intent = new Intent(createAccount.this, MainActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(createAccount.this, "Passwords dont match or less tha 8 characters long", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(createAccount.this, "Invalid Student Name", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(createAccount.this, "Invalid Email ID", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(createAccount.this, "Invalid Student ID", Toast.LENGTH_SHORT).show();
                }

            }
        });

        loginSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(createAccount.this, loginActivity.class);
                startActivity(loginIntent);
            }
        });
    }
}