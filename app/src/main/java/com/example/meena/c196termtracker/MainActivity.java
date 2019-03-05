package com.example.meena.c196termtracker;

import android.app.AlarmManager;
import android.app.PendingIntent;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    String text;

    String endalert;
    String cu;
    Long mill, mills,assessmentmills;
    String us;
    String alertcoursename;
    String name;
    String j;
    String startalert;
    String aalert;
    String titlecolumn;
    String assessmentalert;
    String name2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);







        RelativeLayout.LayoutParams btnParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        btnParams.setMargins(30, 50, 90, 50);
        TextView textView= new TextView(this);
        textView.setLayoutParams(btnParams);
        textView.setTextSize(25);


        String text="Welcome to the WGU term tracker. Use this " +
                "app to track your terms and courses.";
        textView.setText(text);











        RelativeLayout.LayoutParams btnParams1 = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        btnParams1.setMargins(30, 400, 80, 800);



        Button button= new Button(this);
        button.setText("start");

        button.setLayoutParams(btnParams1);

        RelativeLayout relativeLayout= (RelativeLayout) View.inflate(this,R.layout.activity_main, null);

        relativeLayout.addView(textView);
        relativeLayout.addView(button);

        setContentView(relativeLayout);





        Calendar ca = Calendar.getInstance();
        SimpleDateFormat sdfs = new SimpleDateFormat("MM/dd/yyyy");
        String s=sdfs.format(ca.getTime());
        us=s.substring(0,10);


        Myhelper helper=new Myhelper(MainActivity.this);
        SQLiteDatabase database=helper.getReadableDatabase();



        final String MY_QUERY = "SELECT * FROM courses  WHERE courseEndAlert=courseName AND courseEnd=?" ;


        Cursor c=database.rawQuery(MY_QUERY, new String[] {us});

        ArrayList<String> marrayList = new ArrayList<String>();

        while (c.moveToNext()){
            endalert=c.getString(c.getColumnIndex("courseEnd"));
            name="courseName";

            marrayList.add(c.getString(c.getColumnIndex(name)));




        }



        if (endalert!= null) {

            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            try {
                Date date = sdf.parse(endalert);

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                mill = calendar.getTimeInMillis();
            } catch (ParseException e) {
                e.printStackTrace();
            }


            Intent intent = new Intent(MainActivity.this, MyReceiver.class);
            intent.putExtra("alertcourseendname",alertcoursename);
            PendingIntent sender = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, mill, sender);


        }


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor= preferences.edit();
        j=marrayList.toString();


        editor.putString("alert", j);
        editor.commit();






        Myhelper helper3=new Myhelper(MainActivity.this);
        SQLiteDatabase database3=helper3.getReadableDatabase();



        final String MY_QUERY3 = "SELECT * FROM assessments  WHERE assessmentStartAlert=assessmentTitle AND assessmentDate=?" ;



        Cursor c3=database3.rawQuery(MY_QUERY3, new String[] {us});

        ArrayList<String> marrayList3 = new ArrayList<String>();




        while (c3.moveToNext()){
            assessmentalert=c3.getString(c3.getColumnIndex("assessmentDate"));
            titlecolumn="assessmentTitle";

            marrayList3.add(c3.getString(c3.getColumnIndex(titlecolumn)));




        }




        if (assessmentalert!= null) {

            SimpleDateFormat sdf3 = new SimpleDateFormat("MM/dd/yyyy");
            try {
                Date date = sdf3.parse(assessmentalert);

                Calendar calendar3 = Calendar.getInstance();
                calendar3.setTime(date);
                assessmentmills = calendar3.getTimeInMillis();
            } catch (ParseException e) {
                e.printStackTrace();
            }


            Intent intent3 = new Intent(MainActivity.this, MyReceiver.class);

            PendingIntent sender = PendingIntent.getBroadcast(MainActivity.this, 0, intent3, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, assessmentmills, sender);


        }


        SharedPreferences preferences3 = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor3= preferences3.edit();
        aalert=marrayList3.toString();


        editor3.putString("assessmentalert", aalert);
        editor3.commit();







        Myhelper helper2=new Myhelper(MainActivity.this);
        SQLiteDatabase database2=helper2.getReadableDatabase();



        final String MY_QUERY2 = "SELECT * FROM courses  WHERE courseStartAlert=courseName AND courseStart=?" ;



        Cursor c2=database2.rawQuery(MY_QUERY2, new String[] {us});

        ArrayList<String> marrayList2 = new ArrayList<String>();



        while (c2.moveToNext()){
            startalert=c2.getString(c2.getColumnIndex("courseStart"));
            name2="courseName";
            marrayList2.add(c2.getString(c2.getColumnIndex(name2)));




        }



        if (startalert!= null) {

            SimpleDateFormat sdf2 = new SimpleDateFormat("MM/dd/yyyy");
            try {
                Date date = sdf2.parse(startalert);

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                mills = calendar.getTimeInMillis();
            } catch (ParseException e) {
                e.printStackTrace();
            }


            Intent intent2 = new Intent(MainActivity.this, MyReceiver.class);
            PendingIntent sender = PendingIntent.getBroadcast(MainActivity.this, 0, intent2, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, mills, sender);


        }


        SharedPreferences preferences2 = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor2= preferences2.edit();
        startalert=marrayList2.toString();


        editor2.putString("startalert", startalert);
        editor2.commit();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, AllSemester.class);
                startActivity(intent);

            }
        });







    }






}



