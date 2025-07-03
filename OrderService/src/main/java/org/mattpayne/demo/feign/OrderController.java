package org.mattpayne.demo.feign;

import org.mattpayne.demo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final UserServiceClient userServiceClient;
    private final Logger logger = LoggerFactory.getLogger(OrderController.class);

    public OrderController(UserServiceClient userServiceClient) {
        this.userServiceClient = userServiceClient;
    }

    @GetMapping("users")
    public ResponseEntity<Set<User>> getUsers() {

        return ResponseEntity.ok(userServiceClient.getUsers());
    }

    @PostMapping
    public ResponseEntity<Order>
    createOrder(@RequestBody CreateOrderRequest request) {
        try {
            logger.info("Creating order for user: {}", request.getUserId()); // Call User Service via Feign (with automatic retry)
            User user = userServiceClient.getUser(request.getUserId());
            logger.info("Successfully retrieved user: {}", user.getName());
            Order order = new Order(UUID.randomUUID().toString(), request.getUserId(), user.getName(),
                    request.getProductName(), request.getAmount());
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            logger.error("Failed to create order: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(null);
        }
    }
}

