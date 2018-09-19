package com.itechart.example.Contacts;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Admin on 07.09.2018
 */

@Controller
public class TestGreetingController {

    @GetMapping("/contacts")
    public String greeting(@RequestParam(name = "name", defaultValue = "World!")String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }
}
