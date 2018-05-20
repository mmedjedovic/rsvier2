package com.rsvier.workshop.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.rsvier.workshop.dao.AdresRepository;
import com.rsvier.workshop.dao.PersoonRepository;
import com.rsvier.workshop.domein.Adres;
import com.rsvier.workshop.domein.Persoon;

@Controller
@RequestMapping("/adres")
public class AdresController {
	
	@Autowired
	AdresRepository adresRepository;
	
	@Autowired
	PersoonRepository persoonRepository;
	
	@ModelAttribute
	public Adres getAdres() {
		return new Adres();
	}
	
	@GetMapping(value="/adresoverzicht")
    public String adresOverzicht(@RequestParam(value="id", required=true) Long id, Model model) {
		List<Adres> adresList = new ArrayList<>();
		Optional<Persoon> persoonOptional = persoonRepository.findById(id);
		Persoon persoon = persoonOptional.get();
		adresList = (List<Adres>) persoon.getAdresCollection();
		model.addAttribute("adresList", adresList);
		model.addAttribute("persoonId", persoon.getId());
		model.addAttribute("naam", persoon.getVoorNaam());
		return "adresoverzicht";
	}
	
	@GetMapping("/adresaangeven")
	public String adresAangeven(@RequestParam(value="id", required=true) Long id, Model model) {
		Adres adres = new Adres();
		model.addAttribute("persoonId", id);
		model.addAttribute("adres", adres);
		return "adresaangeven";
	}
	//@ModelAttribute("adres") weggehaald na @Validation
	@PostMapping("/maakadres")
	public ModelAndView maakAdres(@RequestParam(value="id", required=true) Long persoonId, @ModelAttribute("adres") Adres adres, Model model) {
		/**
		if(errors.hasErrors()) {
			return new ModelAndView("adresaangeven");
		}*/
		Optional<Persoon> optionaalPersoon = persoonRepository.findById(persoonId);
		Persoon persoon = optionaalPersoon.get();
		Collection<Adres> collection = persoon.getAdresCollection();
		Iterator<Adres> iterator = collection.iterator();
		//in gevall dat hetzelefde soortadres bestaat dan niet adres maken eerst oude adres verwijderen
		if(iterator.hasNext()) {
			if(iterator.next().getAdresSoort().equals(adres.getAdresSoort())) {
				return new ModelAndView("redirect:/klant");
			}
		}
		Adres nieuwAdres = adresRepository.save(adres);
		collection.add(nieuwAdres);
		persoonRepository.save(persoon);
		return new ModelAndView("redirect:/klant");	
	}
	
	@GetMapping("/adresupdaten")
	public String getAdresVoorUpdate(@ModelAttribute("adres") Adres adres, @RequestParam(value="id", required=true) Long id, Model model) {
		Optional<Adres> adresOptional = adresRepository.findById(id);
		adres = adresOptional.get();
		model.addAttribute("adres", adres);
		return "adresupdaten";
	}
	
	@PostMapping("/executeupdate")
	public ModelAndView updateAdres(@ModelAttribute("adres") Adres adres) {
		Adres nieuwAdres = adresRepository.save(adres);
		return new ModelAndView("redirect:/klant");
	}
	
	
}
