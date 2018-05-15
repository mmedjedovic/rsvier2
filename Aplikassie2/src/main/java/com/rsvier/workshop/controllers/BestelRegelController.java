package com.rsvier.workshop.controllers;

import com.rsvier.workshop.dao.ArtikelRepository;
import com.rsvier.workshop.dao.BestelRegelRepository;
import com.rsvier.workshop.dao.BestellingRepository;
import com.rsvier.workshop.dao.PersoonRepository;
import com.rsvier.workshop.domein.Artikel;
import com.rsvier.workshop.domein.BestelRegel;
import com.rsvier.workshop.domein.Bestelling;
import com.rsvier.workshop.domein.Persoon;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
            @ModelAttribute Bestelling bestelling, @ModelAttribute Persoon klant, Model model, 
            @RequestParam(value="id", required=false) Long id) {
        Set<BestelRegel> bestelregels = new HashSet();
        
        if (id == null) {
            Iterable<Persoon> klantIterable = persoonRepository.findAll();
            List<Persoon> personen = new ArrayList();
            klantIterable.forEach(personen::add);

            klant = personen.get(0);
            
            LocalDate bestelDatum = LocalDate.now();
            
            LocalDateTime currentTime = LocalDateTime.now();

            Long tempfactuurnummer = (Long.valueOf(currentTime.getNano()));
            
            bestelling.setBestelDatum(bestelDatum);
            bestelling.setKlant(klant);
            bestelling.setBestelregels(bestelregels);
            bestelling.setTotaalprijs(BigDecimal.ZERO);
            bestelling.setFactuurnummer(tempfactuurnummer);
            bestelling.setStatus(Bestelling.Status.OPEN);
            
            bestellingRepository.save(bestelling);
            
            //super omslachtige manier om een factuurnummer te genereren
            bestelling = bestellingRepository.findByFactuurnummer(tempfactuurnummer);
            String factuurstring = String.valueOf(bestelDatum.getYear() + String.valueOf(bestelling.getId()));
            Long factuurnummer = Long.valueOf(factuurstring);
            bestelling.setFactuurnummer(factuurnummer);
            bestellingRepository.save(bestelling);
        }
        
        else {
            Optional bestellingOptional = bestellingRepository.findById(id);
            bestelling = (Bestelling) bestellingOptional.get();
            bestelregels = bestelling.getBestelregels();
        }
        
        model.addAttribute(bestelling);
        model.addAttribute(bestelregels);
        return "bestellingformulier";
    }
    
    @PostMapping
    public ModelAndView bestelRegelToegevoegen (BestelRegel bestelregel, 
            @RequestParam(value="id", required=true) Long id, @ModelAttribute Bestelling bestelling) {
        Optional bestellingOptional = bestellingRepository.findById(id);
        bestelling = (Bestelling) bestellingOptional.get();
        Set<BestelRegel> bestelRegels = bestelling.getBestelregels();
        bestelRegels.add(bestelregel);
        bestelling.setBestelregels(bestelRegels);
        bestellingRepository.save(bestelling);
        ModelAndView modelAndView = new ModelAndView("redirect:/bestelling/add?id=" + String.valueOf(id));
        return modelAndView;
    }
    
    @GetMapping(value="/klant")
    public String klantToevoegen(@ModelAttribute BestelRegel bestelregel, @ModelAttribute Artikel artikel, 
            @RequestParam(value="id", required=true) Long id, Model model) {
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
            @RequestParam(value="id", required=true) Long id, Model model) {
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
