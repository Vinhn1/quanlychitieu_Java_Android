package com.example.quanlychitieu.Repository;

import android.content.*;
import android.view.contentcapture.*;

import com.example.quanlychitieu.Database.DAO.*;
import com.example.quanlychitieu.Model.*;

import java.util.*;

public class CategoryRepository {
    private CategoryDAO categoryDAO;

    public CategoryRepository(Context context) {
        categoryDAO = new CategoryDAO(context);
    }

    // Thêm Category
    public long addCategory(Category category){
        return categoryDAO.insertCategory(category);
    }

    // Lấy toàn bộ ds
    public List<Category> getAllCategories(){
        return categoryDAO.getAllCategories();
    }

    // update
    public int updateCategory(Category category){
        return categoryDAO.updateCategory(category);
    }

    // delete
    public int deleteCategory(int categoryId){
        return categoryDAO.deleteCategory(categoryId);
    }
}
