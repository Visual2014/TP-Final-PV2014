package ar.edu.unju.fi.util.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("precioFardoProductoValidator")
public class PrecioFardoProductoValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {

		Double precioUnitario = (Double) component.getAttributes().get(
				"precioUnitario");
		Double precioFardo = Double.valueOf(value.toString());

		if (precioUnitario >= precioFardo) {
			throw new ValidatorException(
					new FacesMessage(
							"El precio por fardo no puede ser menor al precio unitario"));
		}

	}

}