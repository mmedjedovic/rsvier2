package com.rsvier.workshop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/klant")
public class KlantMenuController {
    
    @GetMapping
    public String getFormulier() {
        return "klant";
    }
}
