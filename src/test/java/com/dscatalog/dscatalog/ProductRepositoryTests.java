package com.dscatalog.dscatalog;

import com.dscatalog.dscatalog.models.ProductModel;
import com.dscatalog.dscatalog.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;


@DataJpaTest
@Transactional // Importante para rollback após cada teste
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Não substituir o banco de dados real
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository repository;

    @Test
    public void deleteShouldDeleteObjectWhenIdExists(){

        long exintingId = 1L;

        repository.deleteById(exintingId);

        Optional<ProductModel> result = repository.findById(exintingId);
        Assertions.assertFalse(result.isPresent());



    }

}
