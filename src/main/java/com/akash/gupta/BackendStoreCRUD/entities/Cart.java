package com.akash.gupta.BackendStoreCRUD.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cart")
public class Cart {

    @Id
    private String cartId;
    private Date createdAt;

    @OneToOne
    private User user;
    //mampping cart items
    //if cart remove item also got removed see User.java through CascadeType.All
    //@OneToMany(mappedBy = "cart" ,cascade = CascadeType.ALL ,fetch =  FetchType.EAGER, orphanRemoval = true)
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<CartItem> items = new ArrayList<>();

}

//  For any Query Contact : akashguptaworks@gmail.com or skyrisegupta@gmail.com
