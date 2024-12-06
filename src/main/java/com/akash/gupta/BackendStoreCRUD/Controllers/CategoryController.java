package com.akash.gupta.BackendStoreCRUD.Controllers;


import com.akash.gupta.BackendStoreCRUD.repositories.ProductRepository;
import com.akash.gupta.BackendStoreCRUD.services.CategoryService;
import com.akash.gupta.BackendStoreCRUD.services.FileService;
import com.akash.gupta.BackendStoreCRUD.services.ProductService;
import com.akash.gupta.BackendStoreCRUD.dtos.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController()
@RequestMapping("/categories")
@Tag(name = "Category Controller", description = " REST APIs Related to Category Operations")
@SecurityRequirement(name="scheme1")

public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    private FileService fileService;

    @Value("${category.profile.image.path}")
    private String imageUploadpath;

//    @Autowired
//    CategoryRepository categoryRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductService productService;

//    @PreAuthorize("hasRole('ADMIN')")
//    @PostMapping
//    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto){
//        CategoryDto categoryDto1 = categoryService.create(categoryDto);
//        return new ResponseEntity<>(categoryDto1, HttpStatus.CREATED);
//
//    }

    //update
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{categoryId}")
    @Operation(summary = "Update Category", description = "Update category details by ID", tags = {"Category Controller"})
    public ResponseEntity<CategoryDto> updatedCategory(@Valid
                                                       @PathVariable String categoryId,
                                                       @RequestBody CategoryDto categoryDto) {
        CategoryDto updatedCategory = categoryService.update(categoryDto, categoryId);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    //delete
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{categoryId}")
    @Operation(summary = "Delete Category", description = "Delete category by Category ID", tags = {"Category Controller"})
    public ResponseEntity<ApiResponseMessage> deleteCategory(@PathVariable String categoryId) {
        categoryService.delete(categoryId);
        ApiResponseMessage response = ApiResponseMessage.builder().message("category is deleted scuccesfully").status(HttpStatus.OK).success(true).build();
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    //getAll
    @GetMapping
    @Operation(summary = "Get All Categories", description = "Get all categories from the database", tags = {"Category Controller"})
    public ResponseEntity<PageableResponse<CategoryDto>> getAll(

            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {
        PageableResponse<CategoryDto> pageableResponse = categoryService.getAll(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(pageableResponse, HttpStatus.OK);

    }

    //get Single
    @GetMapping("/{categoryId}")
    @Operation(summary = "Get Category by Category ID", description = "Get category details by Category ID", tags = {"Category Controller"})
    public ResponseEntity<CategoryDto> getSingle(@PathVariable String categoryId) {
        CategoryDto categoryDto = categoryService.get(categoryId);
        return ResponseEntity.ok(categoryDto);

    }

    //search
    @GetMapping("/search/{Keyword}")
    @Operation(summary = "Get Category by Search", description = "Search Category by Keyword", tags = {"Category Controller"})
    public ResponseEntity<List<CategoryDto>> searchCategory(@PathVariable String Keyword) {

        return new ResponseEntity<>(categoryService.searchCategory(Keyword), HttpStatus.OK);


    }

    // upload user image
    @PostMapping("/image/{categoryId}")
    @Operation(summary = "Upload Category Image", description = "Upload category image by Category ID", tags = {"Category Controller"})
    public ResponseEntity<ImageResponse> uploadUserImage(@RequestParam("categoryImage") MultipartFile image,
                                                         @PathVariable String categoryId) throws IOException {

        String imageName = fileService.uploadFile(image, imageUploadpath);
        CategoryDto categoryDto = categoryService.get(categoryId);
        categoryDto.setCoverImage(imageName);
        CategoryDto categoryDto1 = categoryService.update(categoryDto, categoryId);
        ImageResponse imageResponse = ImageResponse.builder().imageName(imageName).success(true).status(HttpStatus.OK).build();

        return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);


    }

    //serve image
    @GetMapping("/image/{categoryId}")
    @Operation(summary = "Get Category Image", description = "Get category image by ID", tags = {"Category Controller"})
    public void ServeUserImage(@PathVariable String categoryId, HttpServletResponse response) throws IOException {
        CategoryDto categoryDto = categoryService.get(categoryId);
//        logger.info("User image : {}" , user.getImageName());

        InputStream resource = fileService.getResource(imageUploadpath, categoryDto.getCoverImage());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());


    }

    //create  product and insert data with Category
    @PostMapping("/{categoryId}/products")
    @Operation(summary = "Create Product with Category", description = "Create Product with Category Id ", tags = {"Category Controller"})
    public ResponseEntity<ProductDto> createProductWithCategory(
            @PathVariable("categoryId") String categoryId,
            @RequestBody ProductDto dto

    ) {
        ProductDto ProductWithCategory = productService.createWithCategory(dto, categoryId);
        return new ResponseEntity<>(ProductWithCategory, HttpStatus.CREATED);


    }

    //update Category of existing product
    @PutMapping("/{categoryId}/products/{productId}")
    @Operation(summary = "Update Category of Product", description = "update Category of existing product", tags = {"Category Controller"})
    public ResponseEntity<ProductDto> updateCategoryofProduct(@PathVariable String productId,
                                                              @PathVariable String categoryId) {

        ProductDto productDto = productService.updateCategory(productId, categoryId);
        return new ResponseEntity<>(productDto, HttpStatus.OK);


    }


    //get products of categories
    @GetMapping("/{categoryId}/products")
    @Operation(summary = "Get Products by Category ID", description = "Get products by category ID", tags = {"Category Controller"})
    public ResponseEntity<PageableResponse<ProductDto>> getProductsOfCategory(
            @PathVariable String categoryId,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {


        PageableResponse<ProductDto> response = productService.getAllOfCategory(categoryId, pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(response, HttpStatus.OK);


    }


    //  For any Query Contact : akashguptaworks@gmail.com or skyrisegupta@gmail.com


}

