package ar.edu.unju.fi.util.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import ar.edu.unju.fi.bean.BaseBean;
import ar.edu.unju.fi.dao.ProductoDAO;
import ar.edu.unju.fi.dao.imp.ProductoDAOImp;
import ar.edu.unju.fi.services.ServiceFacade;
import ar.edu.unju.fi.services.SpringUtil;

@FacesValidator("codigoProductoValidator")
public class CodigoProductoValidator extends BaseBean implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {
		Integer codigo = (Integer) value;
		ProductoDAO dao = getService().getProductoDAO();
		if (dao.get(codigo) != null) {
			throw new ValidatorException(
					new FacesMessage("El codigo ya existe"));
		}
	}
}