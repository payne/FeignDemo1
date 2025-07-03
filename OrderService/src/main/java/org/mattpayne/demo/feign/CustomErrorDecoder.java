package org.mattpayne.demo.feign;

import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;

import java.util.Date;

public class CustomErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        // Retry on 5xx server errors and specific 4xx errors
        if (response.status() >= 500 && response.status() <= 599) {
            return new RetryableException(
                    response.status(),
                    "Server error - retrying",
                    response.request().httpMethod(),
                    new Date(),
                    response.request()
            );
        }

        // Retry on 503 Service Unavailable and 429 Too Many Requests
        if (response.status() == 503 || response.status() == 429) {
            return new RetryableException(
                    response.status(),
                    "Service unavailable or rate limited - retrying",
                    response.request().httpMethod(),
                    new Date(),
                    response.request()
            );
        }

        // Don't retry on other errors (4xx client errors)
        return defaultErrorDecoder.decode(methodKey, response);
    }
}