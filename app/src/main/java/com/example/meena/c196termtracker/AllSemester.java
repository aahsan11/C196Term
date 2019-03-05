package com.example.meena.c196termtracker;


import android.content.Intent;
import android.database.Cursor;

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


public class AllSemester extends AppCompatActivity {
    ImageView addbutton;
    Myhelper db;
    ArrayList<String> listItem;
    ArrayAdapter adapter;
    Toolbar toolbar;

    String name;

    ListView userlist;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.all_semester);

        toolbar=findViewById(R.id.allsemesterstoolbar);
        toolbar.setTitle("All Terms");
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.navigation_back);




        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent(AllSemester.this, MainActivity.class);


                startActivity(intent);
            }
        });
        db=new Myhelper(this);
        listItem=new ArrayList<>();
        userlist=findViewById(R.id.users_list);



        userlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {






                Intent myintent=new Intent(AllSemester.this, TermDetails.class);




                myintent.putExtra("termname", (String)userlist.getAdapter().getItem(position));

                startActivity(myintent);

            }
        });


        viewData();



        addbutton=findViewById(R.id.semesteraddbutton);
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AllSemester.this, AllSemesterAdd.class);
                startActivity(intent);
            }
        });

    }
    public void viewData(){
        Cursor cursor=db.viewData();
        if(cursor.getCount()== 0){
            Toast.makeText(this, "No Data to show", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()){
                listItem.add(cursor.getString(1));
            }
            adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listItem);
            userlist.setAdapter(adapter);
        }


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_allsemesters,menu);

        return true;
    }
}
