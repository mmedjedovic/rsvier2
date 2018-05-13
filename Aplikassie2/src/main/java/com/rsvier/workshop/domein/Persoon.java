package com.rsvier.workshop.domein;

import java.time.LocalDate;
import java.util.ArrayList;
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

import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Table(name="persoon")
public class Persoon {
	
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="persoon_id")
    protected Long id;
	
    @Column(name="voornaam", nullable=false)
    protected String voorNaam;
	
    @Column(name="achternaam", nullable=false)
    protected String achterNaam;
	
    @Column(name="tussenvoegsel")
    protected String tussenVoegsel;
    
    @Column(name="geboortedatum")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    protected LocalDate geboorteDatum;
	
    @OneToMany(mappedBy="klant", fetch=FetchType.LAZY, cascade=CascadeType.PERSIST)
    protected Set<Bestelling> bestellingSet = new HashSet<>();
    
    @OneToMany(fetch=FetchType.LAZY, cascade= {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name="persoon_id")
    protected Collection<Adres> adresCollection = new ArrayList<>();
    
    @Enumerated(EnumType.STRING)
    @Column(name="accountsoort", nullable=false)
    protected AccountSoort accountSoort;
    
    @Enumerated(EnumType.STRING)
    @Column(name="persoon_status", nullable=false)
    public PersoonStatus persoonStatus;
    
    @Column(nullable=false)
    protected String gebruikersnaam;
    
    @Column(nullable=false)
    protected String wachtwoord;
    
    public enum PersoonStatus {ACTIEF, INACTIEF}
    
    public enum AccountSoort {ADMINISTRATOR, MEDEWERKER, KLANT}
    
    
    public Persoon() {}

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
}
