package org.mattpayne.demo.feign;

import org.mattpayne.demo.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootApplication
@RestController
public class UserServiceApplication {
    private final AtomicInteger requestCount = new AtomicInteger(0);
    private final Set<User> users = new HashSet<>();

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    @GetMapping("/users")
    public ResponseEntity<Set<User>> getUsers() {
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable String id) {
        int count = requestCount.incrementAndGet();
        // Simulate intermittent failures for demo
        if (count % 3 != 0) {
            throw new RuntimeException("Service temporarily unavailable");
        }
        User user = new User(id, "John Doe", "john@example.com");
        users.add(user); // ugly but quick way to add user for demo purposes
        return ResponseEntity.ok(user);
    }
}
