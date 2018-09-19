package com.itechart.example.Contacts;

import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Admin on 07.09.2018.
 */

@RestController
public class RestGreetingController {

    private final static String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
}
