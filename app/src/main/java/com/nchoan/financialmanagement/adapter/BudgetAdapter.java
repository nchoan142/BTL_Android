package com.nchoan.financialmanagement.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nchoan.financialmanagement.R;
import com.nchoan.financialmanagement.model.BudgetModel;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class BudgetAdapter extends RecyclerView.Adapter<BudgetAdapter.ViewHolder> {
    private final ArrayList<BudgetModel> listBudgets;

    public BudgetAdapter(ArrayList<BudgetModel> listBudgets) {
        this.listBudgets = listBudgets;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.budget_item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BudgetModel item = listBudgets.get(position);
        setupViews(holder, item, position);
    }

    private void setupViews(ViewHolder holder, BudgetModel item, int position) {
        View view = holder.getView();
        TextView tvBudget = view.findViewById(R.id.tv_budget);
        TextView tvCreatingDate = view.findViewById(R.id.tv_creating_date);

        DecimalFormat formatter = new DecimalFormat("#,###");
        String formattedAmount = formatter.format(item.getBudgetAmount());

        tvBudget.setText(formattedAmount);
        tvCreatingDate.setText(item.getCreatingDate());
    }

    @Override
    public int getItemCount() {
        return listBudgets.size();
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
