package com.itechart.contacts.web.controller;

import com.itechart.contacts.web.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class TemplateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void indexShouldReturnIndexString() throws Exception {
        mockMvc.perform(get("/index")).andExpect(status().isOk()).andExpect(view().name("index"));
    }

    @Test
    public void addShouldReturnAddString() throws Exception {

        mockMvc.perform(get("/add")).andExpect(status().isOk()).andExpect(view().name("add"));
    }

    @Test
    public void editShouldReturnEditString() throws Exception {
        mockMvc.perform(get("/edit")).andExpect(status().isOk()).andExpect(view().name("edit"));
    }

    @Test
    public void aboutShouldReturnAboutString() throws Exception {
        mockMvc.perform(get("/about")).andExpect(status().isOk()).andExpect(view().name("about"));
    }

    @Test
    public void searchShouldReturnSearchString() throws Exception {
        mockMvc.perform(get("/search")).andExpect(status().isOk()).andExpect(view().name("search"));
    }

    @Test
    public void emailShouldReturnEmailString() throws Exception {
        mockMvc.perform(get("/email")).andExpect(status().isOk()).andExpect(view().name("email"));
    }

    @Test
    public void advancedSearchShouldReturnAdvSearString() throws Exception {
        mockMvc.perform(get("/advanced-search")).andExpect(status().isOk()).andExpect(view().name("advanced-search"));
    }
}