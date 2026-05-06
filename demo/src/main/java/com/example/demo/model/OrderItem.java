package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Связь: Многие позиции принадлежат одному заказу
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    @JsonIgnore // Чтобы при выдаче JSON не было бесконечного зацикливания
    private WarehouseOrder order;

    // Связь: Какая позиция ссылается на какой товар
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer quantity; // Сколько штук этого товара нужно
}