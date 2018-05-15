package com.rsvier.workshop.controllers;

import com.rsvier.workshop.dao.ArtikelRepository;
import com.rsvier.workshop.dao.BestelRegelRepository;
import com.rsvier.workshop.dao.BestellingRepository;
import com.rsvier.workshop.dao.PersoonRepository;
import com.rsvier.workshop.domein.Artikel;
import com.rsvier.workshop.domein.BestelRegel;
import com.rsvier.workshop.domein.Bestelling;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/bestelling/add")
public class BestelRegelController {
    
    @Autowired
    private BestellingRepository bestellingRepository;
        
    @Autowired
    private BestelRegelRepository bestelRegelRepository;
    
    @Autowired 
    private PersoonRepository persoonRepository;
    
    @Autowired
    private ArtikelRepository artikelRepository;
    
    @GetMapping
    public String bestellingToevoegformulier(@ModelAttribute BestelRegel bestelRegel, 
            Model model, @RequestParam(value="id", required=false) Long id) {
        List<BestelRegel> bestelregels = new ArrayList();
        Bestelling bestelling;
        
        if (id != null) {
            Optional bestellingOptional = bestellingRepository.findById(id);
            bestelling = (Bestelling) bestellingOptional.get();
        }
        
        else {
            bestelling = null;
        }
        
        model.addAttribute(bestelling);
        model.addAttribute(bestelregels);
        return "bestellingformulier";
    }
    
    @PostMapping
    public ModelAndView bestelRegelToegevoegen (BestelRegel bestelregel) {
        //TODO
        ModelAndView modelAndView = new ModelAndView("redirect:/bestelling/add");
        return modelAndView;
    }
    
    @GetMapping(value="/klant")
    public String klantToevoegen(@ModelAttribute BestelRegel bestelregel, @ModelAttribute Artikel artikel, 
            @RequestParam(value="id", required=true) Long id) {
        return "kiesklant";
    }
    
    @PostMapping(value="/klant")
    public ModelAndView klantToevoegevoegd(BestelRegel bestelregel) {
        //TODO
        ModelAndView modelAndView = new ModelAndView("redirect:/bestelling/add");
        return modelAndView;
    }
    
    @GetMapping(value="/nieuweregel")
    public String bestelRegelToevoegen(@ModelAttribute BestelRegel bestelregel, @ModelAttribute Artikel artikel,
            @RequestParam(value="id", required=true) Long id) {
        return "bestelregelformulier";
    }
    
    @PostMapping(value="/nieuweregel")
    public ModelAndView bestelRegelToevoegevoegd(BestelRegel bestelregel) {
        //TODO
        ModelAndView modelAndView = new ModelAndView("redirect:/bestelling/add");
        return modelAndView;
    }

    @GetMapping(value="/edit")
    public ModelAndView wijzigBestelRegel(@RequestParam(value="id", required=true) Long id) {
        //TODO
        ModelAndView modelAndView = new ModelAndView("bestellingdelete");
        return modelAndView;
    }
    
    @PostMapping(value="/edit")
    public ModelAndView bestelRegelGewijzigd(BestelRegel bestelregel) {
        //TODO
        ModelAndView modelAndView = new ModelAndView("redirect:/bestelling/add");
        return modelAndView;
    }

    @GetMapping(value="/delete")
    public ModelAndView verwijderBevestiging(@RequestParam(value="id", required=true) Long id) {
        //TODO
        ModelAndView modelAndView = new ModelAndView("bestellingdelete");
        return modelAndView;
    }

    @PostMapping(value="/delete")
    public ModelAndView bestelRegelVerwijderd(BestelRegel bestelregel) {
        //TODO
        ModelAndView modelAndView = new ModelAndView("redirect:/bestelling/add");
        return modelAndView;
    }
}
