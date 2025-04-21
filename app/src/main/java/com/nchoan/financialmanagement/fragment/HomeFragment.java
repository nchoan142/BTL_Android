package com.nchoan.financialmanagement.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nchoan.financialmanagement.MainActivity;
import com.nchoan.financialmanagement.R;
import com.nchoan.financialmanagement.database.DBManager;
import com.nchoan.financialmanagement.model.BudgetModel;
import com.nchoan.financialmanagement.adapter.TransactionAdapter;
import com.nchoan.financialmanagement.model.TransactionModel;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private String username;
    private int userId;
    private RecyclerView recyclerView;
    private TextView tvBalance, tvIncome, tvExpense;
    private Button btnAddTransaction;
    private TransactionFragment transactionFragment;
    private ArrayList<TransactionModel> listTransactions = new ArrayList<>();
    private ArrayList<BudgetModel> listBudgets = new ArrayList<>();
    private int totalBudget = 0, totalIncome = 0, totalExpense = 0;
    private ImageView ivSearch;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnAddTransaction = view.findViewById(R.id.btn_add_transaction);
        recyclerView = view.findViewById(R.id.recycler_view);
        tvBalance = view.findViewById(R.id.tv_balance);
        tvIncome = view.findViewById(R.id.tv_income);
        tvExpense = view.findViewById(R.id.tv_expense);
        ivSearch = view.findViewById(R.id.iv_search);
        username = getUsername();
        userId = getUserId();

        setupViews();
    }

    public static HomeFragment newInstance(String username) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(MainActivity.USERNAME_KEY, username);
        fragment.setArguments(args);
        return fragment;
    }

    private void setupViews() {
        totalBudget = 0;
        totalIncome = 0;
        totalExpense = 0;

        showAllTransaction();
        caculateTotalIncome();
        caculateTotalExpense();
        caculateTotalBudget();

        DecimalFormat formatter = new DecimalFormat("#,###");
        String formattedTotalIncome = formatter.format(totalIncome);
        String formattedTotalExpense = formatter.format(totalExpense);
        String formattedTotalBudget = formatter.format(totalBudget);

        tvBalance.setText("Số dư: " + formattedTotalBudget + " VND");
        tvIncome.setText("Thu nhập: +" + formattedTotalIncome + " VND");
        tvExpense.setText("Chi tiêu: -" + formattedTotalExpense + " VND");

        btnAddTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString(MainActivity.USERNAME_KEY, username);
                args.putInt("TOTAL_INCOME", totalIncome);
                args.putInt("TOTAL_EXPENSE", totalExpense);
                args.putInt("TOTAL_BUDGET", totalBudget);
                transactionFragment = TransactionFragment.newInstance(username);
                transactionFragment.setArguments(args);

                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, transactionFragment)
                        .commit();
            }
        });

        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchFragment searchFragment = new SearchFragment();
                Bundle args = new Bundle();
                args.putParcelableArrayList("TRANSACTION_LIST", listTransactions);
                searchFragment.setArguments(args);

                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, searchFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private void showAllTransaction() {
        getAllTransactionByUserId(userId);
        TransactionAdapter transactionAdapter = new TransactionAdapter(listTransactions);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setAdapter(transactionAdapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    private int getUserId() {
        DBManager dbManager = new DBManager(requireContext());
        int id = dbManager.getUserIdByUsername(username);
        return id;
    }

    private String getUsername() {
        // Lấy username từ MainActivity
        Bundle args = getArguments();
        if (args != null) {
            username = args.getString(MainActivity.USERNAME_KEY);
        }
        return username;
    }

    private void getAllTransactionByUserId(int userId) {
        DBManager dbManager = new DBManager(requireContext());
        listTransactions = dbManager.getAllTransactionByUserId(userId);
    }

    private void getAllBudgetByUserId(int userId) {
        DBManager dbManager = new DBManager(requireContext());
        listBudgets = dbManager.getAllBudgetByUserId(userId);
    }

    private void caculateTotalBudget() {
        getAllBudgetByUserId(userId);
        for (BudgetModel budget : listBudgets) {
            totalBudget += budget.getBudgetAmount();
        }
        totalBudget = totalBudget + totalIncome - totalExpense;
    }

    private void caculateTotalIncome() {
        for(int i = 0; i < listTransactions.size(); i++) {
            if(listTransactions.get(i).getType().equals("Thu nhập")) {
                totalIncome += listTransactions.get(i).getAmount();
            }
        }
    }

    private void caculateTotalExpense() {
        for(int i = 0; i < listTransactions.size(); i++) {
            if(listTransactions.get(i).getType().equals("Chi tiêu")) {
                totalExpense += listTransactions.get(i).getAmount();
            }
        }
    }
}