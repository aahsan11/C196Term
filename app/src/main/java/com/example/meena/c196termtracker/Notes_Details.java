package com.example.meena.c196termtracker;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class Notes_Details extends AppCompatActivity {
    Button savebutton;
    EditText title, notes;
    String Title;
    android.support.v7.widget.Toolbar toolbar;
    String ids;
    String notesid;
    String vars;
    String termname;
    String not;
    SQLiteDatabase database;
    String tablename;
    String Title1,Notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes__details);

        toolbar=findViewById(R.id.notesdetailstoolbar);
        toolbar.setTitle("Notes Details");
        setSupportActionBar(toolbar);



        title=findViewById(R.id.Title);

        Bundle bundle1=getIntent().getExtras();
        ids=bundle1.getString("ids");

        notes=findViewById(R.id.Notes);
        savebutton=findViewById(R.id.SaveNotesButton);

        Bundle bundle= getIntent().getExtras();
        Title= bundle.getString("title");
        title.setText(Title);

        Bundle bundel3=getIntent().getExtras();
        vars=bundel3.getString("coursename");

        Myhelper myhelper= new Myhelper(Notes_Details.this);

        SQLiteDatabase db= myhelper.getReadableDatabase();
        Cursor c=db.rawQuery("Select notesDetails from notes where notesTitle =? ", new String[]{Title});
        while (c.moveToNext()){
            notes.setText(c.getString(0));
        }

        Myhelper helper1=new Myhelper(Notes_Details.this);
        final SQLiteDatabase database1=helper1.getReadableDatabase();
        Cursor note =database1.rawQuery( "Select _id from notes where notesTitle =? and notesDetails=?", new String[]{title.getText().toString(), notes.getText().toString()});



        if (note.getCount() != 0 && note!=null) {
            if (note.moveToFirst()) {
                do {
                    notesid  = note.getString(note.getColumnIndex("_id"));

                } while (note.moveToNext());

            }
        }

        Bundle bundles= getIntent().getExtras();
        termname= bundles.getString("termname");





        toolbar.setNavigationIcon(R.drawable.navigation_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Notes_Details.this, Notes.class);
                intent.putExtra("ids", ids);
                intent.putExtra("coursename",vars);
                intent.putExtra("termname", termname);
                startActivity(intent);
            }
        });
        title.setEnabled(false);
        notes.setEnabled(false);
        savebutton.setEnabled(false);

        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Myhelper myhelper= new Myhelper(Notes_Details.this);
                SQLiteDatabase database1=myhelper.getWritableDatabase();

                ContentValues values= new ContentValues();
                values.put("notesTitle", title.getText().toString());
                values.put("notesDetails",notes.getText().toString());


                Title1=title.getText().toString();
                Notes=notes.getText().toString();


                if(Title1.isEmpty() || Notes.isEmpty()){

                    AlertDialog.Builder atldial= new AlertDialog.Builder(Notes_Details.this);
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


                    database1.update("notes", values, "_id=?", new String[]{notesid});
                    title.setEnabled(false);
                    notes.setEnabled(false);
                    savebutton.setEnabled(false);
                }
            }
        });
        not=notes.getText().toString();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_notesdetails, menu);

        return true;
    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.delete:
                Myhelper helper=new Myhelper(Notes_Details.this);
                 database=helper.getWritableDatabase();
                 tablename="notes";





                AlertDialog.Builder atldial= new AlertDialog.Builder(Notes_Details.this);
                atldial.setMessage("Do you sure you  want to delete??").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        database.delete(tablename, "notesTitle=? AND notesDetails=?", new String[] {title.getText().toString(), notes.getText().toString()});
                        Intent intent=new Intent(Notes_Details.this, Notes.class);
                        intent.putExtra("ids", ids);
                        intent.putExtra("coursename", vars);
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
                notes.setEnabled(true);
                title.setEnabled(true);
                savebutton.setEnabled(true);



                savebutton.setVisibility(View.VISIBLE);

                return true;

            case R.id.email:

                Intent i= new Intent (Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_TEXT, notes.getText());
                i.putExtra(Intent.EXTRA_SUBJECT, Title);
                startActivity(Intent.createChooser(i, "Send Email"));
                return true;


            case R.id.text:
                Intent in= new Intent (Intent.ACTION_VIEW);

                in.setData(Uri.parse("sms:"));
                in.putExtra("sms_body", not);
                startActivity(in);
                return true;











            default:
                return false;
        }

    }



}
