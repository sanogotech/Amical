package app.gaugiciel.amical.business.implementation.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.contrat.Repository;
import app.gaugiciel.amical.model.Spot;
import app.gaugiciel.amical.repository.SpotRepository;

@Service
public class ServiceRepositorySpot implements Repository<Spot> {

	@Autowired
	private SpotRepository spotRepository;

	@Override
	public Spot enregistrer(Spot spot) {
		return spotRepository.save(spot);
	}

	@Override
	public void modifier() {

	}

	@Override
	public void supprimer(Spot spot) {

	}

}
