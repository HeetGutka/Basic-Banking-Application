package com.example.basicbankingapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class UserHelper extends SQLiteOpenHelper {

    String TABLE_NAME = UserContract.UserEntry.USER_TABLE;

    //Name of the Database File
    private static final String DATABASE_NAME = "UserTable.db";

    //Database Version
    private static final int DATABASE_VERSION = 1;


    public UserHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //SQL Statement to create User Table
        String SQL_CREATE_USER_TABLE = "CREATE TABLE " + UserContract.UserEntry.USER_TABLE + " ("
                + UserContract.UserEntry.COLUMN_USER_ACCOUNT_NUMBER + " INTEGER, "
                + UserContract.UserEntry.COLUMN_USER_NAME + " VARCHAR, "
                + UserContract.UserEntry.COLUMN_USER_EMAIL + " VARCHAR, "
                + UserContract.UserEntry.COLUMN_USER_IFSC_CODE + " VARCHAR, "
                + UserContract.UserEntry.COLUMN_USER_PHONE_NO + " VARCHAR, "
                + UserContract.UserEntry.COLUMN_USER_ACCOUNT_BALANCE + " INTEGER NOT NULL);";

        //Execute the SQL Statement
        db.execSQL(SQL_CREATE_USER_TABLE);

        //Insert values into Table User
        db.execSQL("INSERT INTO " + TABLE_NAME + " VALUES(8093, 'Pratham', 'pratham@gmail.com', 'ABHY0065062', '8169823334', 5500)");
        db.execSQL("INSERT INTO " + TABLE_NAME + " VALUES(1237, 'Nishma', 'nishma@gmail.com', 'UTIB0000061', '7738800700', 8000)");
        db.execSQL("INSERT INTO " + TABLE_NAME + " VALUES(9624, 'Sandeep', 'sandeep@gmail.com', 'BOFA0MM6205', '9867261154', 13000)");
        db.execSQL("INSERT INTO " + TABLE_NAME + " VALUES(6301, 'Parth', 'parth@gmail.com', 'BARB0FLOVAL', '9653111506', 7000)");
        db.execSQL("INSERT INTO " + TABLE_NAME + " VALUES(4495, 'Dhruvi', 'dhruvi@gmail.com', 'CNRB0000255', '9892527909', 3000)");
        db.execSQL("INSERT INTO " + TABLE_NAME + " VALUES(3866, 'Jyoti', 'jyoti@gmail.com', 'HDFC0001235', '9004188546', 9500)");
        db.execSQL("INSERT INTO " + TABLE_NAME + " VALUES(6069, 'Ramesh', 'ramesh@gmail.com', 'PJSB0000017', '8655579909', 11000)");
        db.execSQL("INSERT INTO " + TABLE_NAME + " VALUES(7139, 'Ayush', 'ayush@gmail.com', 'KKBK0001353', '7977245526', 1000)");
        db.execSQL("INSERT INTO " + TABLE_NAME + " VALUES(9096, 'Raj', 'raj@gmail.com', 'PUNB0450900', '9870221331', 4500)");
        db.execSQL("INSERT INTO " + TABLE_NAME + " VALUES(4636, 'Priya', 'priya@gmail.com', 'SBIN0000489', '9867546660', 8500)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            //Drop all tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + UserContract.UserEntry.USER_TABLE, null);
            onCreate(db);
        }
    }


    public Cursor readAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + UserContract.UserEntry.USER_TABLE, null);
        return cursor;
    }


    public Cursor readParticularData(int accountNo) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + UserContract.UserEntry.USER_TABLE
                + " WHERE " + UserContract.UserEntry.COLUMN_USER_ACCOUNT_NUMBER + " = "
                + accountNo, null);
        return cursor;
    }


    public void updateAmount(int accountNo, int amount) {
        Log.d ("TAG", "update Amount");
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("update " + UserContract.UserEntry.USER_TABLE + " set " + UserContract.UserEntry.COLUMN_USER_ACCOUNT_BALANCE + " = " + amount + " where " +
                UserContract.UserEntry.COLUMN_USER_ACCOUNT_NUMBER + " = " + accountNo);
    }
}
