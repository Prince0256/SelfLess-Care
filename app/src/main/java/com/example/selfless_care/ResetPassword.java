package com.example.selfless_care;

import static com.example.selfless_care.R.id.buttonChangePassword;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ResetPassword extends AppCompatActivity {

    EditText edPassword, edConfirm;
    Button btn;
    String receivedData;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpassword);
        btn = findViewById(buttonChangePassword);
        edConfirm = findViewById(R.id.editTextResConfirmPassword);
        edPassword = findViewById(R.id.editTextResPassword);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("KEY_DATA")) {
            receivedData = intent.getStringExtra("KEY_DATA");

            Log.i("test",receivedData);
        }else{
            Toast.makeText(getApplicationContext(), "please fill All detail", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ResetPassword.this, ForgetPasswordActivityActivity.class));
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(ResetPassword.this, First_Login.class));
            String password = edPassword.getText().toString();
                String confirm = edConfirm.getText().toString();

                Database db = new Database(getApplicationContext());
                if ( password.length() == 0 || confirm.length() == 0) {
                    Toast.makeText(getApplicationContext(), "please fill All detail", Toast.LENGTH_SHORT).show();
                }else {
                    if (password.compareTo(confirm) == 0) {
                       if (isValid(password)) {
                            db.Reset(receivedData,password);
                            Toast.makeText(getApplicationContext(), "Record Inserted", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(ResetPassword.this,First_Login.class));
                        }else {
                            Toast.makeText(getApplicationContext(), "Password must contain at least 8 characters,having letter,digit and special symbol", Toast.LENGTH_SHORT).show();
                        }

                    }else {
                        Toast.makeText(getApplicationContext(), "Password or Confirm password didn't match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    public static boolean isValid(String passwordhere) {
        int f1 = 0, f2 = 0, f3 = 0;
        if (passwordhere.length() < 8) {
            return false;
        } else {
            for (int p = 0; p < passwordhere.length(); p++) {
                if (Character.isLetter(passwordhere.charAt(p))) {
                    f1=1;
                }
            }
            for (int r = 0; r < passwordhere.length(); r++) {
                if (Character.isDigit(passwordhere.charAt(r))) {
                    f2=1;
                }
            }
            for (int s = 0; s < passwordhere.length(); s++) {
                char c = passwordhere.charAt(s);
                if (c >= 33 && c <= 46 || c == 64) {
                    f3 = 1;
                }
            }
            if (f1 == 1 && f2 == 1 && f3 == 1)
                return true;
            return false;
        }
    }
}