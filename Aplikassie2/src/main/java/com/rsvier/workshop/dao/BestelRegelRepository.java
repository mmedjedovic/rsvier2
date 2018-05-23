package com.rsvier.workshop.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rsvier.workshop.domein.BestelRegel;
import java.util.List;

@Repository
public interface BestelRegelRepository extends CrudRepository<BestelRegel, Long>{
    public List<BestelRegel> findByBestelling_id(Long id);
}
