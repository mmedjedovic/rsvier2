package com.rsvier.workshop.security;

import com.rsvier.workshop.domein.Adres;
import com.rsvier.workshop.domein.Persoon;
import java.time.LocalDate;
import java.util.Collection;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.password.PasswordEncoder;

public class RegistrationForm {

    @NotNull(message = "Vul a.u.b. een naam in")
    @Size(min = 2, max = 30, message = "Een naam moet tussen 2 en 30 tekens zijn")
    protected String voorNaam;

    @NotNull(message = "Vul a.u.b. een naam in")
    @Size(min = 2, max = 30, message = "Een achternaam moet tussen 2 en 30 tekens zijn")
    protected String achterNaam;

    protected String tussenVoegsel;

    @NotNull(message = "Vul a.u.b. uw geboortedatum")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    protected LocalDate geboorteDatum;

    @NotNull(message = "Vul gebruikersnaam")
    @Size(min = 2, max = 15, message = "gebruikersnaam moet tussen 2 en 15 tekens zijn")
    protected String gebruikersnaam;

    @NotNull(message = "Vul wachtwoord")
    @Pattern(regexp= "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).{4,8}$", message = "Wachtwoord moet minstens 1 kleine letter, 1 hoofdletter, en 1 cijfer bevatten, tussen de 6 en 13 tekens lang zijn en mag geen spaties bevatten")
    protected String wachtwoord;

    @NotNull(message = "Vul a.u.b. straatnaam in")
    @Size(min = 2, max = 30, message = "straatnaam moet tussen 2 en 30 letters zijn")
    protected String straatnaam;

    @NotNull(message = "Vul een huisnummer in")
    protected int huisnummer;

    protected String toevoeging;

    @NotNull(message = "Vul a.u.b. postcode in")
    @Pattern(regexp="^[1-9][0-9]{3} ?(?!sa|sd|ss)[a-z]{2}$/i", message = "Vul een geldige postcode in")
    protected String postcode;

    @NotNull(message = "Vul a.u.b. een naam in")
    @Size(min = 2, max = 30, message = "Een naam moet tussen 2 en 30 tekens zijn")
    protected String woonplaats;

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
    
    public Persoon toPersoon(PasswordEncoder passwordEncoder) {
        Persoon medewerker = new Persoon();
        
        medewerker.setVoorNaam(voorNaam);
        medewerker.setTussenVoegsel(tussenVoegsel);
        medewerker.setAchterNaam(achterNaam);
        medewerker.setGeboorteDatum(geboorteDatum);
        medewerker.setGebruikersnaam(gebruikersnaam);
        medewerker.setWachtwoord(passwordEncoder.encode(wachtwoord));
        
        Collection adressen = medewerker.getAdresCollection();
        
        Adres woonAdres = new Adres();
        
        woonAdres.setHuisnummer(huisnummer);
        woonAdres.setPostcode(postcode);
        woonAdres.setStraatnaam(straatnaam);
        woonAdres.setToevoeging(toevoeging);
        woonAdres.setWoonplaats(woonplaats);
        
        adressen.add(woonAdres);
        
        return medewerker;
    }
}
