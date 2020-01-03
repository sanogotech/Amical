package app.gaugiciel.amical.business.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.contrat.ServiceRepository;
import app.gaugiciel.amical.model.Utilisateur;
import app.gaugiciel.amical.repository.UtilisateurRepository;

@Service
public class ServiceRepositoryUtilisateur implements ServiceRepository<Utilisateur> {

	@Autowired
	private UtilisateurRepository utilisateurRepository;

	@Override
	public void enregistrer(Utilisateur utilisateur) {
		utilisateurRepository.save(utilisateur);
	}

	@Override
	public void modifier() {

	}

	@Override
	public void supprimer(Utilisateur utilisateur) {

	}

}
