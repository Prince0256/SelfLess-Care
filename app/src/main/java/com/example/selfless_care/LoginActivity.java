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

public class LoginActivity extends AppCompatActivity {

    EditText edusername, edPassword;
    Button btn;
    TextView tv,tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edusername = findViewById(R.id.editTextLoginUsername);
        edPassword = findViewById(R.id.editTextLoginPassword);
        btn = findViewById(R.id.buttonLogin);
        tv = findViewById(R.id.textViewNewUser);
        tv2 = findViewById(R.id.textViewNewUser2);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = edusername.getText().toString();
                String password = edPassword.getText().toString();
                Database db = new Database(getApplicationContext());
                if (username.length() == 0 || password.length() == 0) {
                    Toast.makeText(getApplicationContext(), "please fill All details", Toast.LENGTH_SHORT).show();

                } else {
                    CompletableFuture<Integer> loginFuture=db.login("USERS",username, password);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        loginFuture.thenAccept(res -> {
                            if (res == 1) {
                                Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();
                                SharedPreferences sharedpreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString("username", username);
                                editor.apply();

                                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            } else if (res == 2) {
                                Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
                            } else {
                                edPassword.setText("");
                                Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();


                            }
                        });
                    }
                }

            }
        });
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void  onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ForgetPasswordActivityActivity.class));
            }
        });
    }

}
