package com.example.demo.service;

import com.example.demo.dto.CreateOrderRequest;
import com.example.demo.dto.OrderItemDto;
import com.example.demo.model.OrderItem;
import com.example.demo.model.Product;
import com.example.demo.model.WarehouseOrder;
import com.example.demo.repository.OrderItemRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.WarehouseOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    private final WarehouseOrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;

    public OrderService(WarehouseOrderRepository orderRepository, OrderItemRepository orderItemRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public WarehouseOrder createOrder(CreateOrderRequest request) {
        WarehouseOrder order = new WarehouseOrder();
        order.setOrderNumber(request.getOrderNumber());
        order.setStatus("NEW");
        WarehouseOrder savedOrder = orderRepository.save(order);

        for (OrderItemDto itemDto : request.getItems()) {
            Product product = productRepository.findById(itemDto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product with ID " + itemDto.getProductId() + " not found!"));

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(savedOrder);
            orderItem.setProduct(product);
            orderItem.setQuantity(itemDto.getQuantity());

            orderItemRepository.save(orderItem);
        }

        return savedOrder;
    }

    public void deleteAllOrders() {
        orderRepository.truncateAllOrders();
    }
}