package com.example.quanlychitieu.Model;

public class Category {
    private int category_id;
    private String name;
    private String type; // Thu nhập or chi tiêu
    private int user_id;

    public Category() {
    }

    public Category(int category_id, String name, String type, int user_id) {
        this.category_id = category_id;
        this.name = name;
        this.type = type;
        this.user_id = user_id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
