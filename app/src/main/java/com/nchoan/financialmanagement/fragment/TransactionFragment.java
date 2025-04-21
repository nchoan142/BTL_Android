package com.nchoan.financialmanagement.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.nchoan.financialmanagement.MainActivity;
import com.nchoan.financialmanagement.R;
import com.nchoan.financialmanagement.database.DBManager;
import com.nchoan.financialmanagement.model.BudgetModel;
import com.nchoan.financialmanagement.model.TransactionModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TransactionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TransactionFragment extends Fragment {

//    private EditText etAmount, etNote;
//    private Spinner spCategory;
//    private DatePicker dpDate;
//    private Button btnAddTransaction;
    private String username;
    private int userId;
    private long newRowId = -1;
    private ArrayList<TransactionModel> listTransactions = new ArrayList<>();
    private EditText edtAmount, edtNote;
    private Spinner spTradingType;
    private DatePicker dpTradingDate;
    private Button btnAddTransaction, btnBackToHome;
    private String tradingType;
    private int totalBudget = 0, totalIncome = 0, totalExpense = 0;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transaction, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        username = getUsername();
        userId = getUserId();
        Log.d("TransactionFragment", "Username: " + username);

        edtAmount = view.findViewById(R.id.et_amount);
        edtNote = view.findViewById(R.id.et_note);
        spTradingType = view.findViewById(R.id.sp_trading_type);
        dpTradingDate = view.findViewById(R.id.dp_trading_date);
        btnAddTransaction = view.findViewById(R.id.btn_add_transaction);
        btnBackToHome = view.findViewById(R.id.btn_back_to_home);
        setupViews();
    }

    public static TransactionFragment newInstance(String username) {
        TransactionFragment fragment = new TransactionFragment();
        Bundle args = new Bundle();
        args.putString(MainActivity.USERNAME_KEY, username);
        fragment.setArguments(args);
        return fragment;
    }

    private void setupViews() {
        // Tạo Spinner cho spType
        String[] listTradingTypes = {"Thu nhập", "Chi tiêu"};
        ArrayAdapter<String> tradingTypeAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, listTradingTypes);
        spTradingType.setAdapter(tradingTypeAdapter);

        spTradingType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tradingTypeSelected = parent.getItemAtPosition(position).toString();
                tradingType = tradingTypeSelected;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnAddTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int amount = Integer.parseInt(edtAmount.getText().toString());
                String note = edtNote.getText().toString();
                int day = dpTradingDate.getDayOfMonth();
                int month = dpTradingDate.getMonth() + 1;
                int year = dpTradingDate.getYear();
                String tradingDate = day + "/" + month + "/" + year;

                TransactionModel newTransaction = new TransactionModel(userId, amount, tradingType, note, tradingDate);
                insertTransaction(newTransaction);
                checkRegister();
            }
        });

        btnBackToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString(MainActivity.USERNAME_KEY, username);
                args.putBoolean("FROM_BACK_BUTTON", true);

                HomeFragment homeFragment = HomeFragment.newInstance(username);
//                requireActivity().getSupportFragmentManager().popBackStack();
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, homeFragment)
                        .commit();
            }
        });
    }

    private String getUsername() {
        // Lấy username từ MainActivity
        Bundle args = getArguments();
        if (args != null) {
            username = args.getString(MainActivity.USERNAME_KEY);
        }
        return username;
    }

//    private int getTotalBudget() {
//        Bundle args = getArguments();
//        if (args != null) {
//            totalBudget = args.getInt("TOTAL_BUDGET");
//        }
//        return totalBudget;
//    }
//
//    private int getTotalIncome() {
//        Bundle args = getArguments();
//        if (args != null) {
//            totalIncome = args.getInt("TOTAL_INCOME");
//        }
//        return totalIncome;
//    }
//
//    private int getTotalExpense() {
//        Bundle args = getArguments();
//        if (args != null) {
//            totalExpense = args.getInt("TOTAL_EXPENSE");
//        }
//        return totalExpense;
//    }

    private int getUserId() {
        DBManager dbManager = new DBManager(requireContext());
        int id = dbManager.getUserIdByUsername(username);
        return id;
    }

    private void getAllTransactionByUserId(int userId) {
        DBManager dbManager = new DBManager(requireContext());
        listTransactions = dbManager.getAllTransactionByUserId(userId);
    }

    private void checkRegister() {
        if (newRowId != -1) {
            Toast.makeText(requireContext(), "Thêm mới thành công!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(requireContext(), "Thêm mới không thành công!", Toast.LENGTH_SHORT).show();
        }
    }

    private void insertTransaction(TransactionModel transactionModel) {
        DBManager dbManager = new DBManager(requireContext());
        newRowId = dbManager.insertTransaction(transactionModel);
    }
}