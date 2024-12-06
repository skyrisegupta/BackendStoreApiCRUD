package com.akash.gupta.BackendStoreCRUD.repositories;

import com.akash.gupta.BackendStoreCRUD.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

}
