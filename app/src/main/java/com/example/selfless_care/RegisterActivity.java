package com.example.selfless_care;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    EditText edUsername, edEmail,edMobileNumber,edPassword, edConfirm;
    Button btn;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final String EMAIL_REGEX =
                "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        final String USERNAME_REGEX = "^[a-zA-Z0-9_-]{3,16}$";
        final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$";
        final String MOBILENUMBER_REGEX = "^[+]{1}(?:[0-9\\-\\\\(\\\\)\\\\/\\\\.]\\s?){6,15}[0-9]{1}$";

        final Pattern emailpattern = Pattern.compile(EMAIL_REGEX);
        final Pattern usernamepattern = Pattern.compile(USERNAME_REGEX);
        final Pattern passwordPattern = Pattern.compile(PASSWORD_REGEX);
        final Pattern mobilenumberpattern = Pattern.compile(MOBILENUMBER_REGEX);

        edUsername = findViewById(R.id.editTextRegUsername);
        edPassword = findViewById(R.id.editTextRegPassword);
        edMobileNumber = findViewById(R.id.editTextRegMobileNumber);
        edEmail = findViewById(R.id.editTextRegEmail);
        edConfirm = findViewById(R.id.editTextRegConfirmPassword);
        btn = findViewById(R.id.buttonRegister);
        tv = findViewById(R.id.textViewExistingUser);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = edUsername.getText().toString();
                String email = edEmail.getText().toString();
                String password = edPassword.getText().toString();
                String mobilenumber = edMobileNumber.getText().toString();
                String confirm = edConfirm.getText().toString();
                    if (!usernamepattern.matcher(username).matches()) {
                        Toast.makeText(getApplicationContext(), "Enter Valid Username", Toast.LENGTH_SHORT).show();
                    }
                    else if (!emailpattern.matcher(email).matches()) {
                        Toast.makeText(getApplicationContext(), "Enter Valid Email Address", Toast.LENGTH_SHORT).show();
                    }
                    else if (!passwordPattern.matcher(password).matches()) {
                        Toast.makeText(getApplicationContext(), "Password must contain at least 8 characters,having letter,digit and special symbol", Toast.LENGTH_SHORT).show();
                    }
                    else if (!mobilenumberpattern.matcher(mobilenumber).matches()) {
                        Toast.makeText(getApplicationContext(), "Enter Valid Mobile Number with country code", Toast.LENGTH_SHORT).show();

                    //if (username.length() == 0 || email.length() == 0 || mobilenumber.length() == 0 || license.length() == 0 || degreecertificate.length() == 0 || password.length() == 0 || confirm.length() == 0) {
                    //Toast.makeText(getApplicationContext(), "please fill All detail", Toast.LENGTH_SHORT).show();
                }else if (!password.equals(confirm)) {
                        Toast.makeText(getApplicationContext(), "Password or Confirm password didn't match", Toast.LENGTH_SHORT).show();
                    }else{
                    Database db = new Database(getApplicationContext());
                    db.register(username, email, password, mobilenumber);
                    Toast.makeText(getApplicationContext(), "Registered Succesfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                }
            }
        });
    }
}