package com.example.quanlychitieu.Model;

public class Transaction {
    private int transaction_id;
    private int user_id;
    private int category_id;
    private double amount;
    private String note;
    private String create_at;

    public Transaction() {
    }

    public Transaction(int transaction_id, int user_id, int category_id, double amount, String note, String create_at) {
        this.transaction_id = transaction_id;
        this.user_id = user_id;
        this.category_id = category_id;
        this.amount = amount;
        this.note = note;
        this.create_at = create_at;
    }

    public int getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(int transaction_id) {
        this.transaction_id = transaction_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCreate_at() {
        return create_at;
    }

    public void setCreate_at(String create_at) {
        this.create_at = create_at;
    }
}
