package com.rsvier.workshop.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rsvier.workshop.domein.Artikel;

@Repository
public interface ArtikelRepository extends CrudRepository<Artikel, Long>{
    
}
