package com.example.selfless_care;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.CompletableFuture;

public class Doctor_Login_page extends AppCompatActivity {

    Button btn;
    EditText eddoctorusername, eddoctorPassword;
    TextView tv,tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_login_page);

        eddoctorusername= findViewById(R.id.editTextLoginDoctorUsername);
        eddoctorPassword = findViewById(R.id.editTextLoginDoctorPassword);
        btn = findViewById(R.id.buttonLogin);
        tv = findViewById(R.id.textViewDocNewUser);
        tv2 = findViewById(R.id.textViewDocNewUser2);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Doctorusername = eddoctorusername.getText().toString();
                String Doctorpassword = eddoctorPassword.getText().toString();
                String enteredpassword = eddoctorPassword.getText().toString();
                Database db = new Database(getApplicationContext());
                if (Doctorusername.length() == 0 || Doctorpassword.length() == 0) {
                    Toast.makeText(getApplicationContext(), "please fill All details", Toast.LENGTH_SHORT).show();

                } else {
                    CompletableFuture<Integer> loginFuture=db.login("DOC USERS",Doctorusername, Doctorpassword);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        loginFuture.thenAccept(res -> {
                            if (res == 1) {
                                Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();
                                SharedPreferences sharedpreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString("username", Doctorusername);
                                editor.apply();

                                startActivity(new Intent(Doctor_Login_page.this, HomeActivity.class));
                            }else if (res == 2) {

                                Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
                            } else {
                                eddoctorPassword.setText("");
                                Toast.makeText(getApplicationContext(), "Invalid username and password", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Doctor_Login_page.this,Doctor_Register_page.class));
            }
        });
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void  onClick(View view) {
                startActivity(new Intent(Doctor_Login_page.this, ForgetPasswordActivityActivity.class));
            }
        });;
    }

}
