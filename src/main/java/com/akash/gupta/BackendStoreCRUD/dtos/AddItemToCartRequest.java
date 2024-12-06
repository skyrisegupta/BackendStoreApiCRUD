package com.akash.gupta.BackendStoreCRUD.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddItemToCartRequest {


    private  String productId;

    private  int quantity;


}

//  For any Query Contact : akashguptaworks@gmail.com or skyrisegupta@gmail.com
