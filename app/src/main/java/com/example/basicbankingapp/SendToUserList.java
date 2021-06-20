
package com.example.basicbankingapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class SendToUserList extends AppCompatActivity implements SendToUserAdapter.OnUserListener {

    //RecyclerView
    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<User> userArrayList;

    //Database
    private UserHelper dbHelper;

    String date=null, time=null;
    int fromUserAccountNo, toUserAccountNo, toUserAccountBalance;
    String fromUserAccountName, fromUserAccountBalance, transferAmount, toUserAccountName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_to_user_list);

        //Getting the Time Instance
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy, hh:mm a");
        String date_and_time = simpleDateFormat.format(calendar.getTime());

        //Getting Intent Values
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            fromUserAccountName = bundle.getString("FROM_USER_NAME");
            fromUserAccountNo = bundle.getInt("FROM_USER_ACCOUNT_NO");
            fromUserAccountBalance = bundle.getString("FROM_USER_ACCOUNT_BALANCE");
            transferAmount = bundle.getString("TRANSFER_AMOUNT");
        }

        // Create ArrayList of Users
        userArrayList = new ArrayList<User>();

        // Create Table in the Database
        dbHelper = new UserHelper(this);

        // Show list of items
        recyclerView = findViewById(R.id.rv_send_to_user_list);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        myAdapter = new SendToUserAdapter(userArrayList, this);
        recyclerView.setAdapter(myAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    @Override
    public void onUserClick(int position) {
        // Insert data into transactions table
        toUserAccountNo = userArrayList.get(position).getAccountNumber();
        toUserAccountName = userArrayList.get(position).getName();
        toUserAccountBalance = userArrayList.get(position).getBalance();

        calculateAmount();

        new TransactionHelper(this).insertTransferData(fromUserAccountName, toUserAccountName, transferAmount, 1);
        Toast.makeText(this, "Transaction Successful!!", Toast.LENGTH_LONG).show();

        startActivity(new Intent(SendToUserList.this, Dashboard.class));
        finish();
    }

    private void calculateAmount() {
        int currentAmount = Integer.parseInt(fromUserAccountBalance);
        int transferAmountInt = Integer.parseInt(transferAmount);
        int remainingAmount = currentAmount - transferAmountInt;
        int increasedAmount = transferAmountInt + toUserAccountBalance;

        // Update amount in the dataBase
        new UserHelper(this).updateAmount(fromUserAccountNo, remainingAmount);
        new UserHelper(this).updateAmount(toUserAccountNo, increasedAmount);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder_exitButton = new AlertDialog.Builder(SendToUserList.this);
        builder_exitButton.setTitle("Do you wish to cancel Transaction?").setCancelable(false)
                .setPositiveButton ("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick (DialogInterface dialogInterface, int i) {
                        // Transactions Cancelled
                        TransactionHelper dbHelper = new TransactionHelper(getApplicationContext());
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        ContentValues values = new ContentValues();

                        values.put(TransactionContract.TransactionEntry.COLUMN_FROM_NAME, fromUserAccountName);
                        values.put(TransactionContract.TransactionEntry.COLUMN_TO_NAME, toUserAccountName);
                        values.put(TransactionContract.TransactionEntry.COLUMN_STATUS, 0);
                        values.put(TransactionContract.TransactionEntry.COLUMN_AMOUNT, transferAmount);

                        db.insert(TransactionContract.TransactionEntry.TRANSACTION_TABLE, null, values);

                        Toast.makeText(SendToUserList.this, "Transaction Cancelled!", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(SendToUserList.this, Dashboard.class));
                        finish();
                    }
                }).setNegativeButton("No", null);
        AlertDialog alertExit = builder_exitButton.create();
        alertExit.show();
    }

    private void displayDatabaseInfo() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                UserContract.UserEntry.COLUMN_USER_NAME,
                UserContract.UserEntry.COLUMN_USER_ACCOUNT_BALANCE,
                UserContract.UserEntry.COLUMN_USER_ACCOUNT_NUMBER,
                UserContract.UserEntry.COLUMN_USER_PHONE_NO,
                UserContract.UserEntry.COLUMN_USER_EMAIL,
                UserContract.UserEntry.COLUMN_USER_IFSC_CODE,
        };

        Cursor cursor = db.query(
                UserContract.UserEntry.USER_TABLE,
                projection,
                null,
                null,
                null,
                null,
                null);

        try {
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

                // Display the values from each column of the current row in the cursor in the TextView
                userArrayList.add(new User(currentName, accountNumber, phoneNumber, ifscCode, accountBalance, email));
            }
        } finally {
            cursor.close();
        }
    }
}