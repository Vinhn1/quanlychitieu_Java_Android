package com.example.quanlychitieu.Database.DAO;

import android.content.*;
import android.database.*;
import android.database.sqlite.*;

import com.example.quanlychitieu.Database.DBHelper.*;
import com.example.quanlychitieu.Model.*;

import java.util.*;

public class TransactionDAO {
    private SQLiteDatabase db;

    public TransactionDAO(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    // Thêm
    public long insertTransaction(Transaction transaction){
        ContentValues values = new ContentValues();
        values.put("user_id", transaction.getUser_id());
        values.put("category_id", transaction.getCategory_id());
        values.put("amount", transaction.getAmount());
        values.put("note", transaction.getNote());
        values.put("create_at", transaction.getCreate_at());
        return db.insert("Transactions", null, values);
    }

    // Lấy toàn bộ danh sách
//    public List<Transaction> getAllTransactions(){
//        List<Transaction> list = new ArrayList<>();
//        Cursor cursor = db.rawQuery("SELECT * FROM Transactions", null);
//        while (cursor.moveToNext()){
//            Transaction transaction = new Transaction(
//                    cursor.getInt(0),
//                    cursor.getInt(1),
//                    cursor.getInt(2),
//                    cursor.getDouble(3),
//                    cursor.getString(4),
//                    cursor.getString(5)
//            );
//            list.add(transaction);
//        }
//        cursor.close();
//        return list;
//    }

    // Lấy toàn bộ transactions kèm tên category
    public List<Transaction> getAllTransactionsWithCategory() {
        List<Transaction> list = new ArrayList<>();
        String sql = "SELECT t.transaction_id, t.user_id, t.category_id, t.amount, t.note, t.create_at, c.name AS category_name, c.type " +
                "FROM Transactions t " +
                "LEFT JOIN Categories c ON t.category_id = c.category_id " +
                "ORDER BY t.create_at DESC";
        Cursor cursor = db.rawQuery(sql, null);

        while (cursor.moveToNext()) {
            Transaction transaction = new Transaction(
                    cursor.getInt(cursor.getColumnIndexOrThrow("transaction_id")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("user_id")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("category_id")),
                    cursor.getDouble(cursor.getColumnIndexOrThrow("amount")),
                    cursor.getString(cursor.getColumnIndexOrThrow("note")),
                    cursor.getString(cursor.getColumnIndexOrThrow("create_at")),
                    cursor.getString(cursor.getColumnIndexOrThrow("category_name")),
                    cursor.getString(cursor.getColumnIndexOrThrow("type"))
            );

            list.add(transaction);
        }
        cursor.close();
        return list;
    }


    // update
    public int updateTransaction(Transaction transaction){
        ContentValues values = new ContentValues();
        values.put("amount", transaction.getAmount());
        values.put("note", transaction.getNote());
        return db.update("Transactions", values, "transaction_id = ?", new String[]{String.valueOf(transaction.getTransaction_id())});
    }

    // Delete
    public int deleteTransaction(int transactionId){
        return db.delete("Transactions", "transaction_id = ?", new String[]{String.valueOf(transactionId)});
    }


}
