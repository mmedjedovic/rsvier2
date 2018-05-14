package com.rsvier.workshop.controllers;

import com.rsvier.workshop.dao.ArtikelRepository;
import com.rsvier.workshop.domein.Artikel;
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
@RequestMapping(value="/artikel")
public class ArtikelController {
	
        @Autowired
	private ArtikelRepository artikelRepository;

        @ModelAttribute("artikelen")
        public List<Artikel> artikelen() {
            List<Artikel> artikelen = artikelRepository.findActief();
            return artikelen;
        }
	
        @GetMapping
	public String getOverizicht() {
		return "artikel";
	}
	
	@GetMapping(value="/add")
	public String artikelToevoegformulier(@ModelAttribute Artikel artikel) {
                return "artikelformulier";
        }
        
        @PostMapping(value="/add")
        public ModelAndView artikelToegevoegen (Artikel artikel) {
            artikel.setArtikelStatus(Artikel.ArtikelStatus.ACTIEF);
            artikelRepository.save(artikel);
            ModelAndView modelAndView = new ModelAndView("redirect:/artikel");
            return modelAndView;
        }
        
        @GetMapping(value="/edit")
	public ModelAndView artikelWijzigen(@RequestParam(value="id", required=true) Long id) {
		
                Optional artikelOptional = artikelRepository.findById(id);
                Artikel artikel = (Artikel) artikelOptional.get();
                
                if(artikel.getArtikelStatus() == Artikel.ArtikelStatus.INACTIEF) {
                    ModelAndView modelAndView = new ModelAndView("artikel");
                    return modelAndView;
                }
                
                else {
                    ModelAndView modelAndView = new ModelAndView("artikeledit");
                    modelAndView.addObject("artikel",artikel);
                    return modelAndView;
                }
	}
	
	@PostMapping(value="/edit")
	public ModelAndView artikelGewijzigd(Artikel artikel) {
            artikel.setArtikelStatus(Artikel.ArtikelStatus.ACTIEF);
            artikelRepository.save(artikel);
            ModelAndView modelAndView = new ModelAndView("redirect:/artikel");
            return modelAndView;
	}

	@GetMapping(value="/delete")
	public ModelAndView verwijderBevestiging(@RequestParam(value="id", required=true) Long id) {
		ModelAndView modelAndView = new ModelAndView("artikeldelete");
		Optional artikelOptional = artikelRepository.findById(id);
                Artikel artikel = (Artikel) artikelOptional.get();
		modelAndView.addObject("artikel",artikel);
		return modelAndView;
	}
        
        @PostMapping(value="/delete")
	public ModelAndView verwijderArtikelPagina(Artikel artikel) {
            artikel.setArtikelStatus(Artikel.ArtikelStatus.INACTIEF);
            artikelRepository.save(artikel);
            ModelAndView modelAndView = new ModelAndView("redirect:/artikel");
            return modelAndView;
	}
}
