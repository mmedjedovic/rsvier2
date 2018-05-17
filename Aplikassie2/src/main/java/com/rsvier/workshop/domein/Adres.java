package com.rsvier.workshop.domein;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="adres")
public class Adres {
	
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="adres_id")
    protected Long id;
	
    @Column(nullable=false)
    protected String straatnaam;
	
    @Column(nullable=false)
    protected int huisnummer;
	
    protected String toevoeging;
    
    @Column(nullable=false)
    protected String postcode;
    
    @Column(nullable=false)
    protected String woonplaats;
    
    @Enumerated(EnumType.STRING)
    @Column(name="adressoort", nullable=false)
    protected AdresSoort adresSoort;
    
    public enum AdresSoort {WOONADRES, VERZENDADRES, REKENINGADRES}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStraatnaam() {
        return straatnaam;
    }

    public void setStraatnaam(String straatnaam) {
        this.straatnaam = straatnaam;
    }

    public int getHuisnummer() {
        return huisnummer;
    }

    public void setHuisnummer(int huisnummer) {
        this.huisnummer = huisnummer;
    }

    public String getToevoeging() {
        return toevoeging;
    }

    public void setToevoeging(String toevoeging) {
        this.toevoeging = toevoeging;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
    
    public String getWoonplaats() {
		return woonplaats;
	}

	public void setWoonplaats(String woonplaats) {
		this.woonplaats = woonplaats;
	}

	public AdresSoort getAdresSoort() {
        return adresSoort;
    }

    public void setAdresSoort(AdresSoort adresSoort) {
        this.adresSoort = adresSoort;
    }

    @Override
    public String toString() {
        return straatnaam + " " + huisnummer + toevoeging + ", " + postcode + ", " + woonplaats;
    }
}