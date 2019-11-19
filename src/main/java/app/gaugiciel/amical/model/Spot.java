package app.gaugiciel.amical.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

//Persistance
@Entity
@Table(indexes = { @Index(columnList = "lieu_france_id"), @Index(columnList = "nom"), @Index(columnList = "tag_q") })
//Lombok
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@Data
public class Spot implements Serializable {

	private static final long serialVersionUID = 1L;

	// Persistance
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	// Lombok
	@Setter(AccessLevel.PROTECTED)
	private long id;

	// Persistance
	@Column(nullable = false, length = 128)
	// Validation constraints
	@NotNull(message = "{validation.notnull}")
	@Size(min = 1, max = 128, message = "{validation.size.interval}")
	// Lombok
	@NonNull
	@ToString.Include
	private String nom;

	// Persistance
	@Column(length = 2000)
	// Validation constraints
	@Size(max = 2000, message = "{validation.size.max}")
	private String description;

	// Persistance
	@Column(length = 2000)
	// Validation constraints
	@Size(max = 2000, message = "{validation.size.max}")
	private String remarque;

	// Persistance
	@Column(name = "tag_q", nullable = false)
	// Validation constraints
	@NotNull(message = "{validation.notnull}")
	// Lombok
	@NonNull
	private boolean tagQ;

	// Persistance
	@ManyToOne
	@JoinColumn(name = "lieu_france_id", nullable = false)
	// Validation constraints
	@NotNull(message = "{validation.notnull}")
	// Lombok
	@NonNull
	private LieuFrance lieuFrance;

	// Persistance
	@ManyToOne
	@JoinColumn(name = "plan_id")
	private Plan plan;

}