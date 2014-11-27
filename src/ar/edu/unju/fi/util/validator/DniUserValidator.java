package ar.edu.unju.fi.util.validator;

import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.sun.org.apache.bcel.internal.generic.GETSTATIC;

import ar.edu.unju.fi.bean.BaseBean;
import ar.edu.unju.fi.dao.UsuarioDAO;

/**
 * clase para validar el documento de un usuario cuando se crea un nuevo usuario
 */
@FacesValidator()
public class DniUserValidator extends BaseBean implements Validator{

	/**
	 * validator valida que el documento ingresado no coincida con ningun documento
	 * de ningun usuario en la BD
	 */
	@Override
	public void validate(FacesContext context, UIComponent component, Object value)
			throws ValidatorException {
			Integer documento=(Integer)value;
			UsuarioDAO dao = getService().getUsuarioDAO();
			if(dao.get(documento)!=null){
				String texto=null;
				try{
					ResourceBundle bundle = ResourceBundle.getBundle("ar.edu.unju.fi.resources.mensajes",context.getViewRoot().getLocale());
					texto = bundle.getString("msg_errorDniRepetido");
				}catch (Exception e) {
					e.printStackTrace();
				}
				throw new ValidatorException(
						new FacesMessage(texto));
			}
		
	}

}

