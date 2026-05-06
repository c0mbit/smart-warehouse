package com.example.demo.repository;

import com.example.demo.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    // Найти все остатки конкретного товара
    List<Inventory> findByProductId(Long productId);
}