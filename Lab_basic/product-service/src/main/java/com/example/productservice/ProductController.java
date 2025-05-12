package com.example.productservice;

import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    private Map<Long, Product> productMap = new HashMap<>();

    @PostConstruct
    public void setupProducts() {
        Product p1 = new Product(1L, "Laptop", "High performance laptop", 999.99);
        Product p2 = new Product(2L, "Phone", "Smartphone with great camera", 699.99);
        productMap.put(p1.getId(), p1);
        productMap.put(p2.getId(), p2);
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return new ArrayList<>(productMap.values());
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Long id) {
        return productMap.get(id);
    }

    @GetMapping("/stats")
    public Map<String, Object> getProductStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalProducts", productMap.size());
        
        OptionalDouble avgPrice = productMap.values().stream()
                .mapToDouble(Product::getPrice)
                .average();
        stats.put("averagePrice", avgPrice.orElse(0.0));
        
        Map<String, Double> priceRange = new HashMap<>();
        OptionalDouble minPrice = productMap.values().stream()
                .mapToDouble(Product::getPrice)
                .min();
        OptionalDouble maxPrice = productMap.values().stream()
                .mapToDouble(Product::getPrice)
                .max();
        
        priceRange.put("min", minPrice.orElse(0.0));
        priceRange.put("max", maxPrice.orElse(0.0));
        stats.put("priceRange", priceRange);
        
        return stats;
    }
}
