package ar.edu.unju.fi.util.validator;

import java.util.ResourceBundle;

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

/**
 * clase para validar el usuario ingresado
 */
@FacesValidator("loginUsuarioValidator")
public class LoginUsuarioValidator extends BaseBean implements Validator {
	static Logger logger=Logger.getLogger(LoginUsuarioValidator.class);
	
	/**
	 * validator valida la existencia de un usuario en la BD y el estado del mismo 
	 */
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
				String texto=null;
				try{
					ResourceBundle bundle = ResourceBundle.getBundle("ar.edu.unju.fi.resources.mensajes",context.getViewRoot().getLocale());
					texto = bundle.getString("msg_errorUserInactivo");
				}catch (Exception e) {
					e.printStackTrace();
				}
				throw new ValidatorException(
						new FacesMessage(texto));
			}
		} else {
			logger.debug("[+]no existe");
			String texto=null;
			try{
				ResourceBundle bundle = ResourceBundle.getBundle("ar.edu.unju.fi.resources.mensajes",context.getViewRoot().getLocale());
				texto = bundle.getString("msg_errorUserInexistente");
			}catch (Exception e) {
				e.printStackTrace();
			}
			throw new ValidatorException(
					new FacesMessage(texto));
		}
	}
}


