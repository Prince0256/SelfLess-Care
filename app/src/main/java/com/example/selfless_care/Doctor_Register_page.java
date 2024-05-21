package com.example.selfless_care;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Pattern;

public class Doctor_Register_page extends AppCompatActivity {
    EditText eddocusername,eddocemail,eddocmobilenumber,eddocpassword,eddocconfirmpassword,edlicensenumber,eddegrecertificatenumber;
    TextView tv1;
    Button btn;
    ImageButton btn2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_register_page);
        AlertDialog.Builder builder = new AlertDialog.Builder(Doctor_Register_page.this);

        // Set the title and message
        builder.setTitle("Registered Succesfully")
                .setMessage("*Please Note \n You Cannot Access Patients for 2-Day Period.In that Time Period Your License,Certificate,Clinic/Hospital.Will Be Checked And Verified By Our Agent.");
            final String EMAIL_REGEX =
                    "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
            final String USERNAME_REGEX = "^[a-zA-Z0-9_-]{3,16}$";
            final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$";
            final String MOBILENUMBER_REGEX = "^[+]{1}(?:[0-9\\-\\\\(\\\\)\\\\/\\\\.]\\s?){6,15}[0-9]{1}$";
            final String LICENSE_REGEX = "^[a-zA-Z0-9]+$"; // Modify as per your license pattern
            final String DEGREE_CERTIFICATE_REGEX = "^[a-zA-Z0-9]+$"; // Modify as per your degree certificate pattern


            final Pattern emailpattern = Pattern.compile(EMAIL_REGEX);
            final Pattern usernamepattern = Pattern.compile(USERNAME_REGEX);
            final Pattern passwordPattern = Pattern.compile(PASSWORD_REGEX);
            final Pattern mobilenumberpattern = Pattern.compile(MOBILENUMBER_REGEX);
            final Pattern licensePattern = Pattern.compile(LICENSE_REGEX);
            final Pattern degreePattern = Pattern.compile(DEGREE_CERTIFICATE_REGEX);

            eddocusername = findViewById(R.id.editTextRegDocUsername);
            eddocemail = findViewById(R.id.editTextRegDocEmail);
            eddocmobilenumber = findViewById(R.id.editTextRegDocMobileNumber);
            eddocpassword = findViewById(R.id.editTextRegDocPassword);
            eddocconfirmpassword = findViewById(R.id.editTextRegDocConfirmPassword);
            edlicensenumber = findViewById(R.id.editTextRegDocLicenseNumber);
            eddegrecertificatenumber = findViewById(R.id.editTextRegDocDegreeCertificateNumber);
            btn = findViewById(R.id.buttonDocRegister);
            btn2 = findViewById(R.id.buttonpasswordvisible);
            tv1 = findViewById(R.id.textViewDocExistingUser);

            tv1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(Doctor_Register_page.this, Doctor_Login_page.class));
                }
            });
            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
            public void onClick(View view) {
                togglePasswordVisibility();
            }

                private void togglePasswordVisibility() {
                    if (eddocpassword.getTransformationMethod() == PasswordTransformationMethod.getInstance()) {
                        eddocpassword.setTransformationMethod(null);
                        btn2.setImageResource(R.drawable.baseline_visibility_24); // Change to the "hidden eye" icon
                    } else {
                        eddocpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        btn2.setImageResource(R.drawable.baseline_visibility_off_24); // Change to the "visible eye" icon
                    }
                    eddocpassword.setSelection(eddocpassword.length());
                }
            });
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String username = eddocusername.getText().toString();
                    String email = eddocemail.getText().toString();
                    String password = eddocpassword.getText().toString();
                    String mobilenumber = eddocmobilenumber.getText().toString();
                    String confirm = eddocconfirmpassword.getText().toString();
                    String license = edlicensenumber.getText().toString();
                    String degreecertificate = eddegrecertificatenumber.getText().toString();

                        if (!usernamepattern.matcher(username).matches()) {
                            Toast.makeText(getApplicationContext(), "Enter Valid Username", Toast.LENGTH_SHORT).show();
                        }
                        else if (!passwordPattern.matcher(password).matches()) {
                            Toast.makeText(getApplicationContext(), "Password must contain at least 8 characters,having letter,digit and special symbol", Toast.LENGTH_SHORT).show();
                        }
                        else if (!licensePattern.matcher(license).matches()) {
                            Toast.makeText(getApplicationContext(), "Enter Valid License Number", Toast.LENGTH_SHORT).show();
                        }
                        else if (!degreePattern.matcher(degreecertificate).matches()) {
                            Toast.makeText(getApplicationContext(), "Enter Valid Degree Certificate Number", Toast.LENGTH_SHORT).show();
                        }
                        else if (!emailpattern.matcher(email).matches()) {
                            Toast.makeText(getApplicationContext(), "Enter Valid Email Address", Toast.LENGTH_SHORT).show();
                        }
                        else if (!mobilenumberpattern.matcher(mobilenumber).matches()) {

                            Toast.makeText(getApplicationContext(), "Enter Valid Mobile Number", Toast.LENGTH_SHORT).show();

                        //if (username.length() == 0 || email.length() == 0 || mobilenumber.length() == 0 || license.length() == 0 || degreecertificate.length() == 0 || password.length() == 0 || confirm.length() == 0) {
                        // Toast.makeText(getApplicationContext(), "please fill All detail", Toast.LENGTH_SHORT).show();
                    }else if (password.equals(confirm)) {
                            Toast.makeText(getApplicationContext(), "Password or Confirm password didn't match", Toast.LENGTH_SHORT).show();
                        }else{
                            Database db = new Database(getApplicationContext());
                        db.Docregister(username, email, password, mobilenumber, license, degreecertificate);
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        startActivity(new Intent(Doctor_Register_page.this, Doctor_Login_page.class));
                    }
            }
        });
    }
}