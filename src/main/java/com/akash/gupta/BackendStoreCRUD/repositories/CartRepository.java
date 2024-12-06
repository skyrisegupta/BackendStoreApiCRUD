package com.akash.gupta.BackendStoreCRUD.repositories;

import com.akash.gupta.BackendStoreCRUD.entities.Cart;
import com.akash.gupta.BackendStoreCRUD.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository <Cart, String>{

 Optional<Cart> findByUser(User user);


}
