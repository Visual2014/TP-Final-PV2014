package ar.edu.unju.fi.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;

import ar.edu.unju.fi.dao.UsuarioDAO;
import ar.edu.unju.fi.model.Rol;
import ar.edu.unju.fi.model.Usuario;

/**
 * clase bean asociada a las paginas de Lista de Usuarios y nuevos usuarios.
 */

@ManagedBean
@SessionScoped
public class ABMUserBean extends BaseBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/** se utiliza para realizar todas las operaciones del ABM*/
	private Usuario usuario;
	
	/** banderaModif: se utiliza para decidir entre modificar un usuario o crear uno nuevo */
	 private String banderaModif = "false";
	 
	 /** documento: almacena el documento del usuario para ralizar la busqueda */
	 private Integer documento;
	
	 /** apellido: almacena el apellido del usuario para ralizar la busqueda */
	 private String apellido;
	
	 /** estado: almacena el estrado seleccionado para realizar la busqueda */
	 private String estado;
	
	 /** usuarioList: almacena la lista de usuarios que coinciden con los parametros de busqueda ingresados */
	 private List<Usuario> usuarioList;
	 
	 /** datosPersonales: se utiliza para inhabilitar el cambio de rol al momento de modificar los datos personales */
	 private String datosPersonales = "false";
	
	 /** guarda el usuario actualmente logueado */
	 private Usuario logedUser;
	 
	static Logger logger = Logger.getLogger(ABMUserBean.class);
	 
	
	
	//Metodos
	
	/**
	 * Constructor del bean
	 */
	public ABMUserBean() {
	}
	
	/**
	 * Metodo que realiza la busqueda de usuarios en la BD y carga el DataTable de usuarios
	 */
	public String search(){
		logger.debug("test log4j");
		logger.debug("-------Busqueda");
		UsuarioDAO dao = getService().getUsuarioDAO();
		usuarioList = dao.buscarUser(documento, apellido, estado);
		return null;
	}

	/**
	 * Metodo que realiza el alta de un usuario nuevo o la modificacion de uno ya existente
	 * dependiendo de el valor del atributo banderaModif
	 * @return un {@code String} con la url de la lista de usuarios
	 */
	
	public String aceptar(){
		
		logger.debug(" ...... aceptar.....usuario");
		UsuarioDAO dao= getService().getUsuarioDAO();
	
		if(banderaModif.equals("false")){
//			Rol rol= new Rol();
//			rol.setRolId(1);
//			usuario.setRol(rol);

			dao.insert(usuario);
			
			logger.info("el Usuario " + logedUser.getDocumento() + "-"
					+ logedUser.getApellido() + " " + logedUser.getNombre()
					+ " creo un nuevo usuario "+ usuario.getRol().getDescripcion()+ " " + usuario.getDocumento()
					+ " - " + usuario.getApellido() + " " + usuario.getNombre());
		}else{
			dao.update(usuario);
			logger.info("el Usuario " + logedUser.getDocumento() + "-"
					+ logedUser.getApellido() + " " + logedUser.getNombre()
					+ " modifico el usuario "+ usuario.getRol().getDescripcion() + " " + usuario.getDocumento()
					+ " - " + usuario.getApellido() + " " + usuario.getNombre());
		}
		return "listaUsuarios.xhtml?faces-redirect=true";
	}

	/**
	 *Instancia un nuevo usuario y asigna el valor "false" a banderaModif 
	 * @return un {@code String} con la url para crear o modificar un usuario
	 */
	public String preInsert(){
		logger.debug("........pre Insert... usuario");
		setUsuario(new Usuario());
		usuario.setFechaCreacion(new Date());
		usuario.setUsuarioCreacion(logedUser.getDocumento());
		banderaModif = "false";

		return "altaUsuario.xhtml?faces-redirect=true";
	}
	
	/**
	 *Asigna el valor "true" a banderaModif 
	 * @return un {@code String} con la url para crear o modificar un usuario
	 */	
	public String preModif(){
		banderaModif = "true";
		datosPersonales="false";
		usuario.setUsuarioModif(logedUser.getDocumento());
		usuario.setFechaModif(new Date());
		return "altaUsuario.xhtml?faces-redirect=true";
	}
	
	public String preDatosPersonales(){
		banderaModif = "true";
		datosPersonales="true";
		usuario.setUsuarioModif(usuario.getDocumento());
		usuario.setFechaModif(new Date());
		return "altaUsuario.xhtml?faces-redirect=true";
	}
	
	/**
	 * Elimina el usuario seleccionado en la lista de usuarios
	 */
	public String preEliminar(){
		UsuarioDAO dao = getService().getUsuarioDAO();
		dao.delete(usuario);
		search();
		logger.warn("el Usuario " + logedUser.getDocumento() + "-"
				+ logedUser.getApellido() + " " + logedUser.getNombre()
				+ " elimino al "+ usuario.getRol().getDescripcion() + usuario.getDocumento()
				+ " - " + usuario.getApellido() + " " + usuario.getNombre());
		return null;
	}
	
	/**
	 * redirige a la pagina listaUsuarios.html
	 * @return un {@code String} con la url de la lista de usuarios
	 */
	public String url() {
		return "listaUsuarios.xhtml?faces-redirect=true";
	}

	//Getters y Setters de los atributos

	public Usuario getUsuario() {
		return usuario;
	}


	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	public String getBanderaModif() {
		return banderaModif;
	}


	public void setBanderaModif(String banderaModif) {
		this.banderaModif = banderaModif;
	}

	public Integer getDocumento() {
		return documento;
	}

	public void setDocumento(Integer documento) {
		this.documento = documento;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEstado() {
		return estado;
	}


	public void setEstado(String estado) {
		this.estado = estado;
	}


	public List<Usuario> getUsuarioList() {
		return usuarioList;
	}


	public void setUsuarioList(List<Usuario> usuarioList) {
		this.usuarioList = usuarioList;
	}

	public Usuario getLogedUser() {
		return logedUser;
	}

	public void setLogedUser(Usuario logedUser) {
		this.logedUser = logedUser;
	}

	public String getDatosPersonales() {
		return datosPersonales;
	}

	public void setDatosPersonales(String datosPersonales) {
		this.datosPersonales = datosPersonales;
	}


}
