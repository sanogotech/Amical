package app.gaugiciel.amical.controller.ami;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import app.gaugiciel.amical.business.implementation.enregistrement.ServiceEnregistrementFormEditionSecteur;
import app.gaugiciel.amical.business.implementation.enregistrement.ServiceEnregistrementFormNouveauSecteur;
import app.gaugiciel.amical.business.implementation.model.ServiceModel;
import app.gaugiciel.amical.business.implementation.recherche.ServiceRechercheSecteur;
import app.gaugiciel.amical.business.implementation.recherche.ServiceRechercheSpot;
import app.gaugiciel.amical.business.implementation.stockage.ServiceStockagePlan;
import app.gaugiciel.amical.business.implementation.url.ServiceRedirectionUrl;
import app.gaugiciel.amical.controller.form.EditionSecteurForm;
import app.gaugiciel.amical.controller.form.NouveauSecteurForm;
import app.gaugiciel.amical.controller.utils.implementation.validation.ValidationFormEditionSecteur;
import app.gaugiciel.amical.model.Plan;
import app.gaugiciel.amical.model.Secteur;
import app.gaugiciel.amical.model.Spot;

@Controller
@ControllerAdvice
public class AmiSecteurController {

	@Autowired
	private NouveauSecteurForm nouveauSecteurForm;
	@Autowired
	private ServiceRechercheSpot serviceRechercheSpot;
	@Autowired
	private ServiceEnregistrementFormNouveauSecteur serviceEnregistrementFormNouveauSecteur;
	@Autowired
	private ServiceRechercheSecteur serviceRechercheSecteur;
	@Autowired
	private ValidationFormEditionSecteur validationFormEditionSecteur;
	@Autowired
	private ServiceEnregistrementFormEditionSecteur serviceEnregistrementFormEditionSecteur;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private HttpSession session;

	@GetMapping("/ami/spot/{spotId}/secteur/nouveau")
	public String showNouveauSecteurForm(@PathVariable Long spotId, Model model, HttpServletRequest request) {
		nouveauSecteurForm.reinitialiser();
		Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
		if (inputFlashMap != null) {
			Plan plan = (Plan) inputFlashMap.get("plan");
			model.addAttribute("messagePlanEnregistreAvecSucces", messageSource.getMessage(
					"message.planEnregistreAvecSucces", new String[] { plan.getPlan() }, Locale.getDefault()));
			nouveauSecteurForm.setPlan(plan);
		}
		Spot spot = serviceRechercheSpot.findById(spotId);
		nouveauSecteurForm.setSpot(spot);
		nouveauSecteurForm.setNomSpot(spot.getNom());
		model.addAttribute("spotActive", "active");
		model.addAttribute("spotId", spotId);
		session.setAttribute(ServiceRedirectionUrl.SECTEUR_FORM.label,
				"redirect:/ami/spot/" + spotId + "/secteur/nouveau");
		session.setAttribute(ServiceRedirectionUrl.PREVIOUS_URL.label,
				"redirect:/ami/spot/" + spotId + "/secteur/nouveau");
		return "ami_secteur_nouveau";
	}

	@PostMapping("/ami/spot/{spotId}/secteur/nouveau")
	public String checkNouveauSecteurForm(@Valid NouveauSecteurForm nouveauSecteurForm, @PathVariable Long spotId,
			BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("spotActive", "active");
			return "ami_secteur_nouveau";
		}
		redirectAttributes.addFlashAttribute("nouveauSecteurForm", nouveauSecteurForm);
		return "redirect:/ami/spot/" + spotId + "/secteur/nouveau/enregistrement";
	}

	@GetMapping("/ami/spot/{spotId}/secteur/nouveau/enregistrement")
	public String saveNouveauSecteurForm(@PathVariable Long spotId, HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
		if (inputFlashMap != null) {
			NouveauSecteurForm nouveauSecteurForm = (NouveauSecteurForm) inputFlashMap.get("nouveauSecteurForm");
			serviceEnregistrementFormNouveauSecteur.enregistrer(nouveauSecteurForm);
			redirectAttributes.addFlashAttribute("secteur", serviceEnregistrementFormNouveauSecteur.getSecteur());
		}
		return (String) session.getAttribute(ServiceRedirectionUrl.SPOT.label);
	}

	@GetMapping("/ami/spot/{spotId}/secteur/{secteurId}/edition")
	public String showEditionSecteurForm(@PathVariable Long spotId, @PathVariable Long secteurId, Model model) {
		Secteur secteur = serviceRechercheSecteur.findById(secteurId);
		EditionSecteurForm editionSecteurForm = EditionSecteurForm.creer(secteur);
		String urlRedirection = "redirect:/ami/spot/" + spotId + "/secteur/" + secteurId + "/edition";
		model.addAttribute("spot", secteur.getSpot());
		model.addAttribute("secteur", secteur);
		model.addAttribute("editionSecteurForm", editionSecteurForm);
		model.addAttribute("spotActive", "active");
		model.addAttribute("urlSpot", ((String) session.getAttribute(ServiceRedirectionUrl.SPOT.label)).split(":")[1]);
		session.setAttribute(ServiceRedirectionUrl.EDITION_SECTEUR_FORM.label, urlRedirection);
		session.setAttribute(ServiceRedirectionUrl.PREVIOUS_URL.label, urlRedirection);
		return "ami_secteur_edition";
	}

	@PostMapping("/ami/spot/{spotId}/secteur/{secteurId}/edition")
	public String checkEditionSecteurForm(@Valid EditionSecteurForm editionSecteurForm, @PathVariable Long spotId,
			@PathVariable Long secteurId, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {
		Secteur secteur = serviceRechercheSecteur.findById(secteurId);
		if (!validationFormEditionSecteur.isValide(editionSecteurForm)) {
			validationFormEditionSecteur.getListeFieldError().forEach(fieldError -> bindingResult.addError(fieldError));
		}
		if (bindingResult.hasErrors()) {
			model.addAttribute("spot", secteur.getSpot());
			model.addAttribute("secteur", secteur);
			model.addAttribute("spotActive", "active");
			return "ami_secteur_edition";
		}
		redirectAttributes.addFlashAttribute("editionSecteurForm", editionSecteurForm);
		return "redirect:/ami/spot/" + spotId + "/secteur/" + secteurId + "/edition/enregistrement";
	}

	@GetMapping("/ami/spot/{spotId}/secteur/{secteurId}/edition/enregistrement")
	public String saveEditionSecteurForm(@PathVariable Long spotId, @PathVariable Long secteurId,
			HttpServletRequest request, RedirectAttributes redirectAttributes) {
		Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
		if (inputFlashMap != null) {
			EditionSecteurForm editionSecteurForm = (EditionSecteurForm) inputFlashMap.get("editionSecteurForm");
			editionSecteurForm.setPlan(validationFormEditionSecteur.getPlan());
			serviceEnregistrementFormEditionSecteur.enregistrer(editionSecteurForm);
			redirectAttributes.addFlashAttribute("secteur", serviceEnregistrementFormEditionSecteur.getSecteur());
		}
		return (String) session.getAttribute(ServiceRedirectionUrl.SPOT.label);
	}

	@ModelAttribute
	public void addAttributes(Model model) {
		model.addAttribute("nouveauSecteurForm", nouveauSecteurForm);
		model.addAttribute(ServiceModel.UTILISATEUR.label, session.getAttribute(ServiceModel.UTILISATEUR.label));
		model.addAttribute("cheminPlan", ServiceStockagePlan.RESOURCE_HANDLER_PLAN);
	}

}
