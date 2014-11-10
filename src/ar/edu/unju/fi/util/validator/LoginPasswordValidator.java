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
import ar.edu.unju.fi.services.ServiceFacade;
import ar.edu.unju.fi.services.SpringUtil;

@FacesValidator("loginPasswordValidator")
public class LoginPasswordValidator extends BaseBean implements Validator {
	static Logger logger = Logger.getLogger(LoginPasswordValidator.class);

	@Override
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {

		int dni = Integer.valueOf(component.getAttributes().get("dni")
				.toString());
		String pass = (String) value;
		UsuarioDAO dao = getService().getUsuarioDAO();

		logger.debug("validar DNI: " + dni + "  Pass: " + pass);
		if (dao.validarPassword(dni, pass) == null) {
			throw new ValidatorException(new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "error password", null));
		}
	}

}