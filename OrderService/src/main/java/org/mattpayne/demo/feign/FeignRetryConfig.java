package org.mattpayne.demo.feign;


import feign.Retryer;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignRetryConfig {
    /**
     * Configure automatic retry behavior
     * @return Retryer with custom settings
     */
    @Bean
    public Retryer feignRetryer() {
        // period: initial interval between retries (ms)
        // maxPeriod: maximum interval between retries (ms)
        // maxAttempts: maximum number of attempts
        return new Retryer.Default(1000, 3000, 3);
    }

    /**
     * Custom error decoder to determine which errors should trigger retries
     * @return ErrorDecoder
     */
    @Bean
    public ErrorDecoder errorDecoder() {
        return new CustomErrorDecoder();
    }
}
