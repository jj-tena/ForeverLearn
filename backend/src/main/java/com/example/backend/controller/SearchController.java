package com.example.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SearchController {

    @GetMapping("/library")
    public String librayLink(){
        return "library";
    }

    @GetMapping("/library-list")
    public String librayListLink(){
        return "library-list";
    }
}
