package ar.edu.unju.fi.util.validator;

import java.util.Calendar;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.apache.log4j.Logger;

@FacesValidator("fechaPedidoValidator")
public class FechaPedidoValidator implements Validator {
	Logger logger = Logger.getLogger(FechaPedidoValidator.class);

	@Override
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {
		Date fecha = (Date) value;
		Date fechaActual = new Date();

		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);
		cal.add(Calendar.DAY_OF_YEAR, 1);
		fecha = cal.getTime();

		logger.debug("actual: " + fechaActual);
		logger.debug("param: " + fecha);
		logger.debug(fecha.before(fechaActual));
		if (fecha.before(fechaActual)) {
			throw new ValidatorException(
					new FacesMessage(
							"la fecha del pedido debe ser mayor o igual a la fecha actual"));
		}

	}

}
