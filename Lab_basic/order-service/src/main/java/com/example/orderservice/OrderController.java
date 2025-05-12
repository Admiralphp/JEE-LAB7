package com.example.orderservice;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final ProductClient productClient;
    private final UserClient userClient;
    private final Map<Long, Order> orderMap = new HashMap<>();
    private Long nextOrderId = 1L;

    public OrderController(ProductClient productClient, UserClient userClient) {
        this.productClient = productClient;
        this.userClient = userClient;
    }

    @PostMapping
    public Order createOrder(@RequestParam Long productId, @RequestParam Long userId, @RequestParam int quantity) {
        Product product = productClient.getProduct(productId);
        User user = userClient.getUser(userId);

        if (product != null && user != null) {
            double totalPrice = product.getPrice() * quantity;
            Order order = new Order(nextOrderId++, productId, userId, quantity, totalPrice, product, user);
            orderMap.put(order.getId(), order);
            return order;
        }
        return null;
    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable Long id) {
        return orderMap.get(id);
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return new ArrayList<>(orderMap.values());
    }

    @GetMapping("/stats")
    public Map<String, Object> getOrderStats() {
        Map<String, Object> stats = new HashMap<>();
        Map<String, Object> productStats = productClient.getProductStats();
        Map<String, Object> userStats = userClient.getUserStats();

        stats.put("productStats", productStats);
        stats.put("userStats", userStats);
        stats.put("totalOrders", orderMap.size());
        stats.put("totalOrderValue", orderMap.values().stream()
                .mapToDouble(Order::getTotalPrice)
                .sum());

        return stats;
    }
}
