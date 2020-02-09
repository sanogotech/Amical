package app.gaugiciel.amical.controller.form;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Component;

import app.gaugiciel.amical.business.implementation.conversion.ServiceConversionTimestampToInputDate;
import app.gaugiciel.amical.model.Authentification;
import app.gaugiciel.amical.model.LieuFrance;
import app.gaugiciel.amical.model.Manuel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//Spring
@Component
//Lombok
@NoArgsConstructor
@Getter
@Setter
public class EditionTopoForm {

	public static final String ID = "id";
	public static final String NOM = "nom";
	public static final String DATE_PARUTION_INPUT = "dateParutionInput";
	public static final String AUTEUR = "auteur";
	public static final String DESCRIPTION = "description";
	public static final String REMARQUE = "remarque";
	public static final String ETAT = "etat";
	public static final String CATEGORIE = "categorie";
	public static final String AUTHENTIFICATION_EMAIL_INPUT = "authentificationEmailInput";
	public static final String LIEU_FRANCE_INPUT = "lieuFranceInput";
	private static final Map<String, Boolean> IS_NOT_NULL = new HashMap<>();

	static {
		IS_NOT_NULL.put(ID, true);
		IS_NOT_NULL.put(NOM, true);
		IS_NOT_NULL.put(DATE_PARUTION_INPUT, false);
		IS_NOT_NULL.put(AUTEUR, false);
		IS_NOT_NULL.put(DESCRIPTION, false);
		IS_NOT_NULL.put(REMARQUE, false);
		IS_NOT_NULL.put(ETAT, true);
		IS_NOT_NULL.put(CATEGORIE, true);
		IS_NOT_NULL.put(AUTHENTIFICATION_EMAIL_INPUT, true);
		IS_NOT_NULL.put(LIEU_FRANCE_INPUT, false);
	}

	@NotNull(message = "{validation.notnull}")
	private Long id;

	@NotNull(message = "{validation.notnull}")
	@Size(min = 1, max = 128, message = "{validation.size.interval}")
	private String nom;

	private String dateParutionInput;
	private Timestamp dateTimeParution;

	@Size(max = 128, message = "{validation.size.max}")
	private String auteur;

	@Size(max = 2000, message = "{validation.size.max}")
	private String description;

	@Size(max = 2000, message = "{validation.size.max}")
	private String remarque;

	@NotNull(message = "{validation.notnull}")
	@Size(min = 1, max = 64, message = "{validation.size.interval}")
	private String etat;

	@NotNull(message = "{validation.notnull}")
	@Size(min = 1, max = 64, message = "{validation.size.interval}")
	private String categorie;

	@NotNull(message = "{validation.notnull}")
	private String authentificationEmailInput;
	private Authentification authentification;

	private String lieuFranceInput;
	private LieuFrance lieuFrance;

	private EditionTopoForm(Manuel topo) {
		ServiceConversionTimestampToInputDate serviceConversionTimestampToInputDate = new ServiceConversionTimestampToInputDate();
		id = topo.getId();
		nom = topo.getNom();
		dateTimeParution = topo.getDateTimeParution();
		dateParutionInput = dateTimeParution == null ? ""
				: serviceConversionTimestampToInputDate.convertir(dateTimeParution);
		auteur = topo.getAuteur();
		description = topo.getDescription();
		remarque = topo.getRemarque();
		etat = topo.getEtat();
		categorie = topo.getCategorie();
		authentification = topo.getAuthentification();
		authentificationEmailInput = authentification == null ? "" : authentification.getEmail();
		lieuFrance = topo.getLieuFrance();
		lieuFranceInput = lieuFrance == null ? "" : lieuFrance.afficherLieuComplet();
	}

	public static EditionTopoForm creer(Manuel topo) {
		return new EditionTopoForm(topo);
	}

	public void reinitialiser() {
		Stream.of(getClass().getDeclaredFields()).forEach(field -> {
			field.setAccessible(true);
			try {
				field.set(this, null);
			} catch (IllegalArgumentException | IllegalAccessException e) {
			}
		});
	}

	public boolean isNotNull(String nomAttribut) {
		return IS_NOT_NULL.get(nomAttribut);
	}

	public void setObjets(Timestamp dateTimeParution, Authentification authentification, LieuFrance lieuFrance) {
		this.dateTimeParution = dateTimeParution;
		this.authentification = authentification;
		this.lieuFrance = lieuFrance;
	}

	public void updateTopo(Manuel topo) {
		topo.setAuteur(auteur);
		topo.setCategorie(categorie);
		topo.setDateTimeParution(dateTimeParution);
		topo.setDescription(description);
		topo.setEtat(etat);
		topo.setLieuFrance(lieuFrance);
		topo.setNom(nom);
		topo.setRemarque(remarque);
	}

}