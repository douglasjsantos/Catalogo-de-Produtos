package com.dscatalog.dscatalog.repositories;

import com.dscatalog.dscatalog.Factory;
import com.dscatalog.dscatalog.models.ProductModel;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;


@DataJpaTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Não substituir o banco de dados real
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository repository;

    private long existingId = 1L;
    private long countTotalProducts;

    @BeforeEach
    void setUp() throws Exception{
        long existingId = 1L;
        countTotalProducts= 25L;
    }


    /* @Test
    public void saveShouldPersistWithAutoIncrementWhenIdIsNull(){

        ProductModel productModel = Factory.createProduct();
        productModel.setId(null);

        productModel = repository.save(productModel);

        Assertions.assertNotNull(productModel.getId());
        Assertions.assertEquals(countTotalProducts + 1, productModel.getId());
    } */


    @Test
    void deleteShouldDeleteObjectWhenIdExists(){


        repository.deleteById(existingId);

        Optional<ProductModel> result = repository.findById(existingId);
        Assertions.assertFalse(result.isPresent());



    }

}
