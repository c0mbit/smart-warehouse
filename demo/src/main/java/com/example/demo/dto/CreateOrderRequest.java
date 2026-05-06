package com.example.demo.dto;

import lombok.Data;
import java.util.List;

@Data
public class CreateOrderRequest {
    private String orderNumber;
    private List<OrderItemDto> items;
}