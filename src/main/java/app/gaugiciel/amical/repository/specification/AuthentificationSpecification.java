package app.gaugiciel.amical.repository.specification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

import app.gaugiciel.amical.model.Authentification;
import app.gaugiciel.amical.utilitaire.Utils;

public class AuthentificationSpecification {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthentificationSpecification.class);

	public static Specification<Authentification> emailContaining(String email) {
		LOGGER.info("Start {}()", "emailContaining");
		return (root, query, builder) -> {
			if (!Utils.isValid(email)) {
				return null;
			}
			return builder.like(
					builder.function("unaccent", String.class, builder.upper(root.get(Authentification.EMAIL))),
					"%" + Utils.normaliser(email) + "%");
		};
	}

}
