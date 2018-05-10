package com.rsvier.workshop.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rsvier.workshop.domein.Persoon;

@Repository
public interface PersoonRepository extends CrudRepository<Persoon, Long>{

}
