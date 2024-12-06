package com.akash.gupta.BackendStoreCRUD.services;

import com.akash.gupta.BackendStoreCRUD.dtos.CreateOrderRequest;
import com.akash.gupta.BackendStoreCRUD.dtos.OrderDto;
import com.akash.gupta.BackendStoreCRUD.dtos.OrderUpdateRequest;
import com.akash.gupta.BackendStoreCRUD.dtos.PageableResponse;

import java.util.List;

public interface OrderService{

    //create order
    //

    OrderDto createOrder(CreateOrderRequest orderDto);

 //remove order
 void removeOrder(String orderId);

 //get orders of user
    List<OrderDto> getOrdersOfUser (String userId) ;
    //get orders
    //
  PageableResponse<OrderDto> getOrders(int pageNumber , int  pageSize , String sortBy , String sortDir);
  OrderDto updateOrder(String orderId ,  OrderUpdateRequest request);

    //other methods realted to order



}
