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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.rsvier.workshop.dao.AdresRepository;
import com.rsvier.workshop.dao.PersoonRepository;
import com.rsvier.workshop.domein.Adres;
import com.rsvier.workshop.domein.Persoon;

@Controller
@SessionAttributes("persoon")
@RequestMapping("/adres")
public class AdresController {
	
	@Autowired
	AdresRepository adresRepository;
	
	@Autowired
	PersoonRepository persoonRepository;
	
	@ModelAttribute("adres")
	public Adres getAdres() {
		return new Adres();
	}
	
	@ModelAttribute("persoon")
	public Persoon getPersoon() {
		return new Persoon();
	}
	
	@ModelAttribute("message")
	public String getMessage() {
		return new String();
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
	public String adresAangeven(@RequestParam(value="id", required=true) Long id, Model model, @ModelAttribute("persoon") Persoon persoon) {
		Adres adres = new Adres();
		Optional<Persoon> persoonOptional = persoonRepository.findById(id);
		persoon = persoonOptional.get();
		model.addAttribute("persoon", persoon);
		model.addAttribute("adres", adres);
		return "adresaangeven";
	}
	 
	@PostMapping("/maakadres")
	public ModelAndView maakAdres(@Valid Adres adres, Errors errors, Model model, Persoon persoon) {
		if(errors.hasErrors()) {
			return new ModelAndView("adresaangeven");
		}
		Collection<Adres> collection = persoon.getAdresCollection();
		Iterator<Adres> iterator = collection.iterator();
		//in gevall dat hetzelefde soortadres bestaat dan niet adres maken eerst oude adres verwijderen
		if(iterator.hasNext()) {
			if(iterator.next().getAdresSoort().equals(adres.getAdresSoort())) {
				ModelAndView modelAndView = new ModelAndView("redirect:/klant");
				return modelAndView;
			}
		}
		Adres nieuwAdres = adresRepository.save(adres);
		collection.add(nieuwAdres);
		persoonRepository.save(persoon);
		persoon = new Persoon();
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
	public ModelAndView updateAdres(@Valid Adres adres, Errors errors) {
		if(errors.hasErrors()) {
			return new ModelAndView("/adresupdaten");
		}
		adresRepository.save(adres);
		return new ModelAndView("redirect:/klant");
	}
	
	
}
