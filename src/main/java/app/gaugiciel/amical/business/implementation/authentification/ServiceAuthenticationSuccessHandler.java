package app.gaugiciel.amical.business.implementation.authentification;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.implementation.enumeration.NomModel;
import app.gaugiciel.amical.business.implementation.enumeration.RoleUtilisateur;
import app.gaugiciel.amical.business.implementation.recherche.ServiceRechercheUtilisateur;
import app.gaugiciel.amical.model.Utilisateur;

@Service
public class ServiceAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceAuthenticationSuccessHandler.class);

	@Autowired
	private ServiceRechercheUtilisateur serviceRechercheUtilisateur;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		LOGGER.info("Start {}()", "onAuthenticationSuccess");
		HttpSession session = request.getSession();
		Utilisateur utilisateur = serviceRechercheUtilisateur.findByEmail(authentication.getName());
		session.setAttribute(NomModel.UTILISATEUR.label, utilisateur);
		session.setAttribute(NomModel.AUTHENTIFICATION.label, utilisateur.getAuthentification());
		session.setAttribute("isUtilisateurAdmin", authentication.getAuthorities().stream().anyMatch(
				grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_" + RoleUtilisateur.ADMIN.name())));
		response.sendRedirect("/ami/accueil");
	}

}
