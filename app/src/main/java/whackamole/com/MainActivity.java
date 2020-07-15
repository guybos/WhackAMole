package com.whackamole;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
        private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image = (ImageView) findViewById(R.id.image);
        new Thread(new Runnable() {
            @Override public void run()  {
                try {
                    synchronized (this) {
                        MoveAnimation();
                        wait(5000);
                        Intent intent = new Intent (MainActivity.this,MainScreen.class);
                        startActivity(intent);
                        finish();
                    }
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void MoveAnimation() throws InterruptedException {
        Animation moveLeft = new TranslateAnimation(Animation.ABSOLUTE, 1500, Animation.ABSOLUTE, Animation.ABSOLUTE);
        moveLeft.setDuration(5000);
        moveLeft.setFillAfter(true);
        image.startAnimation(moveLeft);

    }
}
