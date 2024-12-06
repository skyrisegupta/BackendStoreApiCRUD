package com.akash.gupta.BackendStoreCRUD.dtos;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class CreateOrderRequest {
    @NotBlank(message = "cart id is required")
    private String cartId;

    @NotBlank(message = "User id is required")
    private String userId;

//    @NotBlank(message = "Order id is required")
//    private String orderId;
//    @NotBlank(message = "Cart id is Required")
    private String orderStatus="PENDING";
    private String paymentStatus="NOTPAID";

    @NotBlank(message = "Address  is required")
    private String billingAddress;

    @NotBlank(message = "Billing Phone NO is required")
    private String billingPhone;

    @NotBlank(message = "Billing Name is Required")
    private String billingName;




}
//  For any Query Contact : akashguptaworks@gmail.com or skyrisegupta@gmail.com
