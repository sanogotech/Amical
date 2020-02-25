package app.gaugiciel.amical.repository.specification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

import app.gaugiciel.amical.model.Authentification;
import app.gaugiciel.amical.model.DemandePretEmpruntManuel;
import app.gaugiciel.amical.model.DemandePretEmpruntManuel_;
import app.gaugiciel.amical.model.Manuel;
import app.gaugiciel.amical.utilitaire.Utils;

public class DemandePretEmpruntManuelSpecification {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DemandePretEmpruntManuelSpecification.class);

	public static Specification<DemandePretEmpruntManuel> proprietaireEqual(Authentification proprietaire) {
		return (root, query, builder) -> {
			if (!Utils.isValid(proprietaire)) {
				return null;
			}
			return builder.equal(root.get(DemandePretEmpruntManuel_.PROPRIETAIRE), proprietaire);
		};
	}

	public static Specification<DemandePretEmpruntManuel> demandeurEqual(Authentification demandeur) {
		return (root, query, builder) -> {
			if (!Utils.isValid(demandeur)) {
				return null;
			}
			return builder.equal(root.get(DemandePretEmpruntManuel_.DEMANDEUR), demandeur);
		};
	}

	public static Specification<DemandePretEmpruntManuel> manuelIdEqual(Long manuelId) {
		return (root, query, builder) -> {
			if (!Utils.isValid(manuelId)) {
				return null;
			}
			return builder.equal(root.get(DemandePretEmpruntManuel_.MANUEL), manuelId);
		};
	}

	public static Specification<DemandePretEmpruntManuel> manuelEqual(Manuel manuel) {
		return (root, query, builder) -> {
			if (!Utils.isValid(manuel)) {
				return null;
			}
			return builder.equal(root.get(DemandePretEmpruntManuel_.MANUEL), manuel);
		};
	}

}
