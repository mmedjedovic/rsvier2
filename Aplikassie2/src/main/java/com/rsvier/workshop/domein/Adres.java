package com.rsvier.workshop.domein;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name="adres")
public class Adres {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="adres_id")
    protected Long id;
	
    @NotNull(message="Vul a.u.b. straatnaam in")
    @Size(min=2, max=30, message="straatnaam moet tussen 2 en 30 letters zijn")
    @Column(nullable=false)
    protected String straatnaam;
	
    @NotNull
    @Column(nullable=false)
    protected int huisnummer;
	
    protected String toevoeging;
    
    @NotNull(message="Vul a.u.b. postcode in")
    @Pattern(regexp="^[1-9][0-9]{3} ?(?!sa|sd|ss)[a-z]{2}$/i", message = "Vul een geldige postcode in")
    @Column(nullable=false)
    protected String postcode;
    
    @NotNull(message="Vul a.u.b. een naam in")
    @Size(min=2, max=30, message="Een naam moet tussen 2 en 30 tekens zijn")
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