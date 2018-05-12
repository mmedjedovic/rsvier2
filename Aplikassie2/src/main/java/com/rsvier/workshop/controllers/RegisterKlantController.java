package com.rsvier.workshop.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.rsvier.workshop.dao.PersoonRepository;
import com.rsvier.workshop.domein.Persoon;
import com.rsvier.workshop.domein.Persoon.AccountSoort;
import com.rsvier.workshop.domein.Persoon.PersoonStatus;

@Controller
@RequestMapping("/register")
public class RegisterKlantController {
	
	CrudRepository repository;
	
	
	@Autowired
	public RegisterKlantController(PersoonRepository persoonRepository) {
		repository = persoonRepository;
	}
	
	
	
	@PostMapping
	public Persoon registerKlant(Persoon persoon) {
		persoon.setPersoonStatus(PersoonStatus.ACTIEF);
		persoon.setAccountSoort(AccountSoort.KLANT);
		repository.save(persoon);
		return persoon;
	}
}
