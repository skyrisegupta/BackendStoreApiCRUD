package com.akash.gupta.BackendStoreCRUD.services;

import com.akash.gupta.BackendStoreCRUD.dtos.PageableResponse;
import com.akash.gupta.BackendStoreCRUD.dtos.ProductDto;

public interface ProductService {

    //create
    ProductDto create(ProductDto productDto);


    //update
    ProductDto update(ProductDto productDto, String productId);


    //delete
    void delete(String productId);


    //get single
    ProductDto get(String productId);

    //get all
//    List<ProductDto> getAll();
    PageableResponse<ProductDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir);

    //get all : Live
    PageableResponse<ProductDto> getAllLive(int pageNumber, int pageSize, String sortBy, String sortDir);

    //search Product
    PageableResponse<ProductDto> searchByTitle(String subtitle, int pageNumber, int pageSize, String sortBy, String sortDir);

    //other methods

    //create Product with Category
    ProductDto createWithCategory(ProductDto productDto, String categoryId);

    //assign category to existing Product
    ProductDto updateCategory(String productId, String categoryId);


    //return product of Give Category
    PageableResponse<ProductDto> getAllOfCategory(String categoryId, int pageNumber, int pageSize, String sortBy, String sortDir);


}
