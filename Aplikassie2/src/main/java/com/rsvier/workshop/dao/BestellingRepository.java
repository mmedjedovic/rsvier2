package com.rsvier.workshop.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rsvier.workshop.domein.Bestelling;

@Repository
public interface BestellingRepository extends CrudRepository<Bestelling, Long>{

}
