package com.nchoan.financialmanagement.model;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.nchoan.financialmanagement.R;
import com.nchoan.financialmanagement.database.DBManager;

public class RegisterActivity extends AppCompatActivity {

    private EditText edtUsername, edtPassword, edtEmail;
    private Button btnRegister;
    private long newRowId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        mapIdToView();
        setupViews();
    }

    private void mapIdToView() {
        edtUsername = findViewById(R.id.edt_username);
        edtPassword = findViewById(R.id.edt_password);
        edtEmail = findViewById(R.id.edt_email);
        btnRegister = findViewById(R.id.btn_register);
    }

    private void setupViews() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                String email = edtEmail.getText().toString();
                UserModel newUser = new UserModel(username, password, email);
                insertUser(newUser);
                if(checkRegister()) {
                    startLoginActivity();
                }
            }
        });
    }

    private boolean checkRegister() {
        if (newRowId != -1) {
            Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(this, "Đăng ký không thành công!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void insertUser(UserModel newUser) {
        DBManager DBManager = new DBManager(getApplicationContext());
        newRowId = DBManager.insertUser(newUser);
    }

    private void startLoginActivity() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}