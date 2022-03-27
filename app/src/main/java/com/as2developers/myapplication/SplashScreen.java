package com.as2developers.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.IOException;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class SplashScreen extends AppCompatActivity {
    TextView logoname;
    ImageView gifSplash;
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

        Dexter.withContext(getApplicationContext()).withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                logoname.clearAnimation();
                                gifSplash.clearAnimation();
                                logoname.setAnimation(fadeoutAnim);
                                gifSplash.setAnimation(fadeoutAnim);
                                logoname.setVisibility(View.INVISIBLE);

                            }
                        },1500);

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                onboardingScreen = getSharedPreferences("OnBoardingScreen",MODE_PRIVATE);
                                boolean isfirstTime = onboardingScreen.getBoolean("FirstTime",true);
                                if (isfirstTime){
                                    SharedPreferences.Editor editor = onboardingScreen.edit();
                                    editor.putBoolean("FirstTime",false);
                                    editor.commit();
                                    startActivity(new Intent(SplashScreen.this, OnBoardingScreen.class));
                                    finish();
                                }
                                else{
                                    startActivity(new Intent(SplashScreen.this, Login_Phone.class));
                                    finish();
                                }

                            }
                        }, 1500);
                    }
                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        Toast.makeText(SplashScreen.this, "Permission DenidedQ!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();



    }
}