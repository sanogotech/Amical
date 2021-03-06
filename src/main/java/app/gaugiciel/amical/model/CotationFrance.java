package app.gaugiciel.amical.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import app.gaugiciel.amical.utilitaire.Utils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

//Persistance
@Entity
@Table(name = "cotation_france", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "unite_principale", "unite_secondaire", "unite_tertiaire" }) }, indexes = {
				@Index(columnList = "unite_principale"), @Index(columnList = "unite_secondaire"),
				@Index(columnList = "unite_tertiaire") })
//Lombok
@NoArgsConstructor
@Getter
@Setter
@RequiredArgsConstructor(staticName = "creer")
public class CotationFrance implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String UNITE_PRINCIPALE = "unitePrincipale";
	public static final String UNITE_SECONDAIRE = "uniteSecondaire";
	public static final String UNITE_TERTIAIRE = "uniteTertiaire";
	public static final String ID = "id";

	// Persistance
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// Lombok
	@Setter(AccessLevel.PROTECTED)
	private Long id;

	// Persistance
	@Column(name = "unite_principale", nullable = false, length = 1)
	// Validation constraints
	@NotNull(message = "{validation.notnull}")
	@Size(min = 1, max = 1, message = "{validation.size.exact}")
	// Lombok
	@NonNull
	private String unitePrincipale;

	// Persistance
	@Column(name = "unite_secondaire", length = 3)
	// Validation constraints
	@Size(max = 3, message = "{validation.size.max}")
	private String uniteSecondaire;

	// Persistance
	@Column(name = "unite_tertiaire", length = 1)
	// Validation constraints
	@Size(min = 1, max = 1, message = "{validation.size.exact}")
	private String uniteTertiaire;

	private CotationFrance(String unitePrincipale, String uniteSecondaire, String uniteTertiaire) {
		this.unitePrincipale = unitePrincipale;
		this.uniteSecondaire = uniteSecondaire;
		this.uniteTertiaire = uniteTertiaire;
	}

	public static CotationFrance creer(String unitePrincipale, String uniteSecondaire, String uniteTertiaire) {
		return new CotationFrance(unitePrincipale, uniteSecondaire, uniteTertiaire);
	}

	@Override
	public String toString() {
		if (!Utils.isValid(unitePrincipale)) {
			return "";
		}
		return unitePrincipale + (Utils.isValid(uniteSecondaire) ? uniteSecondaire : "")
				+ (Utils.isValid(uniteTertiaire) ? uniteTertiaire : "");
	}

}
