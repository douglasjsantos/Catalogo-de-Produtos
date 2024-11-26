package com.dscatalog.dscatalog.repositories;

import com.dscatalog.dscatalog.models.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductModel, Long> {
}
