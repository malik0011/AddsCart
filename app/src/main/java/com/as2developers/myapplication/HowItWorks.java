package com.as2developers.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

public class HowItWorks extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //for slide navigation bar
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_it_works);

        //hooks for navigation bar
        drawerLayout =findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.dummy_content,R.string.dummy_content);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    //also for navigation bar
    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.profile:
                Intent i = new Intent(getApplicationContext(),ProfilePage.class);
                startActivity(i);
                break;
            case R.id.pickup:
                Toast.makeText(this, "Opening to a new pickup..", Toast.LENGTH_SHORT).show();
                break;
            case R.id.howItWorks:
                Toast.makeText(this, "You are already in!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.aboutUs:
              //  Toast.makeText(this, "you are in about us!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),AboutUs.class);
                startActivity(intent);
                break;
            case R.id.call_us:
//                Intent callIntent = new Intent(Intent.ACTION_CALL);
//                String s = "+918867825522";
//                callIntent.setData(Uri.parse("tel:"+s));//change the number.
//                startActivity(callIntent);
                Toast.makeText(this, "This feature will coming soon!", Toast.LENGTH_SHORT).show();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}