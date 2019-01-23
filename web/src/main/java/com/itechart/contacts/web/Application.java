package com.itechart.contacts.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

/**
 * Created by Admin on 11.09.2018
 */
@SpringBootApplication/*(exclude = {ErrorMvcAutoConfiguration.class})*/
@EnableJms
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);

    }
}