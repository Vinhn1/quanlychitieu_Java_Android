package com.example.quanlychitieu.Adapter;

import android.app.*;
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
    private OnItemClickListener listener;

    // Định dạng tiền tệ cho VND
    private DecimalFormat vndFormat = new DecimalFormat("#,###");

    // ==========================================================================================
    // Interface Cho sự kiện click
    public interface OnItemClickListener{
        void onEditClick(Transaction transaction);
        void onDeleteClick(Transaction transaction);
    }

    // ==========================================================================================
    public TransactionAdapter(Context context, List<Transaction> transactionList, OnItemClickListener listener) {
        this.context = context;
        this.transactionList = transactionList;
        this.listener = listener;
    }

    public TransactionAdapter(Context context, OnItemClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    // ==========================================================================================
    public TransactionAdapter() {

    }

    // ==========================================================================================
    // Phương thức cập nhật dữ liệu cho adapter
    public void setData(List<Transaction> list){
        // Gán dữ liệu mới
        transactionList = list;
        // thông báo RecylerView cập nhật toàn bộ
        notifyDataSetChanged();
    }
    // ==========================================================================================
    // Tạo ViewHolder mới (gọi khi cần một view mới cho RecyclerView)
    @NonNull
    @Override
    public TransactionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Chuyển file layout XML thành View (Gọi đến file item_transaction.xml
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction, parent, false);
        return new ViewHolder(view);
    }

    // ==========================================================================================

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
            holder.amount.setText("+ " + formattedAmount);
        }else {
            holder.icon.setImageResource(R.drawable.ic_spending);
            holder.amount.setTextColor(holder.amount.getResources().getColor(R.color.red));
            holder.amount.setText("- " + formattedAmount);
        }

        // Xử lý sự kiện click vào item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onEditClick(t);
                }
            }
        });

        // Xử lý sự kiện long click để xóa
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(listener != null){
                    showDeleteDialog(t);
                }
                return true;
            }

            private void showDeleteDialog(Transaction t) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xóa giao dịch");
                builder.setMessage("Bạn có chắc chắn muốn xóa?");

                builder.setPositiveButton("Xóa", ((dialog, which) -> {
                    if(listener != null){
                        listener.onDeleteClick(t);
                    }
                }));

                builder.setNegativeButton("Hủy", ((dialog, which) -> {
                    dialog.dismiss();
                }));

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }

    // ==========================================================================================

    // Trả về số lượng item của RecyclerView
    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    // ==========================================================================================

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

    // ==========================================================================================
}
