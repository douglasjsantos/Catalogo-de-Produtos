package com.dscatalog.dscatalog.services;

import com.dscatalog.dscatalog.dtos.ProductDTO;
import com.dscatalog.dscatalog.exceptions.DatabaseException;
import com.dscatalog.dscatalog.exceptions.EntityNotFoundException;
import com.dscatalog.dscatalog.models.ProductModel;
import com.dscatalog.dscatalog.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Transactional(readOnly = true)
    public Page<ProductDTO> listAllPaged(PageRequest pageRequest){

        Page<ProductModel> productList = repository.findAll(pageRequest);

        return productList.map(x -> new ProductDTO());
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id){

        Optional<ProductModel> obj = repository.findById(id);
        ProductModel entity = obj.orElseThrow(() -> new EntityNotFoundException("Entity not found"));

        return new ProductDTO(entity, entity.getCategories());

    }

    @Transactional
    public ProductDTO save(ProductDTO productDTO) {

        ProductModel ProductModel = new ProductModel();
        ProductModel.setName(productDTO.getName());
        ProductModel savedModel = repository.save(ProductModel);
        return new ProductDTO(savedModel);
    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO productDTO) {

        try{
            ProductModel productModel = repository.getReferenceById(id);
            productModel.setName(productDTO.getName());
            productModel = repository.save(productModel);
            return new ProductDTO(productModel);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Id not found" + id);
        }

    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {

        if(!repository.existsById(id)){
            throw new EntityNotFoundException("Entity not found");
        }

        try {
            repository.deleteById(id);
        }catch (DataIntegrityViolationException e){
            throw new DatabaseException("Integrity violation");
        }
    }


}
