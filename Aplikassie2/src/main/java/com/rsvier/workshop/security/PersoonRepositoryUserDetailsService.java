package com.rsvier.workshop.security;

import com.rsvier.workshop.dao.PersoonRepository;
import com.rsvier.workshop.domein.Persoon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class PersoonRepositoryUserDetailsService
        implements UserDetailsService {

    private PersoonRepository persoonRepository;

    @Autowired
    public PersoonRepositoryUserDetailsService(PersoonRepository persoonRepository) {
        this.persoonRepository = persoonRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String gebruikersnaam)
            throws UsernameNotFoundException {
        Persoon persoon = persoonRepository.findByGebruikersnaam(gebruikersnaam);
        if (persoon != null) {
            return persoon;
        }
        throw new UsernameNotFoundException(
                "Gebruiker '" + gebruikersnaam + "' kon niet gevonden worden");
    }
}
