package com.example.demo.service;

import com.example.demo.dto.ReceiveGoodsRequest;
import com.example.demo.model.Inventory;
import com.example.demo.model.Location;
import com.example.demo.model.Product;
import com.example.demo.repository.InventoryRepository;
import com.example.demo.repository.LocationRepository;
import com.example.demo.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service // Говорим Spring, что это класс с бизнес-логикой
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;
    private final LocationRepository locationRepository;

    public InventoryService(InventoryRepository inventoryRepository, ProductRepository productRepository, LocationRepository locationRepository) {
        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
        this.locationRepository = locationRepository;
    }

    // Метод приемки товара на склад
    public Inventory receiveGoods(ReceiveGoodsRequest request) {
        // 1. Ищем товар в базе
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Товар не найден!"));

        // 2. Ищем ячейку в базе
        Location location = locationRepository.findById(request.getLocationId())
                .orElseThrow(() -> new RuntimeException("Ячейка не найдена!"));

        // 3. Создаем запись об остатках (связываем их)
        Inventory inventory = new Inventory();
        inventory.setProduct(product);
        inventory.setLocation(location);
        inventory.setQuantity(request.getQuantity());

        // 4. Сохраняем в базу
        return inventoryRepository.save(inventory);
    }
}