package com.dscatalog.dscatalog;

import com.dscatalog.dscatalog.dtos.ProductDTO;
import com.dscatalog.dscatalog.models.CategoryModel;
import com.dscatalog.dscatalog.models.ProductModel;

import java.time.Instant;

public class Factory {

    public static ProductModel createProduct(){
        ProductModel productModel = new ProductModel(1L, "Phone", "Good Phone", 800.00, "https://img.com/img.png", Instant.parse("2020-09-09"));
        productModel.getCategories().add(new CategoryModel(2L, "Electronics"));
        return productModel;
    }

    public static ProductDTO createProductDTO(){
        ProductModel productModel = createProduct();
        return new ProductDTO(productModel, productModel.getCategories());
    }
}
