package com.akash.gupta.BackendStoreCRUD.services.impl;
import com.akash.gupta.BackendStoreCRUD.dtos.PageableResponse;
import com.akash.gupta.BackendStoreCRUD.dtos.ProductDto;
import com.akash.gupta.BackendStoreCRUD.entities.Category;
import com.akash.gupta.BackendStoreCRUD.entities.Product;
import com.akash.gupta.BackendStoreCRUD.exceptions.ResourceNotFoundException;
import com.akash.gupta.BackendStoreCRUD.helper.Helper;
import com.akash.gupta.BackendStoreCRUD.repositories.CategoryRepository;
import com.akash.gupta.BackendStoreCRUD.repositories.ProductRepository;
import com.akash.gupta.BackendStoreCRUD.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.UUID;

@Service




public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper mapper;

    @Value("${product.image.path}")
    private String imagePath;

    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public ProductDto create(ProductDto productDto) {

        Product product = mapper.map(productDto,Product.class);

        String productId = UUID.randomUUID().toString();
        product.setProductId(productId);
        product.setStock(productDto.isStock());

        product.setAddedDate(new Date());
//        product.setStock(productDto.getStock());



        Product saveProduct = productRepository.save(product);

        ProductDto productDto1 = mapper.map(saveProduct, ProductDto.class);
          return productDto1;
    }

    @Override
    public ProductDto update(ProductDto productDto, String productId){
        //fetch the product of the give id
        Product product = productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product not found of the give id"));
     product.setTitle(productDto.getTitle());
     product.setDescription(productDto.getTitle());
     product.setPrice(productDto.getPrice());
     product.setDiscountedPrice(productDto.getDiscountedPrice());
     product.setQuantity(productDto.getQuantity());
     product.setLive(productDto.isLive());
     product.setStock(productDto.isStock());
     product.setProductImageName(productDto.getProductImageName());

//      save the entity
        Product updateProduct = productRepository.save(product);
        return mapper.map(updateProduct,ProductDto.class);


    }

    @Override
    public void delete(String productId){
//       first get the whole details from the ide
        Product product = productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product not found of the give id"));
       String fullPath = imagePath + product.getProductImageName();
        try{
            Path path = Paths.get(fullPath);
            Files.delete(path);
        }catch(NoSuchFileException ex){
            ex.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }


        productRepository.delete(product);



    }

    @Override
    public ProductDto get(String productId){
        Product product = productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product not found of the give id"));
        return mapper.map(product,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAll(int pageNumber, int pageSize, String sortBy , String sortDir){
        Sort sort =  (sortDir.equalsIgnoreCase("desc") ) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber , pageSize , sort);
        Page<Product> page = productRepository.findAll(pageable);
        return Helper.getPageableResponse(page,ProductDto.class);

        //        productRepository.findAll(pageable);

    }
    @Override
    public PageableResponse<ProductDto> getAllLive(int pageNumber, int pageSize, String sortBy , String sortDir){

        Sort sort =  (sortDir.equalsIgnoreCase("desc") ) ? (Sort.by(sortBy)) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber , pageSize , sort);
        Page<Product> page = productRepository.findByLiveTrue(pageable);
        return Helper.getPageableResponse(page,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto>  searchByTitle(String subtitle,int pageNumber, int pageSize, String sortBy , String sortDir){
        Sort sort =  (sortDir.equalsIgnoreCase("desc") ) ? (Sort.by(sortBy)) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber , pageSize , sort);
        Page<Product> page = productRepository.findByTitleContaining(subtitle,pageable);
        return Helper.getPageableResponse(page,ProductDto.class);
    }

    @Override
    public ProductDto createWithCategory(ProductDto productDto, String categoryId) {

        //fetch the category from database
        Category category1 = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("the Category you enter is doesn't Exist"));
        Product product = mapper.map(productDto , Product.class);

        //product id
        String productId = UUID.randomUUID().toString();

        //setting the data which we are saving m which are not passing through Requests
        product.setProductId(productId);
        product.setAddedDate(new Date());

        product.setCategory(category1);
        //saving
        Product product1saved = productRepository.save(product);

        //concverting the saved Product data to dto type and returning it to the Postman
        return mapper.map(product1saved , ProductDto.class);



    }

    @Override
    public ProductDto updateCategory(String productId, String categoryId) {
        //product  Fetch
        Product product = productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product Id Not Found !!"));
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Catgegory Id Not Found !!"));
       product.setCategory(category);
        Product saveProduct = productRepository.save(product);


        return mapper.map(saveProduct , ProductDto.class);






    }

    @Override
    public PageableResponse<ProductDto> getAllOfCategory(String categoryId , int pageNumber , int pageSize , String sortBy , String sortDir) {
        Sort sort =  (sortDir.equalsIgnoreCase("desc") ) ? (Sort.by(sortBy)) : (Sort.by(sortBy).ascending());

        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category Not Found"));
        Pageable pageable = PageRequest.of(pageNumber , pageSize ,sort);
        Page<Product> page = productRepository.findByCategory(category , pageable);
        return Helper.getPageableResponse(page,ProductDto.class);



    }
}
//For any Query Contact : akashguptaworks@gmail.com or skyrisegupta@gmail.com