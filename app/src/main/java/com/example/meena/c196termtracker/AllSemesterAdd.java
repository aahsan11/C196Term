package com.example.meena.c196termtracker;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;





public class AllSemesterAdd extends AppCompatActivity {
    EditText term,start,end;
    Button button;
    android.support.v7.widget.Toolbar toolbar;
    String terms, starts, ends;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_semester_add);
        term=findViewById(R.id.termname);
        start=findViewById(R.id.startdate);
        end=findViewById(R.id.enddate);
        button=findViewById(R.id.save);
        toolbar=findViewById(R.id.allsemestersaddtoolbar);


        toolbar.setNavigationIcon(R.drawable.navigation_back);

        toolbar.setTitle("Add Term");
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(AllSemesterAdd.this, AllSemester.class );
                startActivity(intent);
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                terms=term.getText().toString();
                 starts=start.getText().toString();
                 ends=end.getText().toString();

                if(starts.isEmpty() || terms.isEmpty() || ends.isEmpty() || starts.length()!=10 || ends.length()!=10){

                    AlertDialog.Builder atldial= new AlertDialog.Builder(AllSemesterAdd.this);
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
                    Intent intent = new Intent(AllSemesterAdd.this, AllSemester.class);
                    startActivity(intent);
                }
            }
        });

    }

    public void save(){
        Myhelper helper=new Myhelper(AllSemesterAdd.this);

        SQLiteDatabase database=helper.getWritableDatabase();

        ContentValues contentValues=new ContentValues();

        contentValues.put("termName", term.getText().toString());
        contentValues.put("termStart", start.getText().toString());
        contentValues.put("termEnd", end.getText().toString());
        database.insert("terms", null, contentValues);



        term.getText().clear();
        end.getText().clear();
        start.getText().clear();






    }

}
