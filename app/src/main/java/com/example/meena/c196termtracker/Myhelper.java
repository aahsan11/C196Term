package com.example.meena.c196termtracker;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Myhelper extends SQLiteOpenHelper {




    private static final String DATABASE_NAME = "wgu_terms.db";
    private static final int DATABASE_VERSION = 1;



    public static final String TABLE_TERMS = "terms";
    public static final String TERMS_TABLE_ID = "_id";
    public static final String TERM_NAME = "termName";
    public static final String TERM_START = "termStart";
    public static final String TERM_END = "termEnd";

    public static final String TABLE_COURSES= "courses";
    public static final String COURSE_TABLE_ID = "_id";
    public static final String COURSE_NAME = "courseName";
    public static final String TERM_NAME1 = "termName";
    public static final String COURSE_START = "courseStart";
    public static final String COURSE_END = "courseEnd";
    public static final String COURSE_STATUS = "courseStatus";
    public static final String COURSE_MENTOR = "courseMentor";
    public static final String COURSE_MENTOR_PHONE = "courseMentorPhone";
    public static final String COURSE_MENTOR_EMAIL = "courseMentorEmail";
    public static final String COURSE_Start_Alert = "courseStartAlert";
    public static final String COURSE_End_Alert = "courseEndAlert";




    public static final String TABLE_ASSESSMENTS= "assessments";
    public static final String ASSESSMENT_TABLE_ID = "_id";
    public static final String COURSE_ID = "courseID";
    public static final String ASSESSMENT_TITLE = "assessmentTitle";
    public static final String ASSESSMENT_TYPE = "assessmentType";
    public static final String ASSESSMENT_DATE = "assessmentDate";
    public static final String ASSESSMENT_TIME = "assessmentTime";
    public static final String ASSESSMENT_Start_Alert = "assessmentStartAlert";


    public static final String TABLE_NOTES= "notes";
    public static final String NOTES_TABLE_ID = "_id";
    public static final String NOTES_TITLE = "notesTitle";
    public static final String NOTES_DETAILS = "notesDetails";
    public static final String COURSEID_FINAL = "courseID";




    private static final String TERMS_TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_TERMS + " (" +
                    TERMS_TABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TERM_NAME + " TEXT, " +
                    TERM_START + " TEXT, " +
                    TERM_END + " TEXT " +

                    ")";

    private static final String COURSE_TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_COURSES + " (" +
                    COURSE_TABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TERM_NAME1 + " TEXT, " +
                    COURSE_NAME + " TEXT, " +
                    COURSE_START + " TEXT, " +
                    COURSE_STATUS + " TEXT, " +
                    COURSE_END + " TEXT, " +
                    COURSE_MENTOR + " TEXT, " +
                    COURSE_MENTOR_PHONE + " TEXT, " +
                    COURSE_Start_Alert  + " TEXT, " +
                    COURSE_End_Alert    + " TEXT, " +
                    COURSE_MENTOR_EMAIL + " TEXT " +

                    ")";

    private static final String ASSESSMENT_TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_ASSESSMENTS + " (" +
                    ASSESSMENT_TABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COURSE_ID + " TEXT, " +
                    ASSESSMENT_TITLE + " TEXT, " +
                    ASSESSMENT_TYPE + " TEXT, " +
                    ASSESSMENT_DATE + " TEXT, " +
                    ASSESSMENT_Start_Alert + " TEXT, " +
                    ASSESSMENT_TIME + " TEXT " +


                    ")";


    private static final String NOTES_TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NOTES + " (" +
                    NOTES_TABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NOTES_TITLE + " TEXT, " +
                    NOTES_DETAILS + " TEXT, " +
                    COURSEID_FINAL + " TEXT " +

                    ")";






    public Myhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TERMS_TABLE_CREATE);
        db.execSQL(COURSE_TABLE_CREATE);
        db.execSQL(ASSESSMENT_TABLE_CREATE);
        db.execSQL(NOTES_TABLE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TERMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ASSESSMENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);

        onCreate(db);

    }

    public Cursor viewData(){
     SQLiteDatabase db=this.getWritableDatabase();
     String query= "Select * from "+TABLE_TERMS;
        Cursor cursor=db.rawQuery(query, null);
        return cursor;
    }




}
