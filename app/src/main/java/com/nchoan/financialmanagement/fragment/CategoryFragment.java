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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.nchoan.financialmanagement.R;
import com.nchoan.financialmanagement.database.DBManager;
import com.nchoan.financialmanagement.adapter.CategoryAdapter;
import com.nchoan.financialmanagement.model.CategoryModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryFragment extends Fragment {

    private EditText etCategoryName;
    private Spinner spCategoryType;
    private Button btnAddCategory, btnShowAllCategory;
    private long newRowId = -1;
    private String categoryType;
    private String categoryName;
    private ArrayList<CategoryModel> listCategories = new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycler_view);
        etCategoryName = view.findViewById(R.id.et_category_name);
        spCategoryType = view.findViewById(R.id.sp_category_type);
        btnAddCategory = view.findViewById(R.id.btn_add_category);
        btnShowAllCategory = view.findViewById(R.id.btn_show_category);
        setupViews();
    }

    private void setupViews() {
        // Tạo Spinner cho Category Type
        String[] listCategoryTypes = {"Thu nhập", "Chi tiêu"};
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, listCategoryTypes);
        spCategoryType.setAdapter(categoryAdapter);

        spCategoryType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCategoryType = parent.getItemAtPosition(position).toString();
                categoryType = selectedCategoryType;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        btnAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryName = etCategoryName.getText().toString();
                CategoryModel newCategory = new CategoryModel(categoryName, categoryType);
                insertCategory(newCategory);
                checkRegister();
            }
        });

        btnShowAllCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAllCategory();
                CategoryAdapter categoryAdapter = new CategoryAdapter(listCategories);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext());
                recyclerView.setAdapter(categoryAdapter);
                recyclerView.setLayoutManager(layoutManager);
            }
        });
    }

    private void getAllCategory() {
        DBManager DBManager = new DBManager(requireContext());
        listCategories = DBManager.getAllCategory();
    }

    private void insertCategory(CategoryModel newCategory) {
        // requireContext(): lấy context của Activity chứa Fragment
        DBManager DBManager = new DBManager(requireContext());
        newRowId = DBManager.insertCategory(newCategory);
    }

    private void checkRegister() {
        if (newRowId != -1) {
            Toast.makeText(requireContext(), "Thêm mới thành công!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(requireContext(), "Thêm mới không thành công!", Toast.LENGTH_SHORT).show();
        }
    }
}