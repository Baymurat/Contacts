package com.itechart.contacts.web;

import com.itechart.properties.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * Created by Admin on 11.09.2018
 */
@SpringBootApplication
@EnableConfigurationProperties({FileStorageProperties.class})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}