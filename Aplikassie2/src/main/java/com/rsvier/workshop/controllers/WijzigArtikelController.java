package com.rsvier.workshop.controllers;

import com.rsvier.workshop.dao.ArtikelRepository;
import com.rsvier.workshop.domein.Artikel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/artikelgewijzigd")
public class WijzigArtikelController {
    CrudRepository repository;
	
    @Autowired
    public WijzigArtikelController(ArtikelRepository artikelRepository) {
        repository = artikelRepository;
    }

    @PostMapping
    public Artikel wijzigArtikel(Artikel artikel) {
        repository.save(artikel);
        return artikel;
    }
}
