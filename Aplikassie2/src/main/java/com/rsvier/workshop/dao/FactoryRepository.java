package com.rsvier.workshop.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public class FactoryRepository {
	
	@Autowired
	private AdresRepository adresRepository;
	
	@Autowired
	private ArtikelRepository artikelRepository;
	
	@Autowired
	private BestellingRepository bestellingRepository;
	
	@Autowired
	private BestelRegelRepository bestelRegelRepository;
	
	@Autowired
	private PersoonRepository persoonRepository;
	
	public enum RepositoryName {
		ADRES, ARTIKEL, BESTELLING, BESTELLINGREGEL, PERSOON
	}
	
	
	public <T, ID> CrudRepository<T, ID> getRepository(RepositoryName repositoryName) {
		switch(repositoryName) {
		case ADRES:
			return (CrudRepository) adresRepository;
		case ARTIKEL:
			return (CrudRepository) artikelRepository;
		case BESTELLING:
			return (CrudRepository) bestellingRepository;
		case BESTELLINGREGEL:
			return (CrudRepository) bestelRegelRepository;
		case PERSOON:
			return (CrudRepository) persoonRepository;
		}
		return null;
	}

}
