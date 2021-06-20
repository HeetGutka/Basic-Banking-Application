package com.example.basicbankingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Dashboard extends AppCompatActivity {

    Button btn_allUsers, btn_allTransactions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        btn_allUsers = findViewById(R.id.btn_allUsers);
        btn_allTransactions = findViewById(R.id.btn_allTransactions);

        btn_allUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, AllUsers.class);
                startActivity(intent);
            }
        });

        btn_allTransactions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, AllTransactions.class);
                startActivity(intent);
            }
        });
    }
}