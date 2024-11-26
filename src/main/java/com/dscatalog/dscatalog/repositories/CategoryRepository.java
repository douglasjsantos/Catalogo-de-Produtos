package com.dscatalog.dscatalog.repositories;

import com.dscatalog.dscatalog.models.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface CategoryRepository extends JpaRepository<CategoryModel, Long> {
}
