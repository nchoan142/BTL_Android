package com.nchoan.financialmanagement.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "FinanceDB.db";
    public static final int DATABASE_VERSION = 1;

    // Tạo bảng
    private static final String SQL_CREATE_USER =
            "CREATE TABLE " + DBContract.UserEntry.TABLE_NAME + " (" +
                    DBContract.UserEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    DBContract.UserEntry.COLUMN_USERNAME + " TEXT, " +
                    DBContract.UserEntry.COLUMN_PASSWORD + " TEXT, " +
                    DBContract.UserEntry.COLUMN_EMAIL + " TEXT" +
                    ");";

    private static final String SQL_CREATE_CATEGORY =
            "CREATE TABLE " + DBContract.CategoryEntry.TABLE_NAME + " (" +
                    DBContract.CategoryEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    DBContract.CategoryEntry.COLUMN_NAME + " TEXT, " +
                    DBContract.CategoryEntry.COLUMN_TYPE + " TEXT" +
                    ");";

    private static final String SQL_CREATE_BUDGET =
            "CREATE TABLE " + DBContract.BudgetEntry.TABLE_NAME + " (" +
                    DBContract.BudgetEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    DBContract.BudgetEntry.COLUMN_USER_ID + " INTEGER, " +
                    DBContract.BudgetEntry.COLUMN_AMOUNT + " INTEGER, " +
                    DBContract.BudgetEntry.COLUMN_MONTH + " TEXT, " +
                    "FOREIGN KEY(" + DBContract.BudgetEntry.COLUMN_USER_ID + ") REFERENCES " +
                    DBContract.UserEntry.TABLE_NAME + "(" + DBContract.UserEntry.COLUMN_ID + ")" +
                    ");";
    
    private static final String SQL_CREATE_TRANSACTIONS =
            "CREATE TABLE " + DBContract.TransactionsEntry.TABLE_NAME + " (" +
                    DBContract.TransactionsEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    DBContract.TransactionsEntry.COLUMN_USER_ID + " INTEGER, " +
                    DBContract.TransactionsEntry.COLUMN_AMOUNT + " INTEGER, " +
                    DBContract.TransactionsEntry.COLUMN_TYPE + " TEXT, " +
                    DBContract.TransactionsEntry.COLUMN_NOTE + " TEXT, " +
                    DBContract.TransactionsEntry.COLUMN_DATE + " TEXT, " +
                    "FOREIGN KEY(" + DBContract.TransactionsEntry.COLUMN_USER_ID + ") REFERENCES " +
                    DBContract.UserEntry.TABLE_NAME + "(" + DBContract.UserEntry.COLUMN_ID + ")" +
                    ");";

    // Xóa bảng
    private static final String SQL_DELETE_USER =
            "DROP TABLE IF EXISTS " + DBContract.UserEntry.TABLE_NAME;

    private static final String SQL_DELETE_CATEGORY =
            "DROP TABLE IF EXISTS " + DBContract.CategoryEntry.TABLE_NAME;

    private static final String SQL_DELETE_BUDGET =
            "DROP TABLE IF EXISTS " + DBContract.BudgetEntry.TABLE_NAME;

    private static final String SQL_DELETE_TRANSACTIONS =
            "DROP TABLE IF EXISTS " + DBContract.TransactionsEntry.TABLE_NAME;


    public DBHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_USER);
        db.execSQL(SQL_CREATE_CATEGORY);
        db.execSQL(SQL_CREATE_BUDGET);
        db.execSQL(SQL_CREATE_TRANSACTIONS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TRANSACTIONS);
        db.execSQL(SQL_DELETE_BUDGET);
        db.execSQL(SQL_DELETE_CATEGORY);
        db.execSQL(SQL_DELETE_USER);
        onCreate(db);
    }


//    public static final String DB_NAME = "FinanceDB.db";
//    private final Context context;
//    private SQLiteDatabase database;
//
//    public DBHelper(Context context) {
//        this.context = context;
//        copyDatabaseIfNeeded();
//    }
//
//    private void copyDatabaseIfNeeded() {
//        File dbPath = context.getDatabasePath(DB_NAME);
//        Log.d("DB_PATH", "Database path: " + dbPath.getAbsolutePath());
//
//        if (!dbPath.exists()) {
//            dbPath.getParentFile().mkdirs();
//
//            try (InputStream input = context.getAssets().open(DB_NAME);
//                 OutputStream output = new FileOutputStream(dbPath)) {
//
//                byte[] buffer = new byte[1024];
//                int length;
//                while ((length = input.read(buffer)) > 0) {
//                    output.write(buffer, 0, length);
//                }
//
//                output.flush();
//                Log.d("DB_COPY", "Copy DB from assets success");
//            } catch (IOException e) {
//                Log.e("DB_COPY", "Copy DB failed", e);
//            }
//        } else {
//            Log.d("DB_COPY", "DB already exists, no need to copy.");
//        }
//    }
//
//    public SQLiteDatabase getDatabase() {
//        // context.getDatabasePath(DB_NAME)
//        // có thể là context của activity hoặc application
//        File dbFile = context.getDatabasePath(DB_NAME);
//        database = SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.OPEN_READWRITE);
//        return database;
//    }
//
//    public void close() {
//        if (database != null && database.isOpen()) {
//            database.close();
//        }
//    }
}
