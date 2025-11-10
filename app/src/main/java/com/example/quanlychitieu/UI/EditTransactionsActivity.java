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
import com.example.quanlychitieu.ViewModel.*;
import com.example.quanlychitieu.databinding.*;

import java.util.*;

public class EditTransactionsActivity extends AppCompatActivity {
    private ActivityEditTransactionsBinding binding;
    private TransactionViewModel viewModel;
    private int transactionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityEditTransactionsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(TransactionViewModel.class);

        // nhận dữ liệu từ Itent
        receiveDataFromItent();
        // Khởi tạo tablayout
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Chi tiêu"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Thu nhập"));

        // thiết lập tab dựa trên loại giao dịch
        String categoryType = getIntent().getStringExtra("category_type");
        if("income".equals(categoryType)){
            binding.tabLayout.getTabAt(1).select();
        }else {
            binding.tabLayout.getTabAt(0).select();
        }
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

            if(amountStr.isEmpty()){
                binding.amountTextInputLayout.setError("Vui lòng nhập số tiền!");
            }else {
                binding.amountTextInputLayout.setError(null);
            }

            double amount = Double.parseDouble(amountStr);

            // Tạo Transaction Với dl mới
            Transaction transaction = new Transaction();
            transaction.setTransaction_id(transactionId);
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
    // ==========================================================================================
    private void receiveDataFromItent() {
        transactionId = getIntent().getIntExtra("transaction_id", -1);
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