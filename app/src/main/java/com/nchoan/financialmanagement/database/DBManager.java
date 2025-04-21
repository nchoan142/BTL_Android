package com.nchoan.financialmanagement.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nchoan.financialmanagement.model.BudgetModel;
import com.nchoan.financialmanagement.model.CategoryModel;
import com.nchoan.financialmanagement.model.TransactionModel;
import com.nchoan.financialmanagement.model.UserModel;

import java.util.ArrayList;

public class DBManager {
    private final DBHelper dbHelper;

    public DBManager(Context context) {
        this.dbHelper = new DBHelper(context);
    }

    public long insertUser(UserModel userModel) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBContract.UserEntry.COLUMN_USERNAME, userModel.getUserName());
        values.put(DBContract.UserEntry.COLUMN_PASSWORD, userModel.getUserPassword());
        values.put(DBContract.UserEntry.COLUMN_EMAIL, userModel.getUserEmail());
        long newRowId = db.insert(DBContract.UserEntry.TABLE_NAME, null, values);
        db.close();
        return newRowId;
    }

    public long insertCategory(CategoryModel categoryModel) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBContract.CategoryEntry.COLUMN_NAME, categoryModel.getCategoryName());
        values.put(DBContract.CategoryEntry.COLUMN_TYPE, categoryModel.getCategoryType());
        long newRowId = db.insert(DBContract.CategoryEntry.TABLE_NAME, null, values);
        db.close();
        return newRowId;
    }

    public long insertBudget(BudgetModel budgetModel) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBContract.BudgetEntry.COLUMN_USER_ID, budgetModel.getUserId());
        values.put(DBContract.BudgetEntry.COLUMN_AMOUNT, budgetModel.getBudgetAmount());
        values.put(DBContract.BudgetEntry.COLUMN_MONTH, budgetModel.getCreatingDate());

        long newRowId = db.insert(DBContract.BudgetEntry.TABLE_NAME, null, values);
        db.close();
        return newRowId;
    }

    public long insertTransaction(TransactionModel transactionModel) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DBContract.TransactionsEntry.COLUMN_USER_ID, transactionModel.getUserId());
        values.put(DBContract.TransactionsEntry.COLUMN_AMOUNT, transactionModel.getAmount());
        values.put(DBContract.TransactionsEntry.COLUMN_TYPE, transactionModel.getType());
        values.put(DBContract.TransactionsEntry.COLUMN_NOTE, transactionModel.getNote());
        values.put(DBContract.TransactionsEntry.COLUMN_DATE, transactionModel.getDate());

        long newRowId = db.insert(DBContract.TransactionsEntry.TABLE_NAME, null, values);
        db.close();

        return newRowId;
    }

    public UserModel getUserByUsername(String username) {
        UserModel userModel = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * " +
                    "FROM " + DBContract.UserEntry.TABLE_NAME +
                    " WHERE " + DBContract.UserEntry.COLUMN_USERNAME + " = ?",
                new String[]{username});

        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.UserEntry.COLUMN_USERNAME));
            String password = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.UserEntry.COLUMN_PASSWORD));
            String email = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.UserEntry.COLUMN_EMAIL));

            userModel = new UserModel(name, password, email);
        }

        cursor.close();
        db.close();

        return userModel;
    }

    public int getUserIdByUsername(String username) {
        int userId = -1;
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                    "SELECT " + DBContract.UserEntry.COLUMN_ID +
                        " FROM " + DBContract.UserEntry.TABLE_NAME +
                        " WHERE " + DBContract.UserEntry.COLUMN_USERNAME + " = ?",
                new String[]{username}
        );

//        if (cursor != null && cursor.moveToFirst()) {
//        if (cursor.moveToNext()) {

        while (cursor.moveToNext()) {
            userId = cursor.getInt(cursor.getColumnIndexOrThrow(DBContract.UserEntry.COLUMN_ID));
        }

        cursor.close();
        db.close();
        return userId;
    }

    public ArrayList<UserModel> getAllUsers() {
        ArrayList<UserModel> listUser = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * " +
                    "FROM " + DBContract.UserEntry.TABLE_NAME, null);

        while (cursor.moveToNext()) {
            String username = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.UserEntry.COLUMN_USERNAME));
            String password = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.UserEntry.COLUMN_PASSWORD));
            String email = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.UserEntry.COLUMN_EMAIL));

            UserModel userModel = new UserModel(username, password, email);
            listUser.add(userModel);
        }

        cursor.close();
        db.close();

        return listUser;
    }

    public ArrayList<CategoryModel> getAllCategory() {
        ArrayList<CategoryModel> listCategory = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * " +
                    "FROM " + DBContract.CategoryEntry.TABLE_NAME, null);

        while (cursor.moveToNext()) {
            String categoryName = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.CategoryEntry.COLUMN_NAME));
            String categoryType = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.CategoryEntry.COLUMN_TYPE));

            CategoryModel categoryModel = new CategoryModel(categoryName, categoryType);
            listCategory.add(categoryModel);
        }

        cursor.close();
        db.close();

        return listCategory;
    }

    public ArrayList<BudgetModel> getAllBudgetByUserId(int userId) {
        ArrayList<BudgetModel> listBudget = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * " +
                    "FROM " + DBContract.BudgetEntry.TABLE_NAME +
                    " WHERE " + DBContract.BudgetEntry.COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(userId)}
        );

        while (cursor.moveToNext()) {
            int uid = cursor.getInt(cursor.getColumnIndexOrThrow(DBContract.BudgetEntry.COLUMN_USER_ID));
            int amount = cursor.getInt(cursor.getColumnIndexOrThrow(DBContract.BudgetEntry.COLUMN_AMOUNT));
            String month = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.BudgetEntry.COLUMN_MONTH));

            BudgetModel budget = new BudgetModel(uid, amount, month);
            listBudget.add(budget);
        }

        cursor.close();
        db.close();

        return listBudget;
    }

    public ArrayList<TransactionModel> getAllTransactionByUserId(int userId) {
        ArrayList<TransactionModel> transactionList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * " +
                    "FROM " + DBContract.TransactionsEntry.TABLE_NAME +
                    " WHERE " + DBContract.TransactionsEntry.COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(userId)}
        );

        while (cursor.moveToNext()) {
            int uid = cursor.getInt(cursor.getColumnIndexOrThrow(DBContract.TransactionsEntry.COLUMN_USER_ID));
            int amount = cursor.getInt(cursor.getColumnIndexOrThrow(DBContract.TransactionsEntry.COLUMN_AMOUNT));
            String type = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.TransactionsEntry.COLUMN_TYPE));
            String note = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.TransactionsEntry.COLUMN_NOTE));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.TransactionsEntry.COLUMN_DATE));

            TransactionModel model = new TransactionModel(uid, amount, type, note, date);
            transactionList.add(model);
        }

        cursor.close();
        db.close();
        return transactionList;
    }
}
