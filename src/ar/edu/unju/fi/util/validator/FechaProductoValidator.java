package ar.edu.unju.fi.util.validator;

import java.util.Date;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * clase para validar la fecha cuando se crea un nuevo producto.
 * 
 * @author Mat-iaS
 * 
 */
@FacesValidator("fechaProductoValidator")
public class FechaProductoValidator implements Validator {

	/**
	 * validator valida si la fecha ingresada para el nuevo Producto es mayor a
	 * la actual.
	 */
	@Override
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {
		Date fecha = (Date) value;
		Date fechaActual = new Date();
		if (!fecha.after(fechaActual)) {
			String texto=null;
			try{
				ResourceBundle bundle = ResourceBundle.getBundle("ar.edu.unju.fi.resources.mensajes",context.getViewRoot().getLocale());
				texto = bundle.getString("msg_errorFecha");
			}catch (Exception e) {
				e.printStackTrace();
			}
			throw new ValidatorException(
					new FacesMessage(texto));
		}

	}

}
