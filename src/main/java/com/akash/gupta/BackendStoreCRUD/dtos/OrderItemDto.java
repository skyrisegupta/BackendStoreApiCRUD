package com.akash.gupta.BackendStoreCRUD.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderItemDto {


    private int orderItemid;

    private int quantity;

    private  int totalPrice;


    private ProductDto product;


//    private OrderDto order;
}

//  For any Query Contact : akashguptaworks@gmail.com or skyrisegupta@gmail.com
