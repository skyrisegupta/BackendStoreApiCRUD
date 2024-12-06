package com.akash.gupta.BackendStoreCRUD.repositories;

import com.akash.gupta.BackendStoreCRUD.entities.Category;
import com.akash.gupta.BackendStoreCRUD.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,String> {
    //SEARCH
    Page<Product> findByTitleContaining(String subtitle ,Pageable pageable);
    Page<Product> findByLiveTrue(Pageable pageable);

    Page<Product> findByCategory(Category category , Pageable pageable);




    //others requirement we can add here




}



