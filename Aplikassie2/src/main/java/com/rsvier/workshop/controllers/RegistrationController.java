package com.rsvier.workshop.controllers;

import com.rsvier.workshop.dao.PersoonRepository;
import com.rsvier.workshop.domein.Adres;
import com.rsvier.workshop.domein.Persoon;
import com.rsvier.workshop.security.RegistrationForm;
import java.util.Collection;
import java.util.Iterator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private PersoonRepository persoonRepository;
    private PasswordEncoder passwordEncoder;

    public RegistrationController(
            PersoonRepository persoonRepository, PasswordEncoder passwordEncoder) {
        this.persoonRepository = persoonRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String registerForm(@ModelAttribute("registrationForm") RegistrationForm regform) {
        return "registration";
    }

    @PostMapping
    public String processRegistration(RegistrationForm form) {
        Persoon medewerker = form.toPersoon(passwordEncoder);
        
        medewerker.setAccountSoort(Persoon.AccountSoort.MEDEWERKER);
        medewerker.setPersoonStatus(Persoon.PersoonStatus.ACTIEF);
        setWoonAdresSoort(medewerker);
        persoonRepository.save(medewerker);
        
        return "redirect:/login";
    }
    
    private void setWoonAdresSoort(Persoon persoon) {
		Collection<Adres> collection = persoon.getAdresCollection();
		Iterator<Adres> iterator = collection.iterator();
		iterator.next().setAdresSoort(Adres.AdresSoort.WOONADRES);
	}
}
