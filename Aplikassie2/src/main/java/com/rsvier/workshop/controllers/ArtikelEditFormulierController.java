package com.rsvier.workshop.controllers;

import com.rsvier.workshop.dao.ArtikelRepository;
import com.rsvier.workshop.domein.Artikel;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/artikeleditformulier")
public class ArtikelEditFormulierController {
    ArtikelRepository repository;
	
    @Autowired
    public ArtikelEditFormulierController(ArtikelRepository artikelRepository) {
        repository = artikelRepository;
    }
    
    @GetMapping
    public String getFormulier(Model model) {
        model.addAttribute("artikel", new Artikel());
        return "artikeleditformulier";
    }
    
    @ModelAttribute("artikelen")
    public List<Artikel> artikelen() {
        Iterable<Artikel> artikelenIterable = repository.findAll();
        List<Artikel> artikelen = new ArrayList();
        artikelenIterable.forEach(artikelen::add);
        return artikelen;
    }
    
    @PostMapping
    public String getEditFormulier(Artikel artikel, Model model) {
        model.addAttribute(artikel);
        return "artikeledit";
    }
}
