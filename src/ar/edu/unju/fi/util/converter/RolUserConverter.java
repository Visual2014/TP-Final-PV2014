package ar.edu.unju.fi.util.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import ar.edu.unju.fi.bean.BaseBean;
import ar.edu.unju.fi.dao.RolUsuarioDAO;
import ar.edu.unju.fi.model.Rol;


@FacesConverter("rolUserConverter")
public class RolUserConverter extends BaseBean implements Converter{

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		RolUsuarioDAO dao=getService().getRolUsuarioDAO();
		
		if(arg2.equals(Rol.ADMINISTRADOR))
			return dao.get(2);
		else
			return dao.get(1);
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		
		return arg2.toString();
	}

}
