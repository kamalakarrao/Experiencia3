package com.hkapps.experiencia;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class Main2Activity extends AppCompatActivity {
    ImageView testImage,iv_clouds,iv_rain;
    RotateAnimation rotate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        testImage = findViewById(R.id.img_sunlight);
        iv_clouds = findViewById(R.id.iv_clouds);
        iv_rain = findViewById(R.id.iv_rain);
//        Animation aniFade = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
//        iv_clouds.startAnimation(aniFade);
//        rotate();
//        changerainimage();
//        changecloudimage();
        testImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rotate.reset();

                rotate = new RotateAnimation(0, 720,
                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                        0.5f);
                rotate.setDuration(4000);
                rotate.setRepeatCount(Animation.INFINITE);
                testImage.setAnimation(rotate);

            }
        });
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
