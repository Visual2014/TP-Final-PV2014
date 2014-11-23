package ar.edu.unju.fi.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;

import ar.edu.unju.fi.dao.RolUsuarioDAO;
import ar.edu.unju.fi.dao.UsuarioDAO;
import ar.edu.unju.fi.model.Usuario;

/**
 * Clase bean utilizara para validar el usuario ingresado
 *
 */

@ManagedBean
@SessionScoped
public class LoginBean extends BaseBean {
	static Logger logger = Logger.getLogger(LoginBean.class);

	
	/** dni: Almacena el valor ingresado en el campo "dni" para realizar la validacion */
	public Integer dni;
	
	/** password: Almacena el valor ingresado en el campo "contraseña" para realizar la validacion */
	public String password;
	 
	/** logedUser: Almacena el usuario logeado luego de la validacion */
	public Usuario logedUser;
	
	
	
	
	//Metodos

	/**
	 * redirige a la pagina login.xhtml
	 * @return un {@code String} con la url de la pagina para logearse
	 */
	public String url() {
		logedUser = null;
		return "login?faces-redirect=true";
	}
	
	/**
	 * Metodo que realiza la validacion de los datos ingresados e ingresa a la pagina principal
	 * o da un mensaje de error, segun corresponda 
	 * @return un {@code String} con la url de la pagina principal
	 */
	public String ingresar() {
		UsuarioDAO dao = getService().getUsuarioDAO();
		RolUsuarioDAO daoRol = getService().getRolUsuarioDAO();

		Usuario user = dao.get(dni);
		user.setRol(daoRol.get(user.getRol().getRolId()));
		logedUser = user;
		logger.info(logedUser.getNombre() + " " + logedUser.getApellido()
				+ " Ha iniciado Session...");
		return "home?faces-redirect=true";
	}

	//Getters y Setters de los atributos
	
	public Integer getDni() {
		return dni;
	}

	public void setDni(Integer dni) {
		this.dni = dni;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Usuario getLogedUser() {
		return logedUser;
	}

	public void setLogedUser(Usuario logedUser) {
		this.logedUser = logedUser;
	}

}