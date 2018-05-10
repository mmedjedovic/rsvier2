package com.rsvier.workshop.domein;

import java.math.BigDecimal;
import java.time.LocalDate;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="bestelling")
public class Bestelling {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    protected Long id;
    
    @Column(nullable = false, precision = 6, scale = 2)
    protected BigDecimal totaalprijs;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "persoon_id", nullable = false)
    protected Persoon klant;
    
    @Column(nullable = false)
    protected LocalDate bestelDatum;
    
    @Column(nullable = false)
    protected Long factuurnummer;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    protected Status status;
    
    @OneToMany(mappedBy="bestelling", cascade=CascadeType.PERSIST)
    protected Set<BestelRegel> bestelregels = new HashSet<>();
    
    public enum Status {OPEN, VERZONDEN, GESLOTEN}
    
    protected Bestelling() {}
    
    public Bestelling(Status status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTotaalprijs() {
        return totaalprijs;
    }

    public void setTotaalprijs(BigDecimal totaalprijs) {
        this.totaalprijs = totaalprijs;
    }

    public Persoon getKlant() {
        return klant;
    }

    public void setKlant(Persoon klant) {
        this.klant = klant;
    }

    public LocalDate getBestelDatum() {
        return bestelDatum;
    }

    public void setBestelDatum(LocalDate bestelDatum) {
        this.bestelDatum = bestelDatum;
    }

    public Long getFactuurnummer() {
        return factuurnummer;
    }

    public void setFactuurnummer(Long factuurnummer) {
        this.factuurnummer = factuurnummer;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    
    public Set<BestelRegel> getBestelregels() {
        return bestelregels;
    }

    public void setBestelregels(Set<BestelRegel> bestelregels) {
        this.bestelregels = bestelregels;
    }

    public void addBestelRegel(BestelRegel bestelRegel) {
        this.bestelregels.add(bestelRegel);
    }
    
    @Override
    public String toString() {
        return factuurnummer + " " + bestelDatum.toString() + " (" + klant.getAchterNaam() + "), €" + totaalprijs;
    }
}
