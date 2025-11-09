package com.example.quanlychitieu.UI;

import android.content.*;
import android.database.sqlite.*;
import android.os.Bundle;
import android.view.*;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.*;

import com.example.quanlychitieu.*;
import com.example.quanlychitieu.Database.DBHelper.*;
import com.example.quanlychitieu.ViewModel.*;
import com.example.quanlychitieu.databinding.*;

public class LoginActivity extends AppCompatActivity {

    // Khởi tạo biến viewbinding
    private ActivityLoginBinding binding;

    // Khởi tạo biến viewmodel
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        // ViewBinding
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // // Tạo ViewModel bằng ViewModelProvider
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.init(this);

        // ==========================================================================================
        // Event khi ng dùng chưa có tài khoản click vào txt Đăng kí
        binding.registerTxt.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
        // ==========================================================================================
        // Khi người dùng click vào đăng nhập

        // ==========================================================================================
        // Khi click vào Ghi nhớ tài khoản

        // ==========================================================================================
        // Khi click vào quên mật khẩu
    }
}