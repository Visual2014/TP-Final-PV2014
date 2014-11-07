package ar.edu.unju.fi.util.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import ar.edu.unju.fi.bean.BaseBean;
import ar.edu.unju.fi.dao.UsuarioDAO;
import ar.edu.unju.fi.dao.imp.UsuarioDAOImp;
import ar.edu.unju.fi.model.Usuario;
import ar.edu.unju.fi.model.constantes.EstadoUsuario;
import ar.edu.unju.fi.services.ServiceFacade;
import ar.edu.unju.fi.services.SpringUtil;

@FacesValidator("loginUsuarioValidator")
public class LoginUsuarioValidator extends BaseBean implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {

		int dni = Integer.valueOf(value.toString());
		System.out.println("validator usuario: " + dni);
		UsuarioDAO dao = getService().getUsuarioDAO();
		Usuario user = dao.get(dni);
		if (user != null) {
			if (user.getEstado().equals(EstadoUsuario.NO_ACTIVO)) {
				System.out.println("[+]no activo");
				throw new ValidatorException(new FacesMessage(
						FacesMessage.SEVERITY_WARN,
						"el usuario no esta activo", null));
			}
		} else {
			System.out.println("[+]no existe");
			throw new ValidatorException(new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "el usuario no existe", null));
		}
	}
}
