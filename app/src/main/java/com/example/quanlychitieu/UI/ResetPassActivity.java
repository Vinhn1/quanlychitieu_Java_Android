package com.example.quanlychitieu.UI;

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

public class ResetPassActivity extends AppCompatActivity {

    // Khai báo biến binding
    private ActivityResetPassBinding binding;

    // Khai báo biến viewmodel
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        // Khởi tạo viewbinding
        binding = ActivityResetPassBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Khởi tạo viewmodel
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.init(this);
        // ==========================================================================================
        // Khi click vào đổi mật khẩu
        binding.resetPassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.emailEdt.getText().toString();
                String pass = binding.passEdt.getText().toString();
                String forgotPass = binding.forgotPassEdt.getText().toString();

                // Kt valid
                boolean isValid = true;
                // ========== kt email
                if(email.isEmpty()){
                    binding.emailTextInputLayout.setError("Email không được để trống!");
                    isValid = false;
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    binding.emailTextInputLayout.setError("Email không hợp lệ!");
                    isValid = false;
                }else {
                    // Xóa thông báo nếu hợp lệ
                    binding.emailTextInputLayout.setError(null);
                }

                // ========= kt pass
                if(pass.isEmpty()){
                    binding.passTextInputLayout.setError("Mật khẩu không được để trống!");
                    isValid = false;
                } else if (pass.length() < 6) {
                    binding.passTextInputLayout.setError("Mật khẩu phải >= 6 kí tự!");
                    isValid = false;
                }else {
                    binding.passTextInputLayout.setError(null);
                }

                // =========== Kt forgotPass
                if(forgotPass.isEmpty()){
                    binding.forgotPassTextInputLayout.setError("Vui lòng nhập lại mật khẩu!");
                    isValid = false;
                } else if (!forgotPass.equals(pass)) {
                    binding.forgotPassTextInputLayout.setError("Mật khẩu nhập lại không khớp!");
                    isValid = false;
                }else {
                    binding.forgotPassTextInputLayout.setError(null);
                }


                // Kt các trường nhập liệu đúng vaild chưa
                if(isValid){
                    // Kt user đã tồn tại chưa
                    User existingUser = userViewModel.findByEmail(email);
                    // Nếu email chưa tồn tại cho phép đổi mật khẩu
                    if(existingUser != null){
                        existingUser.setPassword(pass);
                        userViewModel.updateUser(existingUser);
                        // đăng ký thành công
                        Toast.makeText(ResetPassActivity.this, "Đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();
                        // Quay về màn hình login
                        finish();
                    }else {
                        // Nếu email đã tồn tại
                        binding.emailTextInputLayout.setError("Email chưa được đăng ký!");
                    }
                }


            }
        });
        // ==========================================================================================
        // khi click vào nút back
            binding.btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

        // ==========================================================================================

    }
}