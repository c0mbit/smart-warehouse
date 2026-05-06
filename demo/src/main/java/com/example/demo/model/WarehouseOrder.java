package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "warehouse_orders")
public class WarehouseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String orderNumber; // Уникальный номер заказа (например, "ORD-1001")

    private String status; // Статус (например: "NEW", "PICKING", "SHIPPED")

    private LocalDateTime createdAt; // Время создания

    @PrePersist // Spring сам подставит текущее время перед сохранением в базу
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}