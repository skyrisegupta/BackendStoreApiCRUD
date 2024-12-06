package com.akash.gupta.BackendStoreCRUD.dtos;

import com.akash.gupta.BackendStoreCRUD.entities.CartItem;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDto{
    private String cartId;
    private Date createdAt;
    private UserDto user;
    //mapping cart items
    private List<CartItem> items = new ArrayList<>();


}
//  For any Query Contact : akashguptaworks@gmail.com or skyrisegupta@gmail.com

