package com.example.gradecalculatorapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CalculatorDatabaseHelper extends SQLiteOpenHelper {

    SQLiteDatabase sqLiteDatabaseMain;
    public static final String dropQuery = "DROP TABLE IF EXISTS ";
    public static final String DATABASE_NAME = "calculator.db";
    public static final String setPRAGMAForeignKeysOn = "PRAGMA foreign_keys = ON;";

    public static final String GPA = "GPA";
    public static final String Instructor = "Instructor";
    public static final String Course = "Course";
    public static final String Prerequisite = "Prerequisite";
    public static final String Semester = "Semester";
    public static final String Criteria = "Criteria";
    public static final String Program = "Program";
    public static final String Program_core  = "Program_core ";
    public static final String Stream = "Stream";
    public static final String Stream_core = "Stream_core";
    public static final String Ministream = "Ministream";
    public static final String Ministream_core = "Ministream_core";
    public static final String Student = "Student";
    public static final String Student_Course = "Student_Course";
    public static final String Grades = "Grades";

    public static final String GPAID = "GPAID";
    public static final String MinimumPercentage = "MinimumPercentage";
    public static final String MaximumPercentage = "MaximumPercentage";
    public static final String Grade = "Grade";
    public static final String InstructorID = "InstructorID";
    public static final String InstructorName = "InstructorName";
    public static final String Description = "Description";
    public static final String CourseID  = "CourseID ";
    public static final String CourseName = "CourseName";
    public static final String CourseCredits = "CourseCredits";
    public static final String CourseID_in_Prerequisite = "CourseID_in_Prerequisite";
    public static final String PreRequisite_CourseID  = "PreRequisite_CourseID ";
    public static final String Year = "Year";
    public static final String Season = "Season";
    public static final String CriteriaID  = "CriteriaID ";
    public static final String CriteriaName  = "CriteriaName ";
    public static final String ProgramID  = "ProgramID ";
    public static final String ProgramName  = "ProgramName ";
    public static final String ProgramCredits  = "ProgramCredits ";
    public static final String ProgramID_in_Program_Core = "ProgramID_in_Program_Core";
    public static final String CourseID_in_Program_Core = "CourseID_in_Program_Core";
    public static final String StreamID = "StreamID";
    public static final String StreamName = "StreamName";
    public static final String ProgramID_in_Stream_Core = "ProgramID_in_Stream_Core";
    public static final String CourseID_in_Stream_Core = "CourseID_in_Stream_Core";
    public static final String MiniStreamID = "MiniStreamID";
    public static final String MiniStreamName = "MiniStreamName";
    public static final String ProgramID_in_MiniStream_Core = "ProgramID_in_MiniStream_Core";
    public static final String CourseID_in_MiniStream_Core = "CourseID_in_MiniStream_Core";
    public static final String StudentID = "StudentID";
    public static final String StudentPassword = "StudentPassword";
    public static final String StudentName = "StudentName";
    public static final String StudentEmail = "StudentEmail";
    public static final String StudentProgramID = "StudentProgramID";
    public static final String StudentStreamID = "StudentStreamID";
    public static final String StudentMiniStreamID = "StudentMiniStreamID";
    public static final String Student_CourseID = "Student_CourseID";
    public static final String Student_Course_StudentID = "Student_Course_StudentID";
    public static final String Student_Course_ProgramID  = "Student_Course_ProgramID ";
    public static final String Student_Course_CourseID  = "Student_Course_CourseID ";
    public static final String Student_Course_InstructorID  = "Student_Course_InstructorID ";
    public static final String Student_Course_YEAR  = "Student_Course_YEAR ";
    public static final String Student_Course_SEASON  = "Student_Course_SEASON ";
    public static final String GradeID  = "GradeID ";
    public static final String Grade_Student_CourseID  = "Grade_Student_CourseID ";
    public static final String Grade_CriteriaID  = "Grade_CriteriaID ";
    public static final String Marks_Total  = "Marks_Total ";
    public static final String Marks_Acheived  = "Marks_Acheived ";
    public static final String Weight_Total  = "Weight_Total ";
    public static final String Weight_Acheived  = "Weight_Acheived ";

    final String GPATableCreate ="CREATE TABLE IF NOT EXISTS "+GPA+" ("+GPAID+ " TEXT NOT NULL, " +MinimumPercentage+ " INT NOT NULL, " +MaximumPercentage+ " INT NOT NULL, " +Grade + " TEXT NOT NULL, " +"PRIMARY KEY ("+GPAID+"));";
    final String InstructorTableCreate ="CREATE TABLE IF NOT EXISTS "+Instructor+" ("+InstructorID+ " TEXT NOT NULL," +InstructorName+ " TEXT NOT NULL," +Description+ " TEXT," +"PRIMARY KEY ("+InstructorID+"));";
    final String CourseTableCreate ="CREATE TABLE IF NOT EXISTS "+Course+" ("+CourseID + " TEXT NOT NULL," +CourseName+ " TEXT NOT NULL," +CourseCredits+ " INT NOT NULL," +"PRIMARY KEY ("+CourseID +"));";
    final String SemesterTableCreate ="CREATE TABLE IF NOT EXISTS "+Semester+" ("+Year+ " INT NOT NULL," +Season+ " TEXT NOT NULL," +"PRIMARY KEY ("+Year+","+Season+"));";
    final String CriteriaTableCreate ="CREATE TABLE IF NOT EXISTS "+Criteria+" ("+CriteriaID + " INT NOT NULL," +CriteriaName + " TEXT NOT NULL," +"PRIMARY KEY ("+CriteriaID+"));";
    final String ProgramTableCreate ="CREATE TABLE IF NOT EXISTS "+Program+" ("+ProgramID + " TEXT NOT NULL," +ProgramName + " TEXT NOT NULL," +ProgramCredits + " INT NOT NULL," +"PRIMARY KEY ("+ProgramID +"));";
    final String StreamTableCreate ="CREATE TABLE IF NOT EXISTS "+Stream+" ("+StreamID+ " TEXT NOT NULL," +StreamName+ " TEXT NOT NULL," +"PRIMARY KEY ("+StreamID+"));";
    final String MinistreamTableCreate ="CREATE TABLE IF NOT EXISTS "+Ministream+" ("+MiniStreamID+ " TEXT NOT NULL," +MiniStreamName+ " TEXT NOT NULL," +"PRIMARY KEY ("+MiniStreamID+"));";
    final String PrerequisiteTableCreate ="CREATE TABLE IF NOT EXISTS "+Prerequisite+" ("+CourseID_in_Prerequisite+ " TEXT NOT NULL," +PreRequisite_CourseID + " TEXT NOT NULL," +"FOREIGN KEY ("+CourseID_in_Prerequisite+") REFERENCES "+Course+"("+CourseID+"));";
    final String Program_coreTableCreate ="CREATE TABLE IF NOT EXISTS "+Program_core +" ("+ProgramID_in_Program_Core+ " TEXT NOT NULL," +CourseID_in_Program_Core+ " TEXT NOT NULL," +"FOREIGN KEY ("+ProgramID_in_Program_Core+") REFERENCES "+Program+"("+ProgramID+")"+");";
    final String Stream_coreTableCreate ="CREATE TABLE IF NOT EXISTS "+Stream_core+" ("+ProgramID_in_Stream_Core+ " TEXT NOT NULL," +CourseID_in_Stream_Core+ " TEXT NOT NULL," +"FOREIGN KEY ("+ProgramID_in_Stream_Core+") REFERENCES "+Stream+"("+StreamID+"));";
    final String Ministream_coreTableCreate ="CREATE TABLE IF NOT EXISTS "+Ministream_core+" ("+ProgramID_in_MiniStream_Core+ " TEXT NOT NULL," +CourseID_in_MiniStream_Core+ " TEXT NOT NULL," +"FOREIGN KEY ("+ProgramID_in_MiniStream_Core+") REFERENCES "+Ministream+"("+MiniStreamID+"));";
    final String StudentTableCreate ="CREATE TABLE IF NOT EXISTS "+Student+" ("+StudentID+ " TEXT NOT NULL," +StudentPassword+ " TEXT NOT NULL," +StudentName+ " TEXT NOT NULL," +StudentEmail+ " TEXT NOT NULL," +StudentProgramID+ " TEXT NOT NULL," +StudentStreamID+ " TEXT NOT NULL," +StudentMiniStreamID+ " TEXT NOT NULL," +"PRIMARY KEY ("+StudentID+"),"+"FOREIGN KEY ("+StudentProgramID+") REFERENCES "+Program+"("+ProgramID+"),"+"FOREIGN KEY ("+StudentStreamID+") REFERENCES "+Stream+"("+StreamID+"),"+"FOREIGN KEY ("+StudentMiniStreamID+") REFERENCES "+Ministream+"("+MiniStreamID+"));";
    final String Student_CourseTableCreate ="CREATE TABLE IF NOT EXISTS "+Student_Course+" ("+Student_CourseID+ " INT NOT NULL," +Student_Course_StudentID+ " TEXT NOT NULL," +Student_Course_ProgramID + " TEXT NOT NULL," +Student_Course_CourseID + " TEXT NOT NULL," +Student_Course_InstructorID + " TEXT NOT NULL," +Student_Course_YEAR + " INT NOT NULL," +Student_Course_SEASON + " TEXT NOT NULL," +"PRIMARY KEY ("+Student_CourseID+"),"+"FOREIGN KEY ("+Student_Course_StudentID+") REFERENCES "+Student+"("+StudentID+"),"+"FOREIGN KEY ("+Student_Course_ProgramID+") REFERENCES "+Program+"("+ProgramID+"),"+"FOREIGN KEY ("+Student_Course_CourseID+") REFERENCES "+Course+"("+CourseID+"),"+"FOREIGN KEY ("+Student_Course_StudentID+") REFERENCES "+Student+"("+StudentID+"),"+"FOREIGN KEY ("+Student_Course_YEAR+") REFERENCES "+Semester+"("+Year+"),"+"FOREIGN KEY ("+Student_Course_SEASON+") REFERENCES "+Semester+"("+Season+"),"+"FOREIGN KEY ("+Student_Course_InstructorID+") REFERENCES "+Instructor+"("+InstructorID+"));";
    final String GradesTableCreate ="CREATE TABLE IF NOT EXISTS "+Grades+" ("+GradeID + " INT NOT NULL," +Grade_Student_CourseID + " TEXT NOT NULL," +Grade_CriteriaID + " TEXT NOT NULL," +Marks_Total + " TEXT NOT NULL," +Marks_Acheived + " TEXT NOT NULL," +Weight_Total + " TEXT NOT NULL," +Weight_Acheived + " TEXT NOT NULL," +"PRIMARY KEY ("+GradeID+"),"+"FOREIGN KEY ("+Grade_Student_CourseID+") REFERENCES "+Student_Course+"("+Student_CourseID+"),"+"FOREIGN KEY ("+Grade_CriteriaID+") REFERENCES "+Criteria+"("+CriteriaID+"));";

    CalculatorDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        sqLiteDatabaseMain = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try{
            sqLiteDatabase.execSQL(setPRAGMAForeignKeysOn);

            sqLiteDatabase.execSQL(GPATableCreate);
            sqLiteDatabase.execSQL(InstructorTableCreate);
            sqLiteDatabase.execSQL(CourseTableCreate);
            sqLiteDatabase.execSQL(PrerequisiteTableCreate);
            sqLiteDatabase.execSQL(SemesterTableCreate);
            sqLiteDatabase.execSQL(CriteriaTableCreate);
            sqLiteDatabase.execSQL(ProgramTableCreate);
            sqLiteDatabase.execSQL(Program_coreTableCreate);
            sqLiteDatabase.execSQL(StreamTableCreate);
            sqLiteDatabase.execSQL(Stream_coreTableCreate);
            sqLiteDatabase.execSQL(MinistreamTableCreate);
            sqLiteDatabase.execSQL(Ministream_coreTableCreate);
            sqLiteDatabase.execSQL(StudentTableCreate);
            sqLiteDatabase.execSQL(Student_CourseTableCreate);
            sqLiteDatabase.execSQL(GradesTableCreate);
        }catch(Exception e){
            Log.e("DB Demo",e.getMessage());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(dropQuery+GPA);
        sqLiteDatabase.execSQL(dropQuery+Instructor);
        sqLiteDatabase.execSQL(dropQuery+Course);
        sqLiteDatabase.execSQL(dropQuery+Prerequisite);
        sqLiteDatabase.execSQL(dropQuery+Semester);
        sqLiteDatabase.execSQL(dropQuery+Criteria);
        sqLiteDatabase.execSQL(dropQuery+Program);
        sqLiteDatabase.execSQL(dropQuery+Program_core);
        sqLiteDatabase.execSQL(dropQuery+Stream);
        sqLiteDatabase.execSQL(dropQuery+Stream_core);
        sqLiteDatabase.execSQL(dropQuery+Ministream);
        sqLiteDatabase.execSQL(dropQuery+Ministream_core);
        sqLiteDatabase.execSQL(dropQuery+Student);
        sqLiteDatabase.execSQL(dropQuery+Student_Course);
        sqLiteDatabase.execSQL(dropQuery+Grades);
        sqLiteDatabase.execSQL(dropQuery+Grades);
        onCreate(sqLiteDatabase);
    }

    public void insertGPA(List<String[]> GPAList) {
        for (int i = 0; i<GPAList.size(); i++){
            long result;
            ContentValues val = new ContentValues();
//                toastMsg += GPAList.get(i)[0] + " - "+ GPAList.get(i)[1] + " - "+ GPAList.get(i)[2] + " - "+ GPAList.get(i)[3] + "\n";
//                Toast.makeText(SplashActivity.this,GPAList.get(i)[0] + " - "+ GPAList.get(i)[1] + " - "+ GPAList.get(i)[2] + " - "+ GPAList.get(i)[3]  , Toast.LENGTH_LONG).show();
            val.put(GPAID,GPAList.get(i)[0]);
            val.put(MinimumPercentage,GPAList.get(i)[1]);
            val.put(MaximumPercentage,GPAList.get(i)[2]);
            val.put(Grade,GPAList.get(i)[3]);
            result = sqLiteDatabaseMain.insert(GPA,null,val);

            if (result != -1){
                Log.d("DB Demo",GPA + " Insertion Successful");
            }else{
                Log.d("DB Demo",GPA + " Insertion Unsuccessful");
            }
        }
//            Toast.makeText(SplashActivity.this, toastMsg + "", Toast.LENGTH_LONG).show();
    }

    public void insertInstructors(List<String[]> InstructorList) {
        for (int i = 0; i<InstructorList.size(); i++){
            long result;
            ContentValues val = new ContentValues();
//                toastMsg += InstructorList.get(i)[0] + " - "+ InstructorList.get(i)[1] + "\n";
            val.put(InstructorID,InstructorList.get(i)[0]);
            val.put(InstructorName,InstructorList.get(i)[1]);
            val.put(Description,InstructorList.get(i)[2]);
            result = sqLiteDatabaseMain.insert(Instructor,null,val);

            if (result != -1){
                Log.d("DB Demo",Instructor + " Insertion Successful");
            }else{
                Log.d("DB Demo",Instructor + " Insertion Unsuccessful");
            }
        }
//            Toast.makeText(SplashActivity.this, toastMsg + "", Toast.LENGTH_LONG).show();
    }

    public void insertCourse(List<String[]> CourseList) {
        for (int i = 0; i<CourseList.size(); i++){
            long result;
            ContentValues val = new ContentValues();
//                toastMsg += CourseList.get(i)[0] + " - "+ CourseList.get(i)[1] + "\n";
            val.put(CourseID,CourseList.get(i)[0]);
            val.put(CourseName,CourseList.get(i)[1]);
            val.put(CourseCredits,CourseList.get(i)[2]);
            result = sqLiteDatabaseMain.insert(Course,null,val);

            if (result != -1){
                Log.d("DB Demo",Course + " Insertion Successful");
            }else{
                Log.d("DB Demo",Course + " Insertion Unsuccessful");
            }
        }
//            Toast.makeText(SplashActivity.this, toastMsg + "", Toast.LENGTH_LONG).show();
    }

    public void insertPrerequisite(List<String[]> PrerequisiteList) {
        for (int i = 0; i<PrerequisiteList.size(); i++){
            long result;
            ContentValues val = new ContentValues();
//                toastMsg += PrerequisiteList.get(i)[0] + " - "+ PrerequisiteList.get(i)[1] + "\n";
            val.put(CourseID_in_Prerequisite,PrerequisiteList.get(i)[0]);
            val.put(PreRequisite_CourseID,PrerequisiteList.get(i)[1]);
            result = sqLiteDatabaseMain.insert(Prerequisite,null,val);

            if (result != -1){
                Log.d("DB Demo",Prerequisite + " Insertion Successful");
            }else{
                Log.d("DB Demo",Prerequisite + " Insertion Unsuccessful");
            }
        }
//            Toast.makeText(SplashActivity.this, toastMsg + "", Toast.LENGTH_LONG).show();
    }

    public void insertSemester(List<String[]> SemesterList) {
        for (int i = 0; i<SemesterList.size(); i++){
            long result;
            ContentValues val = new ContentValues();
//                toastMsg += SemesterList.get(i)[0] + " - "+ SemesterList.get(i)[1] + "\n";
            val.put(Year,SemesterList.get(i)[1]);
            val.put(Season,SemesterList.get(i)[0]);
            result = sqLiteDatabaseMain.insert(Semester,null,val);

            if (result != -1){
                Log.d("DB Demo",Semester + " Insertion Successful");
            }else{
                Log.d("DB Demo",Semester + " Insertion Unsuccessful");
            }
        }
//            Toast.makeText(SplashActivity.this, toastMsg + "", Toast.LENGTH_LONG).show();
    }

    public void insertCriteria(List<String[]> CriteriaList) {
        for (int i = 0; i<CriteriaList.size(); i++){
            long result;
            ContentValues val = new ContentValues();
//                toastMsg += CriteriaList.get(i)[0] + " - "+ CriteriaList.get(i)[1] + "\n";
            val.put(CriteriaID,CriteriaList.get(i)[0]);
            val.put(CriteriaName,CriteriaList.get(i)[1]);
            result = sqLiteDatabaseMain.insert(Criteria,null,val);

            if (result != -1){
                Log.d("DB Demo",Criteria + " Insertion Successful");
            }else{
                Log.d("DB Demo",Criteria + " Insertion Unsuccessful");
            }
        }
//            Toast.makeText(SplashActivity.this, toastMsg + "", Toast.LENGTH_LONG).show();
    }

    public void insertProgram(List<String[]> ProgramList) {
        for (int i = 0; i<ProgramList.size(); i++){
            long result;
            ContentValues val = new ContentValues();
//                toastMsg += ProgramList.get(i)[0] + " - "+ ProgramList.get(i)[1] + "\n";
            val.put(ProgramID,ProgramList.get(i)[0]);
            val.put(ProgramName,ProgramList.get(i)[1]);
            val.put(ProgramCredits,ProgramList.get(i)[2]);
            result = sqLiteDatabaseMain.insert(Program,null,val);

            if (result != -1){
                Log.d("DB Demo",Program + " Insertion Successful");
            }else{
                Log.d("DB Demo",Program + " Insertion Unsuccessful");
            }
        }
//            Toast.makeText(SplashActivity.this, toastMsg + "", Toast.LENGTH_LONG).show();
    }

    public void insertProgramCore(List<String[]> ProgramCoreList) {
        for (int i = 0; i<ProgramCoreList.size(); i++){
            long result;
            ContentValues val = new ContentValues();
//                toastMsg += ProgramCoreList.get(i)[0] + " - "+ ProgramCoreList.get(i)[1] + "\n";
            val.put(ProgramID_in_Program_Core,ProgramCoreList.get(i)[0]);
            val.put(CourseID_in_Program_Core,ProgramCoreList.get(i)[1]);
            result = sqLiteDatabaseMain.insert(Program_core,null,val);

            if (result != -1){
                Log.d("DB Demo",Program_core + " Insertion Successful");
            }else{
                Log.d("DB Demo",Program_core + " Insertion Unsuccessful");
            }
        }
//            Toast.makeText(SplashActivity.this, toastMsg + "", Toast.LENGTH_LONG).show();
    }

    public void insertStream(List<String[]> StreamList) {
        for (int i = 0; i<StreamList.size(); i++){
            long result;
            ContentValues val = new ContentValues();
//                toastMsg += StreamList.get(i)[0] + " - "+ StreamList.get(i)[1] + "\n";
            val.put(StreamID,StreamList.get(i)[0]);
            val.put(StreamName,StreamList.get(i)[1]);
            result = sqLiteDatabaseMain.insert(Stream,null,val);

            if (result != -1){
                Log.d("DB Demo",Stream + " Insertion Successful");
            }else{
                Log.d("DB Demo",Stream + " Insertion Unsuccessful");
            }
        }
//            Toast.makeText(SplashActivity.this, toastMsg + "", Toast.LENGTH_LONG).show();
    }

    public void insertStreamCore(List<String[]> StreamCoreList) {
        for (int i = 0; i<StreamCoreList.size(); i++){
            long result;
            ContentValues val = new ContentValues();
//                toastMsg += StreamCoreList.get(i)[0] + " - "+ StreamCoreList.get(i)[1] + "\n";
            val.put(ProgramID_in_Stream_Core,StreamCoreList.get(i)[0]);
            val.put(CourseID_in_Stream_Core,StreamCoreList.get(i)[1]);
            result = sqLiteDatabaseMain.insert(Stream_core,null,val);

            if (result != -1){
                Log.d("DB Demo",Stream_core + " Insertion Successful");
            }else{
                Log.d("DB Demo",Stream_core + " Insertion Unsuccessful");
            }
        }
//            Toast.makeText(SplashActivity.this, toastMsg + "", Toast.LENGTH_LONG).show();
    }

    public void insertMinistream(List<String[]> MinistreamList) {
        for (int i = 0; i<MinistreamList.size(); i++){
            long result;
            ContentValues val = new ContentValues();
//                toastMsg += MinistreamList.get(i)[0] + " - "+ MinistreamList.get(i)[1] + "\n";
            val.put(MiniStreamID,MinistreamList.get(i)[0]);
            val.put(MiniStreamName,MinistreamList.get(i)[1]);
            result = sqLiteDatabaseMain.insert(Ministream,null,val);

            if (result != -1){
                Log.d("DB Demo",Ministream + " Insertion Successful");
            }else{
                Log.d("DB Demo",Ministream + " Insertion Unsuccessful");
            }
        }
//            Toast.makeText(SplashActivity.this, toastMsg + "", Toast.LENGTH_LONG).show();
    }

    public void insertMinistreamCore(List<String[]> MinistreamCoreList) {
        for (int i = 0; i<MinistreamCoreList.size(); i++){
            long result;
            ContentValues val = new ContentValues();
//                toastMsg += MinistreamCoreList.get(i)[0] + " - "+ MinistreamCoreList.get(i)[1] + "\n";
            val.put(ProgramID_in_MiniStream_Core,MinistreamCoreList.get(i)[0]);
            val.put(CourseID_in_MiniStream_Core,MinistreamCoreList.get(i)[1]);
            result = sqLiteDatabaseMain.insert(Ministream_core,null,val);

            if (result != -1){
                Log.d("DB Demo",Ministream_core + " Insertion Successful");
            }else{
                Log.d("DB Demo",Ministream_core + " Insertion Unsuccessful");
            }
        }
//            Toast.makeText(SplashActivity.this, toastMsg + "", Toast.LENGTH_LONG).show();
    }

    public ArrayList<String> populateProgramsInSpinner() {
        String getProgram = "Select * FROM " + Program;
        ArrayList<String> resultList = new ArrayList<String>();
        resultList.add("Select Program");
        Cursor c = sqLiteDatabaseMain.rawQuery(getProgram, null);
        if (c.moveToFirst()){
            do {
                // adding to tags list
                resultList.add(c.getString(0) + " (" +c.getString(1) +")");
            } while (c.moveToNext());
        }

        return resultList;
    }

    public List<String> populateStreamsInSpinner() {
        String getProgram = "Select * FROM " + Stream;
        ArrayList<String> resultList = new ArrayList<String>();
        Cursor c = sqLiteDatabaseMain.rawQuery(getProgram, null);
        if (c.moveToFirst()){
            do {
                // adding to tags list
                resultList.add(c.getString(0) + " (" +c.getString(1) +")");
            } while (c.moveToNext());
        }

        return resultList;
    }

    public List<String> populateMiniStreamsInSpinner() {
        String getProgram = "Select * FROM " + Ministream;
        ArrayList<String> resultList = new ArrayList<String>();
        Cursor c = sqLiteDatabaseMain.rawQuery(getProgram, null);
        if (c.moveToFirst()){
            do {
                // adding to tags list
                resultList.add(c.getString(0) + " (" +c.getString(1) +")");
            } while (c.moveToNext());
        }

        return resultList;
    }

    public void createAccountInsertStudentData(int StudentIDArg,String StudentPasswordArg,String StudentNameArg,String StudentEmailArg,String StudentProgramIDArg,String StudentStreamIDArg,String StudentMiniStreamIDArg) {
        long result;
        ContentValues val = new ContentValues();
        val.put(StudentID,StudentIDArg);
        val.put(StudentPassword,StudentPasswordArg);
        val.put(StudentName,StudentNameArg);
        val.put(StudentEmail,StudentEmailArg);
        val.put(StudentProgramID,StudentProgramIDArg);
        val.put(StudentStreamID,StudentStreamIDArg);
        val.put(StudentMiniStreamID,StudentMiniStreamIDArg);
        result = sqLiteDatabaseMain.insert(Student,null,val);

        if (result != -1){
            Log.d("DB Demo",Student + " Insertion Successful");
        }else{
            Log.d("DB Demo",Student + " Insertion Unsuccessful");
        }
    }

    public boolean checkLoginPassword(String studentIDArg, String studentPasswordArg) {
        String getStudent = "Select "+ StudentID +" , "+StudentPassword +" FROM " + Student + " WHERE " + StudentID +" = ? AND " + StudentPassword +" = ?";
        try{
            Cursor c = sqLiteDatabaseMain.rawQuery(getStudent, new String[]{studentIDArg,studentPasswordArg});
            if(c.getCount()==1){
                Log.d("DB Demo", "Query successful");
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            Log.d("DB Demo", "Query Unsuccessful" + e);
            return false;
        }
    }

    public String[] getStudentDataForHomePage(String StudentIDArg) {
        String getProgramForHomePage = "SELECT * FROM "+ Student +" WHERE "+ StudentID +" = ?";
        String[] returnRec = new String[7];
        try{
            Cursor cursor = sqLiteDatabaseMain.rawQuery(getProgramForHomePage, new String[]{StudentIDArg});
            if (cursor!= null && cursor.getCount()==1){
                cursor.moveToFirst();
                returnRec[0] = cursor.getString(0);
                returnRec[1] = cursor.getString(1);
                returnRec[2] = cursor.getString(2);
                returnRec[3] = cursor.getString(3);
                returnRec[4] = cursor.getString(4);
                returnRec[5] = cursor.getString(5);
                returnRec[6] = cursor.getString(6);
            }
        }catch(Exception e){
            Log.d("DB Demo", e.getMessage());
        }finally {
            return  returnRec;
        }
    }

    public String[] getProgramName(String ProgramIDArg) {
        String getProgramName = "SELECT  "+ProgramName+","+ProgramCredits+" FROM "+Program+" WHERE "+ProgramID+" = ?";
        String Program[] = new String[2];
        try{
            Cursor cursor = sqLiteDatabaseMain.rawQuery(getProgramName, new String[]{ProgramIDArg});
            if (cursor!= null && cursor.getCount()==1){
                cursor.moveToFirst();
                Program[0] = cursor.getString(0);
                Program[1] = cursor.getString(1);
            }
        }catch(Exception e){
            Log.d("DB Demo", e.getMessage());
        }
//        Log.d("Program",Program[1]);
        return Program;
    }

    public List<String> populateSemestersInSpinner() {
//        Log.d("Inside Semester","Spinner");
        String getProgram = "Select * FROM " + Semester;
        ArrayList<String> resultList = new ArrayList<String>();
        resultList.add("Select Semester");
        Cursor c = sqLiteDatabaseMain.rawQuery(getProgram, null);
        if (c.moveToFirst()){
            do {
                // adding to tags list
                resultList.add(c.getString(1) + " (" +c.getString(0) +")");
            } while (c.moveToNext());
        }

        return resultList;
    }

    public List<String> getStudentCourses(String SessionStudentID) {
//        Log.d("Inside Spinner","Spinner");


        String getProgramCore = "SELECT "+CourseID_in_Program_Core+" FROM "+Program_core+" LEFT JOIN "
                +Student+" ON "+ProgramID_in_Program_Core+" = "+StudentProgramID+"   WHERE "+
                ProgramID_in_Program_Core+" IN (SELECT "+StudentProgramID+" FROM "+Student+" WHERE "+
                StudentID+" = ?) UNION " + "SELECT "+CourseID_in_Stream_Core+" FROM "+Stream_core +" " +
                "LEFT JOIN "+Student+" ON "+ProgramID_in_Stream_Core +" = "+StudentStreamID   +"   " +
                "WHERE "+ProgramID_in_Stream_Core +" IN (SELECT "+StudentStreamID+" FROM "+
                Student+" WHERE "+StudentID+" = ?) UNION "+ "SELECT "+CourseID_in_MiniStream_Core+" FROM "+
                Ministream_core +" LEFT JOIN "+Student+" ON "+ProgramID_in_MiniStream_Core +" = "+
                StudentMiniStreamID   +"   WHERE "+ProgramID_in_MiniStream_Core +
                " IN (SELECT "+StudentMiniStreamID+" FROM "+Student+" WHERE "+StudentID+" = ?)";
        ArrayList<String> resultList = new ArrayList<String>();
        resultList.add("Select Course");
//        Log.d("Query",getProgramCore + SessionStudentID);
        Cursor cp = sqLiteDatabaseMain.rawQuery(getProgramCore, new String[]{SessionStudentID,SessionStudentID,SessionStudentID});
        if (cp.moveToFirst()){
            do {
                // adding to tags list
                resultList.add(cp.getString(0));
            } while (cp.moveToNext());
        }
        return splitCourseId(resultList, SessionStudentID);
    }

    public ArrayList<String> splitCourseId(ArrayList<String> resultList,String SessionStudentID) {
        ArrayList<String> newResultList = new ArrayList<String>();
        ArrayList<String> newResultListWithName = new ArrayList<String>();
        for (int i = 0; i<resultList.size();i++ ){
//            Log.d("assigning value",resultList.get(i));
            if (resultList.get(i).contains(";")){
                String[] newData = resultList.get(i).split(";");
                for(int j = 0; j<newData.length;j++){
                    newResultList.add(newData[j]);
                }
            }else{
                newResultList.add(resultList.get(i));
            }
        }
        Log.d("Required Courses", resultList.toString());
        Log.d("Split Req Courses", newResultList.toString());
        return getPrerequisuteList(newResultList, SessionStudentID); // All the courses a student have to do
    }

    private ArrayList<String> getPrerequisuteList(ArrayList<String> newResultList,String SessionStudentID) {
        String getPrerequisite = "SELECT "+CourseID_in_Prerequisite+","+PreRequisite_CourseID+" FROM "+Prerequisite+" WHERE "+CourseID_in_Prerequisite+" = ?";
        ArrayList<String> prerequisiteList = new ArrayList<String>();
        for(int i = 0; i < newResultList.size(); i++){
            Cursor cp = sqLiteDatabaseMain.rawQuery(getPrerequisite, new String[]{newResultList.get(i)});
            if (cp.moveToFirst()){
                do {
                    // adding to tags list
                    prerequisiteList.add(cp.getString(0) + " - " + cp.getString(1));
                } while (cp.moveToNext());
            }
        }

        return filteringPrerequisites(newResultList,prerequisiteList, SessionStudentID);
//        Log.d("prerequisiteList", prerequisiteList.toString());
    }

    private ArrayList<String> filteringPrerequisites(ArrayList<String> newResultList, ArrayList<String> prerequisiteList,String SessionStudentID) {
        ArrayList<String> displayCourses = new ArrayList<String>();
        String getStudentCourseCourseID = "SELECT "+Student_Course_CourseID+" FROM "+Student_Course+" WHERE "+Student_Course_StudentID+" = ?";
        Cursor cp = sqLiteDatabaseMain.rawQuery(getStudentCourseCourseID, new String[]{SessionStudentID});
        ArrayList<String> completedCourses = new ArrayList<String>();
        Log.d("newResultList",newResultList.toString());
        Log.d("prerequisiteList",prerequisiteList.toString());
        if (cp.moveToFirst()){
            do {
                // adding to tags list
                completedCourses.add(cp.getString(0));
            } while (cp.moveToNext());
        }
        Log.d("completedCourses",completedCourses.toString());
        for(int i = 0; i<newResultList.size();i++){
//            String x = newResultList.get(i);
            for(int j = 0; j<prerequisiteList.size();j++) {
                String yUnsplit = prerequisiteList.get(j);
                String[] y = yUnsplit.split(" - ");
                if(completedCourses.contains(y[0])){

                }else {
                    if(y[1].equals("N/A")){
                        if(!displayCourses.contains(y[0]) && !completedCourses.contains(y[0])){
                            displayCourses.add(y[0]);
                        }
                    }else if(y[1].contains(";")){
                        String[] splittedPrerequisites = y[1].split(";");
                        boolean completed = false;
                        for(int k = 0;k<splittedPrerequisites.length;k++){

                            if(completedCourses.contains(splittedPrerequisites[k]) && !displayCourses.contains(y[0])){
//                                Log.d(y[0],"completed prereq and added to list");
                                displayCourses.add(y[0]);
                                completed = true;
                                break;
                            }else if(!completedCourses.contains(splittedPrerequisites[k]) && displayCourses.contains(y[0])){
//                                Log.d(y[0],"Not completed prereq and removed from list");
                                displayCourses.remove(y[0]);
                                completed = false;
                            }else if(completedCourses.contains(splittedPrerequisites[k]) && displayCourses.contains(y[0])){
//                                Log.d(y[0],"completed prereq and already in list");
                                completed = true;
                                break;
                            }else{

                            }
                        }
                    }else{
                        if(completedCourses.contains(y[1]) && !displayCourses.contains(y[0]) ){
                            displayCourses.add(y[0]);
                        }else if(!completedCourses.contains(y[1]) && displayCourses.contains(y[0]) ){
                            displayCourses.remove(y[0]);
                        }else if(!completedCourses.contains(y[1]) && !displayCourses.contains(y[0]) ){

                        }else if(completedCourses.contains(y[1]) && displayCourses.contains(y[0]) ){

                        }
                    }
                }


            }
        }

        return getCourseNames(displayCourses);
    }

    private ArrayList<String> getCourseNames(ArrayList<String> displayCoursesOld) {
        ArrayList<String> finalCourseList = new ArrayList<String>();
        String getCourseName = "SELECT "+CourseID+" , "+CourseName+" FROM "+Course+" WHERE "+CourseID+" = ?";
        for (int i = 0; i<displayCoursesOld.size();i++ ){
            Cursor gpn = sqLiteDatabaseMain.rawQuery(getCourseName, new String[]{displayCoursesOld.get(i)});
            if (gpn.moveToFirst()){
                do {
                    String insertion = gpn.getString(0) + " - " + gpn.getString(1) ;
//                    Log.d("insertion",insertion);
                    finalCourseList.add(insertion);

                } while (gpn.moveToNext());
            }
        }

        return finalCourseList;
    }

    public void setStudentCourseValue(String selectedSemester, String[] selectedCourseID, String sessionStudentID) {
        String[] semester = selectedSemester.split(" ");
        String yearSemester = semester[1].replaceAll("[()]", "");
        String seasonSemester = semester[0];
        String studentProgramID = "";
        String getProgramID = "SELECT "+StudentProgramID+" FROM "+Student+" WHERE "+StudentID+" = ?";
        String getCourseCount = "SELECT * FROM "+Student_Course;
        Cursor gpc = sqLiteDatabaseMain.rawQuery(getCourseCount,null);
        int studentCourseID = gpc.getCount() + 1;

        Cursor gpn = sqLiteDatabaseMain.rawQuery(getProgramID, new String[]{sessionStudentID});
        if (gpn.moveToFirst()){
            do {
                String insertion = gpn.getString(0) ;
                studentProgramID = insertion;
            } while (gpn.moveToNext());
        }
        for(int i = 0;i<selectedCourseID.length;i++){
            long result;
            ContentValues val = new ContentValues();
//                toastMsg += InstructorList.get(i)[0] + " - "+ InstructorList.get(i)[1] + "\n";
            val.put(Student_CourseID,studentCourseID);
            val.put(Student_Course_StudentID,sessionStudentID);
            val.put(Student_Course_ProgramID,studentProgramID);
            val.put(Student_Course_CourseID,selectedCourseID[i]);
            val.put(Student_Course_InstructorID,"N/A");
            val.put(Student_Course_YEAR,yearSemester);
            val.put(Student_Course_SEASON,seasonSemester);
            result = sqLiteDatabaseMain.insert(Student_Course,null,val);
            studentCourseID++;
            if (result != -1){
                Log.d("DB Demo",Student_Course + " Insertion Successful");
            }else{
                Log.d("DB Demo",Student_Course + " Insertion Unsuccessful");
            }
        }
    }

    public List<String> getstudentSemesterDetails(String sessionStudentID) {
        ArrayList<String> studentSemesterDetails = new ArrayList<String>();
        String getSemesterDetails = "SELECT DISTINCT "+Student_Course_SEASON+" || \" \"|| "+
                Student_Course_YEAR+" FROM "+Student_Course+" WHERE "+Student_Course_StudentID+" = ?";
        Cursor gpn = sqLiteDatabaseMain.rawQuery(getSemesterDetails, new String[]{sessionStudentID});
            if (gpn.moveToFirst()){
                do {
                    String insertion = gpn.getString(0);
                    studentSemesterDetails.add(insertion);

                } while (gpn.moveToNext());
            }
        return studentSemesterDetails;
    }

    public ArrayList<String> getStudentCourseInfo(String sessionStudentID, String seasonFromSemsterCourseIntent, String yearFromSemsterCourseIntent) {
        String getStudentCourseInfo = "SELECT "+CourseID+", "+CourseName+", "+Student_Course_InstructorID+"  FROM "+Course+" , "+Student_Course+" WHERE "+Student_Course_StudentID+" = ? AND "+Student_Course_YEAR+" = ? AND "+Student_Course_SEASON+" = ? AND "+Student_Course_CourseID+" = "+CourseID;

        ArrayList<String> studentCourseInfo = new ArrayList<String>();
        Cursor gsc = sqLiteDatabaseMain.rawQuery(getStudentCourseInfo, new String[]{sessionStudentID,yearFromSemsterCourseIntent,seasonFromSemsterCourseIntent});
        if (gsc.moveToFirst()){
            do {
                String insertion = gsc.getString(0) +" ; " + gsc.getString(1) + " ; " + gsc.getString(2) ;
                studentCourseInfo.add(insertion);
            } while (gsc.moveToNext());
        }
        Log.d("studentCourseInfo",studentCourseInfo.toString());
        return studentCourseInfo;
    }

    public List<String> populateInstructorInSpinner() {
        String getInstructor = "SELECT *  FROM "+Instructor;
        ArrayList<String> instructorList = new ArrayList<String>();
        Log.d("Inside Helper",getInstructor);
        Cursor gi = sqLiteDatabaseMain.rawQuery(getInstructor, null);
        if (gi.moveToFirst()){
            do {
                instructorList.add(gi.getString(1));
            } while (gi.moveToNext());
        }
        return instructorList;
    }

    public List<String> populateCriteriaInSpinner() {
        String getCriteria = "SELECT *  FROM "+Criteria;
        ArrayList<String> criteriaList = new ArrayList<String>();
//        Log.d("Inside Helper",getCriteria);
        Cursor gi = sqLiteDatabaseMain.rawQuery(getCriteria, null);
        if (gi.moveToFirst()){
            do {
                criteriaList.add(gi.getString(1));
            } while (gi.moveToNext());
        }
        return criteriaList;
    }

    public void insertGrades(String sessionStudentID, String selectedCourseID,
                             String selectedCourseInstructor, String criteriaName,
                             String criteriaPercentage, String totalMarks, String marksObtained) {
        int gradeID = getGradeID();
        String gradeStudentCourseID = getGradeStudentCourseID(selectedCourseID , sessionStudentID);
        String gradeCriteriaID = getGradeCriteriaID(criteriaName);
        String instructorID = getinstructorID(selectedCourseInstructor);
        insertInstructorStudentCourse(gradeStudentCourseID,instructorID);
        Double weightAcheived;
        weightAcheived = ((Double.parseDouble(marksObtained)/Double.parseDouble(totalMarks))*(Double.parseDouble(criteriaPercentage)));
        long result;
        Log.d("Grade Values", gradeID+gradeStudentCourseID+gradeCriteriaID+totalMarks+marksObtained+criteriaPercentage+weightAcheived.toString());
        ContentValues val = new ContentValues();
        val.put(GradeID,gradeID);
        val.put(Grade_Student_CourseID,gradeStudentCourseID);
        val.put(Grade_CriteriaID,gradeCriteriaID);
        val.put(Marks_Total,totalMarks);
        val.put(Marks_Acheived,marksObtained);
        val.put(Weight_Total,criteriaPercentage);
        val.put(Weight_Acheived,weightAcheived.toString());
        result = sqLiteDatabaseMain.insert(Grades,null,val);

        if (result != -1){
            Log.d("DB Demo",Grades + " Insertion Successful");
        }else{
            Log.d("DB Demo",Grades + " Insertion Unsuccessful");
        }
    }

    private String getinstructorID(String selectedCourseInstructor) {

        String getinstructorID = "SELECT "+InstructorID+"  FROM "+ Instructor + " WHERE " + InstructorName + " = ?"  ;
        String instructorID = "";
        Cursor gi = sqLiteDatabaseMain.rawQuery(getinstructorID, new String[]{selectedCourseInstructor});
        if (gi.moveToFirst()){
            do {
                instructorID = gi.getString(0);
            } while (gi.moveToNext());
        }
        Log.d("getinstructorID",selectedCourseInstructor + "  - " +  instructorID);
        return instructorID;
    }

    private void insertInstructorStudentCourse(String gradeStudentCourseID, String instructorID) {
            String insertInstructorID = "SELECT * FROM "+Student_Course+" WHERE "+Student_CourseID+" = ?";
            try{
                Cursor cursor = sqLiteDatabaseMain.rawQuery(insertInstructorID, new String[]{gradeStudentCourseID});
                if (cursor!= null && cursor.getCount()==1){
                    cursor.moveToFirst();
                    ContentValues val = new ContentValues();
                    val.put(Student_Course_InstructorID,instructorID);
                    sqLiteDatabaseMain.update(Student_Course,val,Student_CourseID+ " = ?",new String[]{gradeStudentCourseID});
                }
            }catch(Exception e){
                Log.d("InstructorStudentCourse",e.getMessage());
            }

    }

    public String getGradeCriteriaID(String criteriaName) {
        String getGradeCriteriaID = "SELECT "+CriteriaID+"  FROM "+ Criteria + " WHERE " + CriteriaName + " = ?"  ;
        String gradeCriteriaID = "";
        Cursor gi = sqLiteDatabaseMain.rawQuery(getGradeCriteriaID, new String[]{criteriaName});
        if (gi.moveToFirst()){
            do {
                gradeCriteriaID = gi.getString(0);
            } while (gi.moveToNext());
        }
        return gradeCriteriaID;
    }

    private String getGradeStudentCourseID(String selectedCourseID, String sessionStudentID) {
        String getGradeStudentCourseID = "SELECT "+Student_CourseID+"  FROM "+ Student_Course + " WHERE " + Student_Course_CourseID + " = ? AND " +Student_Course_StudentID + " = ?"  ;
        String gradeStudentCourseID = "";
        Cursor gi = sqLiteDatabaseMain.rawQuery(getGradeStudentCourseID, new String[]{selectedCourseID , sessionStudentID});
        if (gi.moveToFirst()){
            do {
                gradeStudentCourseID = gi.getString(0);
            } while (gi.moveToNext());
        }
        return gradeStudentCourseID;
    }

    private int getGradeID() {
        String gradeID = "SELECT *  FROM " + Grades;
        ArrayList<String> criteriaList = new ArrayList<String>();
        Cursor gi = sqLiteDatabaseMain.rawQuery(gradeID, null);
        int gradeCount  = gi.getCount() + 1;
        return gradeCount;
    }

    public String getInstructorName(String instructorID) {
        String getInstructorName = "SELECT "+InstructorName+"  FROM "+ Instructor + " WHERE " + InstructorID + " = ? "  ;
        String instructorName = "";
        Cursor gi = sqLiteDatabaseMain.rawQuery(getInstructorName, new String[]{instructorID});
        if (gi.moveToFirst()){
            do {
                instructorName = gi.getString(0);
            } while (gi.moveToNext());
        }
        return instructorName;
    }

    public ArrayList<String> getGrades(String sessionStudentID, String selectedCourseID) {
        ArrayList<String> gradesList = new ArrayList<String>();
        String gradeStudentCourseID = getGradeStudentCourseID(selectedCourseID , sessionStudentID);
        gradesList.add("Criteria;Weightage;Total Marks;Marks Obtained;Weightage Acheived;GradeID");
        String getGrades = "SELECT "+CriteriaName+", "+Weight_Total+","+Marks_Total+","+Marks_Acheived+","+Weight_Acheived+","+GradeID+" FROM "+Grades+", "+Criteria+" WHERE "+Grade_Student_CourseID+"  = ? AND "+CriteriaID+" = "+Grade_CriteriaID  ;
        Cursor gg = sqLiteDatabaseMain.rawQuery(getGrades, new String[]{gradeStudentCourseID});
        if (gg.moveToFirst()){
            do {
                gradesList.add(gg.getString(0) + ";" + gg.getString(1) + ";" + gg.getString(2) + ";" + gg.getString(3) + ";" + gg.getString(4)+ ";" + gg.getString(5));
            } while (gg.moveToNext());
        }
        Log.d("gradesList",gradesList.toString());
        return gradesList;
    }

    public Double getPercentage(String sessionStudentID, String selectedCourseID) {
        double weightageTotal=0;
        double weightageAcheived = 0;
        ArrayList<String> gradesList = getGrades(sessionStudentID,selectedCourseID);
        Log.d("gradesList",gradesList.toString());
        for(int i = 1; i< gradesList.size();i++){
            String data[] = gradesList.get(i).split(";");
            weightageTotal += Double.parseDouble(data[1]);
            weightageAcheived += Double.parseDouble(data[4]);
        }
        double percentage = (weightageAcheived*100)/weightageTotal;
        return percentage;
    }

    public String[] getGradeGPA(Double percentage) {
        String getGPATable = "SELECT * FROM "+GPA+" WHERE "+MinimumPercentage+" <= ? AND  "+MaximumPercentage+" >= ?";
        String[] GradeGPA = new String[2];
        Cursor gg = sqLiteDatabaseMain.rawQuery(getGPATable, new String[]{percentage.toString(),percentage.toString()});
        if (gg.moveToFirst()){
            do {
                GradeGPA[0] = gg.getString(0);
                GradeGPA[1] = gg.getString(3);
            } while (gg.moveToNext());
        }

        return GradeGPA;
    }

    public int getTotalCredits(String sessionStudentID){
        String courseCredit = "Select * FROM "+Student_Course+" where "+Student_Course_StudentID+" = ?";
        Cursor creditVal = sqLiteDatabaseMain.rawQuery(courseCredit, new String[]{sessionStudentID});
        int getCoursesListCount = creditVal.getCount();
        int calculateAmountOfCredits = getCoursesListCount * 3;
        return calculateAmountOfCredits;
    }

    public ArrayList<String> getStats(String sessionStudentID){
        String courseQuery = "Select * FROM "+Student_Course+" where "+Student_Course_StudentID+" = ?";
        Cursor cursor = sqLiteDatabaseMain.rawQuery(courseQuery, new String[]{sessionStudentID});

        ArrayList<String> coursesList = new ArrayList<String>();
        ArrayList<Double> grades = new ArrayList<Double>();
        double weightageAcheived = 0;
        ArrayList<String> statsList = new ArrayList<String>();

        if (cursor.moveToFirst()){
            do {
                String course = cursor.getString(3);
                coursesList.add(course);
            } while (cursor.moveToNext());
        }
        for (String course : coursesList){
            Double percentage = getPercentage(sessionStudentID,course);
            String[] gradeGPA = getGradeGPA(percentage);
            if(gradeGPA[0]==null){
                gradeGPA[0] = "0";
            }
            statsList.add(course+" ; "+gradeGPA[0]);

//            ArrayList<String> gradesList = getGrades(sessionStudentID,course);
//
//            for(int i = 1; i< gradesList.size();i++){
//                String data[] = gradesList.get(i).split(";");
//                weightageAcheived += Double.parseDouble(data[4]);
//                grades.add(weightageAcheived);
//            }
        }
        return statsList;
    }

    public Double getPercentageAll(String sessionStudentID) {
        Log.d("getPercentageAll",sessionStudentID);
        double weightageTotal=0;
        double weightageAcheived = 0;
        ArrayList<String> gradesList = getGradesAll(sessionStudentID);
        for(int i = 1; i< gradesList.size();i++){
            String data[] = gradesList.get(i).split(";");
            weightageTotal += Double.parseDouble(data[3]);
            weightageAcheived += Double.parseDouble(data[4]);
        }
        double percentage = (weightageAcheived*100)/weightageTotal;
        Log.d("gradesList", gradesList.toString());
        Log.d("percentage", String.valueOf(percentage));
        return percentage;
    }

    private ArrayList<String> getGradesAll(String sessionStudentID) {
        ArrayList<String> gradesList = new ArrayList<String>();
        ArrayList<String> gradeStudentCourseIDAll = getGradeStudentCourseIDAll(sessionStudentID);
        Log.d("gradeStudentCourseIDAll", gradeStudentCourseIDAll.toString());
        gradesList.add("Criteria;Weightage;Total Marks;Marks Obtained;Weightage Acheived");
        String getGrades = "SELECT "+CriteriaName+", "+Marks_Total+","+Marks_Acheived+","+Weight_Total+","+Weight_Acheived+" FROM "+Grades+", "+Criteria+" WHERE "+Grade_Student_CourseID+"  = ? AND "+CriteriaID+" = "+Grade_CriteriaID  ;
        for (int i=0;i<gradeStudentCourseIDAll.size();i++){
            Cursor gg = sqLiteDatabaseMain.rawQuery(getGrades, new String[]{gradeStudentCourseIDAll.get(i)});
            if (gg.moveToFirst()){
                do {
                    gradesList.add(gg.getString(0) + ";" + gg.getString(1) + ";" + gg.getString(2) + ";" + gg.getString(3) + ";" + gg.getString(4));
                } while (gg.moveToNext());
            }
        }
        return gradesList;
    }

    private ArrayList<String> getGradeStudentCourseIDAll(String sessionStudentID) {
        String getGradeStudentCourseIDAll = "SELECT "+Student_CourseID+"  FROM "+ Student_Course + " WHERE " +Student_Course_StudentID + " = ?"  ;
        ArrayList<String> gradeStudentCourseIDAll = new ArrayList<String>();
        Cursor gi = sqLiteDatabaseMain.rawQuery(getGradeStudentCourseIDAll, new String[]{sessionStudentID});
        if (gi.moveToFirst()){
            do {
                gradeStudentCourseIDAll.add(gi.getString(0));
            } while (gi.moveToNext());
        }
        return gradeStudentCourseIDAll;
    }

    public void upgradeGrade(String gradeID, String gradeCriteriaID, String marksObtained, String totalMarks, String weightageTotal) {
        Double weightAchieved;
        weightAchieved = ((Double.parseDouble(marksObtained)/Double.parseDouble(totalMarks))*(Double.parseDouble(weightageTotal)));
        String upgradeGradeTable = "Select * FROM "+ Grades +" WHERE " + GradeID + " = ?";
        try{
            Cursor cursor = sqLiteDatabaseMain.rawQuery(upgradeGradeTable,new String[]{gradeID});
            if (cursor!= null && cursor.getCount()==1){
                cursor.moveToFirst();
                ContentValues val = new ContentValues();
                val.put(Grade_CriteriaID,gradeCriteriaID);
                val.put(Marks_Acheived,marksObtained);
                val.put(Marks_Total,totalMarks);
                val.put(Weight_Total,weightageTotal);
                val.put(Weight_Acheived,weightAchieved);

                sqLiteDatabaseMain.update(Grades,val,GradeID+ " = ?",new String[]{gradeID});
                Log.d("Grades Updated",GradeID.toString());
            }
        }catch(Exception e){

        }
    }
}
