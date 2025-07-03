package org.mattpayne.demo.feign;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateOrderRequest {
    private int id;
    private String userId;
    private String productName;
    private double amount;
}
