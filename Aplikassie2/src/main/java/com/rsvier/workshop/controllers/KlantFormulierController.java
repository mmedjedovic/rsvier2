package com.rsvier.workshop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rsvier.workshop.domein.Persoon;
import com.rsvier.workshop.domein.Persoon.AccountSoort;

@Controller
@RequestMapping("/klantformulier")
public class KlantFormulierController {
	
	@GetMapping
	public String getFormulier(Model model) {
		model.addAttribute("persoon", new Persoon(AccountSoort.KLANT));
		return "klantformulier";
	}
	
}
