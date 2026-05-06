package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {

    private final ProductRepository productRepository;

    // Внедрение зависимости через конструктор (лучшая практика)
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Получить список всех товаров
    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Создать новый товар
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }
}