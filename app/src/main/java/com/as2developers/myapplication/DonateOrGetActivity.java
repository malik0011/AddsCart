//package com.as2developers.myapplication;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageButton;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.as2developers.myapplication.Modals.UserModal;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.ArrayList;
//
//
//public class DonateOrGetActivity extends AppCompatActivity {
//
//    ImageButton back;
//    Button cash,donate;
//    String Name , Address, AddressLine1,Items,date1,mode,Lon,Lat,locality,longAddress;
//    TextView userName,userAddress;
//
//    FirebaseDatabase database,fdata;
//    DatabaseReference ref;
//    FirebaseAuth mAuth,fAuth;
//
//    //    ArrayList for storing data
//    ArrayList<String> ItemType = new ArrayList<String>();
//    ArrayList<String> FullAddress = new ArrayList<String>();
//    ArrayList<String> Date = new ArrayList<String>();
//    ArrayList<String> PhoneNumber = new ArrayList<String>();
//    ArrayList<String> Latitude = new ArrayList<String>();
//    ArrayList<String> Longitude = new ArrayList<String>();
//    ArrayList<String> iscomplete = new ArrayList<String>();
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_donate_or_get);
//
//        back = findViewById(R.id.img);
//        cash = findViewById(R.id.button);
//        donate = findViewById(R.id.button2);
//        userName = findViewById(R.id.userName);
//        userAddress = findViewById(R.id.userAddress);
//
//
//        fAuth = FirebaseAuth.getInstance();
//        fdata = FirebaseDatabase.getInstance();
//        database=FirebaseDatabase.getInstance();
//        mAuth = FirebaseAuth.getInstance();
//        FirebaseUser user = mAuth.getCurrentUser();
//        ref = database.getReference("Users").child(user.getPhoneNumber());
//
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                UserModal user01 = snapshot.getValue(UserModal.class);
//                String username = user01.getName();
//                userName.setText(username);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//        DatabaseReference refcomp = fdata.getReference("OrderId").child("IsComplete");
//        refcomp.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    String data = dataSnapshot.getValue(String.class);
//                    iscomplete.add(data);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//        DatabaseReference refitems = fdata.getReference("OrderId").child("Items");
//        refitems.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    String data = dataSnapshot.getValue(String.class);
//                    ItemType.add(data);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//        DatabaseReference refdate = fdata.getReference("OrderId").child("Date");
//        refdate.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    String data = dataSnapshot.getValue(String.class);
//                    Date.add(data);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//        DatabaseReference refnumber = fdata.getReference("OrderId").child("Phone Number");
//        refnumber.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    String data = dataSnapshot.getValue(String.class);
//                    PhoneNumber.add(data);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//        DatabaseReference reflat = fdata.getReference("OrderId").child("Latitude");
//        reflat.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    String data = dataSnapshot.getValue(String.class);
//                    Latitude.add(data);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//        DatabaseReference reflong = fdata.getReference("OrderId").child("Longitude");
//        reflong.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    String data = dataSnapshot.getValue(String.class);
//                    Longitude.add(data);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//        DatabaseReference refadd = fdata.getReference("OrderId").child("Address");
//        refadd.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    String data = dataSnapshot.getValue(String.class);
//                    FullAddress.add(data);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//        Intent intent = getIntent();
//        String AddressLine = intent.getStringExtra("AddressLine");
//        String ScrapItem = intent.getStringExtra("items");
//        String date = intent.getStringExtra("date");
//
//        userAddress.setText(AddressLine);
//
//        cash.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(DonateOrGetActivity.this,ConfirmAnimationSound.class);
//                i.putExtra("mode","cash");
//                i.putExtra("AddressLine",AddressLine);
//                i.putExtra("items",ScrapItem);
//                i.putExtra("date",date);
//                i.putExtra("Latitude",getIntent().getStringExtra("Latitude"));
//                i.putExtra("Longitude",getIntent().getStringExtra("Longitude"));
//                i.putExtra("locality",getIntent().getStringExtra("locality"));
//                i.putExtra("longAddress",getIntent().getStringExtra("longAddress"));
//
//                Intent intent = getIntent();
//                AddressLine1 = intent.getStringExtra("AddressLine");
//                Items = intent.getStringExtra("items");
//                date1 = intent.getStringExtra("date");
//                mode = intent.getStringExtra("mode");
//                Lon = intent.getStringExtra("Longitude");
//                Lat = intent.getStringExtra("Latitude");
//                locality = intent.getStringExtra("locality");
//                longAddress = intent.getStringExtra("longAddress");
//
//                iscomplete.add("False");
//                DatabaseReference refcomp2 = fdata.getReference("OrderId").child("IsComplete");
//                refcomp2.setValue(iscomplete);
//
//                ItemType.add(Items);
//                DatabaseReference refitems2 = fdata.getReference("OrderId").child("Items");
//                refitems2.setValue(ItemType);
//
//                Date.add(date1);
//                DatabaseReference refdate2 = fdata.getReference("OrderId").child("Date");
//                refdate2.setValue(Date);
//
//                FirebaseUser user = mAuth.getCurrentUser();
//                PhoneNumber.add(user.getPhoneNumber());
//                DatabaseReference refnumber2 = fdata.getReference("OrderId").child("Phone Number");
//                refnumber2.setValue(PhoneNumber);
//
//                Latitude.add(Lat);
//                DatabaseReference reflat2 = fdata.getReference("OrderId").child("Latitude");
//                reflat2.setValue(Latitude);
//
//                Longitude.add(Lon);
//                DatabaseReference reflong2 = fdata.getReference("OrderId").child("Longitude");
//                reflong2.setValue(Longitude);
//
//                FullAddress.add(locality+", "+AddressLine1);
//                DatabaseReference refadd2 = fdata.getReference("OrderId").child("Address");
//                refadd2.setValue(FullAddress);
//
//
//                startActivity(i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
//                finish();
//            }
//        });
//
//        donate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(DonateOrGetActivity.this, Impact.class);
//                i.putExtra("mode","NGO");
//                i.putExtra("AddressLine",AddressLine);
//                i.putExtra("items",ScrapItem);
//                i.putExtra("date",date);
//                i.putExtra("Latitude",getIntent().getStringExtra("Latitude"));
//                i.putExtra("Longitude",getIntent().getStringExtra("Longitude"));
//                i.putExtra("locality",getIntent().getStringExtra("locality"));
//                i.putExtra("longAddress",getIntent().getStringExtra("longAddress"));
//
//                Intent intent = getIntent();
//                AddressLine1 = intent.getStringExtra("AddressLine");
//                Items = intent.getStringExtra("items");
//                date1 = intent.getStringExtra("date");
//                mode = intent.getStringExtra("mode");
//                Lon = intent.getStringExtra("Longitude");
//                Lat = intent.getStringExtra("Latitude");
//                locality = intent.getStringExtra("locality");
//                longAddress = intent.getStringExtra("longAddress");
//
//                ItemType.add(Items);
//                DatabaseReference refitems2 = fdata.getReference("OrderId").child("Items");
//                refitems2.setValue(ItemType);
//
//                Date.add(date1);
//                DatabaseReference refdate2 = fdata.getReference("OrderId").child("Date");
//                refdate2.setValue(Date);
//
//                FirebaseUser user = mAuth.getCurrentUser();
//                PhoneNumber.add(user.getPhoneNumber());
//                DatabaseReference refnumber2 = fdata.getReference("OrderId").child("Phone Number");
//                refnumber2.setValue(PhoneNumber);
//
//                Latitude.add(Lat);
//                DatabaseReference reflat2 = fdata.getReference("OrderId").child("Latitude");
//                reflat2.setValue(Latitude);
//
//                Longitude.add(Lon);
//                DatabaseReference reflong2 = fdata.getReference("OrderId").child("Longitude");
//                reflong2.setValue(Longitude);
//
//                FullAddress.add(locality+", "+AddressLine1);
//                DatabaseReference refadd2 = fdata.getReference("OrderId").child("Address");
//                refadd2.setValue(FullAddress);
//
//                startActivity(i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
//                finish();
//            }
//        });
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
//
//    }
//}

