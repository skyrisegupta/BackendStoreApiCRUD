package com.akash.gupta.BackendStoreCRUD.services.impl;

import com.akash.gupta.BackendStoreCRUD.dtos.AddItemToCartRequest;
import com.akash.gupta.BackendStoreCRUD.dtos.CartDto;
import com.akash.gupta.BackendStoreCRUD.entities.Cart;
import com.akash.gupta.BackendStoreCRUD.entities.CartItem;
import com.akash.gupta.BackendStoreCRUD.entities.Product;
import com.akash.gupta.BackendStoreCRUD.entities.User;
import com.akash.gupta.BackendStoreCRUD.exceptions.BadApiRequestException;
import com.akash.gupta.BackendStoreCRUD.exceptions.ResourceNotFoundException;
import com.akash.gupta.BackendStoreCRUD.repositories.CartItemRepository;
import com.akash.gupta.BackendStoreCRUD.repositories.CartRepository;
import com.akash.gupta.BackendStoreCRUD.repositories.ProductRepository;
import com.akash.gupta.BackendStoreCRUD.repositories.UserRepository;
import com.akash.gupta.BackendStoreCRUD.services.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public CartDto addItemToCart(String userId, AddItemToCartRequest request) {
        int quantity = request.getQuantity();
        String productId = request.getProductId();

        if (quantity <= 0) {

            throw new BadApiRequestException("Requested quantity is not valid");
        }
        //fetch the Product
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product id is not found in the database"));

        //fetch the user from DB
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("USER is not in the Database first create it"));


        Cart cart = null;
        try {
            // fetching the Car
           // if cart is already exist( IN DB)then it is saved in this variable
            // (also the data will stored in this cart instance)

            cart = cartRepository.findByUser(user).get();

        } catch (Exception e) {

            //but if not we'll create a  New cart
            cart = new Cart();
            cart.setCartId(UUID.randomUUID().toString());
            cart.setCreatedAt(new Date());
        }


//perform cart Operations
//if cart items already present then update

        AtomicReference<Boolean> updated = new AtomicReference<>(false);
        List<CartItem> items = cart.getItems();
        items = items.stream().map(
                item -> {
                    if (item.getProduct().getProductId().equals(productId)) {
                        //item already present in Cart
                        item.setQuantity(quantity);
                        item.setTotalPrice(quantity * product.getDiscountedPrice());
                        System.out.println("111111111111111111111111111");
                        updated.set(true);
                    }
                    return item;
                }).collect(Collectors.toList());

        //create items

        if (!updated.get()) {
            CartItem cartItem = CartItem.builder()
                    .quantity(quantity)
                    .totalPrice(quantity * product.getDiscountedPrice())
                    .cart(cart)
                    .product(product)
                    .build();
            cart.getItems().add(cartItem);
        }

        cart.setUser(user);
        Cart updatedCart = cartRepository.save(cart);
        return mapper.map(updatedCart, CartDto.class);
    }

    @Override
    public void clearCart(String userId) {
        //fetch the user from db
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Cart of Given User NotFound"));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("Cart not exist in Database (Exception"));

        cart.getItems().clear();
        cartRepository.save(cart);


    }

    @Override
    public void removeItemsFromCart(String userId, int cartItem) {
        //Conditions
        CartItem cartItem1 = cartItemRepository.findById(cartItem).orElseThrow(() -> new ResourceNotFoundException("cart item not fund in db !!"));
        cartItemRepository.delete(cartItem1);

    }

    @Override
    public CartDto getCartByUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Cart of Given User NotFound"));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("Cart not exist in Database (Exception"));


        return mapper.map(cart, CartDto.class);


    }


}
//For any Query Contact : akashguptaworks@gmail.com or skyrisegupta@gmail.com