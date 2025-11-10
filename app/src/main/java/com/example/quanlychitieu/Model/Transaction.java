package com.example.quanlychitieu.Model;

public class Transaction {
    private int transaction_id;
    private int user_id;
    private int category_id;
    private double amount;
    private String note;
    private String create_at;

    // Thêm 2 thuộc tính để Join với bảng Category
    private String category_name;
    private String category_type;

    public Transaction() {
    }

    public Transaction(int transaction_id, int user_id, int category_id, double amount, String note, String create_at, String category_name, String category_type) {
        this.transaction_id = transaction_id;
        this.user_id = user_id;
        this.category_id = category_id;
        this.amount = amount;
        this.note = note;
        this.create_at = create_at;
        this.category_name = category_name;
        this.category_type = category_type;
    }


    public Transaction(String category_name, double amount, String note, String create_at, String category_type) {
        this.transaction_id = 0;
        this.user_id = 0;
        this.category_id = 0;
        this.amount = amount;
        this.note = note;
        this.create_at = create_at;
        this.category_name = category_name;
        this.category_type = category_type;
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

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getCategory_type() {
        return category_type;
    }

    public void setCategory_type(String category_type) {
        this.category_type = category_type;
    }
}
