package com.example.meena.c196termtracker;

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
import android.widget.TextView;
import android.widget.Toast;



public class TermDetails extends AppCompatActivity {
    TextView name, date, enddate;
    Toolbar toolbar;
    Button viewcourses;
    String termname;
    SQLiteDatabase database2;
    SQLiteDatabase database;
    String tablenames;
    SQLiteDatabase data;





    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);


        name=findViewById(R.id.termname);
        date=findViewById(R.id.termstart);
        viewcourses=findViewById(R.id.ViewCourses);
        enddate=findViewById(R.id.termend);









        toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("Term Details");
        setSupportActionBar(toolbar);





        Bundle bundle=getIntent().getExtras();
         termname=bundle.getString("termname");
        name.setText(termname);




     //   Toast.makeText(this, "name"+name.getText().toString(), Toast.LENGTH_SHORT).show();

        Myhelper helper=new Myhelper(TermDetails.this);
        SQLiteDatabase database=helper.getReadableDatabase();


        Cursor c =database.rawQuery( "Select termStart from terms where termName =?", new String[]{name.getText().toString()});
        while (c.moveToNext()){
            date.setText(c.getString(0));
        }

        Cursor end =database.rawQuery( "Select termEnd from terms where termName =?", new String[]{name.getText().toString()});
        while (end.moveToNext()){
            enddate.setText(end.getString(0));
        }



        toolbar.setNavigationIcon(R.drawable.navigation_back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TermDetails.this, AllSemester.class);
                startActivity(intent);

            }
        });



        viewcourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent intent=new Intent(TermDetails.this, View_Courses.class);
                intent.putExtra("termname",termname);
                startActivity(intent);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_term,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.delete:

                Myhelper helper2=new Myhelper(TermDetails.this);
                database2=helper2.getReadableDatabase();


                Cursor cur =database2.rawQuery( "Select courseName from courses where termName =?", new String[]{termname});
                if (cur.moveToNext()){



                    AlertDialog.Builder atldial= new AlertDialog.Builder(TermDetails.this);
                    atldial.setMessage("There are courses asscoicated wtih this term??").setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();

                        }
                    });

                    AlertDialog alert=atldial.create();
                    alert.setTitle("Warning");
                    alert.show();

                }
                else{
                    Myhelper helpers=new Myhelper(TermDetails.this);
                    database=helpers.getWritableDatabase();
                    tablenames="terms";

                    AlertDialog.Builder atldial= new AlertDialog.Builder(TermDetails.this);
                    atldial.setMessage("Are you sure you  want to delete??").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            database.delete(tablenames, "termName=?", new String[] {name.getText().toString()});

                            Intent intent=new Intent(TermDetails.this, AllSemester.class);
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


                }




              return true;
                default:
                    return false;
        }

    }

    public void delete(){

        Myhelper helper=new Myhelper(TermDetails.this);
        SQLiteDatabase database=helper.getWritableDatabase();


        String s="DELETE FROM terms WHERE termName="+name.getText().toString();

      database.execSQL(s);

    }
}
