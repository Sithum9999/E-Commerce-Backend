package org.icet.learn.controller.admin;

import lombok.RequiredArgsConstructor;
import org.icet.learn.dto.Category;
import org.icet.learn.entity.CategoryEntity;
import org.icet.learn.service.admin.category.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminCategoryController {

    private final CategoryService categoryService;

    @PostMapping("/category")
    public ResponseEntity<CategoryEntity> createCategory(@RequestBody Category category){
        CategoryEntity categoryEntity = categoryService.createCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryEntity);
    }

    @GetMapping("")
    public ResponseEntity<List<CategoryEntity>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }



}
