package ar.edu.unju.fi.vista.util.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import ar.edu.unju.fi.dao.ProductoDAO;
import ar.edu.unju.fi.dao.imp.ProductoDAOimp;

@FacesValidator("codigoProductoValidator")
public class CodigoProductoValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {
		Integer codigo = (Integer) value;
		ProductoDAO dao = new ProductoDAOimp();
		if (dao.get(codigo) != null) {
			throw new ValidatorException(
					new FacesMessage("El codigo ya existe"));
		}
	}
}
