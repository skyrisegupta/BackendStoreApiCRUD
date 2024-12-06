package com.akash.gupta.BackendStoreCRUD.Controllers;


import com.akash.gupta.BackendStoreCRUD.services.FileService;
import com.akash.gupta.BackendStoreCRUD.services.ProductService;
import com.akash.gupta.BackendStoreCRUD.dtos.ApiResponseMessage;
import com.akash.gupta.BackendStoreCRUD.dtos.ImageResponse;
import com.akash.gupta.BackendStoreCRUD.dtos.PageableResponse;
import com.akash.gupta.BackendStoreCRUD.dtos.ProductDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
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

@RestController
@RequestMapping("/products")
@Tag(name = "Product Controller", description = "REST APIs related to perform Product operations")
@SecurityRequirement(name="scheme1")

public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private FileService fileService;

    @Value("${product.image.path}")
    private String imagePath;


    //create a Product
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @Operation(summary = "Create Product", description = "Create a new product", tags = {"Product Controller"})
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        ProductDto createdProduct = productService.create(productDto);
        return new ResponseEntity<>(createdProduct, HttpStatus.OK);

    }

    //update a Product
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{productId}")
    @Operation(summary = "Update Product Details", description = "Update product details by Product ID", tags = {"Product Controller"})
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto) {
        ProductDto createdProduct = productService.create(productDto);
        return new ResponseEntity<>(createdProduct, HttpStatus.OK);
    }

    //Delete a Product
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{productId}")
    @Operation(summary = "Delete Product", description = "Delete product by Product ID", tags = {"Product Controller"})
    public ResponseEntity<ApiResponseMessage> delete(@PathVariable String productId) {

        productService.delete(productId);
        ApiResponseMessage responseMessage = ApiResponseMessage.builder().message("Product is deleted Successfully").success(true).status(HttpStatus.OK).build();
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }


    //Get Single Product
    @GetMapping("/{productId}")
    @Operation(summary = "Get Product by ID", description = "Get product details by ID", tags = {"Product Controller"})
    public ResponseEntity<ProductDto> getProduct(@PathVariable String productId) {
        ProductDto getProductDto = productService.get(productId);
        return new ResponseEntity<>(getProductDto, HttpStatus.OK);

    }

    //Get All Products
    @GetMapping
    @Operation(summary = "Get All Products", description = "Get all products from the database", tags = {"Product Controller"})
    public ResponseEntity<PageableResponse<ProductDto>> getAll(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir

    ) {


        PageableResponse<ProductDto> pageableResponse = productService.getAll(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(pageableResponse, HttpStatus.OK);

    }


    //Get all Products which are in the Stocks
    @GetMapping("/live")
    @Operation(summary = "Get Live Products", description = "Get live products(Products in Stocks)", tags = {"Product Controller"})
    public ResponseEntity<PageableResponse<ProductDto>> getAllLive(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {
        PageableResponse<ProductDto> pageableResponse = productService.getAllLive(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(pageableResponse, HttpStatus.OK);


    }


    //Search The Products Through Title
    @GetMapping("/search/{Query}")
    @Operation(summary = "Search Products", description = "Search products by Name", tags = {"Product Controller"})
    public ResponseEntity<PageableResponse<ProductDto>> searchUser(@PathVariable String Query,
                                                                   @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
                                                                   @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
                                                                   @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
                                                                   @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {

        PageableResponse<ProductDto> pageableResponse = productService.searchByTitle(Query, pageNumber, pageSize, sortBy, sortDir);

        return new ResponseEntity<>(pageableResponse, HttpStatus.OK);


    }

    //Upload : Image Of  Product
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/image/{productId}")
    @Operation(summary = "Upload or Update Product Image", description = "Upload or update product image by ID", tags = {"Product Controller"})
    public ResponseEntity<ImageResponse> uploadProductImage(
            @PathVariable String productId,
            @RequestParam("productImage") MultipartFile image
    ) throws IOException {
        // productImae = image pass krenge .. /// image path value annotation se aA RHA
        // UPLOAD FILE JO METHIOD BNA HAI USME 2 VALUE APASS HO RHI HAI
        String fileName = fileService.uploadFile(image, imagePath);

        // JO PRODUCT ID HM PASS KR RHE HAI VO LE LENGE UYHAN SE REPOSITORIES KE THROUGH
        ProductDto productDto = productService.get(productId);


        productDto.setProductImageName(fileName);
        ProductDto updateProduct = productService.update(productDto, productId);
        ImageResponse response = ImageResponse.builder().imageName(updateProduct.getProductImageName()).status(HttpStatus.OK).success(true).build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //Serving : Image of  Product
    @GetMapping("/image/{productId}")
    @Operation(summary = "Get Product Image", description = "Get product image by ID", tags = {"Product Controller"})
    public void serveUserImage(@PathVariable String productId, HttpServletResponse response) throws IOException {
        ProductDto productDto = productService.get(productId);

//        logger.info("User image : {}" , user.getImageName());

//        we can read the data from this and give it into the response
        InputStream resource = fileService.getResource(imagePath, productDto.getProductImageName());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }

}
//  For any Query Contact : akashguptaworks@gmail.com or skyrisegupta@gmail.com
