package com.example.quanlychitieu.Database.DBHelper;

import android.content.*;
import android.database.*;
import android.database.sqlite.*;

import androidx.annotation.*;

public class DBHelper extends SQLiteOpenHelper {
    // Tên db và phiên bản
    private static final String DATABASE_NAME = "quanlychitieu.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Hàm được gọi khi db được tạo lần đầu tiên
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng User
        db.execSQL("CREATE TABLE Users (" +
                "user_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "email TEXT, " +
                "password TEXT, " +
                "create_at DATETIME)");

        // Tạo bảng Category
        db.execSQL("CREATE TABLE Categories (" +
                "category_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "type TEXT, " +
                "user_id INTEGER, " +
                "FOREIGN KEY(user_id) REFERENCES Users(user_id))");

        // Tạo bảng Transaction
        db.execSQL("CREATE TABLE Transactions (" +
                "transaction_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "user_id INTEGER, " +
                "category_id INTEGER, " +
                "amount REAL, " +
                "note TEXT, " +
                "create_at DATETIME, " +
                "FOREIGN KEY(user_id) REFERENCES Users(user_id), " +
                "FOREIGN KEY(category_id) REFERENCES Categories(category_id))");
    }

    // Hàm được gọi khi nâng cấp db (thay đổi version)
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xóa bảng cũ nếu tồn tại
        db.execSQL("DROP TABLE IF EXISTS Transactions");
        db.execSQL("DROP TABLE IF EXISTS Categories");
        db.execSQL("DROP TABLE IF EXISTS Users");

        // Tạo lại bảng mới
        onCreate(db);
    }
}
