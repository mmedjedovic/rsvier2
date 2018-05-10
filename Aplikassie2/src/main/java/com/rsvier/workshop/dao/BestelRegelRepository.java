package com.rsvier.workshop.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rsvier.workshop.domein.BestelRegel;

@Repository
public interface BestelRegelRepository extends CrudRepository<BestelRegel, Long>{

}
