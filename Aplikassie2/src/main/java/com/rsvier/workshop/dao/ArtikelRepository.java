package com.rsvier.workshop.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rsvier.workshop.domein.Artikel;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface ArtikelRepository extends CrudRepository<Artikel, Long>{
    @Query(value = "SELECT * FROM artikel a WHERE a.artikel_status = 'ACTIEF'",
            nativeQuery=true)
    public List<Artikel> findActief();
}
