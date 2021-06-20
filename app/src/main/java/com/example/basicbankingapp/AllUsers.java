package com.example.basicbankingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;

import java.util.ArrayList;

public class AllUsers extends AppCompatActivity {

    //Recycler View
    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<User> userArrayList;

    //Database
    private UserHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users);

        //Create Array List of Users
        userArrayList = new ArrayList<User>();

        //Create Table in the Database
        dbHelper = new UserHelper(this);

        //Read Data from Database
        displayDatabaseInfo();

        //Show list of items
        recyclerView = findViewById(R.id.all_user_list);
        recyclerView.setHasFixedSize(true);

        //Layout Manager for Recycler View
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //Initializing Adapter for Recycler View
        myAdapter = new UserListAdapter(this, userArrayList);
        recyclerView.setAdapter(myAdapter);
    }

    private void displayDatabaseInfo() {
        userArrayList.clear();

        //Defining Cursor for reading data from database
        Cursor cursor = new UserHelper(this).readAllData();

        int phoneNoColumnIndex = cursor.getColumnIndex(UserContract.UserEntry.COLUMN_USER_PHONE_NO);
        int emailColumnIndex = cursor.getColumnIndex(UserContract.UserEntry.COLUMN_USER_EMAIL);
        int ifscCodeColumnIndex = cursor.getColumnIndex(UserContract.UserEntry.COLUMN_USER_IFSC_CODE);
        int accountNumberColumnIndex = cursor.getColumnIndex(UserContract.UserEntry.COLUMN_USER_ACCOUNT_NUMBER);
        int nameColumnIndex = cursor.getColumnIndex(UserContract.UserEntry.COLUMN_USER_NAME);
        int accountBalanceColumnIndex = cursor.getColumnIndex(UserContract.UserEntry.COLUMN_USER_ACCOUNT_BALANCE);

        while (cursor.moveToNext()) {
            String currentName = cursor.getString(nameColumnIndex);
            int accountNumber = cursor.getInt(accountNumberColumnIndex);
            String email = cursor.getString(emailColumnIndex);
            String phoneNumber = cursor.getString(phoneNoColumnIndex);
            String ifscCode = cursor.getString(ifscCodeColumnIndex);
            int accountBalance = cursor.getInt(accountBalanceColumnIndex);

            //Displaying each values from the current row in the cursor in the TextView
            userArrayList.add(new User(currentName, accountNumber, phoneNumber, ifscCode, accountBalance, email));
        }
    }
}