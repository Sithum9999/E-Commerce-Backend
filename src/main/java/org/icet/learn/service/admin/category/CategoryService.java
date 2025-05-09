package org.icet.learn.service.admin.category;

import org.icet.learn.dto.Category;
import org.icet.learn.entity.CategoryEntity;

public interface CategoryService {
    CategoryEntity createCategory(Category category);
}
