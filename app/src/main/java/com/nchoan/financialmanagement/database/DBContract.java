package com.nchoan.financialmanagement.database;

public class DBContract {

    private DBContract() {
        // Prevent instantiation
    }

    public static class UserEntry {
        public static final String TABLE_NAME = "User";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_PASSWORD = "password";
        public static final String COLUMN_EMAIL = "email";
    }

    public static class TransactionsEntry {
        public static final String TABLE_NAME = "Transactions";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_USER_ID = "user_id"; // FK User
        public static final String COLUMN_AMOUNT = "amount"; // Số tiền
        public static final String COLUMN_TYPE = "type"; // Thu nhập or Chi tiêu
        public static final String COLUMN_NOTE = "note"; // Mục đích
        public static final String COLUMN_DATE = "date"; // ngày giao dịch
    }

    public static class CategoryEntry {
        public static final String TABLE_NAME = "Category";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_TYPE = "type";
    }

    public static class BudgetEntry {
        public static final String TABLE_NAME = "Budget";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_USER_ID = "user_id"; // FK User
        public static final String COLUMN_AMOUNT = "amount"; // số tiền
        public static final String COLUMN_MONTH = "month";
    }
}
