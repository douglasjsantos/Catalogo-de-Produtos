package com.dscatalog.dscatalog.services;

import com.dscatalog.dscatalog.dtos.CategoryDTO;
import com.dscatalog.dscatalog.exceptions.EntityNotFoundException;
import com.dscatalog.dscatalog.models.CategoryModel;
import com.dscatalog.dscatalog.repositories.CategoryRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<CategoryDTO> listAll(){

        List<CategoryModel> categoryList = categoryRepository.findAll();

        return categoryList.stream()
                .map(category -> new CategoryDTO(category.getId(), category.getName())).collect(Collectors.toList());



    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id){

        Optional<CategoryModel> obj = categoryRepository.findById(id);
        CategoryModel entity = obj.orElseThrow(() -> new EntityNotFoundException("Entity not found"));

        return new CategoryDTO(entity.getId(), entity.getName());


    }

    public CategoryDTO save(CategoryDTO categoryDTO) {

        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setName(categoryDTO.name());

        CategoryModel savedModel = categoryRepository.save(categoryModel);

        return new CategoryDTO(savedModel.getId(), savedModel.getName());


    }
}
