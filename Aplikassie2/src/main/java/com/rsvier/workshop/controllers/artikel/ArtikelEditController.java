package com.rsvier.workshop.controllers.artikel;

import com.rsvier.workshop.dao.ArtikelRepository;
import com.rsvier.workshop.domein.Artikel;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/artikeledit")
public class ArtikelEditController {
    CrudRepository repository;
	
    @Autowired
    public ArtikelEditController(ArtikelRepository artikelRepository) {
        repository = artikelRepository;
    }
    
    @ModelAttribute("artikel")
    public Artikel artikel() {
        Optional artikelOptional = repository.findById(1L);
        Artikel artikel = (Artikel) artikelOptional.get();
        return artikel;
    }
    
    @PostMapping
    public String getFormulier(Model model) {
        //model.addAttribute("artikel", artikel);
        return "artikeledit";
    }
    
    
}
