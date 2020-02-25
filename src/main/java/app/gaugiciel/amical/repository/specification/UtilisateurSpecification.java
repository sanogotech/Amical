package app.gaugiciel.amical.repository.specification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

import app.gaugiciel.amical.model.Utilisateur;
import app.gaugiciel.amical.model.Utilisateur_;
import app.gaugiciel.amical.utilitaire.Utils;

public class UtilisateurSpecification {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UtilisateurSpecification.class);

	public static Specification<Utilisateur> emailContaining(String email) {
		return (root, query, builder) -> {
			if (!Utils.isValid(email)) {
				return null;
			}
			return builder.like(
					builder.function("unaccent", String.class,
							builder.upper(root.get(Utilisateur_.AUTHENTIFICATION_EMAIL))),
					"%" + Utils.normaliser(email) + "%");
		};
	}

	public static Specification<Utilisateur> prenomContaining(String prenom) {
		return (root, query, builder) -> {
			if (!Utils.isValid(prenom)) {
				return null;
			}
			return builder.like(
					builder.function("unaccent", String.class, builder.upper(root.get(Utilisateur_.PRENOM))),
					"%" + Utils.normaliser(prenom) + "%");
		};
	}

	public static Specification<Utilisateur> nomContaining(String nom) {
		return (root, query, builder) -> {
			if (!Utils.isValid(nom)) {
				return null;
			}
			return builder.like(builder.function("unaccent", String.class, builder.upper(root.get(Utilisateur_.NOM))),
					"%" + Utils.normaliser(nom) + "%");
		};
	}

	public static Specification<Utilisateur> proprietesContaining(String template) {
		return Specification.where(emailContaining(template)).or(prenomContaining(template))
				.or(nomContaining(template));
	}

}
