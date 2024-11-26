package com.dscatalog.dscatalog.services;

import com.dscatalog.dscatalog.dtos.CategoryDTO;
import com.dscatalog.dscatalog.exceptions.EntityNotFoundException;
import com.dscatalog.dscatalog.models.CategoryModel;
import com.dscatalog.dscatalog.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Transactional(readOnly = true)
    public List<CategoryDTO> listAll(){

        List<CategoryModel> categoryList = repository.findAll();

        return categoryList.stream()
                .map(category -> new CategoryDTO(category.getId(), category.getName())).collect(Collectors.toList());



    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id){

        Optional<CategoryModel> obj = repository.findById(id);
        CategoryModel entity = obj.orElseThrow(() -> new EntityNotFoundException("Entity not found"));

        return new CategoryDTO(entity.getId(), entity.getName());


    }

    @Transactional
    public CategoryDTO save(CategoryDTO categoryDTO) {

        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setName(categoryDTO.name());
        CategoryModel savedModel = repository.save(categoryModel);
        return new CategoryDTO(savedModel.getId(), savedModel.getName());
    }

    @Transactional
    public CategoryDTO update(Long id, CategoryDTO categoryDTO) {

        try{
            CategoryModel categoryModel = repository.getReferenceById(id);
            categoryModel.setName(categoryDTO.name());
            categoryModel = repository.save(categoryModel);
            return new CategoryDTO(categoryModel.getId(), categoryModel.getName());
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Id not found" + id);
        }

    }
}
