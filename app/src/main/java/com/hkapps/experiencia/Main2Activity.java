package com.hkapps.experiencia;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Switch;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Main2Activity extends AppCompatActivity {
    //actual implementation
    String unique_id  = "nodemcu1";
    DatabaseReference ref;

    //declaring switches
    Switch switch_sunlight,switch_thunder,switch_clouds,switch_rain;

    ImageView testImage,iv_clouds,iv_rain;
    RotateAnimation rotate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        testImage = findViewById(R.id.img_sunlight);
        iv_clouds = findViewById(R.id.iv_clouds);
        iv_rain = findViewById(R.id.iv_rain);

        //casting switches
        switch_rain = (Switch) findViewById(R.id.switch_rain);
        switch_clouds = (Switch) findViewById(R.id.switch_clouds);
        switch_sunlight = (Switch) findViewById(R.id.switch_sunlight);
        switch_thunder = (Switch) findViewById(R.id.switch_thunder);

//        switch_rain.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (switch_rain.isChecked()){
//                    ref.child("rain").setValue("on");
//                }else {
//                    ref.child("rain").setValue("off");
//                }
//                backgroundTask("rain");
//            }
//        });

        switch_rain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switch_rain.isChecked()){
                    ref.child("rain").setValue("on");
                }else {
                    ref.child("rain").setValue("off");
                }
                backgroundTask("rain");
            }
        });

//        switch_clouds.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (switch_clouds.isChecked()) {
//                    ref.child("clouds").setValue("on");
//                } else {
//                    ref.child("clouds").setValue("off");
//                }
//
//                backgroundTask("clouds");
//            }
//        });

        switch_clouds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switch_clouds.isChecked()) {
                    ref.child("clouds").setValue("on");
                } else {
                    ref.child("clouds").setValue("off");
                }

                backgroundTask("clouds");
            }
        });
        switch_sunlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switch_sunlight.isChecked()) {
                    ref.child("sunshine").setValue("on");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ref.child("lightning").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue().equals("on_confirmed")) {
                                ref.child("lightning").setValue("off");
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    //   ref.child("lightning").setValue("off");
                } else {
                    ref.child("sunshine").setValue("off");
                }
                backgroundTask("sunshine");
                backgroundTask("lightning");
            }
        });
//        switch_sunlight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (switch_sunlight.isChecked()) {
//                    ref.child("sunshine").setValue("on");
//                    try {
//                        Thread.sleep(2000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    ref.child("lightning").addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            if (dataSnapshot.getValue().equals("on_confirmed")) {
//                                ref.child("lightning").setValue("off");
//                            }
//                        }
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//
//                        }
//                    });
//
//                    //   ref.child("lightning").setValue("off");
//                } else {
//                    ref.child("sunshine").setValue("off");
//                }
//                backgroundTask("sunshine");
//                backgroundTask("lightning");
//            }
//        });

        switch_thunder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switch_thunder.isChecked()) {
                    ref.child("lightning").setValue("on");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ref.child("sunshine").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue().equals("on_confirmed")) {
                                ref.child("sunshine").setValue("off");
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                } else {
                    ref.child("lightning").setValue("off");
                }
                backgroundTask("sunshine");
                backgroundTask("lightning");
            }
        });
//        switch_thunder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (switch_thunder.isChecked()) {
//                    ref.child("lightning").setValue("on");
//                    try {
//                        Thread.sleep(2000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    ref.child("sunshine").addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            if (dataSnapshot.getValue().equals("on_confirmed")) {
//                                ref.child("sunshine").setValue("off");
//                            }
//                        }
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//                        }
//                    });
//                } else {
//                    ref.child("lightning").setValue("off");
//                }
//                backgroundTask("sunshine");
//                backgroundTask("lightning");
//            }
//        });


//        Animation aniFade = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
//        iv_clouds.startAnimation(aniFade);
//        rotate();
//        changerainimage();
//        changecloudimage();
//        testImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                rotate.reset();
//
//                rotate = new RotateAnimation(0, 720,
//                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
//                        0.5f);
//                rotate.setDuration(4000);
//                rotate.setRepeatCount(Animation.INFINITE);
//                testImage.setAnimation(rotate);
//
//            }
//        });


        //syncing all the switches with database status
        ref = FirebaseDatabase.getInstance().getReference().child("Devices").child(unique_id).child("weather");
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


    }

    private void backgroundTask(final String weather_type) {
        new AsyncTask<Void,Void,Void>(){

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    Thread.sleep(3000);
                    ref.child(weather_type).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String status = dataSnapshot.getValue().toString();
                            if (status.equals("on")){
                                ref.child(weather_type).setValue("off_confirmed");

                            }else if(status.equals("off")) {
                                ref.child(weather_type).setValue("on_confirmed");
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });




                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }

    private void lightningon() {
        switch_thunder.setChecked(true);
    }

    private void lightningoff() {
        switch_thunder.setChecked(false);
    }

    private void sunshineon() {
        switch_sunlight.setChecked(true);
    }

    private void sunshineoff() {
        switch_sunlight.setChecked(false);
    }

    private void cloudon() {
        switch_clouds.setChecked(true);
    }

    private void cloudoff() {
        switch_clouds.setChecked(false);
    }

    private void rainon() {
        switch_rain.setChecked(true);
    }

    private void rainoff() {
        switch_rain.setChecked(false);
    }


    boolean clouds =false;
    private void changecloudimage() {
        if (!clouds){
            iv_clouds.setBackgroundResource(R.drawable.cloudsflow);
        }else {
            iv_clouds.setBackgroundResource(R.drawable.clouds);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!clouds){
                    clouds=true;
                    iv_clouds.setBackgroundResource(R.drawable.cloudsflow);
                }else {
                    clouds =false;
                    iv_clouds.setBackgroundResource(R.drawable.clouds);
                }
                changecloudimage();
            }

        }, 100);
    }

    boolean rain=false;
    private void changerainimage() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!rain){
                    rain=true;
                    iv_rain.setBackgroundResource(R.drawable.rainflow);
                }else {
                    rain =false;
                    iv_rain.setBackgroundResource(R.drawable.rain);
                }
                changerainimage();
            }

        }, 500);
    }

    private void rotate() {
        rotate = new RotateAnimation(0, 720,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        rotate.setDuration(4000);
        rotate.setRepeatCount(Animation.INFINITE);
        testImage.setAnimation(rotate);
    }
}
