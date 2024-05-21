package com.example.selfless_care;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.selfless_care.utils.AndroidUtils;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;

public class ForgetPasswordActivityActivity extends AppCompatActivity {

    EditText edUsername, edEmail, edMobileNumber, edLastPasswordyouremember, edRecievedOTP;
    Button btnResetPassword,btnSendOTP;
    TextView tv;
    Long timeoutSeconds = 60L;
    String verificationCode;
    PhoneAuthProvider.ForceResendingToken resendingToken;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);

        final String EMAIL_REGEX =
                "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        final String USERNAME_REGEX = "^[a-zA-Z0-9_-]{3,16}$";
        final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$";
        final String MOBILENUMBER_REGEX = "^[+]{1}(?:[0-9\\-\\\\(\\\\)\\\\/\\\\.]\\s?){6,15}[0-9]{1}$";

        final Pattern emailpattern = Pattern.compile(EMAIL_REGEX);
        final Pattern usernamepattern = Pattern.compile(USERNAME_REGEX);
        final Pattern passwordPattern = Pattern.compile(PASSWORD_REGEX);
        final Pattern mobilenumberpattern = Pattern.compile(MOBILENUMBER_REGEX);

        edUsername = findViewById(R.id.editTextForUsername);
        edLastPasswordyouremember = findViewById(R.id.editTextForLastPasswordYouRemember);
        edMobileNumber = findViewById(R.id.editTextForMobileNumber);
        edEmail = findViewById(R.id.editTextForEmail);
        edRecievedOTP = findViewById(R.id.editTextForRecievedOTP);
        btnResetPassword = findViewById(R.id.buttonResetPassword);
        btnSendOTP = findViewById(R.id.buttonSendOTP);
        tv = findViewById(R.id.buttonback);

        //edMobileNumber = getIntent().getExtras().getString("phone");

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){
                startActivity(new Intent(ForgetPasswordActivityActivity.this,LoginActivity.class));
            }
        });


        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edUsername.getText().toString();
                startActivity(new Intent("com.example.selfless_care.ACTION_RESET_PASSWORD").putExtra("KEY_DATA", username));
            }
        });
        btnSendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = edUsername.getText().toString();
                String email = edEmail.getText().toString();
                String mobilenumber = edMobileNumber.getText().toString();
                AtomicReference<Boolean> OTPSent= new AtomicReference<>(false);
                String lastpasswordyouremember = edLastPasswordyouremember.getText().toString();

                    if (!usernamepattern.matcher(username).matches()) {
                        Toast.makeText(getApplicationContext(), "Enter Valid Username", Toast.LENGTH_SHORT).show();
                    }
                else if (!emailpattern.matcher(email).matches()) {
                    Toast.makeText(getApplicationContext(), "Enter Valid Email Address", Toast.LENGTH_SHORT).show();
                }
                else if (!mobilenumberpattern.matcher(mobilenumber).matches()) {
                    Toast.makeText(getApplicationContext(), "Enter Valid Mobile Number", Toast.LENGTH_SHORT).show();

                    }else{
                        Database db = new Database(getApplicationContext());
                    CompletableFuture<Integer> futureCheckDetails=db.checkDetails("USERS",username, email,mobilenumber);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        futureCheckDetails.thenAccept(res -> {
                            if (res == 1) {
                                sendOTP(mobilenumber,false);
                                OTPSent.set(true);
                                Toast.makeText(getApplicationContext(), "Verify for OTP", Toast.LENGTH_SHORT).show();
                            } else if (res == 2) {
                                Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Invalid username or Email or Mobile Number", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                }
                if (OTPSent.get()) {
                    edUsername.setEnabled(false);
                    edEmail.setEnabled(false);
                    edMobileNumber.setEnabled(false);
                    edLastPasswordyouremember.setEnabled(false);

                }
        }
    });
} void sendOTP (String mobilenumber,boolean isResend) {
        PhoneAuthOptions.Builder builder =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(mobilenumber)
                        .setTimeout(timeoutSeconds, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                signIn(phoneAuthCredential);
                                AndroidUtils.showToast(getApplicationContext(),"OTP Verification Successful");

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                AndroidUtils.showToast(getApplicationContext(),"OTP Verification Failed");

                            }

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(s, forceResendingToken);
                                verificationCode = s;
                                resendingToken = forceResendingToken;
                                AndroidUtils.showToast(getApplicationContext(),"OTP Sent Successfully");
                            }
                        });
        if (isResend){
            PhoneAuthProvider.verifyPhoneNumber(builder.setForceResendingToken(resendingToken).build());
        }else{
            PhoneAuthProvider.verifyPhoneNumber(builder.build());
        }


    }void signIn(PhoneAuthCredential phoneAuthCredential){

    }
}
