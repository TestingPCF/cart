package com.hcl.cloud.cart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class to start the application.
 * @author baghelp
 */
@SpringBootApplication
public class CartApplication {

    /**
     * main method.
     * @param args Argument array
     */
    public static void main(final String[] args) {
        SpringApplication.run(CartApplication.class, args);
    }

    /**
     * Print Method.
     */
    public final void printApplication() {
        System.out.println("Application started::");
    }

}
