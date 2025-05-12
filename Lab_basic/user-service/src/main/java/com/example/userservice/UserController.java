package com.example.userservice;

import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;

@RestController
@RequestMapping("/users")
public class UserController {
    
    private Map<Long, User> userMap = new HashMap<>();

    @PostConstruct
    public void setupUsers() {
        User u1 = new User(1L, "john.doe", "john@example.com", "John Doe");
        User u2 = new User(2L, "jane.smith", "jane@example.com", "Jane Smith");
        userMap.put(u1.getId(), u1);
        userMap.put(u2.getId(), u2);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return new ArrayList<>(userMap.values());
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userMap.get(id);
    }

    @GetMapping("/stats")
    public Map<String, Object> getUserStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalUsers", userMap.size());
        stats.put("usernames", userMap.values().stream()
                .map(User::getUsername)
                .collect(Collectors.toList()));
        return stats;
    }
}
