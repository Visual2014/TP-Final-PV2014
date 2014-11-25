package ar.edu.unju.fi.util.validator;

import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.sun.faces.application.resource.Resource;

import ar.edu.unju.fi.bean.BaseBean;
import ar.edu.unju.fi.dao.ProductoDAO;

/**
 * clase para validar el codigo de producto cuando se crea un Nuevo producto.
 * 
 * @author Mat-iaS
 * 
 */
@FacesValidator("codigoProductoValidator")
public class CodigoProductoValidator extends BaseBean implements Validator {

	/**
	 * validator valida que el codigo ingresado para el nuevo producto no exista
	 * en la BD.
	 */
	@Override
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {
		Integer codigo = (Integer) value;
		ProductoDAO dao = getService().getProductoDAO();
		if (dao.get(codigo) != null) {
			String texto=null;
			try{
				ResourceBundle bundle = ResourceBundle.getBundle("ar.edu.unju.fi.resources.mensajes",context.getViewRoot().getLocale());
				texto = bundle.getString("msg_errorCodigoRepetido");
			}catch (Exception e) {
				e.printStackTrace();
			}
			throw new ValidatorException(
					new FacesMessage(texto));
		}
	}
}
