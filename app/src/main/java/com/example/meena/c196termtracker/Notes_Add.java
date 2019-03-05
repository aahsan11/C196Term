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
import android.widget.EditText;
import android.widget.Toast;

public class Notes_Add extends AppCompatActivity {

    EditText title, notes;
    Button save;
    String termname, coursename;
    String id;
    String ids;
    String vars;
    Toolbar toolbar;
    String Title, Notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes__add);

        title=findViewById(R.id.Title);
        notes=findViewById(R.id.Notes);

        toolbar=findViewById(R.id.notesaddtoolbar);
        toolbar.setTitle("Add Notes");
        setSupportActionBar(toolbar);


        Bundle bundle=getIntent().getExtras();
        ids=bundle.getString("ids");

        Bundle bundel2=getIntent().getExtras();
        vars=bundel2.getString("coursename");

        save=findViewById(R.id.SaveNoteButton);

       // Toast.makeText(this, "" + ids, Toast.LENGTH_SHORT).show();

        Bundle bundels=getIntent().getExtras();
        termname=bundels.getString("termname");

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Save();
                Intent intent= new Intent(Notes_Add.this, Notes.class);
                intent.putExtra("ids", ids);
                intent.putExtra("coursename", vars);
                intent.putExtra("termname", termname);
                startActivity(intent);

            }
        });

        toolbar.setNavigationIcon(R.drawable.navigation_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent(Notes_Add.this, Notes.class);
                intent.putExtra("ids", ids);
                intent.putExtra("coursename", vars);
                intent.putExtra("termname", termname);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_notesadd,menu);

        return true;
    }

    public void Save(){
        Title=title.getText().toString();
        Notes=notes.getText().toString();
        Myhelper helper =new Myhelper(Notes_Add.this);
        SQLiteDatabase database=helper.getWritableDatabase();

        if(Title.isEmpty() || Notes.isEmpty() ){

            AlertDialog.Builder atldial= new AlertDialog.Builder(Notes_Add.this);
            atldial.setMessage("Please fill all fields 1st").setPositiveButton("ok", new DialogInterface.OnClickListener() {
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


            ContentValues values = new ContentValues();
            values.put("notesTitle", title.getText().toString());
            values.put("notesDetails", notes.getText().toString());
            values.put("courseID", ids);

            database.insert("notes", null, values);
        }
    }
}
