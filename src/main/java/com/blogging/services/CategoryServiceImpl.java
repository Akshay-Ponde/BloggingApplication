package com.blogging.services;

import com.blogging.exceptions.ResourceNotFoundException;
import com.blogging.models.Category;
import com.blogging.models.User;
import com.blogging.payloads.CategoryDto;
import com.blogging.payloads.UserDto;
import com.blogging.repositories.CategoryRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    CategoryRepo categoryRepo;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = this.DtoToCategory(categoryDto);
        Category savedCategory = this.categoryRepo.save(category);
        return this.CategoryToDto(savedCategory);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer id) {

        Category category = this.categoryRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Category" , "CategoryId" , id));

        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());

        Category updatedCategory = this.categoryRepo.save(category);
        return this.CategoryToDto(updatedCategory);
    }

    @Override
    public CategoryDto getCategoryById(Integer id) {
        Category category = this.categoryRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Category" , "CategoryId" , id));
        return this.CategoryToDto(category);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = this.categoryRepo.findAll();
        return categories.stream().map(category -> this.CategoryToDto(category)).collect(Collectors.toList());
    }

    @Override
    public void deleteCategory(Integer id) {
        Category category = this.categoryRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Category" , "CategoryId" , id));
        categoryRepo.delete(category);
    }

    Category DtoToCategory(CategoryDto categoryDto)
    {
        Category category = this.modelMapper.map(categoryDto , Category.class);
        return category;
    }

    CategoryDto CategoryToDto(Category category)
    {
        CategoryDto categoryDto = this.modelMapper.map(category , CategoryDto.class);
        return categoryDto;
    }
}
