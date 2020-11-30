package com.example.gradecalculatorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class loginActivity extends AppCompatActivity {

    CalculatorDatabaseHelper calculatorDatabaseHelper;

    EditText editTextLoginPassword,editTextLoginStudentID;
    Button loginCreateAccountSubmitButton,loginLoginSubmitButton;
    String StudentID,StudentPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        calculatorDatabaseHelper = new CalculatorDatabaseHelper(this);

        editTextLoginStudentID = (EditText)findViewById(R.id.editTextLoginStudentID);
        editTextLoginPassword = (EditText)findViewById(R.id.editTextLoginPassword);
        loginLoginSubmitButton =(Button)findViewById(R.id.loginSubmitButton);
        loginCreateAccountSubmitButton =(Button)findViewById(R.id.CreateAccountSubmitButton);

        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(loginActivity.this);
        final SharedPreferences.Editor editor= sharedPreferences.edit();

        String key = sharedPreferences.getString("SessionStudentID","0");

        loginLoginSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StudentID = editTextLoginStudentID.getText().toString();
                StudentPassword = editTextLoginPassword.getText().toString();
                boolean loginValidation = calculatorDatabaseHelper.checkLoginPassword(StudentID,StudentPassword);
                if(loginValidation){
                    Toast.makeText(loginActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    editor.putString("SessionStudentID", StudentID);
                    Intent homeIntent = new Intent(loginActivity.this, homePage.class);
                    startActivity(homeIntent);
                    editor.commit();
                }else{
                    Toast.makeText(loginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();

                }
            }
        });

        loginCreateAccountSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent createIntent = new Intent(loginActivity.this, createAccount.class);
                startActivity(createIntent);
            }
        });
    }
}