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
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="artikel")
public class Artikel {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    protected Long id;
    
    @Column(nullable = false, length = 45)
    protected String naam;
    
    @Column(nullable = false, precision = 6, scale = 2)
    protected BigDecimal prijs;
    
    @Column(nullable = false)
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
