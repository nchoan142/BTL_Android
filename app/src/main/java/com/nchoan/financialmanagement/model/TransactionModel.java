package com.nchoan.financialmanagement.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class TransactionModel implements Parcelable {
    private int userId;
    private int amount;
    private String type;
    private String note;
    private String date;

    public TransactionModel(int userId, int amount, String type, String note, String date) {
        this.userId = userId;
        this.amount = amount;
        this.type = type;
        this.note = note;
        this.date = date;
    }

    protected TransactionModel(Parcel in) {
        userId = in.readInt();
        amount = in.readInt();
        type = in.readString();
        note = in.readString();
        date = in.readString();
    }

    public static final Creator<TransactionModel> CREATOR = new Creator<TransactionModel>() {
        @Override
        public TransactionModel createFromParcel(Parcel in) {
            return new TransactionModel(in);
        }

        @Override
        public TransactionModel[] newArray(int size) {
            return new TransactionModel[size];
        }
    };

    public int getUserId() {
        return userId;
    }

    public int getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    public String getNote() {
        return note;
    }

    public String getDate() {
        return date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(userId);
        dest.writeInt(amount);
        dest.writeString(type);
        dest.writeString(note);
        dest.writeString(date);
    }
}
