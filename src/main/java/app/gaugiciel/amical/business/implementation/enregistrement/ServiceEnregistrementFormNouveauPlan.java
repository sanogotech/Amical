package app.gaugiciel.amical.business.implementation.enregistrement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.contrat.Enregistrement;
import app.gaugiciel.amical.business.implementation.repository.ServiceRepositoryPlan;
import app.gaugiciel.amical.controller.form.NouveauPlanForm;
import app.gaugiciel.amical.model.Plan;
import lombok.Getter;
import lombok.Setter;

@Service
public class ServiceEnregistrementFormNouveauPlan implements Enregistrement<NouveauPlanForm> {

	@Autowired
	private ServiceRepositoryPlan serviceRepositoryPlan;
	@Getter
	@Setter
	private Plan plan;

	@Override
	public void enregistrer(NouveauPlanForm nouveauPlanForm) {
		plan = serviceRepositoryPlan
				.enregistrer(Plan.creer(nouveauPlanForm.getNomFichier(), nouveauPlanForm.getDescription()));
	}

}
