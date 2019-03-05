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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

public class Notes extends AppCompatActivity {

    ImageView notesadd;
    ListView listview;

    ArrayList<String> listItem;
    ArrayAdapter adapter;
    EditText title, notes;
    Button savebutton;
    Toolbar toolbar;
    String co;
    String ids;
    String vars;
    String termname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        notesadd=findViewById(R.id.NotesAdd);

        toolbar=findViewById(R.id.notestoolbar);
        toolbar.setTitle("Notes");
        setSupportActionBar(toolbar);


        Bundle bundle=getIntent().getExtras();
        ids=bundle.getString("ids");

        Bundle bundle2=getIntent().getExtras();
        vars=bundle2.getString("coursename");

       // Toast.makeText(this, "vars"+vars, Toast.LENGTH_SHORT).show();

        //Toast.makeText(this, "id "+ids, Toast.LENGTH_SHORT).show();

        Bundle bundle1=getIntent().getExtras();
        termname=bundle1.getString("termname");


        toolbar.setNavigationIcon(R.drawable.navigation_back);




        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent(Notes.this, Course_Info.class);
                intent.putExtra("coursename", vars);
                intent.putExtra("termname",termname);

                startActivity(intent);
            }
        });


        listItem=new ArrayList<>();


        listview=findViewById(R.id.NotesListView);









        Myhelper helper=new Myhelper(Notes.this);
        SQLiteDatabase database=helper.getReadableDatabase();

        final String MY_QUERY = "SELECT notesTitle FROM notes a INNER JOIN courses b ON a.courseID=b._id WHERE a.courseID=?";



        Cursor c=database.rawQuery(MY_QUERY, new String[] {ids});

        while (c.moveToNext()){
            listItem.add(c.getString(0));


        }
        adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listItem);
        listview.setAdapter(adapter);


        notesadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Notes.this, Notes_Add.class);
                intent.putExtra("ids", ids);
                intent.putExtra("coursename", vars);
                intent.putExtra("termname",termname);
                startActivity(intent);
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {






                Intent myintent=new Intent(Notes.this, Notes_Details.class);




                myintent.putExtra("title", (String)listview.getAdapter().getItem(position));
                myintent.putExtra("ids", ids);
                myintent.putExtra("coursename", vars);
                myintent.putExtra("termname",termname);
                startActivity(myintent);
                //  }
            }
        });




    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_notes,menu);

        return true;
    }

}
