package com.rsvier.workshop.controllers.artikel;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/artikel")
public class ArtikelMenuController {
    
    @GetMapping
    public String getFormulier() {
        return "artikel";
    }
}
