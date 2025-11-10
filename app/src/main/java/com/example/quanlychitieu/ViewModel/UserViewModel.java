package com.example.quanlychitieu.ViewModel;

import android.content.*;

import androidx.lifecycle.*;

import com.example.quanlychitieu.Model.*;
import com.example.quanlychitieu.Repository.*;

import java.util.*;

public class UserViewModel extends ViewModel {

    // repository dùng để truy vấn dữ liệu
     private UserRepository repository;

     // // SharedPreferences để lưu session hoặc cấu hình nhỏ
     private SharedPreferences sharedPreferences;

     // ==========================================================================================
    // Hàm init khởi tạo repository và SharedPreferences khi cần
     public void init(Context context){
         // Chỉ khởi tạo một lần (tránh tạo lại khi gọi nhiều lần)
         if(repository == null){
             // Tạo repository, truyền Context (để UserRepository tạo DAO/DBHelper)
             repository = new UserRepository(context);
             // Lấy SharedPreferences với tên "user_session"
             // Context.MODE_PRIVATE: file prefs chỉ app hiện tại có quyền đọc/ghi
             sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE);
         }
     }
    // ==========================================================================================
    // Đăng ký người dùng mới
    public boolean register(User user){
         // Kiểm tra ds người dùng trong hệ thống ( existing: ds ng dùng hiện có)
        List<User> existing =  repository.getAllUser();
        // Duyệt qua từng người dùng trong danh sách hiện có
        for(User u : existing){
            // So sánh email (không phân biệt chữ hoa/chữ thường)
            if(u.getEmail().equalsIgnoreCase(user.getEmail())){
                // Nếu có người dùng trùng email => không cho đăng ký
                return false;
            }
        }
        // Nếu không trùng email thì thêm người dùng mới vào cơ sở dữ liệu
        repository.addUser(user);
        // Trả về true => đăng ký thành công
        return true;
    }

    // ==========================================================================================
    // Đăng nhập
    public boolean login(String name, String password){
         // Lấy danh sách tất cả người dùng hiện có trong hệ thống
        List<User> users = repository.getAllUser();
        // Duyệt qua từng người dùng để kiểm tra thông tin đăng nhập
        for(User user : users){
            // So sánh email (bỏ qua chữ hoa/thường) và mật khẩu (so sánh chính xác)
            if(user.getName().equalsIgnoreCase(name) && user.getPassword().equals(password)){
                //  Nếu khớp → lưu lại phiên đăng nhập bằng user_id
                saveLoginSession(user.getUser_id());
                // // Đăng nhập thành công → trả về true
                return true;
            }
        }
        // Nếu không tìm thấy người dùng phù hợp → đăng nhập thất bại
        return false;
    }

    private void saveLoginSession(int userId) {
         // Mở SharedPreferences ở chế độ ghi (edit)
         SharedPreferences.Editor editor = sharedPreferences.edit();
         // Lưu user_id của người dùng vừa đăng nhập vào file SharedPreferences
         editor.putInt("logged_in_user_id", userId);
         // Ghi thay đổi (apply: ghi bất đồng bộ, nhanh hơn commit)
         editor.apply();
    }
    // ==========================================================================================
    // Kiểm tra xem có người dùng đang đăng nhập hay không
        public boolean isLoggeIn(){
         return sharedPreferences.contains("logged_in_user_id");
        }
    // ==========================================================================================
    // Xóa phiên đăng nhập hiện tại (đăng xuất người dùng)
    public void logout(){
         sharedPreferences.edit().remove("logged_in_user_id").apply();
    }

    // ==========================================================================================

}
