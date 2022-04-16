package com.example.jwttoken.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatementController {

    @GetMapping("getPreDateStatement")
    public String getPreDateStatement() {
        return "here is the statement";
    }
}
