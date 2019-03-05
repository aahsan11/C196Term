package com.example.meena.c196termtracker;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AssessmentDetails extends AppCompatActivity {
    Toolbar assessmentToolbar;
    String assessmenttitle;

    EditText title, type, date,time;
    String Title;
    Button savebutton;
    String assessmentid;
    String vars;
    String termname;
    String Title1,Type,Date,Time;
    CheckBox updateassessmentcheck;
    Spinner mySpinner;




    String ids;
    SQLiteDatabase database;
            String tablename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);
        mySpinner=findViewById(R.id.spinner);

        updateassessmentcheck=findViewById(R.id.updateassessmentstartcheck);

        title=findViewById(R.id.title);
       // type=findViewById(R.id.type);
        date=findViewById(R.id.date);
        time=findViewById(R.id.time);



        Bundle bundle1=getIntent().getExtras();
        ids=bundle1.getString("ids");
        Title=bundle1.getString("title");

        savebutton=findViewById(R.id.saveassessment);




        assessmentToolbar=findViewById(R.id.assessmenttoolbar);
        assessmentToolbar.setTitle("Assessment Details");
        setSupportActionBar(assessmentToolbar);

        assessmentToolbar.setNavigationIcon(R.drawable.navigation_back);




        Bundle bundle=getIntent().getExtras();
        assessmenttitle=bundle.getString("assessmenttitle");
        title.setText(Title);

        Bundle bundles=getIntent().getExtras();
        termname=bundles.getString("termname");


        assessmentToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AssessmentDetails.this, AllAssessments.class);
                intent.putExtra("ids", ids);
                intent.putExtra("coursename", vars);
                intent.putExtra("termname", termname);
                startActivity(intent);
            }
        });



        Myhelper helper=new Myhelper(AssessmentDetails.this);
        final SQLiteDatabase database=helper.getReadableDatabase();


        Cursor assessmenttype =database.rawQuery( "Select assessmentType from assessments where assessmentTitle =? and courseID=? ", new String[]{Title, ids});
        while (assessmenttype.moveToNext()){
            //type.setText(assessmenttype.getString(0));
            final ArrayList<String> arra= new ArrayList<String>();


            arra.add(assessmenttype.getString(0));


            Toast.makeText(this, "no"+arra, Toast.LENGTH_SHORT).show();

            ArrayAdapter adapter=new ArrayAdapter(AssessmentDetails.this,
                    android.R.layout.simple_list_item_1,arra);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            mySpinner.setAdapter(adapter);

           // mySpinner.setSelection(arra.indexOf(1));

        }
        Cursor assessmentdate =database.rawQuery( "Select assessmentDate from assessments where assessmentTitle =? and courseID=? ", new String[]{Title, ids});
        while (assessmentdate.moveToNext()){
            date.setText(assessmentdate.getString(0));
        }
        Cursor assessmenttime =database.rawQuery( "Select assessmentTime from assessments where assessmentTitle =? and courseID=? ", new String[]{Title, ids});
        while (assessmenttime.moveToNext()){
            time.setText(assessmenttime.getString(0));
        }


        title.setEnabled(false);
//        type.setEnabled(false);
        date.setEnabled(false);
        time.setEnabled(false);




        Myhelper helper1=new Myhelper(AssessmentDetails.this);
        final SQLiteDatabase database1=helper1.getReadableDatabase();
        Cursor assessment =database1.rawQuery( "Select _id from assessments where assessmentTitle =? and courseID=?", new String[]{title.getText().toString(), ids});



        if (assessment.getCount() != 0 && assessment!=null) {
            if (assessment.moveToFirst()) {
                do {
                    assessmentid  = assessment.getString(assessment.getColumnIndex("_id"));

                } while (assessment.moveToNext());

            }
        }

        Bundle bundle2=getIntent().getExtras();
        vars=bundle2.getString("coursename");









        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Myhelper myhelper= new Myhelper(AssessmentDetails.this);
                SQLiteDatabase database1=myhelper.getWritableDatabase();


                ContentValues values= new ContentValues();
                values.put("assessmentTitle", title.getText().toString());
                values.put("assessmentType", mySpinner.getSelectedItem().toString());
                values.put("assessmentDate", date.getText().toString());
                values.put("assessmentTime",time.getText().toString());

                if (updateassessmentcheck.isChecked()){
                    values.put("assessmentStartAlert",title.getText().toString());
                }

                else{
                    values.put("assessmentStartAlert", "");
                }

                Title1=title.getText().toString();
               // Type=type.getText().toString();
                Date=date.getText().toString();
                Time=time.getText().toString();

                if(Title1.isEmpty() || Date.isEmpty() || Time.isEmpty() || Date.length()!=10   ){

                    AlertDialog.Builder atldial= new AlertDialog.Builder(AssessmentDetails.this);
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


                    database1.update("assessments", values, "_id=?", new String[]{assessmentid});
                    title.setEnabled(false);
                    //type.setEnabled(false);
                    date.setEnabled(false);
                    time.setEnabled(false);
                    savebutton.setEnabled(false);
                }
            }
        });




    }








    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.men_assessmentdetails,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.delete:


                Myhelper helper=new Myhelper(AssessmentDetails.this);
                 database=helper.getWritableDatabase();
                 tablename="assessments";


                AlertDialog.Builder atldial= new AlertDialog.Builder(AssessmentDetails.this);
                atldial.setMessage("Do you sure you  want to delete??").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        database.delete(tablename, "assessmentTitle=? AND courseID=?", new String[] {Title, ids});

                        Intent intent=new Intent(AssessmentDetails.this, AllAssessments.class);
                        intent.putExtra("ids", ids);
                        intent.putExtra("coursename",vars);
                        intent.putExtra("termname", termname);
                        startActivity(intent);

                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                    }
                });

                AlertDialog alert=atldial.create();
                alert.setTitle("Confirmation box");
                alert.show();





                return true;

            case R.id.edit:
                title.setEnabled(true);
//                type  .setEnabled(true);
                date.setEnabled(true);
                time.setEnabled(true);


                final String [] array=getResources().getStringArray(R.array.Type);

                ArrayAdapter adapter=new ArrayAdapter(AssessmentDetails.this,
                        android.R.layout.simple_list_item_1,array);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                mySpinner.setAdapter(adapter);

                savebutton.setVisibility(View.VISIBLE);








            default:
                return false;
        }

    }
}