//package com.as2developers.myapplication;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageButton;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.as2developers.myapplication.Modals.UserModal;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.ArrayList;
//
//
//public class DonateOrGetActivity extends AppCompatActivity {
//
//    ImageButton back;
//    Button cash,donate;
//    String Name , Address, AddressLine1,Items,date1,mode,Lon,Lat,locality,longAddress;
//    TextView userName,userAddress;
//
//    FirebaseDatabase database,fdata;
//    DatabaseReference ref;
//    FirebaseAuth mAuth,fAuth;
//
////    ArrayList for storing data
//    ArrayList<String> ItemType = new ArrayList<String>();
//    ArrayList<String> FullAddress = new ArrayList<String>();
//    ArrayList<String> Date = new ArrayList<String>();
//    ArrayList<String> PhoneNumber = new ArrayList<String>();
//    ArrayList<String> Latitude = new ArrayList<String>();
//    ArrayList<String> Longitude = new ArrayList<String>();
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_donate_or_get);
//
//        back = findViewById(R.id.img);
//        cash = findViewById(R.id.button);
//        donate = findViewById(R.id.button2);
//        userName = findViewById(R.id.userName);
//        userAddress = findViewById(R.id.userAddress);
//
//
//        fAuth = FirebaseAuth.getInstance();
//        fdata = FirebaseDatabase.getInstance();
//        database=FirebaseDatabase.getInstance();
//        mAuth = FirebaseAuth.getInstance();
//        FirebaseUser user = mAuth.getCurrentUser();
//        ref = database.getReference("Users").child(user.getPhoneNumber());
//
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                UserModal user01 = snapshot.getValue(UserModal.class);
//                String username = user01.getName();
//                userName.setText(username);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//        DatabaseReference refitems = fdata.getReference("OrderId").child("Items");
//        refitems.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    String data = dataSnapshot.getValue(String.class);
//                    ItemType.add(data);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//        DatabaseReference refdate = fdata.getReference("OrderId").child("Date");
//        refdate.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    String data = dataSnapshot.getValue(String.class);
//                    Date.add(data);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//        DatabaseReference refnumber = fdata.getReference("OrderId").child("Phone Number");
//        refnumber.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    String data = dataSnapshot.getValue(String.class);
//                    PhoneNumber.add(data);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//        DatabaseReference reflat = fdata.getReference("OrderId").child("Latitude");
//        reflat.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    String data = dataSnapshot.getValue(String.class);
//                    Latitude.add(data);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//        DatabaseReference reflong = fdata.getReference("OrderId").child("Longitude");
//        reflong.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    String data = dataSnapshot.getValue(String.class);
//                    Longitude.add(data);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//        DatabaseReference refadd = fdata.getReference("OrderId").child("Address");
//        refadd.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    String data = dataSnapshot.getValue(String.class);
//                    FullAddress.add(data);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//        Intent intent = getIntent();
//        String AddressLine = intent.getStringExtra("AddressLine");
//        String ScrapItem = intent.getStringExtra("items");
//        String date = intent.getStringExtra("date");
//
//        userAddress.setText(AddressLine);
//
//        cash.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(DonateOrGetActivity.this,ConfirmAnimationSound.class);
//                i.putExtra("mode","cash");
//                i.putExtra("AddressLine",AddressLine);
//                i.putExtra("items",ScrapItem);
//                i.putExtra("date",date);
//                i.putExtra("Latitude",getIntent().getStringExtra("Latitude"));
//                i.putExtra("Longitude",getIntent().getStringExtra("Longitude"));
//                i.putExtra("locality",getIntent().getStringExtra("locality"));
//                i.putExtra("longAddress",getIntent().getStringExtra("longAddress"));
//
//                Intent intent = getIntent();
//                AddressLine1 = intent.getStringExtra("AddressLine");
//                Items = intent.getStringExtra("items");
//                date1 = intent.getStringExtra("date");
//                mode = intent.getStringExtra("mode");
//                Lon = intent.getStringExtra("Longitude");
//                Lat = intent.getStringExtra("Latitude");
//                locality = intent.getStringExtra("locality");
//                longAddress = intent.getStringExtra("longAddress");
//
//                ItemType.add(Items);
//                DatabaseReference refitems2 = fdata.getReference("OrderId").child("Items");
//                refitems2.setValue(ItemType);
//
//                Date.add(date1);
//                DatabaseReference refdate2 = fdata.getReference("OrderId").child("Date");
//                refdate2.setValue(Date);
//
//                FirebaseUser user = mAuth.getCurrentUser();
//                PhoneNumber.add(user.getPhoneNumber());
//                DatabaseReference refnumber2 = fdata.getReference("OrderId").child("Phone Number");
//                refnumber2.setValue(PhoneNumber);
//
//                Latitude.add(Lat);
//                DatabaseReference reflat2 = fdata.getReference("OrderId").child("Latitude");
//                reflat2.setValue(Latitude);
//
//                Longitude.add(Lon);
//                DatabaseReference reflong2 = fdata.getReference("OrderId").child("Longitude");
//                reflong2.setValue(Longitude);
//
//                FullAddress.add(locality+", "+AddressLine1);
//                DatabaseReference refadd2 = fdata.getReference("OrderId").child("Address");
//                refadd2.setValue(FullAddress);
//
//
//                startActivity(i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
//                finish();
//            }
//        });
//
//        donate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(DonateOrGetActivity.this, Impact.class);
//                i.putExtra("mode","NGO");
//                i.putExtra("AddressLine",AddressLine);
//                i.putExtra("items",ScrapItem);
//                i.putExtra("date",date);
//                i.putExtra("Latitude",getIntent().getStringExtra("Latitude"));
//                i.putExtra("Longitude",getIntent().getStringExtra("Longitude"));
//                i.putExtra("locality",getIntent().getStringExtra("locality"));
//                i.putExtra("longAddress",getIntent().getStringExtra("longAddress"));
//
//                Intent intent = getIntent();
//                AddressLine1 = intent.getStringExtra("AddressLine");
//                Items = intent.getStringExtra("items");
//                date1 = intent.getStringExtra("date");
//                mode = intent.getStringExtra("mode");
//                Lon = intent.getStringExtra("Longitude");
//                Lat = intent.getStringExtra("Latitude");
//                locality = intent.getStringExtra("locality");
//                longAddress = intent.getStringExtra("longAddress");
//
//                ItemType.add(Items);
//                DatabaseReference refitems2 = fdata.getReference("OrderId").child("Items");
//                refitems2.setValue(ItemType);
//
//                Date.add(date1);
//                DatabaseReference refdate2 = fdata.getReference("OrderId").child("Date");
//                refdate2.setValue(Date);
//
//                FirebaseUser user = mAuth.getCurrentUser();
//                PhoneNumber.add(user.getPhoneNumber());
//                DatabaseReference refnumber2 = fdata.getReference("OrderId").child("Phone Number");
//                refnumber2.setValue(PhoneNumber);
//
//                Latitude.add(Lat);
//                DatabaseReference reflat2 = fdata.getReference("OrderId").child("Latitude");
//                reflat2.setValue(Latitude);
//
//                Longitude.add(Lon);
//                DatabaseReference reflong2 = fdata.getReference("OrderId").child("Longitude");
//                reflong2.setValue(Longitude);
//
//                FullAddress.add(locality+", "+AddressLine1);
//                DatabaseReference refadd2 = fdata.getReference("OrderId").child("Address");
//                refadd2.setValue(FullAddress);
//
//                startActivity(i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
//                finish();
//            }
//        });
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
//
//    }
//}
package com.as2developers.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.as2developers.myapplication.Modals.UserModal;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class DonateOrGetActivity extends AppCompatActivity {

    ImageButton back;
    Button cash,donate;
    String Name , Address, AddressLine1,Items,date1,mode,Lon,Lat,locality,longAddress;
    TextView userName,userAddress;

    FirebaseDatabase database,fdata;
    DatabaseReference ref;
    FirebaseAuth mAuth,fAuth;

    //    ArrayList for storing data
    ArrayList<String> ItemType = new ArrayList<String>();
    ArrayList<String> FullAddress = new ArrayList<String>();
    ArrayList<String> Date = new ArrayList<String>();
    ArrayList<String> PhoneNumber = new ArrayList<String>();
    ArrayList<String> Latitude = new ArrayList<String>();
    ArrayList<String> Longitude = new ArrayList<String>();
    ArrayList<String> iscomplete = new ArrayList<String>();
    ArrayList<String> modeoft = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_or_get);

        back = findViewById(R.id.img);
        cash = findViewById(R.id.button);
        donate = findViewById(R.id.button2);
        userName = findViewById(R.id.userName);
        userAddress = findViewById(R.id.userAddress);


        fAuth = FirebaseAuth.getInstance();
        fdata = FirebaseDatabase.getInstance();
        database=FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        ref = database.getReference("Users").child(user.getPhoneNumber());

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserModal user01 = snapshot.getValue(UserModal.class);
                String username = user01.getName();
                userName.setText(username);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference refmode = fdata.getReference("OrderId").child("Mode");
        refmode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String data = dataSnapshot.getValue(String.class);
                    modeoft.add(data);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference refcomp = fdata.getReference("OrderId").child("IsComplete");
        refcomp.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String data = dataSnapshot.getValue(String.class);
                    iscomplete.add(data);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference refitems = fdata.getReference("OrderId").child("Items");
        refitems.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String data = dataSnapshot.getValue(String.class);
                    ItemType.add(data);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference refdate = fdata.getReference("OrderId").child("Date");
        refdate.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String data = dataSnapshot.getValue(String.class);
                    Date.add(data);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference refnumber = fdata.getReference("OrderId").child("Phone Number");
        refnumber.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String data = dataSnapshot.getValue(String.class);
                    PhoneNumber.add(data);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference reflat = fdata.getReference("OrderId").child("Latitude");
        reflat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String data = dataSnapshot.getValue(String.class);
                    Latitude.add(data);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference reflong = fdata.getReference("OrderId").child("Longitude");
        reflong.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String data = dataSnapshot.getValue(String.class);
                    Longitude.add(data);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference refadd = fdata.getReference("OrderId").child("Address");
        refadd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String data = dataSnapshot.getValue(String.class);
                    FullAddress.add(data);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Intent intent = getIntent();
        String AddressLine = intent.getStringExtra("AddressLine");
        String ScrapItem = intent.getStringExtra("items");
        String date = intent.getStringExtra("date");

        userAddress.setText(AddressLine);

        cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DonateOrGetActivity.this,ConfirmAnimationSound.class);
                i.putExtra("mode","cash");
                i.putExtra("AddressLine",AddressLine);
                i.putExtra("items",ScrapItem);
                i.putExtra("date",date);
                i.putExtra("Latitude",getIntent().getStringExtra("Latitude"));
                i.putExtra("Longitude",getIntent().getStringExtra("Longitude"));
                i.putExtra("locality",getIntent().getStringExtra("locality"));
                i.putExtra("longAddress",getIntent().getStringExtra("longAddress"));

                Intent intent = getIntent();
                AddressLine1 = intent.getStringExtra("AddressLine");
                Items = intent.getStringExtra("items");
                date1 = intent.getStringExtra("date");
                mode = intent.getStringExtra("mode");
                Lon = intent.getStringExtra("Longitude");
                Lat = intent.getStringExtra("Latitude");
                locality = intent.getStringExtra("locality");
                longAddress = intent.getStringExtra("longAddress");

                modeoft.add("Cash");
                DatabaseReference refmode2=fdata.getReference("OrderId").child("Mode");
                refmode2.setValue(modeoft);

                iscomplete.add("False");
                DatabaseReference refcomp2 = fdata.getReference("OrderId").child("IsComplete");
                refcomp2.setValue(iscomplete);

                ItemType.add(Items);
                DatabaseReference refitems2 = fdata.getReference("OrderId").child("Items");
                refitems2.setValue(ItemType);

                Date.add(date1);
                DatabaseReference refdate2 = fdata.getReference("OrderId").child("Date");
                refdate2.setValue(Date);

                FirebaseUser user = mAuth.getCurrentUser();
                PhoneNumber.add(user.getPhoneNumber());
                DatabaseReference refnumber2 = fdata.getReference("OrderId").child("Phone Number");
                refnumber2.setValue(PhoneNumber);

                Latitude.add(Lat);
                DatabaseReference reflat2 = fdata.getReference("OrderId").child("Latitude");
                reflat2.setValue(Latitude);

                Longitude.add(Lon);
                DatabaseReference reflong2 = fdata.getReference("OrderId").child("Longitude");
                reflong2.setValue(Longitude);

                FullAddress.add(locality+", "+AddressLine1);
                DatabaseReference refadd2 = fdata.getReference("OrderId").child("Address");
                refadd2.setValue(FullAddress);


                startActivity(i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
            }
        });

        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DonateOrGetActivity.this, Impact.class);
                i.putExtra("mode","NGO");
                i.putExtra("AddressLine",AddressLine);
                i.putExtra("items",ScrapItem);
                i.putExtra("date",date);
                i.putExtra("Latitude",getIntent().getStringExtra("Latitude"));
                i.putExtra("Longitude",getIntent().getStringExtra("Longitude"));
                i.putExtra("locality",getIntent().getStringExtra("locality"));
                i.putExtra("longAddress",getIntent().getStringExtra("longAddress"));

                Intent intent = getIntent();
                AddressLine1 = intent.getStringExtra("AddressLine");
                Items = intent.getStringExtra("items");
                date1 = intent.getStringExtra("date");
                mode = intent.getStringExtra("mode");
                Lon = intent.getStringExtra("Longitude");
                Lat = intent.getStringExtra("Latitude");
                locality = intent.getStringExtra("locality");
                longAddress = intent.getStringExtra("longAddress");

                modeoft.add("Donate");
                DatabaseReference refmode2=fdata.getReference("OrderId").child("Mode");
                refmode2.setValue(modeoft);

                iscomplete.add("False");
                DatabaseReference refcomp2 = fdata.getReference("OrderId").child("IsComplete");
                refcomp2.setValue(iscomplete);

                ItemType.add(Items);
                DatabaseReference refitems2 = fdata.getReference("OrderId").child("Items");
                refitems2.setValue(ItemType);

                Date.add(date1);
                DatabaseReference refdate2 = fdata.getReference("OrderId").child("Date");
                refdate2.setValue(Date);

                FirebaseUser user = mAuth.getCurrentUser();
                PhoneNumber.add(user.getPhoneNumber());
                DatabaseReference refnumber2 = fdata.getReference("OrderId").child("Phone Number");
                refnumber2.setValue(PhoneNumber);

                Latitude.add(Lat);
                DatabaseReference reflat2 = fdata.getReference("OrderId").child("Latitude");
                reflat2.setValue(Latitude);

                Longitude.add(Lon);
                DatabaseReference reflong2 = fdata.getReference("OrderId").child("Longitude");
                reflong2.setValue(Longitude);

                FullAddress.add(locality+", "+AddressLine1);
                DatabaseReference refadd2 = fdata.getReference("OrderId").child("Address");
                refadd2.setValue(FullAddress);

                startActivity(i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}