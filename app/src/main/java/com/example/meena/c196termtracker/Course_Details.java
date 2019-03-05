package com.example.meena.c196termtracker;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;

import android.database.sqlite.SQLiteDatabase;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;


public class Course_Details extends AppCompatActivity {
    EditText course, startdate, enddate, status, term, mentorname, mentorphone, mentoremail;
    Button savebutton;
    Toolbar toolbar;
    CheckBox coursestartcheck;
    CheckBox courseendcheck;
    ContentValues contentValues;
    String Course, Startdate, Enddate, Status, Mentorname, Mentorphone, Mentoremail;



    String termname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course__details);

        toolbar=findViewById(R.id.coursedetailstoolbar);
        toolbar.setTitle("Add Course");
        setSupportActionBar(toolbar);

        coursestartcheck=findViewById(R.id.coursestartcheck);
        courseendcheck=findViewById(R.id.courseendcheck);




        term=findViewById(R.id.TermName);
        term.setEnabled(false);

        Bundle bundle=getIntent().getExtras();
        termname=bundle.getString("termname");

        term.setText(termname);

        course=findViewById(R.id.CourseName);
        startdate=findViewById(R.id.StartDate);
        enddate=findViewById(R.id.EndDate);
        status=findViewById(R.id.Status);
        mentorname=findViewById(R.id.MentorName);
        mentorphone=findViewById(R.id.MentorPhone);
        mentoremail=findViewById(R.id.MentorEmail);
        savebutton=findViewById(R.id.SaveButton);

        Bundle bundle1=getIntent().getExtras();
        termname=bundle1.getString("termname");

        toolbar.setNavigationIcon(R.drawable.navigation_back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Course_Details.this, View_Courses.class);
                intent.putExtra("termname", termname);

                startActivity(intent);

            }
        });

        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Course=course.getText().toString();
                Startdate=startdate.getText().toString();
                Enddate=enddate.getText().toString();
                Status=status.getText().toString();
                Mentorname=mentorname.getText().toString();
                Mentoremail=mentoremail.getText().toString();
                Mentorphone=mentorphone.getText().toString();

                if(Course.isEmpty() || Startdate.isEmpty() || Enddate.isEmpty() ||
                Status.isEmpty() || Mentorname.isEmpty() ||Mentorphone.isEmpty() || Mentoremail.isEmpty() || Startdate.length()!=10 || Enddate.length()!=10){

                    AlertDialog.Builder atldial= new AlertDialog.Builder(Course_Details.this);
                    atldial.setMessage("Please fill all fields correctly").setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.cancel();

                        }
                    });

                    AlertDialog alert=atldial.create();
                    alert.setTitle("Warning");
                    alert.show();



                }



                else {

                    save();
                    Intent intent = new Intent(Course_Details.this, View_Courses.class);
                    intent.putExtra("termname", termname);
                    startActivity(intent);
                }
            }
        });



    }

    public void save(){

        Myhelper myhelper=new Myhelper(Course_Details.this);
        SQLiteDatabase database=myhelper.getWritableDatabase();
        contentValues=new ContentValues();

        contentValues.put("courseName", course.getText().toString());
        contentValues.put("courseStart", startdate.getText().toString());
        contentValues.put("courseEnd", enddate.getText().toString());
        contentValues.put("courseStatus", status.getText().toString());
        contentValues.put("termName",term.getText().toString() );
        contentValues.put("courseMentor", mentorname.getText().toString());
        contentValues.put("courseMentorPhone", mentorphone.getText().toString());
        contentValues.put("courseMentorEmail", mentoremail.getText().toString());

        if (coursestartcheck.isChecked()){
            contentValues.put("courseStartAlert", course.getText().toString());


        }

        if (courseendcheck.isChecked()){
            contentValues.put("courseEndAlert", course.getText().toString());

        }


        database.insert("courses",null,contentValues);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_coursedetails,menu);

        return true;
    }

}
