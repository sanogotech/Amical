package app.gaugiciel.amical.controller.utils.implementation.comparaison;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import app.gaugiciel.amical.controller.utils.contrat.ComparaisonField;

@Component
public class ComparaisonFieldInteger implements ComparaisonField<Integer> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ComparaisonFieldInteger.class);

	@Override
	public int comparer(Integer arg1, Integer arg2) {
		LOGGER.info("Start {}()", "comparer");
		int i1 = Objects.isNull(arg1) ? 0 : arg1;
		int i2 = Objects.isNull(arg2) ? 0 : arg2;
		return i1 - i2;
	}

}
