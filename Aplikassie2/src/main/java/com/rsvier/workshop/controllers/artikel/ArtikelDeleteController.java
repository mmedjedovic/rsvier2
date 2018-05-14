package com.rsvier.workshop.controllers.artikel;

import com.rsvier.workshop.dao.ArtikelRepository;
import com.rsvier.workshop.domein.Artikel;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/artikeldelete")
public class ArtikelDeleteController {
    CrudRepository repository;
	
    @Autowired
    public ArtikelDeleteController(ArtikelRepository artikelRepository) {
        repository = artikelRepository;
    }

    @GetMapping
    public String getFormulier(Artikel artikel, Model model) {
        System.out.println("ARTIKEL: " + artikel.getId() + artikel.getNaam());
        Optional artikelOptional = repository.findById(artikel.getId());
        Artikel artikelcompleet = (Artikel) artikelOptional.get();
        model.addAttribute("artikel", artikelcompleet);
        System.out.println("ARTIKEL: " + artikelcompleet.getId() + artikelcompleet.getNaam());
        return "artikeldelete";
    }
}
