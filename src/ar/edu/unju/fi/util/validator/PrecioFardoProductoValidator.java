package ar.edu.unju.fi.util.validator;

import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * Clase para validar el precio por fardo de un producto
 *
 */
@FacesValidator("precioFardoProductoValidator")
public class PrecioFardoProductoValidator implements Validator {

	/**
	 * validator valida que el precio por fardo de un producto sea mayor al 
	 * precio unitario del mismo
	 */
	@Override
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {

		Double precioUnitario = (Double) component.getAttributes().get(
				"precioUnitario");
		Double precioFardo = Double.valueOf(value.toString());

		if (precioUnitario >= precioFardo) {
			String texto=null;
			try{
				ResourceBundle bundle = ResourceBundle.getBundle("ar.edu.unju.fi.resources.mensajes",context.getViewRoot().getLocale());
				texto = bundle.getString("msg_errorPrecioFardo");
			}catch (Exception e) {
				e.printStackTrace();
			}
			throw new ValidatorException(
					new FacesMessage(texto));
		}

	}

}

