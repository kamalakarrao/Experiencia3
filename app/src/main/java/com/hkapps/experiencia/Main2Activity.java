package com.hkapps.experiencia;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class Main2Activity extends AppCompatActivity {
    ImageView testImage;
    RotateAnimation rotate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        testImage = findViewById(R.id.img_sunlight);

        rotate();

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

    private void rotate() {
        rotate = new RotateAnimation(0, 720,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        rotate.setDuration(4000);
        rotate.setRepeatCount(Animation.INFINITE);
        testImage.setAnimation(rotate);
    }
}
