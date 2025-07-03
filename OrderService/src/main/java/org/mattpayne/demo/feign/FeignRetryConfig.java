package org.mattpayne.demo.feign;

import feign.Logger;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignRetryConfig {
    @Bean
    public Retryer feignRetryer() {
        return new Retryer.Default(1000, // Initial interval (1 second)
                3000, // Max interval (3 seconds)
                3 // Max attempts
        );
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new CustomErrorDecoder();
    }

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
