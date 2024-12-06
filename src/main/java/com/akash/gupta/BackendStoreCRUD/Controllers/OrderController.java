package com.akash.gupta.BackendStoreCRUD.Controllers;

import com.akash.gupta.BackendStoreCRUD.dtos.*;
import com.akash.gupta.BackendStoreCRUD.services.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@Tag(name = "Order Controller", description = "REST APIs related to perform Order Operations")
@SecurityRequirement(name="scheme1")
public class OrderController {

    @Autowired
    private OrderService orderService;

    //create
    @PreAuthorize("hasRole('NORMAL')")
    @PostMapping
    @Operation(summary = "Create Order", description = "Create a new order", tags = {"Order Controller"})
    public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody
                                                CreateOrderRequest request) {

        OrderDto order = orderService.createOrder(request);
        return new ResponseEntity<>(order, HttpStatus.CREATED);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{orderId}")
    @Operation(summary = "Remove Order Status", description = "Remove order items by  orderId ", tags = {"Order Controller"})
    public ResponseEntity<ApiResponseMessage> removeOrder(@PathVariable String orderId) {

        orderService.removeOrder(orderId);
        ApiResponseMessage responseMessage = ApiResponseMessage.builder().status(HttpStatus.OK).message("Order is Removed ").success(true).build();
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);


    }


    //get orders of the users
    @PreAuthorize("hasRole('NORMAL')")
    @GetMapping("/users/{userId}")
    @Operation(summary = "Get Ordered items", description = "Get ordered items of a user by userId", tags = {"Order Controller"})
    public ResponseEntity<List<OrderDto>> getOrdersOfUser(@PathVariable String userId) {

        List<OrderDto> orderOfUser = orderService.getOrdersOfUser(userId);
        return new ResponseEntity<>(orderOfUser, HttpStatus.OK);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    @Operation(summary = "Get All Orders", description = "Get all orders from the database", tags = {"Order Controller"})
    public ResponseEntity<PageableResponse<OrderDto>> getOrders(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "orderedDate", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "desc", required = false) String sortDir
    ) {
        // admin can use this api to use all orders . for getting the particular order informatioj admin can use this API
        PageableResponse<OrderDto> orderOfUser = orderService.getOrders(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(orderOfUser, HttpStatus.OK);

    }

    @PreAuthorize("hasRole('NORMAL')")
    @PutMapping("/{orderId}")
    @Operation(summary = "Update Order Status", description = "Update order status by order ID", tags = {"Order Controller"})
    public ResponseEntity<OrderDto> updateOrder(
            @PathVariable("orderId") String orderId,
            @RequestBody OrderUpdateRequest request
    ) {

        OrderDto orderDto = orderService.updateOrder(orderId, request);
        return ResponseEntity.ok(orderDto);


    }


}

//  For any Query Contact : akashguptaworks@gmail.com or skyrisegupta@gmail.com












