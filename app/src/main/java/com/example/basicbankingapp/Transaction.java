package com.example.basicbankingapp;

public class Transaction {
    private String fromUser;
    private String toUser;
    private int amountTransferred;
    private int status;


    //Constructors
    public Transaction(String fromUser, String toUser, int amountTransferred, int status) {
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.amountTransferred = amountTransferred;
        this.status = status;
    }

    public Transaction() {
    }


    //Getters and Setters
    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public int getAmountTransferred() {
        return amountTransferred;
    }

    public void setAmountTransferred(int amountTransferred) {
        this.amountTransferred = amountTransferred;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
