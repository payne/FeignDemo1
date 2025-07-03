package org.mattpayne.demo.feign;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Order {
    String id;
    String userId;
    String userName;
    String product;
    double amount;
}
