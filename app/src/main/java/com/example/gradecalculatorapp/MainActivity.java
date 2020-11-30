package com.example.gradecalculatorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    CalculatorDatabaseHelper calculatorDatabaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Log.d("CALLING HELPER CLASS","WORKED");

        calculatorDatabaseHelper = new CalculatorDatabaseHelper(this);

        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        SharedPreferences.Editor editor= sharedPreferences.edit();

        String DBCreatekey = sharedPreferences.getString("DBCREATED","default");
        String Loginkey = sharedPreferences.getString("SessionStudentID","0");
//        Log.d("Key Value" ,"Before Login" + key);
        if (!DBCreatekey.equals("yes")){
            insertData();
//            Log.d("Key Value" ,"In if Login" + key);
            editor.putString("DBCREATED", "yes");
            editor.commit();
        }
        if(Loginkey.equals("0")){
            Intent loginIntent = new Intent(MainActivity.this, loginActivity.class);
            startActivity(loginIntent);
        }else{
            Intent homeIntent = new Intent(MainActivity.this, homePage.class);
            startActivity(homeIntent);
            Toast.makeText(MainActivity.this, "To Main Page", Toast.LENGTH_SHORT).show();
        }


    }

    private void insertData() {
        insertGPA();
        insertInstructors();
        insertCourse();
        insertPrerequisite();
        insertSemester();
        insertCriteria();
        insertProgram();
        insertProgramCore();
        insertStream();
        insertStreamCore();
        insertMinistream();
        insertMinistreamCore();
    }

    private void insertGPA() {
        List<String[]> GPAList=new ArrayList<>();
        InputStream inputStream= getResources().openRawResource(R.raw.gpa);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try{
            String csvLine;
            while((csvLine = reader.readLine())!= null){
                String[] eachRecord = csvLine.split(",");
                GPAList.add(eachRecord);
            }
//            Toast.makeText(SplashActivity.this, "GPA FILE READ", Toast.LENGTH_LONG).show();
        }catch(Exception e){
            throw new RuntimeException("Error reading CSV File" + e);
        } finally {
            try{
                inputStream.close();
            }catch (IOException e){
                throw new RuntimeException("Error Closing Input Stream" + e);
            }
//            String toastMsg = "";
        calculatorDatabaseHelper.insertGPA(GPAList);
        }
    }
    private void insertInstructors() {
        List<String[]> InstructorList=new ArrayList<>();
        InputStream inputStream= getResources().openRawResource(R.raw.instructors);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try{
            String csvLine;
            while((csvLine = reader.readLine())!= null){
                String[] eachRecord = csvLine.split(",");
                InstructorList.add(eachRecord);
            }
//            Toast.makeText(SplashActivity.this, "GPA FILE READ", Toast.LENGTH_LONG).show();
        }catch(Exception e){
            throw new RuntimeException("Error reading CSV File" + e);
        } finally {
            try{
                inputStream.close();
            }catch (IOException e){
                throw new RuntimeException("Error Closing Input Stream" + e);
            }
//            String toastMsg = "";
            calculatorDatabaseHelper.insertInstructors(InstructorList);

        }
    }

    private void insertCourse() {
        List<String[]> CourseList=new ArrayList<>();
        InputStream inputStream= getResources().openRawResource(R.raw.course);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try{
            String csvLine;
            while((csvLine = reader.readLine())!= null){
                String[] eachRecord = csvLine.split(",");
                CourseList.add(eachRecord);
            }
//            Toast.makeText(SplashActivity.this, "GPA FILE READ", Toast.LENGTH_LONG).show();
        }catch(Exception e){
            throw new RuntimeException("Error reading CSV File" + e);
        } finally {
            try{
                inputStream.close();
            }catch (IOException e){
                throw new RuntimeException("Error Closing Input Stream" + e);
            }
//            String toastMsg = "";
            calculatorDatabaseHelper.insertCourse(CourseList);

        }
    }

    private void insertPrerequisite() {
        List<String[]> PrerequisiteList=new ArrayList<>();
        InputStream inputStream= getResources().openRawResource(R.raw.prerequisite);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try{
            String csvLine;
            while((csvLine = reader.readLine())!= null){
                String[] eachRecord = csvLine.split(",");
                PrerequisiteList.add(eachRecord);
            }
//            Toast.makeText(SplashActivity.this, "GPA FILE READ", Toast.LENGTH_LONG).show();
        }catch(Exception e){
            throw new RuntimeException("Error reading CSV File" + e);
        } finally {
            try{
                inputStream.close();
            }catch (IOException e){
                throw new RuntimeException("Error Closing Input Stream" + e);
            }
//            String toastMsg = "";
            calculatorDatabaseHelper.insertPrerequisite(PrerequisiteList);

        }
    }

    private void insertSemester() {
        List<String[]> SemesterList=new ArrayList<>();
        InputStream inputStream= getResources().openRawResource(R.raw.semester);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try{
            String csvLine;
            while((csvLine = reader.readLine())!= null){
                String[] eachRecord = csvLine.split(",");
                SemesterList.add(eachRecord);
            }
//            Toast.makeText(SplashActivity.this, "GPA FILE READ", Toast.LENGTH_LONG).show();
        }catch(Exception e){
            throw new RuntimeException("Error reading CSV File" + e);
        } finally {
            try{
                inputStream.close();
            }catch (IOException e){
                throw new RuntimeException("Error Closing Input Stream" + e);
            }
//            String toastMsg = "";
            calculatorDatabaseHelper.insertSemester(SemesterList);

        }
    }

    private void insertCriteria() {
        List<String[]> CriteriaList=new ArrayList<>();
        InputStream inputStream= getResources().openRawResource(R.raw.criteria);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try{
            String csvLine;
            while((csvLine = reader.readLine())!= null){
                String[] eachRecord = csvLine.split(",");
                CriteriaList.add(eachRecord);
            }
//            Toast.makeText(SplashActivity.this, "GPA FILE READ", Toast.LENGTH_LONG).show();
        }catch(Exception e){
            throw new RuntimeException("Error reading CSV File" + e);
        } finally {
            try{
                inputStream.close();
            }catch (IOException e){
                throw new RuntimeException("Error Closing Input Stream" + e);
            }
//            String toastMsg = "";
            calculatorDatabaseHelper.insertCriteria(CriteriaList);

        }
    }

    private void insertProgram() {
        List<String[]> ProgramList=new ArrayList<>();
        InputStream inputStream= getResources().openRawResource(R.raw.programs);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try{
            String csvLine;
            while((csvLine = reader.readLine())!= null){
                String[] eachRecord = csvLine.split(",");
                ProgramList.add(eachRecord);
            }
//            Toast.makeText(SplashActivity.this, "GPA FILE READ", Toast.LENGTH_LONG).show();
        }catch(Exception e){
            throw new RuntimeException("Error reading CSV File" + e);
        } finally {
            try{
                inputStream.close();
            }catch (IOException e){
                throw new RuntimeException("Error Closing Input Stream" + e);
            }
//            String toastMsg = "";
            calculatorDatabaseHelper.insertProgram(ProgramList);

        }
    }

    private void insertProgramCore() {
        List<String[]> ProgramCoreList=new ArrayList<>();
        InputStream inputStream= getResources().openRawResource(R.raw.programcore);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try{
            String csvLine;
            while((csvLine = reader.readLine())!= null){
                String[] eachRecord = csvLine.split(",");
                ProgramCoreList.add(eachRecord);
            }
//            Toast.makeText(SplashActivity.this, "GPA FILE READ", Toast.LENGTH_LONG).show();
        }catch(Exception e){
            throw new RuntimeException("Error reading CSV File" + e);
        } finally {
            try{
                inputStream.close();
            }catch (IOException e){
                throw new RuntimeException("Error Closing Input Stream" + e);
            }
//            String toastMsg = "";
            calculatorDatabaseHelper.insertProgramCore(ProgramCoreList);

        }
    }

    private void insertStream() {
        List<String[]> StreamList=new ArrayList<>();
        InputStream inputStream= getResources().openRawResource(R.raw.stream);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try{
            String csvLine;
            while((csvLine = reader.readLine())!= null){
                String[] eachRecord = csvLine.split(",");
                StreamList.add(eachRecord);
            }
//            Toast.makeText(SplashActivity.this, "GPA FILE READ", Toast.LENGTH_LONG).show();
        }catch(Exception e){
            throw new RuntimeException("Error reading CSV File" + e);
        } finally {
            try{
                inputStream.close();
            }catch (IOException e){
                throw new RuntimeException("Error Closing Input Stream" + e);
            }
//            String toastMsg = "";
            calculatorDatabaseHelper.insertStream(StreamList);

        }
    }

    private void insertStreamCore() {
        List<String[]> StreamCoreList=new ArrayList<>();
        InputStream inputStream= getResources().openRawResource(R.raw.streamcore);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try{
            String csvLine;
            while((csvLine = reader.readLine())!= null){
                String[] eachRecord = csvLine.split(",");
                StreamCoreList.add(eachRecord);
            }
//            Toast.makeText(SplashActivity.this, "GPA FILE READ", Toast.LENGTH_LONG).show();
        }catch(Exception e){
            throw new RuntimeException("Error reading CSV File" + e);
        } finally {
            try{
                inputStream.close();
            }catch (IOException e){
                throw new RuntimeException("Error Closing Input Stream" + e);
            }
//            String toastMsg = "";
            calculatorDatabaseHelper.insertStreamCore(StreamCoreList);

        }
    }

    private void insertMinistream() {
        List<String[]> MinistreamList=new ArrayList<>();
        InputStream inputStream= getResources().openRawResource(R.raw.ministream);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try{
            String csvLine;
            while((csvLine = reader.readLine())!= null){
                String[] eachRecord = csvLine.split(",");
                MinistreamList.add(eachRecord);
            }
//            Toast.makeText(SplashActivity.this, "GPA FILE READ", Toast.LENGTH_LONG).show();
        }catch(Exception e){
            throw new RuntimeException("Error reading CSV File" + e);
        } finally {
            try{
                inputStream.close();
            }catch (IOException e){
                throw new RuntimeException("Error Closing Input Stream" + e);
            }
//            String toastMsg = "";
            calculatorDatabaseHelper.insertMinistream(MinistreamList);

        }
    }

    private void insertMinistreamCore() {
        List<String[]> MinistreamCoreList=new ArrayList<>();
        InputStream inputStream= getResources().openRawResource(R.raw.ministreamcore);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try{
            String csvLine;
            while((csvLine = reader.readLine())!= null){
                String[] eachRecord = csvLine.split(",");
                MinistreamCoreList.add(eachRecord);
            }
//            Toast.makeText(SplashActivity.this, "GPA FILE READ", Toast.LENGTH_LONG).show();
        }catch(Exception e){
            throw new RuntimeException("Error reading CSV File" + e);
        } finally {
            try{
                inputStream.close();
            }catch (IOException e){
                throw new RuntimeException("Error Closing Input Stream" + e);
            }
//            String toastMsg = "";
            calculatorDatabaseHelper.insertMinistreamCore(MinistreamCoreList);

        }
    }


}