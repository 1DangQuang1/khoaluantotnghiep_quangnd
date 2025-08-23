package com.example.restapi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class RestApiApplication {
    static {
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "debug");
        System.setProperty("org.slf4j.simpleLogger.log.com.example.restapi.RestApiApplication", "debug");
    }
    public static void main(String[] args) {
        SpringApplication.run(RestApiApplication.class, args);
    }
}