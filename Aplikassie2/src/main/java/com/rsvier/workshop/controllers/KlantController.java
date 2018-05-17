package com.rsvier.workshop.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.rsvier.workshop.dao.PersoonRepository;
import com.rsvier.workshop.domein.Adres;
import com.rsvier.workshop.domein.Persoon;
import com.rsvier.workshop.domein.Adres.AdresSoort;
import com.rsvier.workshop.domein.Persoon.AccountSoort;
import com.rsvier.workshop.domein.Persoon.PersoonStatus;

@Controller
@RequestMapping("/klant")
@SessionAttributes("persoon")
public class KlantController {
	
	@Autowired
	PersoonRepository repository;
	
	@ModelAttribute("persoon")
	public Persoon getPersoon() {
		return new Persoon();
	}
	
	@GetMapping
	public String getKlantMenu() {
		return "klant";
	}
	
	@GetMapping("/klantformulier")
	public String getFormulier(Model model) {
		return "/klantformulier";
	}
	
	@PostMapping("/register")
	public ModelAndView registerKlant(@ModelAttribute("persoon") Persoon persoon, Model model) {
		persoon.setPersoonStatus(PersoonStatus.ACTIEF);
		persoon.setAccountSoort(AccountSoort.KLANT);
		setAdresSoort(persoon);
		repository.save(persoon);
		persoon = new Persoon();
		model.addAttribute("persoon", persoon);
		return new ModelAndView("redirect:/klant");
	}
	
	@GetMapping("/klantenoverzicht")
	public String getKlantenOverzicht(@ModelAttribute("persoon") Persoon persoon, Model model) {
		Iterable<Persoon> persoonList = new ArrayList<Persoon>();
		PersoonStatus status = persoon.getPersoonStatus();
		if(status != null) {
			persoonList = repository.findByPersoonStatus(status);
		} else {
			persoonList = repository.findAll();
		}
		model.addAttribute("persoonList", persoonList);
		return "klantenoverzicht";
	}
	
	
	
	private void setAdresSoort(Persoon persoon) {
		Collection<Adres> collection = persoon.getAdresCollection();
		Iterator<Adres> iterator = collection.iterator();
		iterator.next().setAdresSoort(AdresSoort.WOONADRES);
	}

}
