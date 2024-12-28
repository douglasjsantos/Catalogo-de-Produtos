package com.dscatalog.dscatalog.repositories;

import com.dscatalog.dscatalog.models.ProductModel;
import com.dscatalog.dscatalog.projections.ProductProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductModel, Long> {

    @Query(nativeQuery = true, value = """
            SELECT * FROM (
            
            SELECT DISTINCT TB_PRODUCT.ID, TB_PRODUCT.NAME
            FROM TB_PRODUCT
            INNER JOIN TB_PRODUCT_CATEGORY ON TB_PRODUCT.ID = TB_PRODUCT_CATEGORY.PRODUCT_ID
            WHERE (:categoryIds IS NULL OR TB_PRODUCT_CATEGORY.CATEGORY_ID IN :categoryIds)
            AND LOWER(TB_PRODUCT.NAME) LIKE LOWER(CONCAT('%',:name, '%'))
            ) AS TB_RESULT
            """, countQuery = """
            SELECT COUNT(*) FROM (
            SELECT DISTINCT TB_PRODUCT.ID, TB_PRODUCT.NAME
            FROM TB_PRODUCT
            INNER JOIN TB_PRODUCT_CATEGORY ON TB_PRODUCT.ID = TB_PRODUCT_CATEGORY.PRODUCT_ID
            WHERE (:categoryIds IS NULL OR TB_PRODUCT_CATEGORY.CATEGORY_ID IN :categoryIds)
            AND LOWER(TB_PRODUCT.NAME) LIKE LOWER(CONCAT('%',:name, '%'))
            ) AS TB_RESULT
            """)
    Page<ProductProjection> searchProducts(List<Long> categoryIds, String name, Pageable pageable);


    @Query("SELECT obj FROM ProductModel obj JOIN FETCH obj.categories cat WHERE obj.id IN :productIds")
    List<ProductModel> searchProductsWithCategories(List<Long> productIds);
}
