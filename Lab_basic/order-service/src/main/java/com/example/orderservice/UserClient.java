package com.example.orderservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.Map;

@FeignClient(name = "user-service")
public interface UserClient {
    
    @GetMapping("/users/{id}")
    User getUser(@PathVariable("id") Long id);

    @GetMapping("/users/stats")
    Map<String, Object> getUserStats();
}
