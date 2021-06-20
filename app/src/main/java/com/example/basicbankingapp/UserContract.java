package com.example.basicbankingapp;

import android.provider.BaseColumns;

public final class UserContract {

    private UserContract() {}

    public static final class UserEntry implements BaseColumns {
        //Name of Database Table for Users
        public final static String USER_TABLE = "user_table";

        //Name of the Fields used in this table
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_USER_NAME = "name";
        public final static String COLUMN_USER_ACCOUNT_NUMBER = "accountNo";
        public final static String COLUMN_USER_EMAIL = "email";
        public final static String COLUMN_USER_IFSC_CODE = "ifscCode";
        public final static String COLUMN_USER_PHONE_NO = "phoneNo";
        public final static String COLUMN_USER_ACCOUNT_BALANCE = "balance";
    }
}
