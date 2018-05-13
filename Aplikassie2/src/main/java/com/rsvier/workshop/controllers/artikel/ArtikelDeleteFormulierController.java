package com.rsvier.workshop.controllers.artikel;

import com.rsvier.workshop.dao.ArtikelRepository;
import com.rsvier.workshop.domein.Artikel;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/artikeldeleteformulier")
public class ArtikelDeleteFormulierController {
    ArtikelRepository repository;
	
    @Autowired
    public ArtikelDeleteFormulierController(ArtikelRepository artikelRepository) {
        repository = artikelRepository;
    }
    
    @GetMapping
    public String getFormulier(Model model) {
        model.addAttribute("artikel", new Artikel());
        return "artikeldeleteformulier";
    }
    
    @ModelAttribute("artikelen")
    public List<Artikel> artikelen() {
        List<Artikel> artikelen = repository.findActief();
        return artikelen;
    }
    
    
}
