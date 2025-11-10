package com.example.quanlychitieu.UI;

import android.content.*;
import android.os.Bundle;
import android.view.*;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.*;
import androidx.recyclerview.widget.*;

import com.example.quanlychitieu.Adapter.*;
import com.example.quanlychitieu.Model.*;
import com.example.quanlychitieu.R;
import com.example.quanlychitieu.ViewModel.*;
import com.example.quanlychitieu.databinding.*;

import java.util.*;

public class HomeActivity extends AppCompatActivity {
    // tạo biến binding
    private ActivityHomeBinding binding;
    //  Tạo biến adapter
    private TransactionAdapter adapter;

    // Tạo viewModel
    private TransactionViewModel viewModel;

    // tạo biến chứa ds các Transactions
   // private List<Transaction> transactionList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // khởi tạo viewmodel
        viewModel = new ViewModelProvider(this).get(TransactionViewModel.class);

        // Khởi tạo adapter và gán cho RecyclerView
        adapter = new TransactionAdapter();
        binding.rvTransactions.setLayoutManager(new LinearLayoutManager(this));
        binding.rvTransactions.setAdapter(adapter);

        // Quan sát dữ liệu từ viewmodel
        viewModel.getTransactionList().observe(this, transactions -> {
            if (transactions != null) {
                adapter.setData(transactions);
            }
        });


        // ==========================================================================================
        // khi click vào nút thêm
        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, AddTransactionActivity.class);
                startActivity(intent);
            }
        });
        // ==========================================================================================
        // Hiển thị lên reyclerview
        // giả lập dữ liệu
//        transactionList = new ArrayList<>();
//        transactionList.add(new Transaction("Ăn uống", 50000, "Cà phê sáng", "2025-11-11", "expense"));
//        transactionList.add(new Transaction("Lương", 12000000, "Tháng 11", "2025-11-10", "income"));

        // khởi tạo adapter
//        adapter = new TransactionAdapter(this, transactionList);
//        binding.rvTransactions.setLayoutManager(new LinearLayoutManager(this));
//        binding.rvTransactions.setAdapter(adapter);
        // ==========================================================================================
    }

    @Override
    protected void onResume() {
        super.onResume();
        // mỗi khi quay lại, load lại dữ liệu
        viewModel.loadData();
    }
}