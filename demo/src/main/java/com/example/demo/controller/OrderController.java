package com.example.demo.controller;

import com.example.demo.dto.CreateOrderRequest;
import com.example.demo.model.WarehouseOrder;
import com.example.demo.service.OrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public WarehouseOrder createOrder(@RequestBody CreateOrderRequest request) {
        return orderService.createOrder(request);
    }

    @DeleteMapping("/reset")
    public void deleteAllOrders() {
        orderService.deleteAllOrders();
    }
}