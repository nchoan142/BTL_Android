package com.nchoan.financialmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.nchoan.financialmanagement.fragment.AccountFragment;
import com.nchoan.financialmanagement.fragment.BudgetFragment;
import com.nchoan.financialmanagement.fragment.CategoryFragment;
import com.nchoan.financialmanagement.fragment.HomeFragment;
import com.nchoan.financialmanagement.fragment.TransactionFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private AccountFragment accountFragment;
    private BudgetFragment budgetFragment;
    private HomeFragment homeFragment;
    private CategoryFragment categoryFragment;
    public static final String USERNAME_KEY = "Username";
    public String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        username = getUsernameFromLoginActivity();
        Log.d("Username MainActivity", "Username: " + username);
        mapIdToView();
        setupViews();

    }

    private String getUsernameFromLoginActivity() {
        Intent intent = getIntent();
        return intent.getStringExtra(USERNAME_KEY);
    }

    private void mapIdToView() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
    }

    private void setupViews() {
        // args: Tham số truyền vào Fragment
        Bundle args = new Bundle();
        args.putString(USERNAME_KEY, username);

        homeFragment = new HomeFragment();
        budgetFragment = new BudgetFragment();
        accountFragment = new AccountFragment();
        categoryFragment = new CategoryFragment();

        // Truyền username vào từng Fragment
        homeFragment.setArguments(args);
        budgetFragment.setArguments(args);
        accountFragment.setArguments(args);

        // Fragment đầu tiên sẽ hiển thị
        // Khi MainActivity được khởi tạo
        // onCreateView() chỉ được gọi sau khi gọi commit()
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, homeFragment)
                .commit();

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_home) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, homeFragment)
                            .commit();
                    return true;
                }  else if (id == R.id.nav_budget) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, budgetFragment)
                            .commit();
                    return true;
                } else if (id == R.id.nav_account) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, accountFragment)
                            .commit();
                    return true;
                } else if (id == R.id.nav_category) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, categoryFragment)
                            .commit();
                    return true;
                }
                return false;
            }
        });
    }
}