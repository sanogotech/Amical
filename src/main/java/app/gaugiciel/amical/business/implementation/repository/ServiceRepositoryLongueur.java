package app.gaugiciel.amical.business.implementation.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.contrat.Repository;
import app.gaugiciel.amical.model.Longueur;
import app.gaugiciel.amical.repository.LongueurRepository;

@Service
public class ServiceRepositoryLongueur implements Repository<Longueur> {

	@Autowired
	private LongueurRepository longueurRepository;

	@Override
	public Longueur enregistrer(Longueur longueur) {
		return longueurRepository.save(longueur);
	}

	@Override
	public void modifier() {

	}

	@Override
	public void supprimer(Longueur longueur) {

	}

}
