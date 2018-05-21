package com.rsvier.workshop.domein;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "persoon")
public class Persoon implements UserDetails {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "persoon_id")
    protected Long id;

    @NotNull(message = "Vul a.u.b. een naam in")
    @Size(min = 2, max = 30, message = "Een naam moet tussen 2 en 30 tekens zijn")
    @Column(name = "voornaam", nullable = false)
    protected String voorNaam;

    @NotNull(message = "Vul a.u.b. een naam in")
    @Size(min = 2, max = 30, message = "Een achternaam moet tussen 2 en 30 tekens zijn")
    @Column(name = "achternaam", nullable = false)
    protected String achterNaam;

    @Column(name = "tussenvoegsel")
    protected String tussenVoegsel;

    @NotNull(message = "Vul a.u.b. uw geboortedatum")
    @Column(name = "geboortedatum")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    protected LocalDate geboorteDatum;

    @OneToMany(mappedBy = "klant", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    protected Set<Bestelling> bestellingSet = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "persoon_id")
    protected Collection<Adres> adresCollection = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "accountsoort", nullable = false)
    protected AccountSoort accountSoort;

    @Enumerated(EnumType.STRING)
    @Column(name = "persoon_status")
    public PersoonStatus persoonStatus;

    @NotNull(message = "Vul gebruikersnaam")
    @Size(min = 2, max = 15, message = "gebruikersnaam moet tussen 2 en 15 tekens zijn")
    @Column(nullable = false)
    protected String gebruikersnaam;

    @NotNull(message = "Vul wachtwoord in")
    @Column(nullable = false)
    protected String wachtwoord;

    public enum PersoonStatus {
        ACTIEF, INACTIEF
    }

    public enum AccountSoort {
        ADMINISTRATOR, MEDEWERKER, KLANT
    }

    public Persoon() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVoorNaam() {
        return voorNaam;
    }

    public void setVoorNaam(String voorNaam) {
        this.voorNaam = voorNaam;
    }

    public String getAchterNaam() {
        return achterNaam;
    }

    public void setAchterNaam(String achterNaam) {
        this.achterNaam = achterNaam;
    }

    public String getTussenVoegsel() {
        return tussenVoegsel;
    }

    public void setTussenVoegsel(String tussenVoegsel) {
        this.tussenVoegsel = tussenVoegsel;
    }

    public LocalDate getGeboorteDatum() {
        return geboorteDatum;
    }

    public void setGeboorteDatum(LocalDate geboorteDatum) {
        this.geboorteDatum = geboorteDatum;
    }

    public Set<Bestelling> getBestellingSet() {
        return bestellingSet;
    }

    public void setBestellingSet(Set<Bestelling> bestellingSet) {
        this.bestellingSet = bestellingSet;
    }

    public Collection<Adres> getAdresCollection() {
        return adresCollection;
    }

    public void setAdresCollection(Collection<Adres> adresCollection) {
        this.adresCollection = adresCollection;
    }

    public AccountSoort getAccountSoort() {
        return accountSoort;
    }

    public void setAccountSoort(AccountSoort accountSoort) {
        this.accountSoort = accountSoort;
    }

    public PersoonStatus getPersoonStatus() {
        return persoonStatus;
    }

    public void setPersoonStatus(PersoonStatus persoonStatus) {
        this.persoonStatus = persoonStatus;
    }

    public String getGebruikersnaam() {
        return gebruikersnaam;
    }

    public void setGebruikersnaam(String gebruikersnaam) {
        this.gebruikersnaam = gebruikersnaam;
    }

    public String getWachtwoord() {
        return wachtwoord;
    }

    public void setWachtwoord(String wachtwoord) {
        this.wachtwoord = wachtwoord;
    }

    @Override
    public String toString() {
        return achterNaam + ", " + voorNaam + " " + tussenVoegsel;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_MEDEWERKER"));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getPassword() {
        return wachtwoord;
    }

    @Override
    public String getUsername() {
        return gebruikersnaam;
    }
}
