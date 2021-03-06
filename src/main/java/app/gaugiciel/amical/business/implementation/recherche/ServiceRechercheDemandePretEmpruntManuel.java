package app.gaugiciel.amical.business.implementation.recherche;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.contrat.Recherche;
import app.gaugiciel.amical.model.Authentification;
import app.gaugiciel.amical.model.DemandePretEmpruntManuel;
import app.gaugiciel.amical.model.Manuel;
import app.gaugiciel.amical.repository.DemandePretEmpruntManuelRepository;
import app.gaugiciel.amical.repository.specification.DemandePretEmpruntManuelSpecification;

@Service
public class ServiceRechercheDemandePretEmpruntManuel implements Recherche<DemandePretEmpruntManuel, Object> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceRechercheDemandePretEmpruntManuel.class);

	@Autowired
	private DemandePretEmpruntManuelRepository demandePretEmpruntManuelRepository;

	public List<DemandePretEmpruntManuel> findByProprietaire(Authentification proprietaire) {
		LOGGER.info("Start {}()", "findByProprietaire");
		return demandePretEmpruntManuelRepository
				.findAll(DemandePretEmpruntManuelSpecification.proprietaireEqual(proprietaire));
	}

	public List<DemandePretEmpruntManuel> findByDemandeur(Authentification demandeur) {
		LOGGER.info("Start {}()", "findByDemandeur");
		return demandePretEmpruntManuelRepository
				.findAll(DemandePretEmpruntManuelSpecification.demandeurEqual(demandeur));
	}

	public DemandePretEmpruntManuel findByManuelId(Long manuelId) {
		LOGGER.info("Start {}()", "findByManuelId");
		return demandePretEmpruntManuelRepository.findOne(DemandePretEmpruntManuelSpecification.manuelIdEqual(manuelId))
				.orElse(null);
	}

	public boolean existsByManuel(Manuel manuel) {
		LOGGER.info("Start {}()", "existsByManuel");
		return demandePretEmpruntManuelRepository.findOne(DemandePretEmpruntManuelSpecification.manuelEqual(manuel))
				.isPresent();
	}

}
