package com.example.meena.c196termtracker;

import android.content.Intent;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;


import java.util.ArrayList;

public class AllAssessments extends AppCompatActivity {
    ImageView addbutton;
    ArrayList <String> listItem;
    ArrayAdapter adapter;
    ListView listview;
    String termname;
    String ids;
    Toolbar assessmentToolbar;
    String vars;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_assessments);


        Bundle bundle=getIntent().getExtras();
        ids=bundle.getString("ids");

        Bundle bundle2=getIntent().getExtras();
        vars=bundle2.getString("coursename");
      //  Toast.makeText(this, ""+ids, Toast.LENGTH_SHORT).show();

        addbutton=findViewById(R.id.addbutton);
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(AllAssessments.this, AllAssesmentAdd.class);
                intent.putExtra("ids", ids);
                intent.putExtra("coursename",vars);
                intent.putExtra("termname", termname);
                startActivity(intent);
            }
        });
        assessmentToolbar=findViewById(R.id.assessmentstoolbar);
        assessmentToolbar.setTitle(" All Assessments");
        setSupportActionBar(assessmentToolbar);

        listItem=new ArrayList<>();

        Bundle bundle3=getIntent().getExtras();
        termname=bundle3.getString("termname");



        listview=findViewById(R.id.assessmentslistview);


        Myhelper helper=new Myhelper(AllAssessments.this);
        SQLiteDatabase database=helper.getReadableDatabase();

        final String MY_QUERY = "SELECT assessmentTitle FROM assessments a INNER JOIN courses b ON a.courseID=b._id WHERE a.courseID=?";



        Cursor c=database.rawQuery(MY_QUERY, new String[] {ids});


        while (c.moveToNext()){
            listItem.add(c.getString(0));


        }
        adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listItem);
        listview.setAdapter(adapter);


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {






                Intent myintent=new Intent(AllAssessments.this, AssessmentDetails.class);




                myintent.putExtra("title", (String)listview.getAdapter().getItem(position));
                myintent.putExtra("ids", ids);
                myintent.putExtra("coursename",vars);
                myintent.putExtra("termname",termname);
                startActivity(myintent);

            }
        });


        assessmentToolbar.setNavigationIcon(R.drawable.navigation_back);
        assessmentToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(AllAssessments.this, Course_Info.class);
                intent.putExtra("coursename",vars);
                intent.putExtra("termname",termname);
                intent.putExtra("ids",ids);
                startActivity(intent);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_allassessments,menu);

        return true;
    }
}
