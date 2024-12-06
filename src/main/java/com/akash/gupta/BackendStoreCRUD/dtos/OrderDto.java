package com.akash.gupta.BackendStoreCRUD.dtos;

import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class OrderDto{

    private String orderId;
    //pending ,Dispatched , Delivered
    //enum
    private String orderStatus="PENDING";
    //Not paid , paid
    //enum
    // boolean - false => NotPaid || true => Paid
    private String paymentStatus="NOTPAID";
    private int orderAmount ;
    private String billingAddress;
    private String billingPhone;
    private String billingName;
    private Date orderedDate = new Date();
    private Date deliveredDate;


    //user
    private UserDto user;

    //if you want to send the information
    // Of user  if you don't want the information
    // comment it

    private List<OrderItemDto> orderItems = new ArrayList<>();

}

//  For any Query Contact : akashguptaworks@gmail.com or skyrisegupta@gmail.com
