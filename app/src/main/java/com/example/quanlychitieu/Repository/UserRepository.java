package com.example.quanlychitieu.Repository;

import android.content.*;

import com.example.quanlychitieu.Database.DAO.*;
import com.example.quanlychitieu.Model.*;

import java.util.*;

// Cầu nối giữa ViewModel Và DAO

public class UserRepository {
    // DAO thực hiện truy vấn trực tiếp trên SQLite
    private UserDAO userDAO;

    // Nhận context từ viewmodel(hoặc Activity), Khởi tạo đối tượng userDAO để thao tác với csdl
    public UserRepository(Context context){
        userDAO = new UserDAO(context);
    }

    // Thêm người dùng vào csdl
    public long addUser(User user){
        return userDAO.insertUser(user);
    }

    // lấy toàn bộ ds người dùng từ db
    public List<User> getAllUser(){
        return userDAO.getAllUser();
    }

    // update in4 người dùng
    public int updateUser(User user){
        return userDAO.updateUser(user);
    }

    // xóa ng dùng theo ID
    public int deleteUser(int userId){
        return userDAO.deleteUser(userId);
    }
}
