package com.rsvier.workshop;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/klantformulier")
public class KlantFormulierController {
	
	@GetMapping
	public String getFormulier(Model model) {
		return "klantformulier";
	}
	
	
}
