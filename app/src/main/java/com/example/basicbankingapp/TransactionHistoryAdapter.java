package com.example.basicbankingapp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TransactionHistoryAdapter extends RecyclerView.Adapter<TransactionHistoryAdapter.ViewHolder> {
    private ArrayList<Transaction> transactionArrayList;

    public TransactionHistoryAdapter(Context context, ArrayList<Transaction> list) {
        transactionArrayList = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView fromName, toName, amountTransferred, date, time;
        CardView cardView;
        LinearLayout toUserInfo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            fromName = itemView.findViewById(R.id.tv_from_name);
            toName = itemView.findViewById(R.id.tv_to_name);
            amountTransferred = itemView.findViewById(R.id.tv_amount);
            cardView = itemView.findViewById(R.id.cv_transaction_card_view);
            toUserInfo = itemView.findViewById(R.id.to_user_info);
            date = itemView.findViewById(R.id.tv_date);
            time = itemView.findViewById(R.id.tv_time);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });
        }
    }

    @NonNull
    @Override
    public TransactionHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from (viewGroup.getContext()).inflate(R.layout.transaction_history_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.itemView.setTag(transactionArrayList.get(position));
        viewHolder.fromName.setText(transactionArrayList.get(position).getFromUser());
        viewHolder.toName.setText(transactionArrayList.get(position).getToUser());
        viewHolder.amountTransferred.setText(String.format("%d", transactionArrayList.get(position).getAmountTransferred()));


        if (transactionArrayList.get(position).getStatus() == 1) {
            viewHolder.cardView.setCardBackgroundColor(Color.rgb(50, 205, 50));
        }
        else {
            viewHolder.cardView.setCardBackgroundColor(Color.rgb(239, 100, 100));
        }
    }

    @Override
    public int getItemCount() {
        return transactionArrayList.size();
    }
}