package com.example.quanlychitieu.UI;

import android.content.*;
import android.os.Bundle;
import android.util.*;
import android.view.*;
import android.widget.*;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.*;

import com.example.quanlychitieu.Model.*;
import com.example.quanlychitieu.R;
import com.example.quanlychitieu.ViewModel.*;
import com.example.quanlychitieu.databinding.*;

public class RegisterActivity extends AppCompatActivity {
    // Khai báo biến binding
    private ActivityRegisterBinding binding;
    // Khai báo viewmodel
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Tạo ViewModel bằng ViewModelProvider
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        // Khởi tạo (truyền context)
        userViewModel.init(this);

        // ==========================================================================================
        // Event khi click vào nút back
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        // ==========================================================================================
        // Event khi click vào đăng ký
        binding.registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy dữ liệu từ các ô nhập
                String email = binding.emailEdt.getText().toString();
                String name = binding.userEdt.getText().toString();
                String pass = binding.passEdt.getText().toString();
                String forgotPass = binding.forgotPassEdt.getText().toString();

                boolean isValid = true;

                // =========== Kiểm tra Email
                if(email.isEmpty()){
                    binding.emailTextInput.setError("Email không được để trống!");
                    isValid = false;
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    binding.emailTextInput.setError("Email không hợp lệ!");
                    isValid = false;
                }else {
                    // Xóa thông báo nếu hợp lệ
                    binding.emailTextInput.setError(null);
                }

                // ===========  Kt tên
                if(name.isEmpty()){
                    binding.userTextInput.setError("Tên không được để trống!");
                    isValid = false;
                }else {
                    binding.userTextInput.setError(null);
                }

                // =========== Kt pass
                if(pass.isEmpty()){
                    binding.passTextInput.setError("Mật khẩu không được để trống!");
                    isValid = false;
                } else if (pass.length() < 6) {
                    binding.passTextInput.setError("Mật khẩu phải >= 6 kí tự!");
                    isValid = false;
                }else {
                    binding.passTextInput.setError(null);
                }

                // =========== Kt forgotPass
                if(forgotPass.isEmpty()){
                    binding.forgotPassTextInput.setError("Vui lòng nhập lại mật khẩu!");
                    isValid = false;
                } else if (!forgotPass.equals(pass)) {
                    binding.forgotPassTextInput.setError("Mật khẩu nhập lại không khớp!");
                    isValid = false;
                }else {
                    binding.forgotPassTextInput.setError(null);
                }

                // =========== Nếu tất cả hợp lệ
                // có lỗi -> dừng
                if(!isValid) return;

                // ====================== Xử lý đăng ký ==================================
                // Tạo đối tượng User mới
                User newUser = new User();
                newUser.setEmail(email);
                newUser.setName(name);
                newUser.setPassword(pass);

                // Gọi ViewModel để đk
                boolean success = userViewModel.register(newUser);
                if(success){
                    // Nếu đăng ký thành công
                    Toast.makeText(RegisterActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                    // Quay về màn hình login
                    finish();
                }else {
                    // Nếu email đã tồn tại
                    binding.emailTextInput.setError("Email đã tồn tại!");
                }
            }
        });
        // ==========================================================================================
    }
}