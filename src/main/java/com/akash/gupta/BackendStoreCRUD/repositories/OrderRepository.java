package com.akash.gupta.BackendStoreCRUD.repositories;

import com.akash.gupta.BackendStoreCRUD.entities.Order;
import com.akash.gupta.BackendStoreCRUD.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, String> {

    List<Order> findByUser(User user);


}
