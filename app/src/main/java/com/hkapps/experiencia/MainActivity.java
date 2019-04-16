package com.hkapps.experiencia;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Devices").child("nodemcu1").child("weather");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               // Toast.makeText(MainActivity.this, "Triggered", Toast.LENGTH_SHORT).show();
           if (dataSnapshot.child("rain").getValue().toString().equals( "off_confirmed")){
               rainoff();

           }else if (dataSnapshot.child("rain").getValue().toString().equals("on_confirmed")){
               rainon();
           }

                if (dataSnapshot.child("clouds").getValue().toString().equals("off_confirmed")){
                    cloudoff();

                }else if (dataSnapshot.child("clouds").getValue().toString().equals("on_confirmed")){
                    cloudon();
                }

                if (dataSnapshot.child("sunshine").getValue().toString().equals("off_confirmed")){
                    sunshineoff();

                }else if (dataSnapshot.child("sunshine").getValue().toString().equals("on_confirmed")){
                    sunshineon();
                }


                if (dataSnapshot.child("lightning").getValue().toString().equals("off_confirmed")){
                    lightningoff();

                }else if (dataSnapshot.child("lightning").getValue().toString().equals("on_confirmed")){
                    lightningon();
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        Button rain_button = (Button) findViewById(R.id.rain_button);
        Button cloud_button = (Button) findViewById(R.id.cloud_button);
        Button sunshine_button = (Button) findViewById(R.id.sunshine_button);
        Button lightning_button = (Button) findViewById(R.id.lightning_button);


        rain_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImageView rain_img = (ImageView) findViewById(R.id.rain_img);
                if (rain_img.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.rainoff).getConstantState()) {
                ref.child("rain").setValue("on");
                } else {
                    ref.child("rain").setValue("off");
                }
            }
        });




        cloud_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImageView cloud_img = (ImageView) findViewById(R.id.cloud_img);
                if (cloud_img.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.cloudoff).getConstantState()) {
                    ref.child("clouds").setValue("on");
                } else {
                    ref.child("clouds").setValue("off");
                }
            }
        });




        sunshine_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImageView sunshine_img = (ImageView) findViewById(R.id.sunshine_img);
                if (sunshine_img.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.sunshineoff).getConstantState()) {
                    ref.child("lightning").setValue("off");

                    ref.child("sunshine").setValue("on");
                } else {
                    ref.child("sunshine").setValue("off");
                }
            }
        });


        lightning_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImageView lightning_img = (ImageView) findViewById(R.id.lightning_img);
                if (lightning_img.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.lightningoff).getConstantState()) {

                    ref.child("sunshine").setValue("off");

                    ref.child("lightning").setValue("on");


                } else {
                    ref.child("lightning").setValue("off");
                }
            }
        });

    }



    private void rainon(){
        ImageView rain_img = (ImageView) findViewById(R.id.rain_img);
        rain_img.setImageResource(R.drawable.rainon);
    }




    private void rainoff(){
        ImageView rain_img = (ImageView) findViewById(R.id.rain_img);
        rain_img.setImageResource(R.drawable.rainoff);
    }





    private void cloudoff(){
        ImageView cloud_img = (ImageView) findViewById(R.id.cloud_img);
        cloud_img.setImageResource(R.drawable.cloudoff);
    }



    private void cloudon(){
        ImageView cloud_img = (ImageView) findViewById(R.id.cloud_img);
        cloud_img.setImageResource(R.drawable.cloudon);
    }




    private void sunshineon(){
        ImageView sunshine_img = (ImageView) findViewById(R.id.sunshine_img);
        ImageView lightning_img = (ImageView) findViewById(R.id.lightning_img);
        sunshine_img.setImageResource(R.drawable.sunshineon);
        lightning_img.setImageResource(R.drawable.lightningoff);
    }

    private void sunshineoff(){
        ImageView sunshine_img = (ImageView) findViewById(R.id.sunshine_img);
        //ImageView lightning_img = (ImageView) findViewById(R.id.lightning_img);
        sunshine_img.setImageResource(R.drawable.sunshineoff);
        //lightning_img.setImageResource(R.drawable.lightningoff);
    }

    private void lightningon(){
        ImageView sunshine_img = (ImageView) findViewById(R.id.sunshine_img);
        ImageView lightning_img = (ImageView) findViewById(R.id.lightning_img);
        sunshine_img.setImageResource(R.drawable.sunshineoff);
        lightning_img.setImageResource(R.drawable.lightningon);
    }

    private void lightningoff(){
       // ImageView sunshine_img = (ImageView) findViewById(R.id.sunshine_img);
        ImageView lightning_img = (ImageView) findViewById(R.id.lightning_img);
      //  sunshine_img.setImageResource(R.drawable.sunshineoff);
        lightning_img.setImageResource(R.drawable.lightningoff);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
