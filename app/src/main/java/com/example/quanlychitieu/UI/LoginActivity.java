package com.example.quanlychitieu.UI;

import android.database.sqlite.*;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.*;

import com.example.quanlychitieu.*;
import com.example.quanlychitieu.Database.DBHelper.*;
import com.example.quanlychitieu.ViewModel.*;
import com.example.quanlychitieu.databinding.*;

public class LoginActivity extends AppCompatActivity {

    // Khởi tạo viewbinding
    private LoginMainBinding binding;

    // Khởi tạo viewmodel
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        // ViewBinding
        binding = LoginMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // UserViewModel
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.init(this);



        // Event khi ng dùng chưa có tài khoản click vào txt Đăng kí

    }
}