package com.itechart.contacts.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by Admin on 11.09.201
 */
@Controller
public class TemplateController {

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/add")
    public String add() {
        return "add";
    }

    @GetMapping("/edit")
    public String edit() {
        return "edit";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/search")
    public String search() {
        return "search";
    }

    @GetMapping("/email")
    public String email() {
        return "email";
    }

    @GetMapping("/advanced-search")
    public String advancedSearch() {
        return "advanced-search";
    }
}
