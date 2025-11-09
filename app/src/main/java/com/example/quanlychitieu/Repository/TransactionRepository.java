package com.example.quanlychitieu.Repository;

import android.content.*;

import com.example.quanlychitieu.Database.DAO.*;
import com.example.quanlychitieu.Model.*;

import java.net.*;
import java.util.*;

public class TransactionRepository {
    private TransactionDAO transactionDAO;

    public TransactionRepository(Context context) {
        transactionDAO = new TransactionDAO(context);
    }

    // thêm
    public long addCategory(Transaction transaction){
        return transactionDAO.insertTransaction(transaction);
    }

    // Lấy toàn bộ danh sách
    public List<Transaction> getAllTransactions(){
        return transactionDAO.getAllTransactions();
    }

    // Update
    public int updateTransaction(Transaction transaction){
        return transactionDAO.updateTransaction(transaction);
    }

    // Delete
    public int deleteTransaction(int transactionId){
        return transactionDAO.deleteTransaction(transactionId);
    }


}
