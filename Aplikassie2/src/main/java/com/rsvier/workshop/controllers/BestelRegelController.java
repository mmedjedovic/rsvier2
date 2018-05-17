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
        List<BestelRegel> bestelregels = new ArrayList();
        
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
            bestelregels = bestelRegelRepository.findByBestelling_id(bestelling.getId());
            
        }
            model.addAttribute(bestelling);
            model.addAttribute("bestelregels",bestelregels);
            return "bestellingformulier";
    }
    
    @PostMapping
    public ModelAndView bestelRegelToegevoegen (BestelRegel bestelregel, 
            @RequestParam(value="id", required=true) Long id, Bestelling bestelling) {
        Optional bestellingOptional = bestellingRepository.findById(id);
        bestelling = (Bestelling) bestellingOptional.get();
        Set<BestelRegel> bestelRegels = bestelling.getBestelregels();
        bestelRegels.add(bestelregel);
        bestelling.setBestelregels(bestelRegels);
        bestellingRepository.save(bestelling);
        ModelAndView modelAndView = new ModelAndView("redirect:/bestelling/add?id=" + String.valueOf(id));
        return modelAndView;
    }
    
    @GetMapping(value = "/nieuweregel")
    public ModelAndView bestelRegelToevoegen(@ModelAttribute("bestelregel") BestelRegel bestelregel,
            @RequestParam(value = "id", required = true) Long id) {
        ModelAndView modelAndView = new ModelAndView("bestelregelformulier");

        List<Artikel> artikellijst = artikelRepository.findActief();
        modelAndView.addObject("artikellijst", artikellijst);

        Optional bestellingOptional = bestellingRepository.findById(id);
        Bestelling bestelling = (Bestelling) bestellingOptional.get();

        bestelregel.setBestelling(bestelling);
        modelAndView.addObject("bestelregel", bestelregel);
        modelAndView.addObject("bestelling", bestelling);

        return modelAndView;
    } 
    
    @PostMapping(value="/nieuweregel")
    public ModelAndView bestelRegelToegevoegd(BestelRegel bestelregel, Artikel artikel, Bestelling bestelling) {
        ModelAndView modelAndView = null;
        
        Optional artikelOptional = artikelRepository.findById(artikel.getId());
        artikel = (Artikel) artikelOptional.get();
        
        if (bestelregel.getAantal() > artikel.getVoorraad()) {
            modelAndView = new ModelAndView("redirect:/bestelling/add?id=" + String.valueOf(bestelling.getId()));
            return modelAndView;
            //TODO errorhandling
            
        }
        
        else {
            artikel.setVoorraad(artikel.getVoorraad() - bestelregel.getAantal());
            artikelRepository.save(artikel);

            Optional bestellingOptional = bestellingRepository.findById(bestelling.getId());
            bestelling = (Bestelling) bestellingOptional.get();

            bestelregel.setArtikelPrijs(artikel.getPrijs());
            BigDecimal oudetotaalprijs = bestelling.getTotaalprijs();
            BigDecimal artikelprijs = bestelregel.getArtikelPrijs();
            BigDecimal aantalartikelen = new BigDecimal(bestelregel.getAantal());
            BigDecimal x = aantalartikelen.multiply(artikelprijs);
            BigDecimal nieuwetotaalprijs = oudetotaalprijs.add(x);

            bestelling.setTotaalprijs(nieuwetotaalprijs);
            bestellingRepository.save(bestelling);

            bestelregel.setArtikel(artikel);
            bestelregel.setBestelling(bestelling);
            bestelRegelRepository.save(bestelregel);

            modelAndView = new ModelAndView("redirect:/bestelling/add?id=" + String.valueOf(bestelling.getId()));
            return modelAndView;
        }    
    }

    @GetMapping(value="/edit")
    public ModelAndView wijzigBestelRegel(@RequestParam(value="bestelling_id", required=true) Long id, 
            @RequestParam(value="bestelregel_id", required=true) Long bestelregel_id) {
        
        ModelAndView modelAndView = new ModelAndView("bestelregelaanpassen");
        
        Optional bestellingOptional = bestellingRepository.findById(id);
        Bestelling bestelling = (Bestelling) bestellingOptional.get();
        
        Optional bestelRegelOptional = bestelRegelRepository.findById(bestelregel_id);
        BestelRegel bestelregel = (BestelRegel) bestelRegelOptional.get();
        
        List<Artikel> artikellijst = artikelRepository.findActief();
        
        modelAndView.addObject("bestelling",bestelling);
        modelAndView.addObject("bestelregel",bestelregel);
        modelAndView.addObject("artikellijst",artikellijst);
        return modelAndView;
    }
    
    @PostMapping(value="/edit")
    public ModelAndView bestelRegelGewijzigd(BestelRegel bestelregel) {
        Artikel artikel = bestelregel.getArtikel();
        Bestelling bestelling = bestelregel.getBestelling();
        Optional oudeBestelRegelOptional = bestelRegelRepository.findById(bestelregel.getId());
        BestelRegel oudeBestelRegel = (BestelRegel) oudeBestelRegelOptional.get();
        bestelregel.setId(oudeBestelRegel.getId());
        bestelregel.setArtikelPrijs(artikel.getPrijs());
        
        //Artikelvoorraad aanpassen
        int oudAantal = oudeBestelRegel.getAantal();
        int nieuwAantal = bestelregel.getAantal();
        int verschil = oudAantal - nieuwAantal;
        int artikelVoorraad = artikel.getVoorraad();
        
        if (verschil < 0) {
            int nieuweArtikelVoorraad = artikelVoorraad - Math.abs(verschil);
            
            if (nieuweArtikelVoorraad < 0) {
                //TODO errorhandling
            }
            
            artikel.setVoorraad(nieuweArtikelVoorraad);
            artikelRepository.save(artikel);
        }
        
        else if (verschil > 0) {
            int nieuweArtikelVoorraad = artikelVoorraad + verschil;
            artikel.setVoorraad(nieuweArtikelVoorraad);
            artikelRepository.save(artikel);
            
            if (nieuweArtikelVoorraad > oudAantal + artikelVoorraad) {
                //TODO errorhandling
            }
        }
        
        bestelRegelRepository.save(bestelregel);
        
        //totaalprijs bestelling veranderen
        BigDecimal oudetotaalprijs = bestelling.getTotaalprijs();
        BigDecimal artikelprijs = bestelregel.getArtikelPrijs();
        BigDecimal aantalartikelen = new BigDecimal(bestelregel.getAantal());
        BigDecimal x = aantalartikelen.multiply(artikelprijs);
        BigDecimal nieuwetotaalprijs = oudetotaalprijs.add(x);
        bestelling.setTotaalprijs(nieuwetotaalprijs);
        bestellingRepository.save(bestelling);
        
        ModelAndView modelAndView = new ModelAndView("redirect:/bestelling/add?id=" + String.valueOf(bestelling.getId()));
        return modelAndView;
    }

    @GetMapping(value="/delete")
    public ModelAndView verwijderBevestiging(@RequestParam(value="bestelling_id", required=true) Long id, 
            @RequestParam(value="bestelregel_id", required=true) Long bestelregel_id) {
        Optional bestellingOptional = bestellingRepository.findById(id);
        Bestelling bestelling = (Bestelling) bestellingOptional.get();
        
        Optional bestelRegelOptional = bestelRegelRepository.findById(bestelregel_id);
        BestelRegel bestelregel = (BestelRegel) bestelRegelOptional.get();
        
        Artikel artikel = bestelregel.getArtikel();
        
        //Voorraad artikel terugzetten
        artikel.setVoorraad(artikel.getVoorraad() + bestelregel.getAantal());
        artikelRepository.save(artikel);
        
        //Totaalprijs bestelling terugzetten
        BigDecimal oudeTotaalPrijs = bestelling.getTotaalprijs();
        BigDecimal artikelPrijs = bestelregel.getArtikelPrijs();
        BigDecimal aantalArtikel = new BigDecimal(bestelregel.getAantal());
        BigDecimal nieuweTotaalPrijs = oudeTotaalPrijs.min(artikelPrijs.multiply(aantalArtikel));
        bestelling.setTotaalprijs(nieuweTotaalPrijs);
        bestellingRepository.save(bestelling);
        
        //Bestelregel uit database halen
        bestelRegelRepository.delete(bestelregel);
        
        ModelAndView modelAndView = new ModelAndView("redirect:/bestelling/add?id=" + String.valueOf(bestelling.getId()));
        return modelAndView;
    }

}