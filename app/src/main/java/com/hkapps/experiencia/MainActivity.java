package com.hkapps.experiencia;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String unique_id = "nodemcu1";
    DatabaseReference ref;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final int RC_SIGN_IN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FirebaseAuthenticationProcess();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent i = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(i);
            }
        });

/*
        ref = FirebaseDatabase.getInstance().getReference().child("Devices").child(unique_id).child("weather");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Toast.makeText(MainActivity.this, "Triggered", Toast.LENGTH_SHORT).show();
                if (dataSnapshot.child("rain").getValue().toString().equals("off_confirmed")) {
                    rainoff();

                } else if (dataSnapshot.child("rain").getValue().toString().equals("on_confirmed")) {
                    rainon();
                }

                if (dataSnapshot.child("clouds").getValue().toString().equals("off_confirmed")) {
                    cloudoff();

                } else if (dataSnapshot.child("clouds").getValue().toString().equals("on_confirmed")) {
                    cloudon();
                }

                if (dataSnapshot.child("sunshine").getValue().toString().equals("off_confirmed")) {
                    sunshineoff();

                } else if (dataSnapshot.child("sunshine").getValue().toString().equals("on_confirmed")) {
                    sunshineon();
                }


                if (dataSnapshot.child("lightning").getValue().toString().equals("off_confirmed")) {
                    lightningoff();

                } else if (dataSnapshot.child("lightning").getValue().toString().equals("on_confirmed")) {
                    lightningon();
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
*/


        Button rain_button = (Button) findViewById(R.id.rain_button);
        Button cloud_button = (Button) findViewById(R.id.cloud_button);
        Button sunshine_button = (Button) findViewById(R.id.sunshine_button);
        Button lightning_button = (Button) findViewById(R.id.lightning_button);


        rain_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImageView rain_img = (ImageView) findViewById(R.id.rain_img);
                if (rain_img.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.rainoff).getConstantState()) {
                    String status = "on";
                    ref.child("rain").setValue(status);
                } else {
                    ref.child("rain").setValue("off");
                }

                backgroundTask("rain");
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

                backgroundTask("clouds");

            }
        });


        sunshine_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImageView sunshine_img = (ImageView) findViewById(R.id.sunshine_img);
                if (sunshine_img.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.sunshineoff).getConstantState()) {


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


        lightning_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImageView lightning_img = (ImageView) findViewById(R.id.lightning_img);
                if (lightning_img.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.lightningoff).getConstantState()) {


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
                    //  ref.child("sunshine").setValue("off");


                } else {
                    ref.child("lightning").setValue("off");
                }


                backgroundTask("sunshine");

                backgroundTask("lightning");


            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
                Toast.makeText(this, response.getError().getErrorCode(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN){

            if(resultCode == RESULT_OK){

                Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show();



            }else if(resultCode == RESULT_CANCELED) {

                Toast.makeText(this, "Sign in Cancelled", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
*/
    @Override
    protected void onStop() {
        super.onStop();

        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }



    private void rainon() {
        ImageView rain_img = (ImageView) findViewById(R.id.rain_img);
        rain_img.setImageResource(R.drawable.rainon);
    }


    private void rainoff() {
        ImageView rain_img = (ImageView) findViewById(R.id.rain_img);
        rain_img.setImageResource(R.drawable.rainoff);
    }


    private void cloudoff() {
        ImageView cloud_img = (ImageView) findViewById(R.id.cloud_img);
        cloud_img.setImageResource(R.drawable.cloudoff);
    }


    private void cloudon() {
        ImageView cloud_img = (ImageView) findViewById(R.id.cloud_img);
        cloud_img.setImageResource(R.drawable.cloudon);
    }


    private void sunshineon() {
        ImageView sunshine_img = (ImageView) findViewById(R.id.sunshine_img);
        ImageView lightning_img = (ImageView) findViewById(R.id.lightning_img);
        sunshine_img.setImageResource(R.drawable.sunshineon);
        lightning_img.setImageResource(R.drawable.lightningoff);
    }

    private void sunshineoff() {
        ImageView sunshine_img = (ImageView) findViewById(R.id.sunshine_img);
        //ImageView lightning_img = (ImageView) findViewById(R.id.lightning_img);
        sunshine_img.setImageResource(R.drawable.sunshineoff);
        //lightning_img.setImageResource(R.drawable.lightningoff);
    }

    private void lightningon() {
        ImageView sunshine_img = (ImageView) findViewById(R.id.sunshine_img);
        ImageView lightning_img = (ImageView) findViewById(R.id.lightning_img);
        sunshine_img.setImageResource(R.drawable.sunshineoff);
        lightning_img.setImageResource(R.drawable.lightningon);
    }

    private void lightningoff() {
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


    private void backgroundTask(final String weather_type) {


        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    Thread.sleep(3000);

                    //DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference().child("Devices").child(unique_id).child("switches");
                    // Log.e("Inside Asynctask"+model.getName(),"status :"+model.getStatus()+", physical status:"+model.getPhysical_status());

                    ref.child(weather_type).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String status = dataSnapshot.getValue().toString();
//                            DatabaseReference ref3 = FirebaseDatabase.getInstance().getReference().child("Devices").child(room_id).child("switches");

                            if (status.equals("on")) {

                                ref.child(weather_type).setValue("off_confirmed");

                            } else if (status.equals("off")) {
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




    private void FirebaseAuthenticationProcess(){

        mAuth = FirebaseAuth.getInstance();


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {



                    DatabaseReference uploadUserData = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid());
                    uploadUserData.child("email").setValue(user.getEmail().toString());
                    uploadUserData.child("username").setValue(user.getDisplayName().toString());
                    uploadUserData.child("userid").setValue(user.getUid().toString());


                } else {

                    // User is signed out
                 //   Log.d(TAG, "onAuthStateChanged:signed_out");

                    //Load LoginScreen
                    loadFirebaseLoginScreenUi();

                }
                // ...
            }
        };
    }


    private void loadFirebaseLoginScreenUi(){


        List<AuthUI.IdpConfig> providers = Arrays.asList(
               // new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setIsSmartLockEnabled(false)
                        .setTheme(R.style.LoginTheme)
                        .setAvailableProviders(providers).build(),
                RC_SIGN_IN);






    }

}
