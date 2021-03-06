package app.gaugiciel.amical.business.implementation.recherche;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.contrat.Recherche;
import app.gaugiciel.amical.model.LieuFrance;
import app.gaugiciel.amical.repository.LieuFranceRepository;
import app.gaugiciel.amical.repository.specification.LieuFranceSpecification;
import app.gaugiciel.amical.utilitaire.Utils;

@Service
public class ServiceRechercheLieuFrance implements Recherche<LieuFrance, Object> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceRechercheLieuFrance.class);

	@Autowired
	private LieuFranceRepository lieuFranceRepository;

	public LieuFrance findById(Long id) {
		LOGGER.info("Start {}()", "findById");
		return lieuFranceRepository.findById(id).orElseThrow();
	}

	public List<String> rechercherLieuFrance(String lieuFrance) {
		LOGGER.info("Start {}()", "rechercherLieuFrance");
		List<LieuFrance> listeLieuFrance = lieuFranceRepository
				.findAll(LieuFranceSpecification.lieuContaining(lieuFrance));
		return listeLieuFrance.stream().sequential().limit(Utils.AUTO_COMPLETE_MAX_RESULTS)
				.map(lieu -> lieu.afficherLieuComplet()).collect(Collectors.toList());
	}

	public Optional<LieuFrance> findByNomComplet(String nomComplet) {
		LOGGER.info("Start {}()", "findByNomComplet");
		return lieuFranceRepository.findOne(LieuFranceSpecification.nomCompletEqual(nomComplet));
	}

}
