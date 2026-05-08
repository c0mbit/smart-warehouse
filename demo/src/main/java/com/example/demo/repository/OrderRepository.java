package com.example.demo.repository;

import com.example.demo.model.WarehouseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface OrderRepository extends JpaRepository<WarehouseOrder, Long> {

    // Добавляем вот этот блок:
    @Modifying
    @Transactional
    @Query(value = "TRUNCATE TABLE order_items, warehouse_orders CASCADE", nativeQuery = true)
    void truncateAllOrders();
}