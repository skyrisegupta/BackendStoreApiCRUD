package com.akash.gupta.BackendStoreCRUD.services.impl;

import com.akash.gupta.BackendStoreCRUD.dtos.CategoryDto;
import com.akash.gupta.BackendStoreCRUD.dtos.PageableResponse;
import com.akash.gupta.BackendStoreCRUD.dtos.ProductDto;
import com.akash.gupta.BackendStoreCRUD.entities.Category;
import com.akash.gupta.BackendStoreCRUD.exceptions.ResourceNotFoundException;
import com.akash.gupta.BackendStoreCRUD.helper.Helper;
import com.akash.gupta.BackendStoreCRUD.repositories.CategoryRepository;
import com.akash.gupta.BackendStoreCRUD.services.CategoryService;
import com.akash.gupta.BackendStoreCRUD.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper mapper;
    @Value("${category.profile.image.path}")
    private String imageUploadpath;

    @Autowired
    ProductService productService;

    @Override
    public CategoryDto create(CategoryDto categoryDto) {
        // creating category id randomly
        String categoryId = UUID.randomUUID().toString();


        //        WE ARE HERE CONVERTING CATEORY DTO TO ENTIY
        categoryDto.setCategoryId(categoryId);
        Category category = mapper.map(categoryDto, Category.class);
        Category savedCategory = categoryRepository.save(category);
        CategoryDto returningcategoryDto = mapper.map(savedCategory, CategoryDto.class);

        return returningcategoryDto;


    }

    @Override
    public CategoryDto update(CategoryDto categoryDto, String categoryId) {
        //getCategory of the given id
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found with given ID"));
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        category.setCoverImage(categoryDto.getCoverImage());
        Category updatedCategory = categoryRepository.save(category);

        return mapper.map(updatedCategory, CategoryDto.class);
    }

    @Override
    public void delete(String categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("THE USER YOU WANT TO DELETE IS NOT EXIST IN OUR DATABASE"));
        String fullpath = imageUploadpath + category.getCoverImage();
        try {
            Path path = Paths.get(fullpath);
            Files.delete(path);
        } catch (NoSuchFileException ex) {


            ex.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        categoryRepository.delete(category);

    }

    @Override
    public PageableResponse<CategoryDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc") ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending()));

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Category> page = categoryRepository.findAll(pageable);
        PageableResponse<CategoryDto> pageableResponse = Helper.getPageableResponse(page, CategoryDto.class);
        return pageableResponse;
    }

    @Override
    public CategoryDto get(String categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("THE USER YOU WANT TO DELETE IS NOT EXIST IN OUR DATABASE"));
        return mapper.map(category, CategoryDto.class);

    }

    @Override
    public List<CategoryDto> searchCategory(String keyword) {

        List<Category> categoryList = categoryRepository.findByTitleContaining(keyword);
        List<CategoryDto> dtoList = categoryList.stream()
                .map(categories -> entitytoDto(categories))
                .collect(Collectors.toList());

        return dtoList;
    }

    private CategoryDto entitytoDto(Category category) {
        return mapper.map(category, CategoryDto.class);
    }

    //create Product with Category

    public ResponseEntity<ProductDto> createProductWithCategory(
            @PathVariable("categoryId") String categoryId,
            @RequestBody ProductDto dto

    ) {
        ProductDto ProductWithCategory = productService.createWithCategory(dto, categoryId);
        return new ResponseEntity<>(ProductWithCategory, HttpStatus.CREATED);


    }


}
//For any Query Contact : akashguptaworks@gmail.com or skyrisegupta@gmail.com