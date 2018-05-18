package com.rsvier.workshop.domein;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="bestel_regel")
public class BestelRegel {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    protected Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bestelling_id", nullable = false)
    protected Bestelling bestelling;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artikel_id", nullable = false)
    protected Artikel artikel;
    
    @Column(nullable = false, precision = 6, scale = 2)
    protected BigDecimal artikelPrijs;
    
    //@NotNull(message="Aantal kan niet leeg zijn")
    //@Min(value=1, message="Er moet minstens 1 artikel toegevoegd worden")
    @Column(nullable = false)
    protected int aantal;
    
    public BestelRegel() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Bestelling getBestelling() {
        return bestelling;
    }

    public void setBestelling(Bestelling bestelling) {
        this.bestelling = bestelling;
    }

    public Artikel getArtikel() {
        return artikel;
    }

    public void setArtikel(Artikel artikel) {
        this.artikel = artikel;
    }

    public BigDecimal getArtikelPrijs() {
        return artikelPrijs;
    }

    public void setArtikelPrijs(BigDecimal artikelPrijs) {
        this.artikelPrijs = artikelPrijs;
    }

    public int getAantal() {
        return aantal;
    }

    public void setAantal(int aantal) {
        this.aantal = aantal;
    }
    
    @Override
    public String toString() {
        return aantal + " x " + artikel.getNaam() + " (€" + artikelPrijs + "), totaal: €" + artikelPrijs.multiply(new BigDecimal(aantal)) ;
    }
}
