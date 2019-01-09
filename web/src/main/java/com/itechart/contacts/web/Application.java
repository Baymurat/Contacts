package com.itechart.contacts.web;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;

/**
 * Created by Admin on 11.09.2018
 */
@SpringBootApplication/*(exclude = {ErrorMvcAutoConfiguration.class})*/
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);

    }
}