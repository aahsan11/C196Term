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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;


public class AllAssesmentAdd extends AppCompatActivity {
    EditText title, type, date, time;
    String termname;
    Button saveassessment;
    String ids;
    String vars;
    Toolbar toolbar;
    CheckBox assessmentstartcheck;
    String Title,Type,Date,Time;
    Spinner mySpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_assesment_add);

        assessmentstartcheck=findViewById(R.id.assessmentstartcheck);

        Bundle bundle=getIntent().getExtras();
        ids=bundle.getString("ids");

        title=findViewById(R.id.Title);
        type=findViewById(R.id.Type);
        date=findViewById(R.id.Date);
        time=findViewById(R.id.Time);
         mySpinner=findViewById(R.id.spinner);


        toolbar=findViewById(R.id.allassessmentsaddtoolbar);
        toolbar.setTitle("Add Assessments");
        setSupportActionBar(toolbar);

        final String [] array=getResources().getStringArray(R.array.Type);

        ArrayAdapter adapter=new ArrayAdapter(AllAssesmentAdd.this,
                android.R.layout.simple_list_item_1,array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mySpinner.setAdapter(adapter);







        Bundle bundle2=getIntent().getExtras();
        vars=bundle2.getString("coursename");


        Bundle bundle3=getIntent().getExtras();
        termname=bundle3.getString("termname");
        saveassessment=findViewById(R.id.SaveAssessment);
        saveassessment.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Title=title.getText().toString();

                Date=date.getText().toString();
                Time=time.getText().toString();



                if(Title.isEmpty() || Date.isEmpty() || Time.isEmpty() || Date.length()!=10   ){

                    AlertDialog.Builder atldial= new AlertDialog.Builder(AllAssesmentAdd.this);
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

                    Save();
                    Intent intent = new Intent(AllAssesmentAdd.this, AllAssessments.class);
                    intent.putExtra("ids", ids);
                    intent.putExtra("coursename", vars);
                    intent.putExtra("termname", termname);
                    startActivity(intent);
                }
            }
        });

        toolbar.setNavigationIcon(R.drawable.navigation_back);




        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent(AllAssesmentAdd.this, AllAssessments.class);
                intent.putExtra("ids", ids);
                intent.putExtra("coursename",vars);
                intent.putExtra("termname",termname);

                startActivity(intent);
            }
        });

    }

    public void Save(){
        Myhelper helper=new Myhelper(AllAssesmentAdd.this);

        SQLiteDatabase database=helper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("assessmentTitle", title.getText().toString());
        values.put("courseID", ids);

        values.put("assessmentType", mySpinner.getSelectedItem().toString());
        values.put("assessmentTime", time.getText().toString());
        values.put("assessmentDate",date.getText().toString());


        if (assessmentstartcheck.isChecked()){
            values.put("assessmentStartAlert",title.getText().toString());


        }

        database.insert("assessments", null, values);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_allassessmentadd,menu);

        return true;
    }
}
