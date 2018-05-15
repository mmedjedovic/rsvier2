package com.rsvier.workshop.controllers;

import com.rsvier.workshop.dao.BestelRegelRepository;
import com.rsvier.workshop.dao.BestellingRepository;
import com.rsvier.workshop.dao.PersoonRepository;
import com.rsvier.workshop.domein.BestelRegel;
import com.rsvier.workshop.domein.Bestelling;
import java.util.ArrayList;
import java.util.List;
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
    
    @GetMapping
    public String bestellingToevoegformulier(@ModelAttribute Bestelling bestelling, @ModelAttribute BestelRegel bestelRegel, 
            Model model) {
        List<BestelRegel> bestelregels = new ArrayList();
        model.addAttribute(bestelregels);
        return "bestellingformulier";
    }
    
    @PostMapping
    public ModelAndView bestellingToegevoegen (Bestelling bestelling) {
        //TODO
        ModelAndView modelAndView = new ModelAndView("redirect:/bestelling");
        return modelAndView;
    }
    
    @PostMapping(value="/edit")
	public ModelAndView bestellingGewijzigd(Bestelling bestelling) {
            //TODO
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
            //TODO
            ModelAndView modelAndView = new ModelAndView("redirect:/bestelling");
            return modelAndView;
	}
}
