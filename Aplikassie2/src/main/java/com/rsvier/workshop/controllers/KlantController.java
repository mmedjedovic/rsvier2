package com.rsvier.workshop.controllers;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.rsvier.workshop.dao.PersoonRepository;
import com.rsvier.workshop.domein.Adres;
import com.rsvier.workshop.domein.Persoon;
import com.rsvier.workshop.domein.Adres.AdresSoort;
import com.rsvier.workshop.domein.Persoon.AccountSoort;
import com.rsvier.workshop.domein.Persoon.PersoonStatus;

@Controller
@RequestMapping
@SessionAttributes("persoon")
public class KlantController {
	
	CrudRepository repository;
	
	@Autowired
	public KlantController(PersoonRepository persoonRepository) {
		repository = persoonRepository;
	}
	
	@ModelAttribute("persoon")
	public Persoon getPersoon() {
		return new Persoon();
	}
	
	@GetMapping("klant")
	public String getKlantMenu() {
		return "klant";
	}
	
	@GetMapping("/klantformulier")
	public String getFormulier(Model model) {
		return "klantformulier";
	}
	
	@PostMapping("/register")
	public String registerKlant(@ModelAttribute("persoon") Persoon persoon) {
		persoon.setPersoonStatus(PersoonStatus.ACTIEF);
		persoon.setAccountSoort(AccountSoort.KLANT);
		setAdresSoort(persoon);
		repository.save(persoon);
		return "nogtemaken";
	}
	
	@GetMapping("/klantenzaken")
	public String getKlantenZaken() {
		return "klantenzaken";
	}
	
	@GetMapping("test")
	public String getTest(@ModelAttribute("persoon") Persoon persoon, Model model) {
		persoon.setVoorNaam("bu");
		String statusName = persoon.getPersoonStatus().name();
		model.addAttribute("statusName", statusName);
		return "test";
	}
	
	
	
	private void setAdresSoort(Persoon persoon) {
		Collection<Adres> collection = persoon.getAdresCollection();
		Iterator<Adres> iterator = collection.iterator();
		iterator.next().setAdresSoort(AdresSoort.WOONADRES);
	}

}
