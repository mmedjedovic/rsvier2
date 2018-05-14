package com.rsvier.workshop.controllers.artikel;

import com.rsvier.workshop.dao.ArtikelRepository;
import com.rsvier.workshop.domein.Artikel;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/artikeledit")
public class ArtikelEditController {
    CrudRepository repository;
	
    @Autowired
    public ArtikelEditController(ArtikelRepository artikelRepository) {
        repository = artikelRepository;
    }
    
    //@ModelAttribute("artikel")
    //public Artikel artikel() {
    //    Optional artikelOptional = repository.findById(1L);
    //    Artikel artikel = (Artikel) artikelOptional.get();
    //    return artikel;
    //}
    
    //@ModelAttribute("artikel")
    //public Artikel artikel(Artikel artikel) {
    //    return artikel;
    //}
    
    @GetMapping
    public String getFormulier(Artikel artikel, Model model) {
        System.out.println("ARTIKEL: " + artikel.getId() + artikel.getNaam());
        Optional artikelOptional = repository.findById(artikel.getId());
        Artikel artikelcompleet = (Artikel) artikelOptional.get();
        model.addAttribute("artikel", artikelcompleet);
        System.out.println("ARTIKEL: " + artikelcompleet.getId() + artikelcompleet.getNaam());
        return "artikeledit";
    }
    
    
}
