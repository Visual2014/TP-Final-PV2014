package ar.edu.unju.fi.vista.util.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import ar.edu.unju.fi.dao.UsuarioDAO;
import ar.edu.unju.fi.dao.imp.UsuarioDAOImp;
import ar.edu.unju.fi.model.Usuario;
import ar.edu.unju.fi.model.constantes.EstadoUsuario;

@FacesValidator("loginUsuarioValidator")
public class LoginUsuarioValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {

		System.out.println("--------validator usuario");
		int dni = Integer.valueOf(value.toString());
		UsuarioDAO dao = new UsuarioDAOImp();
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
