package ar.edu.unju.fi.vista.util.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import ar.edu.unju.fi.dao.UsuarioDAO;
import ar.edu.unju.fi.dao.imp.UsuarioDAOImpl;

@FacesValidator()
public class DniUserValidator implements Validator{

	@Override
	public void validate(FacesContext context, UIComponent component, Object value)
			throws ValidatorException {
			Integer documento=(Integer)value;
			UsuarioDAO dao = new UsuarioDAOImpl();
			if(dao.get(documento)!=null){
				throw new ValidatorException(
						new FacesMessage("El documento ya existe"));
			}
		
	}

}
