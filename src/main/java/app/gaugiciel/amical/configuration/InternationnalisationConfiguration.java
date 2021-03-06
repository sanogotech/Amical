package app.gaugiciel.amical.configuration;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class InternationnalisationConfiguration implements WebMvcConfigurer {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(InternationnalisationConfiguration.class);

	@Bean
	public LocaleResolver localeResolver() {
		LOGGER.info("Start {}()", "localeResolver");
		SessionLocaleResolver slr = new SessionLocaleResolver();
		slr.setDefaultLocale(Locale.FRANCE);
		return slr;
	}

	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LOGGER.info("Start {}()", "localeChangeInterceptor");
		LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
		lci.setParamName("lang");
		return lci;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		LOGGER.info("Start {}()", "addInterceptors");
		registry.addInterceptor(localeChangeInterceptor());
	}

}
