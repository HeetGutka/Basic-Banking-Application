package com.example.basicbankingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class TransactionHelper extends SQLiteOpenHelper {

    //Name of the Database File
    private static final String DATABASE_NAME = "TransactionTable.db";

    //Database Version
    private static final int DATABASE_VERSION = 1;

    public TransactionHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //SQL Statement to Create Transaction Table
        String SQL_CREATE_TRANSACTION_TABLE = "CREATE TABLE " + TransactionContract.TransactionEntry.TRANSACTION_TABLE + " ("
                + TransactionContract.TransactionEntry.COLUMN_FROM_NAME + " VARCHAR, "
                + TransactionContract.TransactionEntry.COLUMN_TO_NAME + " VARCHAR, "
                + TransactionContract.TransactionEntry.COLUMN_AMOUNT + " INTEGER, "
                + TransactionContract.TransactionEntry.COLUMN_STATUS + " INTEGER);";

        //Execute the SQL Statement
        db.execSQL(SQL_CREATE_TRANSACTION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            //Drop all tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TransactionContract.TransactionEntry.TRANSACTION_TABLE);
            onCreate(db);
        }
    }


    public boolean insertTransferData (String fromName, String toName, String amount, int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(TransactionContract.TransactionEntry.COLUMN_FROM_NAME, fromName);
        contentValues.put(TransactionContract.TransactionEntry.COLUMN_TO_NAME, toName);
        contentValues.put(TransactionContract.TransactionEntry.COLUMN_AMOUNT, amount);
        contentValues.put(TransactionContract.TransactionEntry.COLUMN_STATUS, status);

        long result = db.insert(TransactionContract.TransactionEntry.TRANSACTION_TABLE, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
}
