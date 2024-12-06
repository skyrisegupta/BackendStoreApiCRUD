package com.akash.gupta.BackendStoreCRUD.services;

import com.akash.gupta.BackendStoreCRUD.dtos.CategoryDto;
import com.akash.gupta.BackendStoreCRUD.dtos.PageableResponse;

import java.util.List;

public interface CategoryService {
    //create
    CategoryDto create(CategoryDto categoryDto);

    //update
    CategoryDto update(CategoryDto categoryDto , String categoryId);

    //delete
    void delete(String categoryId);

    //getall
    PageableResponse<CategoryDto>  getAll(int pageNumber , int pageSize ,  String sortBy , String sortDir) ;


    //getsingle detail
    CategoryDto get(String categoryId);

    //search
    List<CategoryDto> searchCategory(String keyword);


}
