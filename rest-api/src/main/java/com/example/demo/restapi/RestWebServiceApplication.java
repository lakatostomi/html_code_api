package com.example.demo.restapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = "com.example.demo")
public class RestWebServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestWebServiceApplication.class, args);
    }
}
