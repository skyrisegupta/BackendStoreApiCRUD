package com.akash.gupta.BackendStoreCRUD.dtos;

import com.akash.gupta.BackendStoreCRUD.entities.Product;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemDto {
    private int cartItemId;
    private Product product;
    private int quantity;
    private int totalPrice;


}
//  For any Query Contact : akashguptaworks@gmail.com or skyrisegupta@gmail.com
