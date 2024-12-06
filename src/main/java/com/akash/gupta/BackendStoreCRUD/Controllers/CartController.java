package com.akash.gupta.BackendStoreCRUD.Controllers;

import com.akash.gupta.BackendStoreCRUD.dtos.AddItemToCartRequest;
import com.akash.gupta.BackendStoreCRUD.dtos.ApiResponseMessage;
import com.akash.gupta.BackendStoreCRUD.dtos.CartDto;
import com.akash.gupta.BackendStoreCRUD.services.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
@Tag(name = "Cart Controller", description = "REST APIs related to Perform Cart operations")
@SecurityRequirement(name="scheme1")

public class CartController {

    @Autowired
    private CartService cartService;
    //add items to the Cart

    Logger logger = LoggerFactory.getLogger(CartController.class);

    @PreAuthorize("hasRole('NORMAL')")
    @PostMapping("/{userId}")
    @Operation(summary = "Create User's Cart", description = "Create a new cart for a user", tags = {"Cart Controller"})

    public ResponseEntity<CartDto> addItemToCart(@PathVariable String userId, @RequestBody AddItemToCartRequest request) {
        CartDto cartDto = cartService.addItemToCart(userId, request);
        logger.info("Returning Response is{}", cartDto);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);


    }

    //Delete items from the CART
    @PreAuthorize("hasRole('NORMAL')")
    @DeleteMapping("/{userId}/item/{itemId}")
    @Operation(summary = "Remove Item from User's Cart", description = "Remove an item from the user's cart", tags = {"Cart Controller"})

    public ResponseEntity<ApiResponseMessage> removeiItemFromCart(@PathVariable int itemId, @PathVariable String userId) {

        cartService.removeItemsFromCart(userId, itemId);

        ApiResponseMessage response = ApiResponseMessage.builder()
                .message("Item is Removed from the Cart !!")
                .success(true).build();

        return new ResponseEntity<>(response, HttpStatus.OK);


    }

    //clear the cart
    @PreAuthorize("hasRole('NORMAL')")
    @DeleteMapping("/{userId}")
    @Operation(summary = "Delete User's Cart", description = "Delete the cart for a specific user", tags = {"Cart Controller"})

    public ResponseEntity<ApiResponseMessage> removeiItemFromCart(@PathVariable String userId) {

        cartService.clearCart(userId);
        ApiResponseMessage response = ApiResponseMessage.builder()
                .message("Cart cleared ( !!")
                .success(true).build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //Get Cart of the User by userid
    @PreAuthorize("hasRole('NORMAL')")
    @GetMapping("/{userId}")
    @Operation(summary = "Retrieve User's Cart", description = "Retrieve the cart for a specific user", tags = {"Cart Controller"})

    public ResponseEntity<CartDto> getCart(@PathVariable String userId) {
        CartDto cartDto = cartService.getCartByUser(userId);

        return new ResponseEntity<>(cartDto, HttpStatus.OK);


    }


}
//  For any Query Contact : akashguptaworks@gmail.com or skyrisegupta@gmail.com
