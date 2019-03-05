package com.example.meena.c196termtracker;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class Course_Info extends AppCompatActivity {
    EditText course, startdate, enddate, status, term, mentorname, mentorphone, mentoremail;
    Button savebutton;
    Toolbar toolbar;
    String vars;
    Button viewassessmentsbutton, viewnotesbutton;
     String ids;
     String termname;
     String tablename;
     SQLiteDatabase database;
    CheckBox updatecoursestartcheck;
    CheckBox updatecourseendcheck;
    SQLiteDatabase database2;
    String Course, Startdate, Enddate, Statu, Mentorname, Mentorphone, Mentoremail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course__info);
        toolbar=findViewById(R.id.coursetoolbar);
        toolbar.setTitle("Course Details");
        setSupportActionBar(toolbar);
        updatecoursestartcheck=findViewById(R.id.updatecoursestartcheck);
        updatecourseendcheck=findViewById(R.id.updatecourseendcheck);


        Bundle bundle1=getIntent().getExtras();
        termname=bundle1.getString("termname");

        term=findViewById(R.id.termname);

        course=findViewById(R.id.coursename);
        startdate=findViewById(R.id.startdate);
        enddate=findViewById(R.id.enddate);
        status=findViewById(R.id.status);
        mentorname=findViewById(R.id.mentorname);
        mentorphone=findViewById(R.id.mentorphone);
        mentoremail=findViewById(R.id.mentoremail);
        savebutton=findViewById(R.id.savebutton);

        course.setEnabled(false);
        startdate.setEnabled(false);
        enddate.setEnabled(false);
        status.setEnabled(false);
        mentorname.setEnabled(false);
        mentorphone.setEnabled(false);
        mentoremail.setEnabled(false);
        term.setEnabled(false);

        Bundle bundle=getIntent().getExtras();
        vars=bundle.getString("coursename");
        course.setText(vars);

        Myhelper helper1=new Myhelper(Course_Info.this);
        final SQLiteDatabase database1=helper1.getReadableDatabase();
        Cursor c =database1.rawQuery( "Select _id from courses where courseName =?", new String[]{course.getText().toString()});
        while (c.moveToNext()){
            ids=(c.getString(0));
        }

       // Toast.makeText(this, "" + ids, Toast.LENGTH_SHORT).show();








        Myhelper helper=new Myhelper(Course_Info.this);
        final SQLiteDatabase database=helper.getReadableDatabase();
        Cursor courseStart =database.rawQuery( "Select courseStart from courses where courseName =?", new String[]{vars});
        while (courseStart.moveToNext()){
            startdate.setText(courseStart.getString(0));
        }
        Cursor endDate =database.rawQuery( "Select courseEnd from courses where courseName =?", new String[]{vars});
        while (endDate.moveToNext()){
            enddate.setText(endDate.getString(0));
        }
        Cursor Status =database.rawQuery( "Select courseStatus from courses where courseName =?", new String[]{vars});
        while (Status.moveToNext()){
            status.setText(Status.getString(0));
        }
        Cursor Term =database.rawQuery( "Select termName from courses where courseName =?", new String[]{vars});
        while (Term.moveToNext()){
            term.setText(Term.getString(0));
        }
        Cursor mentorName =database.rawQuery( "Select courseMentor from courses where courseName =?", new String[]{vars});
        while (mentorName.moveToNext()){
            mentorname.setText(mentorName.getString(0));
        }
        Cursor mentorPhone =database.rawQuery( "Select courseMentorPhone from courses where courseName =?", new String[]{vars});
        while (mentorPhone.moveToNext()){
            mentorphone.setText(mentorPhone.getString(0));
        }
        Cursor mentorEmail =database.rawQuery( "Select courseMentorEmail from courses where courseName =?", new String[]{vars});
        while (mentorEmail.moveToNext()){
            mentoremail.setText(mentorEmail.getString(0));
        }

        viewnotesbutton=findViewById(R.id.ViewNotes);
        viewnotesbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Course_Info.this, Notes.class);

                intent.putExtra("ids", ids);
                intent.putExtra("coursename",vars);
                intent.putExtra("termname", termname);
                startActivity(intent);
            }
        });

        toolbar.setNavigationIcon(R.drawable.navigation_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent(Course_Info.this, View_Courses.class);

                intent.putExtra("termname", termname);
                startActivity(intent);
            }
        });

        savebutton=findViewById(R.id.savebutton);
        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Myhelper myhelper= new Myhelper(Course_Info.this);
                SQLiteDatabase database1=myhelper.getWritableDatabase();

                ContentValues values= new ContentValues();
                values.put("termName", term.getText().toString());
                values.put("coursename",course.getText().toString());
                values.put("courseStart", startdate.getText().toString());
                values.put("courseEnd", enddate.getText().toString());
                values.put("courseStatus", status.getText().toString());
                values.put("courseMentor", mentorname.getText().toString());
                values.put("courseMentorPhone", mentorphone.getText().toString());
                values.put("courseMentorEmail", mentoremail.getText().toString());

                if (updatecoursestartcheck.isChecked()){
                    values.put("courseStartAlert", course.getText().toString());
                }
                else {
                    values.put("courseStartAlert","");
                }

                if (updatecourseendcheck.isChecked()){
                    values.put("courseEndAlert", course.getText().toString());
                }
                else {
                    values.put("courseEndAlert","");
                }




                Course=course.getText().toString();
                Startdate=startdate.getText().toString();
                Enddate=enddate.getText().toString();
                Statu=status.getText().toString();
                Mentorname=mentorname.getText().toString();
                Mentoremail=mentoremail.getText().toString();
                Mentorphone=mentorphone.getText().toString();

                if(Course.isEmpty() || Startdate.isEmpty() || Enddate.isEmpty() ||
                        Statu.isEmpty() || Mentorname.isEmpty() || Mentorphone.isEmpty() || Mentoremail.isEmpty()
                        || Startdate.length()!=10 || Enddate.length()!=10){

                    AlertDialog.Builder atldial= new AlertDialog.Builder(Course_Info.this);
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




                else

                {


                    database.update("courses", values, "courseName=? AND termName=?", new String[]{vars, term.getText().toString()});
                    course.setEnabled(false);
                    startdate.setEnabled(false);
                    enddate.setEnabled(false);
                    status.setEnabled(false);
                    mentorname.setEnabled(false);
                    mentorphone.setEnabled(false);
                    mentoremail.setEnabled(false);
                    savebutton.setVisibility(View.INVISIBLE);
                }

            }
        });

        viewassessmentsbutton=findViewById(R.id.ViewAssessmentButton);
        viewassessmentsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Course_Info.this, AllAssessments.class);
                intent.putExtra("ids", ids);
                intent.putExtra("coursename",vars);
                intent.putExtra("termname", termname);

                startActivity(intent);
            }
        });







    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_course,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.delete:


                Myhelper helper2=new Myhelper(Course_Info.this);
                database2=helper2.getReadableDatabase();


                Cursor cur =database2.rawQuery( "Select _id from assessments where courseID =?", new String[]{ids});
                if (cur.moveToNext()){



                    AlertDialog.Builder atldial= new AlertDialog.Builder(Course_Info.this);
                    atldial.setMessage("There are assessments asscoicated wtih this course??").setPositiveButton("ok", new DialogInterface.OnClickListener() {
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


                    Myhelper helper = new Myhelper(Course_Info.this);
                    database = helper.getWritableDatabase();
                    tablename = "courses";


                    AlertDialog.Builder atldial = new AlertDialog.Builder(Course_Info.this);
                    atldial.setMessage("Are you sure you  want to delete??").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            database.delete(tablename, "courseName=? AND termName=?", new String[]{course.getText().toString(), term.getText().toString()});

                            Intent intent = new Intent(Course_Info.this, View_Courses.class);
                            intent.putExtra("termname", termname);
                            startActivity(intent);

                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    AlertDialog alert = atldial.create();
                    alert.setTitle("Confirmation box");
                    alert.show();
                }

                return true;

            case R.id.edit:
                course.setEnabled(true);
                startdate.setEnabled(true);
                enddate.setEnabled(true);
                status.setEnabled(true);
                mentorname.setEnabled(true);
                mentorphone.setEnabled(true);
                mentoremail.setEnabled(true);
                savebutton.setVisibility(View.VISIBLE);








            default:
                return false;
        }

    }
}

