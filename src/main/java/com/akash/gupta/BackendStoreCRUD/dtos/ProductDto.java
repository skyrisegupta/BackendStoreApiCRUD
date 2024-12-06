package com.akash.gupta.BackendStoreCRUD.dtos;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
//@ToString

public class ProductDto{
    private String productImageName;
    private String productId;
    private String title;
    private CategoryDto category;
    private String description;
    private int price;
    private int discountedPrice;
    private int quantity;
    private Date addedDate;
    private boolean live;
    private boolean stock;

}
//  For any Query Contact : akashguptaworks@gmail.com or skyrisegupta@gmail.com
