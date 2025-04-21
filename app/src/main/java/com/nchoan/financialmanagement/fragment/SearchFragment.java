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
import android.widget.EditText;
import android.widget.Toast;

import com.nchoan.financialmanagement.R;
import com.nchoan.financialmanagement.adapter.TransactionAdapter;
import com.nchoan.financialmanagement.model.TransactionModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    private ArrayList<TransactionModel> listTransactions = new ArrayList<>();
    private ArrayList<TransactionModel> listResultSearch = new ArrayList<>();
    private EditText edtSearch;
    private Button btnSearch;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edtSearch = view.findViewById(R.id.edt_search);
        btnSearch = view.findViewById(R.id.btn_search);
        recyclerView = view.findViewById(R.id.recycler_view);

        getListTransactions();
        setupViews();
    }

    private void getListTransactions() {
        Bundle args = getArguments();
        if (args != null) {
            listTransactions = args.getParcelableArrayList("TRANSACTION_LIST");
        }
    }

    private void setupViews() {
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listResultSearch.clear();
                String searchText = edtSearch.getText().toString().trim();
                if(searchText.isEmpty()) {
                    Toast.makeText(requireContext(), "Vui lòng nhập nội dung tìm kiếm!", Toast.LENGTH_SHORT).show();
                } else {
                    for(int i = 0; i < listTransactions.size(); i++) {
                        if (listTransactions.get(i).getType().equals(searchText)) {
                            listResultSearch.add(listTransactions.get(i));
                        }
                    }
                }
                TransactionAdapter transactionAdapter = new TransactionAdapter(listResultSearch);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext());
                recyclerView.setAdapter(transactionAdapter);
                recyclerView.setLayoutManager(layoutManager);
            }
        });
    }
}