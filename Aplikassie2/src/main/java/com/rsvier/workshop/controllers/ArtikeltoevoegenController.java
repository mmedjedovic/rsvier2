package com.rsvier.workshop.controllers;

import com.rsvier.workshop.domein.Artikel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/artikeltoevoegen")
public class ArtikeltoevoegenController {
    
    @GetMapping
    public String getFormulier(Model model) {
        model.addAttribute("artikel", new Artikel(true));
        return "artikeltoevoegen";
    }
}
