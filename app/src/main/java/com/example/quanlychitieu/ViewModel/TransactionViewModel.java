package com.example.quanlychitieu.ViewModel;

import android.app.*;

import androidx.annotation.*;
import androidx.lifecycle.*;

import com.example.quanlychitieu.Model.*;
import com.example.quanlychitieu.Repository.*;

import java.util.*;

public class TransactionViewModel extends AndroidViewModel {

    private TransactionRepository repository;
    private MutableLiveData<List<Transaction>> transactionList = new MutableLiveData<>();

    public TransactionViewModel(@NonNull Application application) {
        super(application);
        repository = new TransactionRepository(application);
        loadData();
    }

    // Lấy dữ liệu ban đầu
    public void loadData() {
        transactionList.setValue(repository.getAllTransactionsWithCategory());
    }

    // Expose dữ liệu ra ngoài dạng LiveData
    public LiveData<List<Transaction>> getTransactionList(){
        return transactionList;
    }

    // Thêm giao dịch mới
    public long addTransaction(Transaction transaction){
        long id = repository.addTransaction(transaction);
        // Cập nhật lại ds sau khi thêm
        if(id != -1){
            loadData(); // cập nhật LiveData
        }
        return id;
    }

    // Xóa
    public void deleteTransaction(int id){
        repository.deleteTransaction(id);
        loadData();
    }

    // Cập nhật
    public void updateTransaction(Transaction transaction){
        repository.updateTransaction(transaction);
        loadData();
    }

}
