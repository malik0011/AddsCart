package com.as2developers.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.material.navigation.NavigationView;

public class ConfirmPickupActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView txt_itemPickup, txt_pickUpdate, txt_confirm, scarpItem_txt, change_scrap, chane_Adddress, change_pickup,
            txt_homeAddress, txt_Address, txt_pickupTime,txt_AddressType;
    View fragment_location;
    ImageButton back;
    Button continue_btn;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    private static final int REQUEST_CALL =1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_pickup);
        //for back
        back = findViewById(R.id.Img);
        //hooks for navigation bar
        drawerLayout =findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.dummy_content,R.string.dummy_content);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        txt_itemPickup = findViewById(R.id.text_itemPickup);
        txt_pickUpdate = findViewById(R.id.txt_pickupDate);
        txt_confirm = findViewById(R.id.txt_confirm);
        scarpItem_txt = findViewById(R.id.scarpitem_text);
        txt_homeAddress = findViewById(R.id.txt_homeAddress);
        txt_pickupTime = findViewById(R.id.pickUp_time);
        txt_Address = findViewById(R.id.txt_Address);
        txt_AddressType = findViewById(R.id.txt_Addresstype);

        continue_btn = findViewById(R.id.btn_proceed);

        //to set date also selected items
        Intent gIntent = getIntent();
        String temp = gIntent.getStringExtra("date");
        String LocationType = gIntent.getStringExtra("LocationType");
        String ItemCount = String.format("%s items for pickup", getIntent().getStringExtra("itemCount"));

        txt_AddressType.setText(LocationType);
        txt_pickupTime.setText(temp +"\n10AM-6PM");
        scarpItem_txt.setText(getIntent().getStringExtra("items"));
        txt_itemPickup.setText(ItemCount);
        txt_Address.setText(String.format("%s\n%s\n%s",getIntent().getStringExtra("locality")!=null?getIntent().getStringExtra("locality"):"",getIntent().getStringExtra("longAddress")!=null?getIntent().getStringExtra("longAddress"):"", getIntent().getStringExtra("AddressLine")!=null?getIntent().getStringExtra("AddressLine"):""
                ));  //null checking

        continue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ConfirmPickupActivity.this,DonateOrGetActivity.class);
                i.putExtra("AddressLine",getIntent().getStringExtra("AddressLine"));
                i.putExtra("items",getIntent().getStringExtra("items"));
                i.putExtra("date",getIntent().getStringExtra("date"));
                i.putExtra("Latitude",getIntent().getStringExtra("Latitude"));
                i.putExtra("Longitude",getIntent().getStringExtra("Longitude"));
                i.putExtra("locality",getIntent().getStringExtra("locality"));
                i.putExtra("longAddress",getIntent().getStringExtra("longAddress"));
                startActivity(i.putExtra("locationType",getIntent().getStringExtra("locationType").toString()));
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.profile:
                startActivity(new Intent(this,ProfilePage.class));
                break;
            case R.id.howItWorks:
                startActivity(new Intent(this,HowItWorks.class));;
                break;
            case R.id.aboutUs:
                startActivity(new Intent(this,AboutUs.class));
                break;
            case R.id.call_us:
               makePhoneCall();
               // Toast.makeText(this, "This feature will coming soon!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.logOut:
                Toast.makeText(this, "Logging out..", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,Login_Phone.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
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

    private void makePhoneCall(){

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.CALL_PHONE}, REQUEST_CALL);
        }
        else{
            String phoneNo = "tel:"+"8867825523";
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse(phoneNo));
            startActivity(intent);
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==REQUEST_CALL){
            if(grantResults.length > 0  && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                //when permission granted call method
                makePhoneCall();
            }else{
                Toast.makeText(this, "Call Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }
}