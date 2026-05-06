package com.example.demo.config;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    private final ProductRepository productRepository;

    public DataSeeder(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Проверяем, пустая ли база. Если товаров меньше 5, добавляем наш каталог.
        if (productRepository.count() < 5) {
            System.out.println("Database Initialization: Adding Starter Products...");

            Product p1 = createProduct("BRK-PADS-F", "Brake Pads (Front)", 1.5, "Brakes");
            Product p2 = createProduct("BRK-DISC-F", "Brake Discs (Front)", 5.0, "Brakes");
            Product p3 = createProduct("SPARK-NGK", "NGK Spark Plugs (Set of 4)", 0.2, "Ignition");
            Product p4 = createProduct("BATT-VARTA", "Varta Car Battery 60Ah", 15.0, "Electrical");
            Product p5 = createProduct("WIPER-BOSCH", "Bosch Wiper Blades", 0.3, "Accessories");
            Product p6 = createProduct("ANTIFREEZE-G12", "Antifreeze G12 (5L)", 5.5, "Fluids");
            Product p7 = createProduct("BULB-H7", "H7 Headlight Bulb", 0.05, "Electrical");
            Product p8 = createProduct("AIR-FIL", "Engine Air Filter", 0.4, "Filters");
            Product p9 = createProduct("CAB-FIL", "Cabin Air Filter", 0.2, "Filters");
            Product p10 = createProduct("TRANS-FLUID", "Transmission Fluid (1L)", 1.0, "Fluids");
            Product p11 = createProduct("TIMING-BELT", "Timing Belt Kit", 1.2, "Engine");
            Product p12 = createProduct("WATER-PUMP", "Water Pump", 2.0, "Engine");
            Product p13 = createProduct("OIL-10W40", "Motor Oil 10W-40 (5L)", 5.0, "Fluids");

            productRepository.saveAll(List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13));
            System.out.println("Products added successfully!");
        }
    }

    private Product createProduct(String sku, String name, Double weight, String category) {
        Product product = new Product();
        product.setSku(sku);
        product.setName(name);
        product.setWeight(weight);
        product.setCategory(category);
        return product;
    }
}