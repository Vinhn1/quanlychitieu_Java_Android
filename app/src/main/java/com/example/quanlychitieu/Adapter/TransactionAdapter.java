package com.example.quanlychitieu.Adapter;

import android.content.*;
import android.view.*;
import android.widget.*;

import androidx.annotation.*;
import androidx.recyclerview.widget.*;

import com.example.quanlychitieu.*;
import com.example.quanlychitieu.Model.*;
import java.text.DecimalFormat;
import java.util.*;

// TransactionAdapter.ViewHolder là lớp con quản lý từng item trong danh sách
// Adapter dùng cho RecyclerView hiển thị danh sách giao dịch
public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {
    // Danh sách các giao dịch, khởi tạo rỗng để tránh NullPointerException
    private List<Transaction> transactionList = new ArrayList<>();
    private Context context;

    // Định dạng tiền tệ cho VND
    private DecimalFormat vndFormat = new DecimalFormat("#,###");

    public TransactionAdapter(Context context, List<Transaction> transactionList) {
        this.context = context;
        this.transactionList = transactionList;
    }

    public TransactionAdapter() {

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
        holder.category.setText(String.valueOf(t.getCategory_name()));
        holder.note.setText(t.getNote());
        holder.date.setText(t.getCreate_at());
        // Định dạng tiền VND và thêm ký hiệu "đ"
        String formattedAmount = vndFormat.format(t.getAmount()) + " đ";
        holder.amount.setText(formattedAmount);


        // Thay đổi icon và màu sắc amount dựa vào loại giao dịch
        if("income".equalsIgnoreCase(t.getCategory_type())){
            holder.icon.setImageResource(R.drawable.ic_income);
            holder.amount.setTextColor(holder.amount.getResources().getColor(R.color.green));
        }else {
            holder.icon.setImageResource(R.drawable.ic_spending);
            holder.amount.setTextColor(holder.amount.getResources().getColor(R.color.red));
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
