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
import ar.edu.unju.fi.model.Usuario;
import ar.edu.unju.fi.model.constantes.EstadoUsuario;

@FacesValidator("loginUsuarioValidator")
public class LoginUsuarioValidator extends BaseBean implements Validator {
	static Logger logger=Logger.getLogger(LoginUsuarioValidator.class);
	
	@Override
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {

		int dni = Integer.valueOf(value.toString());
		logger.debug("validator usuario: " + dni);
		UsuarioDAO dao = getService().getUsuarioDAO();
		Usuario user = dao.get(dni);
		if (user != null) {
			if (user.getEstado().equals(EstadoUsuario.NO_ACTIVO)) {
				logger.debug("[+]no activo");
				throw new ValidatorException(new FacesMessage(
						FacesMessage.SEVERITY_WARN,
						"el usuario no esta activo", null));
			}
		} else {
			logger.debug("[+]no existe");
			throw new ValidatorException(new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "el usuario no existe", null));
		}
	}
}
