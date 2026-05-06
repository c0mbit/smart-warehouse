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

    @Transactional // Гарантирует целостность базы данных
    public WarehouseOrder createOrder(CreateOrderRequest request) {
        // 1. Создаем шапку заказа
        WarehouseOrder order = new WarehouseOrder();
        order.setOrderNumber(request.getOrderNumber());
        order.setStatus("NEW");
        WarehouseOrder savedOrder = orderRepository.save(order);

        // 2. Проходимся по списку товаров и привязываем их к заказу
        for (OrderItemDto itemDto : request.getItems()) {
            Product product = productRepository.findById(itemDto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Товар с ID " + itemDto.getProductId() + " не найден!"));

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(savedOrder);
            orderItem.setProduct(product);
            orderItem.setQuantity(itemDto.getQuantity());

            orderItemRepository.save(orderItem);
        }

        return savedOrder;
    }
}