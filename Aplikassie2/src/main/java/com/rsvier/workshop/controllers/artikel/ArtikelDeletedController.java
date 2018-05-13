package com.rsvier.workshop.controllers.artikel;

import com.rsvier.workshop.dao.ArtikelRepository;
import com.rsvier.workshop.domein.Artikel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/artikeldeleted")
public class ArtikelDeletedController {
    CrudRepository repository;
	
    @Autowired
    public ArtikelDeletedController(ArtikelRepository artikelRepository) {
        repository = artikelRepository;
    }

    @PostMapping
    public Artikel registerArtikel(Artikel artikel) {
        artikel.setArtikelStatus(Artikel.ArtikelStatus.INACTIEF);
        repository.save(artikel);
        return artikel;
    }
}
