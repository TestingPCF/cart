package com.hcl.cloud.cart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Main class to start the application.
 * @author baghelp
 */
@SpringBootApplication
@EnableDiscoveryClient
public class CartApplication {

    /**
     * main method.
     * @param args Argument array
     */
    public static void main(final String[] args) {
        SpringApplication.run(CartApplication.class, args);
    }

    /**
     * add Method Method.
     */
    public final int printApplication(final int a, final int b) {
        return a + b;
    }

}
