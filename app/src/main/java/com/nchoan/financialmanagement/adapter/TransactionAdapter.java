package com.nchoan.financialmanagement.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nchoan.financialmanagement.R;
import com.nchoan.financialmanagement.model.TransactionModel;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {
    private final ArrayList<TransactionModel> listTransactions;

    public TransactionAdapter(ArrayList<TransactionModel> listTransactions) {
        this.listTransactions = listTransactions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.transaction_item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TransactionModel item = listTransactions.get(position);
        setupViews(holder, item, position);
    }

    private void setupViews(ViewHolder holder, TransactionModel item, int position) {
        View view = holder.getView();
        TextView tvAmount = view.findViewById(R.id.tv_amount);
        TextView tvType = view.findViewById(R.id.tv_type);
        TextView tvNote = view.findViewById(R.id.tv_note);
        TextView tvDate = view.findViewById(R.id.tv_date);

        DecimalFormat formatter = new DecimalFormat("#,###");
        String formattedAmount = formatter.format(item.getAmount());

        tvAmount.setText(formattedAmount);
        tvType.setText(item.getType());
        tvNote.setText(item.getNote());
        tvDate.setText(item.getDate());
    }

    @Override
    public int getItemCount() {
        return listTransactions.size();
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
