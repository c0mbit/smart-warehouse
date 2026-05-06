package com.example.demo.service;

import com.example.demo.dto.ReceiveGoodsRequest;
import com.example.demo.model.Inventory;
import com.example.demo.model.Location;
import com.example.demo.model.Product;
import com.example.demo.repository.InventoryRepository;
import com.example.demo.repository.LocationRepository;
import com.example.demo.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;
    private final LocationRepository locationRepository;

    public InventoryService(InventoryRepository inventoryRepository, ProductRepository productRepository, LocationRepository locationRepository) {
        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
        this.locationRepository = locationRepository;
    }

    public Inventory receiveGoods(ReceiveGoodsRequest request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found!"));

        Location location = locationRepository.findById(request.getLocationId())
                .orElseThrow(() -> new RuntimeException("Location not found!"));

        Inventory inventory = new Inventory();
        inventory.setProduct(product);
        inventory.setLocation(location);
        inventory.setQuantity(request.getQuantity());

        return inventoryRepository.save(inventory);
    }
}