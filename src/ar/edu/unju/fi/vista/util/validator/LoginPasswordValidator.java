package ar.edu.unju.fi.vista.util.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import ar.edu.unju.fi.dao.RolUsuarioDAO;
import ar.edu.unju.fi.dao.UsuarioDAO;
import ar.edu.unju.fi.dao.imp.RolUsuarioDAOimp;
import ar.edu.unju.fi.dao.imp.UsuarioDAOImp;
import ar.edu.unju.fi.model.Usuario;

@FacesValidator("loginPasswordValidator")
public class LoginPasswordValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {

		int dni = Integer.valueOf(component.getAttributes().get("dni")
				.toString());
		String pass = (String) value;
		UsuarioDAO dao = new UsuarioDAOImp();

		System.out.println("validar DNI: " + dni + "  Pass: " + pass);
		if (dao.validarPassword(dni, pass) == null) {
			throw new ValidatorException(new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "error password", null));
		}
	}

}