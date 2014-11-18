package ar.edu.unju.fi.vista.util.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import ar.edu.unju.fi.dao.UsuarioDAO;
import ar.edu.unju.fi.dao.imp.UsuarioDAOImp;

/**
 * clase para validar el documento de un usuario cuando se crea un nuevo usuario
 */
@FacesValidator()
public class DniUserValidator implements Validator{

	/**
	 * validator valida que el documento ingresado no coincida con ningun documento
	 * de ningun usuario en la BD
	 */
	@Override
	public void validate(FacesContext context, UIComponent component, Object value)
			throws ValidatorException {
			Integer documento=(Integer)value;
			UsuarioDAO dao = new UsuarioDAOImp();
			if(dao.get(documento)!=null){
				throw new ValidatorException(
						new FacesMessage("El documento ya existe"));
			}
		
	}

}
