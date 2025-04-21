package com.nchoan.financialmanagement.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nchoan.financialmanagement.MainActivity;
import com.nchoan.financialmanagement.R;
import com.nchoan.financialmanagement.database.DBManager;
import com.nchoan.financialmanagement.adapter.BudgetAdapter;
import com.nchoan.financialmanagement.model.BudgetModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BudgetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BudgetFragment extends Fragment {
    private String username;
    private int userId;
    private long newRowId = -1;
    private ArrayList<BudgetModel> listBudgets = new ArrayList<>();
    private RecyclerView recyclerView;
    private EditText edtBudgetAmount;
    private DatePicker dpCreatingDate;
    private Button btnCreatingBudget, btnShowAllBudget;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_budget, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        username = getUsername();
        userId = getUserId();

        edtBudgetAmount = view.findViewById(R.id.et_budget_amount);
        dpCreatingDate = view.findViewById(R.id.dp_creating_date);
        btnCreatingBudget = view.findViewById(R.id.btn_creating_budget);
        btnShowAllBudget = view.findViewById(R.id.btn_show_budget);
        recyclerView = view.findViewById(R.id.recycler_view);

        setupViews();
    }

    public static BudgetFragment newInstance(String username) {
        BudgetFragment fragment = new BudgetFragment();
        Bundle args = new Bundle();
        args.putString(MainActivity.USERNAME_KEY, username);
        fragment.setArguments(args);
        return fragment;
    }

    public void setupViews() {
        btnCreatingBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int amountBudget = Integer.parseInt(edtBudgetAmount.getText().toString());
                int day = dpCreatingDate.getDayOfMonth();
                int month = dpCreatingDate.getMonth() + 1;
                int year = dpCreatingDate.getYear();
                String creatingDate = day + "/" + month + "/" + year;

                BudgetModel newBudget = new BudgetModel(userId, amountBudget, creatingDate);

                insertBudget(newBudget);
                checkRegister();
            }
        });

        btnShowAllBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAllBudgetByUserId(userId);
                BudgetAdapter budgetAdapter = new BudgetAdapter(listBudgets);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext());
                recyclerView.setAdapter(budgetAdapter);
                recyclerView.setLayoutManager(layoutManager);
            }
        });
    }

    private void insertBudget(BudgetModel budgetModel) {
        DBManager dbManager = new DBManager(requireContext());
        newRowId = dbManager.insertBudget(budgetModel);
    }

    private void getAllBudgetByUserId(int userId) {
        DBManager dbManager = new DBManager(requireContext());
        listBudgets = dbManager.getAllBudgetByUserId(userId);
    }

    private void checkRegister() {
        if (newRowId != -1) {
            Toast.makeText(requireContext(), "Thêm mới thành công!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(requireContext(), "Thêm mới không thành công!", Toast.LENGTH_SHORT).show();
        }
    }

    private String getUsername() {
        // Lấy username từ MainActivity
        Bundle args = getArguments();
        if (args != null) {
            username = args.getString(MainActivity.USERNAME_KEY);
        }
        return username;
    }

    private int getUserId() {
        DBManager dbManager = new DBManager(requireContext());
        int id = dbManager.getUserIdByUsername(username);
        return id;
    }
}