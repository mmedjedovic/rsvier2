package com.rsvier.workshop.controllers;

import com.rsvier.workshop.dao.ArtikelRepository;
import com.rsvier.workshop.dao.BestelRegelRepository;
import com.rsvier.workshop.dao.BestellingRepository;
import com.rsvier.workshop.dao.PersoonRepository;
import com.rsvier.workshop.domein.Artikel;
import com.rsvier.workshop.domein.BestelRegel;
import com.rsvier.workshop.domein.Bestelling;
import com.rsvier.workshop.domein.Persoon;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/bestelling")
public class BestellingController {
	
    @Autowired
    private BestellingRepository bestellingRepository;

    @Autowired
    private BestelRegelRepository bestelRegelRepository;

    @Autowired
    private PersoonRepository persoonRepository;
    
    @Autowired
    private ArtikelRepository artikelRepository;

    @ModelAttribute("bestellingen")
    public List<Bestelling> actieveBestellingen() {
        Iterable<Bestelling> bestellingenIterable = bestellingRepository.findAll();
        List<Bestelling> bestellingen = new ArrayList();
        bestellingenIterable.forEach(bestellingen::add);
        return bestellingen;
    }

    @GetMapping
    public String getOverizicht() {
        return "bestelling";
    }

    @GetMapping(value="/edit")
    public ModelAndView wijzigBestelling(@RequestParam(value="id", required=true) Long id) {
        Optional bestellingOptional = bestellingRepository.findById(id);
        Bestelling bestelling = (Bestelling) bestellingOptional.get();
        List<BestelRegel> bestelregels = bestelRegelRepository.findByBestelling_id(id);
        ModelAndView modelAndView;

        if (bestelling.getStatus() == Bestelling.Status.OPEN) {
            modelAndView = new ModelAndView("bestellingformulier");
            modelAndView.addObject("bestelling", bestelling);
            modelAndView.addObject("bestelregels", bestelregels);
        }
        
        else {
            modelAndView = new ModelAndView("bekijkbestelling");
            modelAndView.addObject("bestelling", bestelling);
            modelAndView.addObject("bestelregels", bestelregels);
        }
        
        return modelAndView;
    }

    @PostMapping(value="/edit")
    public ModelAndView bestellingGewijzigd(Bestelling bestelling) {
        bestellingRepository.save(bestelling);
        ModelAndView modelAndView = new ModelAndView("redirect:/bestelling");
        return modelAndView;
    }

    @GetMapping(value="/close")
    public ModelAndView sluitBevestiging(@RequestParam(value="id", required=true) Long id) {
        ModelAndView modelAndView = new ModelAndView("bestellingsluit");
        Optional bestellingOptional = bestellingRepository.findById(id);
        Bestelling bestelling = (Bestelling) bestellingOptional.get();
        modelAndView.addObject("bestelling",bestelling);
        return modelAndView;
    }

    @PostMapping(value="/close")
    public ModelAndView sluitBestelling(Bestelling bestelling) {
        Optional bestellingOptional = bestellingRepository.findById(bestelling.getId());
        bestelling = (Bestelling) bestellingOptional.get();
        bestelling.setBestelDatum(LocalDate.now());
        bestelling.setStatus(Bestelling.Status.GESLOTEN);
        bestellingRepository.save(bestelling);
        ModelAndView modelAndView = new ModelAndView("redirect:/bestelling");
        return modelAndView;
    }
    
    @GetMapping(value="/delete")
    public ModelAndView verwijderBevestiging(@RequestParam(value="id", required=true) Long id) {
        ModelAndView modelAndView = new ModelAndView("bestellingdelete");
        Optional bestellingOptional = bestellingRepository.findById(id);
        Bestelling bestelling = (Bestelling) bestellingOptional.get();
        modelAndView.addObject("bestelling",bestelling);
        return modelAndView;
    }

    @PostMapping(value="/delete")
    public ModelAndView verwijderBestelling(Bestelling bestelling) {
        List<BestelRegel> bestelregels = bestelRegelRepository.findByBestelling_id(bestelling.getId());
        Optional bestellingOptional = bestellingRepository.findById(bestelling.getId());
        bestelling = (Bestelling) bestellingOptional.get();
        
        if (bestelling.getStatus() != Bestelling.Status.VERZONDEN) {
            //plaats artikelvoorraad terug en verwijder bestelregels
            
            for (BestelRegel bestelRegel: bestelregels) {
                Artikel artikel = bestelRegel.getArtikel();
                artikel.setVoorraad(artikel.getVoorraad() + bestelRegel.getAantal());
                artikelRepository.save(artikel);
                bestelRegelRepository.delete(bestelRegel);
            }
        }
        
        else {
            //verwijder bestelregels
            for (BestelRegel bestelRegel: bestelregels) {
                bestelRegelRepository.delete(bestelRegel);
            }
            
        }
        
        bestellingRepository.delete(bestelling);
        ModelAndView modelAndView = new ModelAndView("redirect:/bestelling");
        return modelAndView;
    }

    @GetMapping(value="/verzonden")
    public ModelAndView verzendBevestiging(@RequestParam(value="id", required=true) Long id) {
        Optional bestellingOptional = bestellingRepository.findById(id);
        Bestelling bestelling = (Bestelling) bestellingOptional.get();
        List<BestelRegel> bestelregels = bestelRegelRepository.findByBestelling_id(id);
        ModelAndView modelAndView;

        if (bestelling.getStatus() == Bestelling.Status.GESLOTEN) {
            modelAndView = new ModelAndView("bestellingverzend");
            modelAndView.addObject("bestelling", bestelling);
            modelAndView.addObject("bestelregels", bestelregels);
        }
        
        else {
            modelAndView = new ModelAndView("redirect:/bestelling");
        }
        
        return modelAndView;
    }

    @PostMapping(value="/verzonden")
    public ModelAndView verzendBestelling(Bestelling bestelling) {
        bestelling.setStatus(Bestelling.Status.VERZONDEN);
        bestellingRepository.save(bestelling);
        ModelAndView modelAndView = new ModelAndView("redirect:/bestelling");
        return modelAndView;
    }
    
    @GetMapping(value="/add/klant")
    public ModelAndView klantToevoegen(@RequestParam(value="id", required=true) Long id, 
            @RequestParam(value="klant_id", required=false) Long klant_id, @ModelAttribute Persoon klant) {
        
        if (klant_id == null) {
            ModelAndView modelAndView = new ModelAndView("kiesklant");
            Optional bestellingOptional = bestellingRepository.findById(id);
            Bestelling bestelling = (Bestelling) bestellingOptional.get();
            modelAndView.addObject("bestelling",bestelling);

            Iterable<Persoon> klantIterable = persoonRepository.findAll();
            List<Persoon> klanten = new ArrayList();
            klantIterable.forEach(klanten::add);
            modelAndView.addObject("klanten",klanten);

            return modelAndView;
        }
        
        else {
            ModelAndView modelAndView = new ModelAndView("redirect:/bestelling/add?id=" + String.valueOf(id));
            Optional bestellingOptional = bestellingRepository.findById(id);
            Bestelling bestelling = (Bestelling) bestellingOptional.get();

            Optional klantOptional = persoonRepository.findById(klant_id);
            klant = (Persoon) klantOptional.get();

            bestelling.setKlant(klant);
            bestellingRepository.save(bestelling);
            return modelAndView;
        }
    }
}
