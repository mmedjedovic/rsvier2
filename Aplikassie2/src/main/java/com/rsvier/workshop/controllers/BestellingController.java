package com.rsvier.workshop.controllers;

import com.rsvier.workshop.dao.BestelRegelRepository;
import com.rsvier.workshop.dao.BestellingRepository;
import com.rsvier.workshop.dao.PersoonRepository;
import com.rsvier.workshop.domein.BestelRegel;
import com.rsvier.workshop.domein.Bestelling;
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

        @ModelAttribute("allebestellingen")
        public List<Bestelling> alleBestellingen() {
            Iterable<Bestelling> bestellingenIterable = bestellingRepository.findAll();
            List<Bestelling> bestellingen = new ArrayList();
            bestellingenIterable.forEach(bestellingen::add);
            return bestellingen;
        }
        
        @ModelAttribute("bestellingen")
        public List<Bestelling> actieveBestellingen() {
            List<Bestelling> bestellingen = bestellingRepository.findActief();
            return bestellingen;
        }
        
        @GetMapping
	public String getOverizicht() {
            return "bestelling";
	}
	
	@GetMapping(value="/edit")
	public ModelAndView bestellingWijzigen(@RequestParam(value="id", required=true) Long id) {
            Optional bestellingOptional = bestellingRepository.findById(id);
            Bestelling bestelling = (Bestelling) bestellingOptional.get();
            List<BestelRegel> bestelregels = bestelRegelRepository.findByBestelling_id(id);
            ModelAndView modelAndView = new ModelAndView("bestellingformulier");
            modelAndView.addObject("bestelling", bestelling);
            modelAndView.addObject("bestelregels", bestelregels);
            return modelAndView;
        }
	
	@PostMapping(value="/edit")
	public ModelAndView bestellingGewijzigd(Bestelling bestelling) {
            bestellingRepository.save(bestelling);
            ModelAndView modelAndView = new ModelAndView("redirect:/bestelling");
            return modelAndView;
	}

	@GetMapping(value="/delete")
	public ModelAndView verwijderBevestiging(@RequestParam(value="id", required=true) Long id) {
            //TODO
            ModelAndView modelAndView = new ModelAndView("bestellingdelete");
            return modelAndView;
	}
        
        @PostMapping(value="/delete")
	public ModelAndView verwijderBestellingPagina(Bestelling bestelling) {
            bestelling.setStatus(Bestelling.Status.GESLOTEN);
            bestellingRepository.save(bestelling);
            ModelAndView modelAndView = new ModelAndView("redirect:/bestelling");
            return modelAndView;
	}
}
