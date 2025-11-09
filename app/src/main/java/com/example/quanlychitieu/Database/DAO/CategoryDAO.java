package com.example.quanlychitieu.Database.DAO;

import android.content.*;
import android.database.*;
import android.database.sqlite.*;

import com.example.quanlychitieu.Database.DBHelper.*;
import com.example.quanlychitieu.Model.*;

import java.util.*;

public class CategoryDAO {
    private SQLiteDatabase db;

    public CategoryDAO(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    // Them category
    public long insertCategory(Category category){
        ContentValues values = new ContentValues();
        values.put("name", category.getName());
        values.put("type", category.getType());
        values.put("user_id", category.getUser_id());
        return db.insert("Categories", null, values);
    }

    // Lấy toàn bộ danh sách
    public List<Category> getAllCategories(){
        List<Category> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM Categories", null);
        while (cursor.moveToNext()){
            Category category = new Category(cursor.getInt(0),
            cursor.getString(1),
            cursor.getString(2),
            cursor.getInt(3)
            );
            list.add(category);
        };
        cursor.close();
        return list;
    }

    // UpdateCategory
    public int updateCategory(Category category){
        ContentValues values = new ContentValues();
        values.put("name", category.getName());
        values.put("type", category.getType());
        return db.update("Categories", values, "category_id = ?", new String[]{String.valueOf(category.getCategory_id())});
    }

    // Delete
    public int deleteCategory(int categoryId){
        return db.delete("Categories", "category_id = ?", new String[]{String.valueOf(categoryId)});
    }

}
