package com.example.demo.controller;

import com.example.demo.dto.ReceiveGoodsRequest;
import com.example.demo.model.Inventory;
import com.example.demo.service.InventoryService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
@CrossOrigin(origins = "*")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping("/receive")
    public Inventory receiveGoods(@RequestBody ReceiveGoodsRequest request) {
        return inventoryService.receiveGoods(request);
    }
}