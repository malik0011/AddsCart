package com.as2developers.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.VideoView;

import pl.droidsonroids.gif.GifImageView;

public class SplashScreen extends AppCompatActivity {
    TextView logoname;
    GifImageView gifSplash;
    Animation upAnim, downAnim, fadeoutAnim;

    SharedPreferences onboardingScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES);
        }
        //hooks
        gifSplash = findViewById(R.id.gifSplash);
        logoname = findViewById(R.id.logo_name);
        upAnim = AnimationUtils.loadAnimation(this, R.anim.up_animation);
        downAnim = AnimationUtils.loadAnimation(this, R.anim.down_animation);
        fadeoutAnim = AnimationUtils.loadAnimation(this,R.anim.fade_out_anim);
        gifSplash.setAnimation(downAnim);
        logoname.setAnimation(upAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                logoname.clearAnimation();
                gifSplash.clearAnimation();
                logoname.setAnimation(fadeoutAnim);
                gifSplash.setAnimation(fadeoutAnim);
                logoname.setVisibility(View.INVISIBLE);

            }
        },3000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onboardingScreen = getSharedPreferences("OnBoardingScreen",MODE_PRIVATE);
                boolean isfirstTime = onboardingScreen.getBoolean("FirstTime",true);
                if (isfirstTime){
                    SharedPreferences.Editor editor = onboardingScreen.edit();
                    editor.putBoolean("FirstTime",false);
                    editor.commit();
                    startActivity(new Intent(SplashScreen.this, SelectLocationFromMap.class));
                    finish();
                }
                else{
                    startActivity(new Intent(SplashScreen.this, SelectLocationFromMap.class)); //i have to change this
                    finish();
                }

            }
        }, 3000);
    }
}