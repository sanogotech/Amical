package app.gaugiciel.amical.business.implementation.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.contrat.Repository;
import app.gaugiciel.amical.model.Manuel;
import app.gaugiciel.amical.repository.ManuelRepository;

@Service
public class ServiceRepositoryManuel implements Repository<Manuel> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceRepositoryManuel.class);

	@Autowired
	private ManuelRepository manuelRepository;

	@Override
	public Manuel enregistrer(Manuel manuel) {
		LOGGER.info("Start {}()", "enregistrer");
		return manuelRepository.save(manuel);
	}

	@Override
	public void modifier() {

	}

	@Override
	public void supprimer(Manuel manuel) {
		LOGGER.info("Start {}()", "supprimer");
		manuelRepository.delete(manuel);
	}

}
