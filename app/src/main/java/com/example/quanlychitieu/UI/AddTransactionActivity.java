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

import com.example.quanlychitieu.Database.DAO.*;
import com.example.quanlychitieu.Model.*;
import com.example.quanlychitieu.R;
import com.example.quanlychitieu.ViewModel.*;
import com.example.quanlychitieu.databinding.*;

import java.util.*;

public class AddTransactionActivity extends AppCompatActivity {
    private ActivityAddTransactionBinding binding;
    private TransactionViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityAddTransactionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(TransactionViewModel.class);

        // Khởi tạo tablayout
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Chi tiêu"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Thu nhập"));
        // ==========================================================================================
        // Khi click vào nút thêm
        binding.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    // Lấy dữ liệu từ ô input
                    String amountStr = binding.amountTxt.getText().toString();
                    String categoryName = binding.categoryTxt.getText().toString();
                    String note = binding.noteTxt.getText().toString();
                    String create_at = binding.createAtTxt.getText().toString();

                    // tab 0 = Chi tiêu
                    boolean isExpense = binding.tabLayout.getSelectedTabPosition() == 0;
                    String category_type = isExpense ? "spending" : "income";

                    if(amountStr.isEmpty() || categoryName.isEmpty()){
                        Toast.makeText(AddTransactionActivity.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    double amount = Double.parseDouble(amountStr);


                    // Tìm category_id từ tên danh mục và loại
                    CategoryDAO categoryDAO = new CategoryDAO(AddTransactionActivity.this);
                    List<Category> categories = categoryDAO.getAllCategories();
                    int categoryId = -1;
                    for(Category cat : categories){
                        if(cat.getName().equalsIgnoreCase(categoryName) && cat.getType().equalsIgnoreCase(category_type)){
                            categoryId = cat.getCategory_id();
                            break;
                        }
                    }

                    if(categoryId == -1){
                        // Nếu không tìm thấy tạo danh mục mới
                        Category newCategory = new Category();
                        newCategory.setName(categoryName);
                        newCategory.setType(category_type);
                        newCategory.setUser_id(1);
                        categoryId = (int) categoryDAO.insertCategory(newCategory);
                    }

                    // Tạo transaction với category_id
                    Transaction t = new Transaction();
                    t.setCategory_id(categoryId);
                    t.setAmount(amount);
                    t.setNote(note);
                    t.setCreate_at(create_at);
                    t.setUser_id(1);
                    long result = viewModel.addTransaction(t);

                    if (result != -1) {
                        Toast.makeText(AddTransactionActivity.this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
                        finish(); // quay lại HomeActivity
                    } else {
                        Toast.makeText(AddTransactionActivity.this, "Thêm thất bại!", Toast.LENGTH_SHORT).show();
                    }


                }catch (Exception e){
                    Toast.makeText(AddTransactionActivity.this, "Lỗi nhập dữ liệu", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }
        });
        // ==========================================================================================
        // DatePickerDialog
        binding.createAtTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePicker = new DatePickerDialog(AddTransactionActivity.this,
                        (view, selectedYear, selectedMonth, selectedDay) -> {
                            // Month bắt đầu từ 0 nên cộng thêm 1
                            String date = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                            binding.createAtTxt.setText(date);
                        }, year, month, day);

                datePicker.show();
            }
        });
    }
}