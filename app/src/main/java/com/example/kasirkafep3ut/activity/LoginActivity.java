package com.example.kasirkafep3ut.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kasirkafep3ut.R;
import com.example.kasirkafep3ut.adapter.AlertDialogManager;
import com.example.kasirkafep3ut.helper.DatabaseHelper;
import com.example.kasirkafep3ut.session.SessionManager;

public class LoginActivity extends AppCompatActivity {

    EditText txtUsername, txtPassword;
    Button btnLogin, btnRegister;
    AlertDialogManager alert = new AlertDialogManager();
    SessionManager session;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        session = new SessionManager(getApplicationContext());

        txtUsername = findViewById(R.id.email);
        txtPassword = findViewById(R.id.password);

        btnLogin = findViewById(R.id.masuk);
        btnRegister = findViewById(R.id.ke_daftar);

        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Get username, password from EditText
                String username = txtUsername.getText().toString();
                String password = txtPassword.getText().toString();

                // Check if username, password is filled
                try {
                    // Check if username, password is filled
                    if (username.trim().length() > 0 && password.trim().length() > 0) {
                        dbHelper.open();

                        if (dbHelper.Login(username, password)) {
                            session.createLoginSession(username);

                            finish();
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(i);

                        } else {
                            alert.showAlertDialog(LoginActivity.this, "Login gagal..", "Email atau Password salah!", false);

                        }
                    } else {
                        alert.showAlertDialog(LoginActivity.this, "Login gagal..", "Form tidak boleh kosong!", false);
                    }
                } catch (Exception e) {
                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
    }


    @Override
    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setMessage("Apakah Anda ingin keluar dari aplikasi?");
        builder.setCancelable(true);
        builder.setNegativeButton(getString(R.string.batal), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setPositiveButton(getString(R.string.keluar), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
