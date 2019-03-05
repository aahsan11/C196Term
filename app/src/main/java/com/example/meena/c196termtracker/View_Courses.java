package com.example.meena.c196termtracker;

import android.content.Intent;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class View_Courses extends AppCompatActivity {

    ImageView addcoursesbutton;
    ListView listView;
    ArrayList<String> listItem;
    ArrayAdapter adapter;
    Toolbar toolbar;
    String termname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__courses);

        toolbar=findViewById(R.id.viewcoursestoolbar);
        toolbar.setTitle("All Courses");
        setSupportActionBar(toolbar);


        Bundle bundle=getIntent().getExtras();
        termname=bundle.getString("termname");


        listItem=new ArrayList<>();
        addcoursesbutton=findViewById(R.id.coursesaddbutton);

        addcoursesbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent=new Intent(View_Courses.this, Course_Details.class);
               intent.putExtra("termname", termname);
               startActivity(intent);

            }
        });




        listView=findViewById(R.id.courseslistview);

        Myhelper helper=new Myhelper(View_Courses.this);
        SQLiteDatabase database=helper.getReadableDatabase();

        Cursor c =database.rawQuery( "Select courseName from courses where termName =?", new String[]{termname});
        while (c.moveToNext()){
            listItem.add(c.getString(0));


        }
        adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listItem);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {








                Intent myintent=new Intent(View_Courses.this, Course_Info.class);




                myintent.putExtra("coursename", (String)listView.getAdapter().getItem(position));
                myintent.putExtra("termname", termname);

                startActivity(myintent);

            }
        });

     //   Toast.makeText(this, "this is viw corsee"+ termname, Toast.LENGTH_SHORT).show();


        toolbar.setNavigationIcon(R.drawable.navigation_back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(View_Courses.this, TermDetails.class);
                intent.putExtra("termname", termname);

                startActivity(intent);

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_viewcourses,menu);

        return true;
    }
}
