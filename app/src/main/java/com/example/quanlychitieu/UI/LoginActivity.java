package com.example.quanlychitieu.UI;

import android.content.*;
import android.database.sqlite.*;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

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

    // Khai báo biến SharedPreferences
    private SharedPreferences prefs;

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
        // Khởi tạo SharedPreferences
        prefs = getSharedPreferences("user_session", Context.MODE_PRIVATE);
        // Lấy thông tin ghi nhớ (nếu có)
        boolean isRemember = prefs.getBoolean("remember_checked", false);
        if(isRemember){
            String saveName = prefs.getString("remember_name", "");
            String savePass = prefs.getString("remember_pass", "");
            binding.usernameEditText.setText(saveName);
            binding.passwordEditText.setText(savePass);
            binding.rememberLoginChkBox.setChecked(true);
        }


        // ==========================================================================================
        // Event khi ng dùng chưa có tài khoản click vào txt Đăng kí
        binding.registerTxt.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
        // ==========================================================================================
        // Khi người dùng click vào đăng nhập
        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.usernameEditText.getText().toString();
                String pass = binding.passwordEditText.getText().toString();

                // ================ Kiểm tra Valid
                boolean isValid = true;
                if(name.isEmpty()){
                    binding.userTextInputLayout.setError("Tên đăng nhập không được để trống!");
                    isValid = false;
                }else {
                    binding.userTextInputLayout.setError(null);
                }

                if(pass.isEmpty()){
                    binding.passTextInputLayout.setError("Mật khẩu không được để trống!");
                    isValid = false;
                } else if (pass.length() < 6) {
                    binding.passTextInputLayout.setError("Mật khẩu phải >= 6 kí tự!");
                    isValid = false;
                }else {
                    binding.passTextInputLayout.setError(null);
                }

                if(!isValid) return;


                // Ghi nhớ đăng nhập
                boolean remember = binding.rememberLoginChkBox.isChecked();
                if(userViewModel.login(name, pass)){
                    // Nếu nhập đúng và có chọn ghi nhớ
                    SharedPreferences.Editor editor = prefs.edit();
                    if(remember){
                        editor.putString("remember_name", name);
                        editor.putString("remember_pass", pass);
                        editor.putBoolean("remember_checked", true);
                    }else {
                        editor.clear();
                    }

                    editor.apply();

                    // Chuyển sang màn hình chính
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(LoginActivity.this, "Sai tên hoặc mật khẩu!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // ==========================================================================================
        // Khi click vào quên mật khẩu
        binding.forgotPassTxt.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, ResetPassActivity.class);
            startActivity(intent);
        });
    }
}