package com.as2developers.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login_Phone extends AppCompatActivity {
    EditText mPhoneNumber;
    Button mgetOTP;
    FirebaseAuth fAuth;
    FirebaseUser currentUser;
    FirebaseDatabase fdata;
    //we have to take this two variables
    private DatabaseReference databaseReference;
    private ValueEventListener valueEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_phone);
        mPhoneNumber = findViewById(R.id.PhoneNumber);
        mgetOTP = findViewById(R.id.getOTP);
        fAuth = FirebaseAuth.getInstance();
        currentUser = fAuth.getCurrentUser();
        fdata = FirebaseDatabase.getInstance();

        mPhoneNumber.requestFocus();
//        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        if (currentUser != null) {
            // The value will be default as empty string because for
            // the very first time when the app is opened, there is nothing to show
            String s1 = sh.getString("Latitude", "");
            String s2 = sh.getString("Longitude", "");
            String s3 = sh.getString("locationType", "");
            String s4 = sh.getString("LocationDetails", "");
            String s5 = sh.getString("pin", "");
            String s6 = sh.getString("locality", "");
            String s7 = sh.getString("longAddress", "");

            if( !s1.isEmpty() && !s2.isEmpty() && !s3.isEmpty()){
                //we have the lat and long
                startActivity(new Intent(Login_Phone.this, FormFillupActivity.class)
                        .putExtra("Latitude",s1)
                        .putExtra("Longitude",s2)
                        .putExtra("locationType",s3)
                        .putExtra("LocationDetails",s4)
                        .putExtra("pin",s5)
                        .putExtra("locality",s6)
                        .putExtra("longAddress",s7));
                finish();
            }else {
                Intent intent = new Intent(Login_Phone.this, SelectLocationFromMap.class);
                startActivity(intent);
                finish();
            }
        } else {
            mgetOTP.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String mobileNo = mPhoneNumber.getText().toString().trim();

                    if (mobileNo.isEmpty()) {
                        mPhoneNumber.setError("Phone Number is required");
                        mPhoneNumber.requestFocus();
                        return;
                    }
                    if (mobileNo.length()!=10) {
                        mPhoneNumber.setError("Enter a valid Phone Number");
                        mPhoneNumber.requestFocus();
                        return;
                    }

                    databaseReference = fdata.getReference("Users").child("+91"+mobileNo);
                    valueEventListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(!snapshot.exists()){
                                Intent intent = new Intent(Login_Phone.this, UserDetails.class);
                                intent.putExtra("mobile", mobileNo);
                                startActivity(intent);
                            }
                            else{
                                Intent intent = new Intent(Login_Phone.this, Verify_Number.class);
                                intent.putExtra("mobile", mobileNo);
                                startActivity(intent);
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    };
                    databaseReference.addValueEventListener(valueEventListener);
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(databaseReference != null && valueEventListener != null) databaseReference.removeEventListener(valueEventListener);
    }
}

//ours code
//package com.as2developers.myapplication;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.view.View;
//import android.view.inputmethod.InputMethodManager;
//import android.widget.Button;
//import android.widget.EditText;
//
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//public class Login_Phone extends AppCompatActivity {
//    EditText mPhoneNumber;
//    Button mgetOTP;
//    FirebaseAuth fAuth;
//    FirebaseUser currentUser;
//    FirebaseDatabase fdata;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login_phone);
//        mPhoneNumber = findViewById(R.id.PhoneNumber);
//        mgetOTP = findViewById(R.id.getOTP);
//        fAuth = FirebaseAuth.getInstance();
//        currentUser = fAuth.getCurrentUser();
//        fdata = FirebaseDatabase.getInstance();
//
//        mPhoneNumber.requestFocus();
////        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
////        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
//        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
//        if (currentUser != null) {
//            // The value will be default as empty string because for
//            // the very first time when the app is opened, there is nothing to show
//            String s1 = sh.getString("Latitude", "");
//            String s2 = sh.getString("Longitude", "");
//            String s3 = sh.getString("locationType", "");
//            String s4 = sh.getString("LocationDetails", "");
//            String s5 = sh.getString("pin", "");
//            String s6 = sh.getString("locality", "");
//            String s7 = sh.getString("longAddress", "");
//
//            if( !s1.isEmpty() && !s2.isEmpty() && !s3.isEmpty()){
//                //we have the lat and long
//                startActivity(new Intent(Login_Phone.this, FormFillupActivity.class)
//                        .putExtra("Latitude",s1)
//                        .putExtra("Longitude",s2)
//                        .putExtra("locationType",s3)
//                        .putExtra("LocationDetails",s4)
//                        .putExtra("pin",s5)
//                        .putExtra("locality",s6)
//                        .putExtra("longAddress",s7));
//                finish();
//            }else {
//                Intent intent = new Intent(Login_Phone.this, SelectLocationFromMap.class);
//                startActivity(intent);
//                finish();
//            }
//        } else {
//            mgetOTP.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    String mobileNo = mPhoneNumber.getText().toString().trim();
//
//                    if (mobileNo.isEmpty()) {
//                        mPhoneNumber.setError("Phone Number is required");
//                        mPhoneNumber.requestFocus();
//                        return;
//                    }
//                    if (mobileNo.length()!=10) {
//                        mPhoneNumber.setError("Enter a valid Phone Number");
//                        mPhoneNumber.requestFocus();
//                        return;
//                    }
//
//                    DatabaseReference databaseReference = fdata.getReference("Users").child("+91"+mobileNo);
//                    databaseReference.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            if(!snapshot.exists()){
//                                Intent intent = new Intent(Login_Phone.this, UserDetails.class);
//                                intent.putExtra("mobile", mobileNo);
//                                startActivity(intent);
//                            }
//                            else{
//                                Intent intent = new Intent(Login_Phone.this, Verify_Number.class);
//                                intent.putExtra("mobile", mobileNo);
//                                startActivity(intent);
//                            }
//                        }
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });
//                }
//            });
//        }
//    }
//}
