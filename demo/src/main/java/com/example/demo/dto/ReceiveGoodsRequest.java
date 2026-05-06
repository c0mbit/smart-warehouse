package com.example.demo.dto;

import lombok.Data;

@Data
public class ReceiveGoodsRequest {
    private Long productId;
    private Long locationId;
    private Integer quantity;
}