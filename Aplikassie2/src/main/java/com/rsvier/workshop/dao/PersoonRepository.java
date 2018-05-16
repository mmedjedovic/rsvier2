package com.rsvier.workshop.dao;

import java.util.Iterator;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rsvier.workshop.domein.Persoon;
import com.rsvier.workshop.domein.Persoon.PersoonStatus;

@Repository
public interface PersoonRepository extends CrudRepository<Persoon, Long>{
	
	Iterable<Persoon> findByPersoonStatus(PersoonStatus status);

}
