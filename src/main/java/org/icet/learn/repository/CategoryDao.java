package org.icet.learn.repository;

import org.icet.learn.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryDao extends JpaRepository<CategoryEntity, Long> {

}
