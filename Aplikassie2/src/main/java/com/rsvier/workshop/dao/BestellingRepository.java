package com.rsvier.workshop.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rsvier.workshop.domein.Bestelling;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface BestellingRepository extends CrudRepository<Bestelling, Long>{
    @Query(value = "SELECT * FROM bestelling b WHERE b.status = 'OPEN' OR 'VERZONDEN'",
            nativeQuery=true)
    public List<Bestelling> findActief();
    public Bestelling findByFactuurnummer(Long factuurnummer);
}
