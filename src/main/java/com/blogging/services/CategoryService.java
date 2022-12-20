package com.blogging.services;

import com.blogging.payloads.CategoryDto;
import com.blogging.payloads.UserDto;

import java.util.List;

public interface CategoryService {

    CategoryDto createCategory(CategoryDto categoryDto);
    CategoryDto updateCategory(CategoryDto categoryDto , Integer id);
    CategoryDto getCategoryById(Integer id);
    List<CategoryDto> getAllCategories();
    void deleteCategory(Integer id);
}
