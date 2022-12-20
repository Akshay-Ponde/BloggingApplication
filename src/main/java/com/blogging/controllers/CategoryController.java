package com.blogging.controllers;

import com.blogging.payloads.CategoryDto;
import com.blogging.payloads.ResponseApi;
import com.blogging.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    CategoryService categoryService;


    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto savedCategory = this.categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(savedCategory , HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Integer id) {
        CategoryDto updatedCategory = this.categoryService.updateCategory(categoryDto , id);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Integer id) {
        CategoryDto categoryDto = this.categoryService.getCategoryById(id);
        return new ResponseEntity<>(categoryDto , HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<CategoryDto> categories =  this.categoryService.getAllCategories();
        return new ResponseEntity<>(categories , HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseApi> deleteCategory(@PathVariable Integer id) {
        this.categoryService.deleteCategory(id);
        return new ResponseEntity<>(new ResponseApi("Category deleted successfully !!!" , true) , HttpStatus.OK);
    }
}
