package com.example.quanlychitieu.UI;

import android.app.*;
import android.os.Bundle;
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
import com.example.quanlychitieu.Repository.*;
import com.example.quanlychitieu.ViewModel.*;
import com.example.quanlychitieu.databinding.*;
import com.google.android.material.tabs.*;

import java.util.*;

public class EditTransactionsActivity extends AppCompatActivity {
    private ActivityEditTransactionsBinding binding;
    private TransactionViewModel viewModel;
    private int transactionId;
    private int categoryId;

    // lưu danh mục hiện tại
    private String currentCategoryType;

    // Respository cho Category
    private CategoryRepository categoryRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityEditTransactionsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(TransactionViewModel.class);
        categoryRepository = new CategoryRepository(this);

        // nhận dữ liệu từ Itent
        receiveDataFromItent();
        // Khởi tạo tablayout
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Chi tiêu"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Thu nhập"));

        // thiết lập tab dựa trên loại giao dịch
        String categoryType = getIntent().getStringExtra("category_type");
        // Lưu danh mục hiện tại
        currentCategoryType = categoryType;

        if("income".equals(categoryType)){
            binding.tabLayout.getTabAt(1).select();
        }else {
            binding.tabLayout.getTabAt(0).select();
        }

        // Xử lý sự kiện chuyển tab
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                currentCategoryType = tab.getPosition() == 0 ? "expense" : "income";
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        // ==========================================================================================
        // Xử lý sự kiện cập nhật
        binding.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTransaction();
            }
        });
        // ==========================================================================================

        binding.backBtn.setOnClickListener(v -> finish());

        // ==========================================================================================
        // THÊM DATEPICKER CHO EDIT ACTIVITY
        binding.createAtTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePicker = new DatePickerDialog(EditTransactionsActivity.this,
                        (view, selectedYear, selectedMonth, selectedDay) -> {
                            // Month bắt đầu từ 0 nên cộng thêm 1
                            String date = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                            binding.createAtTxt.setText(date);
                        }, year, month, day);

                datePicker.show();
            }

        });
    }


    // ==========================================================================================
    private void updateTransaction() {
        try{
            // lấy dữ liệu từ ô Input
            String amountStr = binding.amountTxt.getText().toString();
            String note = binding.noteTxt.getText().toString();
            String create_at = binding.createAtTxt.getText().toString();
            String categoryName = binding.categoryTxt.getText().toString();

            if(amountStr.isEmpty()){
                binding.amountTextInputLayout.setError("Vui lòng nhập số tiền!");
                return;
            }else {
                binding.amountTextInputLayout.setError(null);
            }

            if(categoryName.isEmpty()){
                binding.categoryTextInputLayout.setError("Vui lòng nhập danh mục!");
                return;
            }else{
                binding.categoryTextInputLayout.setError(null);
            }

            double amount = Double.parseDouble(amountStr);

            // Xử lý danh mục: tìm hoặc tạo danh mục mới 
            int newCategoryId = getOrCreateCategoryId(categoryName, currentCategoryType);

            // Tạo Transaction Với dl mới
            Transaction transaction = new Transaction();
            transaction.setTransaction_id(transactionId);
            transaction.setCategory_id(newCategoryId);
            transaction.setAmount(amount);
            transaction.setNote(note);
            transaction.setCreate_at(create_at);

            // Gọi viewModel để cập nhật
            viewModel.updateTransaction(transaction);

            Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
            finish(); // Quay lại màn hình chính
        }catch (Exception e){
            Toast.makeText(this, "Lỗi cập nhật dữ liệu", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    // Lấy category_id từ tên danh mục, nếu chưa có thì tạo mới
    private int getOrCreateCategoryId(String categoryName, String type) {
        // Lấy tất cả danh mục
        List<Category> allCategories = categoryRepository.getAllCategories();

        // Tìm danh mục theo tên và loại
        for(Category category : allCategories){
            if(category.getName().equalsIgnoreCase(categoryName) && category.getType().equals(type)){
                return category.getCategory_id();
            }
        }

        // Nếu không tìm thấy, tạo danh mục mới
        Category newCategory = new Category();
        newCategory.setName(categoryName);
        newCategory.setType(type);
        newCategory.setUser_id(1);

        long newId = categoryRepository.addCategory(newCategory);
        if(newId != -1){
            return (int) newId;
        }else {
            // Nếu tạo mới thất bại, trả về categoryId cũ hoặc xử lý lỗi
            return categoryId;
        }
    }

    // ==========================================================================================
    private void receiveDataFromItent() {
        transactionId = getIntent().getIntExtra("transaction_id", -1);
        categoryId = getIntent().getIntExtra("category_id", -1);
        double amount = getIntent().getDoubleExtra("amount", 0);
        String note = getIntent().getStringExtra("note");
        String createAt = getIntent().getStringExtra("create_at");
        String categoryName = getIntent().getStringExtra("category_name");

        // Hiển thị dữ liệu lên giao diện
        binding.amountTxt.setText(String.valueOf(amount));
        binding.categoryTxt.setText(categoryName);
        binding.noteTxt.setText(note);
        binding.createAtTxt.setText(createAt);
    }
    // ==========================================================================================
}