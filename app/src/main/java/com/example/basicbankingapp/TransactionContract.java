package com.example.basicbankingapp;

import android.provider.BaseColumns;

public final class TransactionContract {

    private TransactionContract() {}

    public static final class TransactionEntry implements BaseColumns {
        //Name of Database Table for Users
        public final static String TRANSACTION_TABLE = "transaction_table";

        //Name of the Fields used in this table
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_FROM_NAME = "from_name";
        public final static String COLUMN_TO_NAME = "to_name";
        public final static String COLUMN_AMOUNT = "amount";
        public final static String COLUMN_STATUS = "status";

    }
}
