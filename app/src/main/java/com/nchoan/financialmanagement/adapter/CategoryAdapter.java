package com.nchoan.financialmanagement.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nchoan.financialmanagement.R;
import com.nchoan.financialmanagement.model.CategoryModel;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private final ArrayList<CategoryModel> listCategories;

    public CategoryAdapter(ArrayList<CategoryModel> listCategories) {
        this.listCategories = listCategories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.category_item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryModel item = listCategories.get(position);
        setupViews(holder, item, position);
    }

    private void setupViews(ViewHolder holder, CategoryModel item, int position) {
        View view = holder.getView();
        TextView categoryName = view.findViewById(R.id.tv_category_name);
        TextView categoryType = view.findViewById(R.id.tv_category_type);

        categoryName.setText(item.getCategoryName());
        categoryType.setText(item.getCategoryType());
    }

    @Override
    public int getItemCount() {
        return listCategories.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View view;

        ViewHolder(View view) {
            super(view);
            this.view = view;
        }

        View getView() {
            return view;
        }
    }
}
