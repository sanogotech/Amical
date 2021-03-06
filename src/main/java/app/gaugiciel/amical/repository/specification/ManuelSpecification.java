package app.gaugiciel.amical.repository.specification;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;

import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

import app.gaugiciel.amical.controller.form.RechercheTopoForm;
import app.gaugiciel.amical.model.Authentification;
import app.gaugiciel.amical.model.LieuFrance;
import app.gaugiciel.amical.model.Manuel;
import app.gaugiciel.amical.model.Utilisateur;
import app.gaugiciel.amical.utilitaire.Utils;

public class ManuelSpecification {

	private static final Logger LOGGER = LoggerFactory.getLogger(ManuelSpecification.class);

	public static Specification<Manuel> nomContaining(String nom) {
		LOGGER.info("Start {}()", "nomContaining");
		return (root, query, builder) -> {
			if (!Utils.isValid(nom)) {
				return null;
			}
			return builder.like(builder.function("unaccent", String.class, builder.upper(root.get(Manuel.NOM))),
					"%" + Utils.normaliser(nom) + "%");
		};
	}

	public static Specification<Manuel> lieuContaining(String lieu) {
		LOGGER.info("Start {}()", "lieuContaining");
		return (root, query, builder) -> {
			if (!Utils.isValid(lieu)) {
				return null;
			}

			Subquery<Long> subqueryLieuFrance = query.subquery(Long.class);
			Root<LieuFrance> rootLieuFrance = subqueryLieuFrance.from(LieuFrance.class);

			subqueryLieuFrance.select(rootLieuFrance.get(LieuFrance.ID))
					.where(LieuFranceSpecification.lieuContaining(lieu).toPredicate(rootLieuFrance, query, builder));
			query.where(root.get(Manuel.LIEU_FRANCE).in(subqueryLieuFrance));

			return query.getRestriction();
		};
	}

	public static Specification<Manuel> categorieEqual(String categorie) {
		LOGGER.info("Start {}()", "categorieEqual");
		return (root, query, builder) -> {
			if (!Utils.isValid(categorie)) {
				return null;
			}
			return builder.equal(root.get(Manuel.CATEGORIE), categorie);
		};
	}

	public static Specification<Manuel> etatEqual(String etat) {
		LOGGER.info("Start {}()", "etatEqual");
		return (root, query, builder) -> {
			if (!Utils.isValid(etat)) {
				return null;
			}
			return builder.equal(root.get(Manuel.ETAT), etat);
		};
	}

	public static Specification<Manuel> dateBetween(Timestamp t1, Timestamp t2) {
		LOGGER.info("Start {}()", "dateBetween");
		return (root, query, builder) -> {
			if (!Utils.isValid(t1) && !Utils.isValid(t2)) {
				return null;
			}
			if (!Utils.isValid(t1) && Utils.isValid(t2)) {
				return builder.between(root.get(Manuel.DATE_TIME_PARUTION), Timestamp.valueOf("1900-1-1 00:00:00"), t2);
			}
			if (Utils.isValid(t1) && !Utils.isValid(t2)) {
				return builder.between(root.get(Manuel.DATE_TIME_PARUTION), t1, Timestamp.from(Instant.now()));
			}
			return builder.between(root.get(Manuel.DATE_TIME_PARUTION), t1, t2);
		};
	}

	public static Specification<Manuel> auteurContaining(String auteur) {
		LOGGER.info("Start {}()", "auteurContaining");
		return (root, query, builder) -> {
			if (!Utils.isValid(auteur)) {
				return null;
			}
			return builder.like(builder.function("unaccent", String.class, builder.upper(root.get(Manuel.AUTEUR))),
					"%" + Utils.normaliser(auteur) + "%");
		};
	}

	public static Specification<Manuel> proprietaireContaining(String proprietaire) {
		LOGGER.info("Start {}()", "proprietaireContaining");
		return (root, query, builder) -> {
			if (!Utils.isValid(proprietaire)) {
				return null;
			}
			Subquery<String> subqueryUtilisateur = query.subquery(String.class);
			Root<Utilisateur> rootUtilisateur = subqueryUtilisateur.from(Utilisateur.class);
			subqueryUtilisateur.select(rootUtilisateur.get(Utilisateur.AUTHENTIFICATION_EMAIL))
					.where(UtilisateurSpecification.proprietesContaining(proprietaire).toPredicate(rootUtilisateur,
							query, builder));
			query.where(root.get(Manuel.AUTHENTIFICATION).in(subqueryUtilisateur));

			return query.getRestriction();
		};
	}

	public static Specification<Manuel> proprietaireEqual(Authentification proprietaire) {
		LOGGER.info("Start {}()", "proprietaireEqual");
		return (root, query, builder) -> {
			if (Objects.isNull(proprietaire)) {
				return null;
			}
			return builder.equal(root.get(Manuel.AUTHENTIFICATION), proprietaire);
		};
	}

	public static Specification<Manuel> hasAll(RechercheTopoForm rechercheTopoForm) {
		LOGGER.info("Start {}()", "hasAll");
		if (rechercheTopoForm.estVide()) {
			return null;
		}
		return Specification.where(nomContaining(rechercheTopoForm.getNom()))
				.and(lieuContaining(rechercheTopoForm.getLieuFranceTemplate()))
				.and(categorieEqual(rechercheTopoForm.getCategorie())).and(etatEqual(rechercheTopoForm.getEtat()))
				.and(dateBetween(rechercheTopoForm.getDateParutionMin(), rechercheTopoForm.getDateParutionMax()))
				.and(auteurContaining(rechercheTopoForm.getAuteur()))
				.and(proprietaireContaining(rechercheTopoForm.getProprietaireTemplate()));
	}

}
