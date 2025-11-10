package com.example.quanlychitieu.Adapter;

import android.view.*;
import android.widget.*;

import androidx.annotation.*;
import androidx.recyclerview.widget.*;

import com.example.quanlychitieu.*;
import com.example.quanlychitieu.Model.*;

import java.util.*;

// TransactionAdapter.ViewHolder là lớp con quản lý từng item trong danh sách
// Adapter dùng cho RecyclerView hiển thị danh sách giao dịch
public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {
    // Danh sách các giao dịch, khởi tạo rỗng để tránh NullPointerException
    private List<Transaction> transactionList = new ArrayList<>();
    // map category_id → icon drawable
    private Map<Integer, Integer> categoryIconMap;

    public TransactionAdapter(Map<Integer, Integer> categoryIconMap) {
        this.categoryIconMap = categoryIconMap;
    }

    // Phương thức cập nhật dữ liệu cho adapter
    public void setData(List<Transaction> list){
        // Gán dữ liệu mới
        transactionList = list;
        // thông báo RecylerView cập nhật toàn bộ
        notifyDataSetChanged();
    }

    // Tạo ViewHolder mới (gọi khi cần một view mới cho RecyclerView)
    @NonNull
    @Override
    public TransactionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Chuyển file layout XML thành View (Gọi đến file item_transaction.xml
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction, parent, false);
        return new ViewHolder(view);
    }

    // Gán dữ liệu vào từng item của RecyclerView
    @Override
    public void onBindViewHolder(@NonNull TransactionAdapter.ViewHolder holder, int position) {
        Transaction t = transactionList.get(position);
        // Gán dữ liệu
        holder.category.setText(t.getCategory_id());
        holder.note.setText(t.getNote());
        holder.date.setText(t.getCreate_at());
        holder.amount.setText(String.format("%,.0fđ", t.getAmount()));

        // Gán icon dựa vào category_id
        Integer iconRes = categoryIconMap.get(t.getCategory_id());
        if(iconRes != null){
            holder.icon.setImageResource(R.drawable.ic_income);
        }else {
            holder.icon.setImageResource(R.drawable.ic_spending);
        }
    }

    // Trả về số lượng item của RecyclerView
    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    // Lớp ViewHolder giữ tham chiếu đến các view trong mỗi item
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView category, note, date, amount;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Gán view từ layout item_transaction.xml
            icon = itemView.findViewById(R.id.imgCategory);
            category = itemView.findViewById(R.id.txtCategory);
            note = itemView.findViewById(R.id.txtNote);
            date = itemView.findViewById(R.id.txtDate);
            amount = itemView.findViewById(R.id.txtAmount);
        }
    }
}
