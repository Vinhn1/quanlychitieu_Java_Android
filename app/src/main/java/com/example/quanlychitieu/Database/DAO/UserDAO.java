package com.example.quanlychitieu.Database.DAO;

import android.content.*;
import android.database.*;
import android.database.sqlite.*;

import com.example.quanlychitieu.Database.DBHelper.*;
import com.example.quanlychitieu.Model.*;

import java.util.*;

public class UserDAO {
    private SQLiteDatabase db;

    public UserDAO(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    // Thêm người dùng
    public long insertUser(User user){
        ContentValues values = new ContentValues();
        values.put("name", user.getName());
        values.put("email", user.getEmail());
        values.put("password", user.getPassword());
        values.put("create_at", user.getCreate_at());
        return db.insert("Users", null, values);
    }

    // Lấy tất cả người dùng
    public List<User> getAllUser(){
        List<User> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM Users", null);
        while (cursor.moveToNext()){
            User user = new User(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4)
            );
            list.add(user);
        }
        cursor.close();
        return list;
    }

    // Cập nhật người dùng
    public int updateUser(User user){
        ContentValues values = new ContentValues();
        values.put("name", user.getName());
        values.put("email", user.getEmail());
        values.put("password", user.getPassword());
        return db.update("Users", values, "user_id = ?", new String[]{String.valueOf(user.getUser_id())});
    }

    // Xóa người dùng
    public int deleteUser(int userId){
        return db.delete("Users", "user_id = ?", new String[]{String.valueOf(userId)});
    }

}
