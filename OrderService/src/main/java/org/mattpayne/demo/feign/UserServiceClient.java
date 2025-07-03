package org.mattpayne.demo.feign;

import org.mattpayne.demo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;

@FeignClient( name = "UserServiceClient", url = "http://localhost:8081", configuration = FeignRetryConfig.class )
public interface UserServiceClient {
    @GetMapping("/users/{id}")
    User getUser(@PathVariable("id") String id);

    @GetMapping("/users")
    Set<User> getUsers();
}


