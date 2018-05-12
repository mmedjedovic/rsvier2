package com.rsvier.workshop.controllers;

import com.rsvier.workshop.dao.ArtikelRepository;
import com.rsvier.workshop.domein.Artikel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/artikeltoevoegen")
public class ArtikeltoevoegenController {
    
    CrudRepository repository;
	
    @Autowired
    public ArtikeltoevoegenController(ArtikelRepository artikelRepository) {
            repository = artikelRepository;
    }
    
    @GetMapping
    public String getFormulier(Model model) {
        model.addAttribute("artikel", new Artikel(true));
        return "artikeltoevoegen";
    }
    
    @PostMapping
    public Artikel registerArtikel(@ModelAttribute Artikel artikel) {
            artikel.setActief(true);
            repository.save(artikel);
            return artikel;
    }
}
