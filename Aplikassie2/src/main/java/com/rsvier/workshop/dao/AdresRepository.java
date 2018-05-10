package com.rsvier.workshop.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rsvier.workshop.domein.Adres;

@Repository
public interface AdresRepository extends CrudRepository<Adres, Long>{

}
