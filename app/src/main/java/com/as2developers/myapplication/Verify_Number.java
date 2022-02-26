package com.as2developers.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Verify_Number extends AppCompatActivity {
    String mVerificationId;
    PinView enterOTP;
    FirebaseAuth mAuth;
    Button LoginPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_number);
        mAuth = FirebaseAuth.getInstance();
        enterOTP = findViewById(R.id.enterOTP);
        LoginPhone = findViewById(R.id.LoginPhone);
        Intent intent = getIntent();
        String mobile = intent.getStringExtra("mobile");
        sendVerificationCode(mobile);

        enterOTP.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        LoginPhone.setOnClickListener(v -> {
            String code = Objects.requireNonNull(enterOTP.getText()).toString().trim();
            if (code.isEmpty() || code.length() < 6) {
                enterOTP.setError("Enter valid code");
                enterOTP.requestFocus();
                return;
            }
            verifyVerificationCode(code);
        });

    }

    private void sendVerificationCode(String mobile) {

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+91" + mobile)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

//        PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                "+91"+mobile,        // Phone number to verify
//                60,                 // Timeout duration
//                TimeUnit.SECONDS,   // Unit of timeout
//                this,               // Activity (for callback binding)
//                mCallbacks);                     // OnVerificationStateChangedCallbacks
    }

    private final PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                    String code = phoneAuthCredential.getSmsCode();
                    if (code != null) {
                        enterOTP.setText(code);
                        verifyVerificationCode(code);
                    }
                }

                @Override
                public void onVerificationFailed(FirebaseException e) {
                    Toast.makeText(Verify_Number.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e("TAG", e.getMessage());
                }

                @Override
                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s,forceResendingToken);
                    mVerificationId = s;
                }
            };

    private void verifyVerificationCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(Verify_Number.this, task -> {
            if (task.isSuccessful()) {
                //verification successful we will start the profile activity
                Intent intent = new Intent(Verify_Number.this, SelectLocationFromMap.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            } else {
                String message = "Something is wrong, we will fix it soon...";

                if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                    message = "Invalid code entered...";
                }
                Toast.makeText(Verify_Number.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
