package com.rsvier.workshop.domein;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="artikel")
public class Artikel {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    protected Long id;
    
    @NotNull(message="Vul a.u.b. een naam in")
    @Size(min=2, max=30, message="Een artikelnaam moet tussen 2 en 30 tekens zijn")
    protected String naam;
    
    @NotNull(message="Prijs kan niet leeg zijn")
    @Digits(integer=6, fraction=2, message="De prijs kan maximaal 999999.99 bedragen en moet gescheiden zijn met een punt")
    protected BigDecimal prijs;
    
    @NotNull(message="Voorraad moet worden ingevuld")
    @Min(value=0, message="Voorraad moet positief zijn")
    protected int voorraad;
    
    @Enumerated(EnumType.STRING)
    @Column(name="artikel_status", nullable=false)
    protected ArtikelStatus artikelStatus;
    
    public Artikel() {}
    
    public enum ArtikelStatus {ACTIEF, INACTIEF}
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public BigDecimal getPrijs() {
        return prijs;
    }

    public void setPrijs(BigDecimal prijs) {
        this.prijs = prijs;
    }

    public int getVoorraad() {
        return voorraad;
    }

    public void setVoorraad(int voorraad) {
        this.voorraad = voorraad;
    }

    public ArtikelStatus getArtikelStatus() {
        return artikelStatus;
    }

    public void setArtikelStatus(ArtikelStatus artikelStatus) {
        this.artikelStatus = artikelStatus;
    }

    @Override
    public String toString() {
        return naam;
    }
}
