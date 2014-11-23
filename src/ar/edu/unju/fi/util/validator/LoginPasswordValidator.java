package ar.edu.unju.fi.util.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.apache.log4j.Logger;

import ar.edu.unju.fi.bean.BaseBean;
import ar.edu.unju.fi.dao.UsuarioDAO;

/**
 * Clase para validar el password ingresado
 */
@FacesValidator("loginPasswordValidator")
public class LoginPasswordValidator extends BaseBean implements Validator {
	static Logger logger = Logger.getLogger(LoginPasswordValidator.class);

	/**
	 * validator valida que el documento y la contraseña coincidan con 
	 * los mismos datos de algun usuario de la BD
	 * 
	 */
	@Override
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {

		try {
			int dni = Integer.valueOf(component.getAttributes().get("dni")
					.toString());
			String pass = (String) value;
			UsuarioDAO dao = getService().getUsuarioDAO();

			logger.debug("validar DNI: " + dni + "  Pass: " + pass);
			if (dao.validarPassword(dni, pass) == null) {
				throw new ValidatorException(new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "error password", null));
			}

		} catch (NullPointerException e) {
//			e.printStackTrace();
		}

	}

}