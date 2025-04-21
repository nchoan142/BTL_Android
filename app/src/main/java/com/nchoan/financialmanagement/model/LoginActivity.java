package com.nchoan.financialmanagement.model;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.nchoan.financialmanagement.MainActivity;
import com.nchoan.financialmanagement.R;
import com.nchoan.financialmanagement.database.DBManager;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private ArrayList<UserModel> listUser = new ArrayList<>();
    private EditText edtUsername, edtPassword;
    private Button btnLogin;
    private TextView tvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        mapIdToView();
        getAllUser();
        setupViews();
    }

    private void setupViews() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();

                Log.d("TAG", "Username: " + username + ", Password: " + password);

                if (checkLogin(username, password)) {
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                    startMainActivity(username); // Truyền username vào MainActivity
                } else {
                    Toast.makeText(LoginActivity.this, "Đăng nhập không thành công!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRegisterActivity();
            }
        });
    }

    private boolean checkLogin(String username, String password) {
        boolean check = false;
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập tên đăng nhập và mật khẩu", Toast.LENGTH_SHORT).show();
        } else if (!checkUsernameExists(username)) {
            Toast.makeText(this, "Tài khoản chưa tồn tại", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            for (int i = 0; i < listUser.size(); i++) {
                if (listUser.get(i).getUserName().equals(username)) {
                    if (listUser.get(i).getUserPassword().equals(password)) {
                        check = true;
                        break;
                    }
                }
            }
            if(!check) {
                Toast.makeText(this, "Tên đăng nhập hoặc mật khẩu không chính xác!", Toast.LENGTH_SHORT).show();
            }
        }
        return check;
    }

    // Có thể sử dụng cho RegisterActivity
    private boolean checkUsernameExists(String username) {
        for (UserModel user : listUser) {
            if (user.getUserName().equals(username)) {
                return true;
            }
        }
        return false;
    }

    private void startMainActivity(String username) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra(MainActivity.USERNAME_KEY, username);
        startActivity(intent);
        finish();
    }

    private void startRegisterActivity() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    private void mapIdToView() {
        edtUsername = findViewById(R.id.edt_username);
        edtPassword = findViewById(R.id.edt_password);
        btnLogin = findViewById(R.id.btn_login);
        tvRegister = findViewById(R.id.lb_register);
    }

    private void getAllUser() {
        DBManager dbManager = new DBManager(getApplicationContext());
        listUser = dbManager.getAllUsers();
        Log.d("listUser", "Số lượng phần tử: " + listUser.size());
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllUser();
    }
}