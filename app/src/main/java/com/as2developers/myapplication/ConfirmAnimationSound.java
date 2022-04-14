package com.as2developers.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class ConfirmAnimationSound extends AppCompatActivity {

    LottieAnimationView lottieAnimationView;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_animation_sound);
        textView = (TextView) findViewById(R.id.tv);
        lottieAnimationView = (LottieAnimationView) findViewById(R.id.confirmationAnim);
        lottieAnimationView.animate().translationX(2000).setDuration(2000).setStartDelay(2900);
        textView.animate().translationX(2000).setDuration(2000).setStartDelay(2900).scaleX(2000);
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.confirm_sound);
        mp.start();
                new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(getApplicationContext(),confirmation.class);
                i.putExtra("mode","cash");
                i.putExtra("AddressLine",getIntent().getStringExtra("AddressLine"));
                i.putExtra("items",getIntent().getStringExtra("items"));
                i.putExtra("date",getIntent().getStringExtra("date"));
                i.putExtra("Latitude",getIntent().getStringExtra("Latitude"));
                i.putExtra("Longitude",getIntent().getStringExtra("Longitude"));
                i.putExtra("locality",getIntent().getStringExtra("locality"));
                i.putExtra("longAddress",getIntent().getStringExtra("longAddress"));
                startActivity(i.putExtra("locationType",getIntent().getStringExtra("locationType").toString()));
                finish();
            }
        },2010);
    }
}