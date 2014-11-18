package ar.edu.unju.fi.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;

import ar.edu.unju.fi.dao.RolUsuarioDAO;
import ar.edu.unju.fi.dao.UsuarioDAO;
import ar.edu.unju.fi.dao.imp.RolUsuarioDAOImp;
import ar.edu.unju.fi.dao.imp.UsuarioDAOImp;
import ar.edu.unju.fi.model.Rol;
import ar.edu.unju.fi.model.Usuario;

/**
 * clase bean asociada a las paginas de Lista de Usuarios y nuevos usuarios.
 */

@ManagedBean
@SessionScoped
public class ABMUserBean extends BaseBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	//Atributos
	/**
	 * usuario: se utiliza para realizar todas las operaciones del ABM
	 * banderaModif: se utiliza para decidir entre modificar un usuario o crear uno nuevo
	 * documento: almacena el documento del usuario para ralizar la busqueda
	 * apellido: almacena el apellido del usuario para ralizar la busqueda
	 * estado: almacena el estrado seleccionado para realizar la busqueda
	 * usuarioList: almacena la lista de usuarios que coinciden con los parametros de busqueda ingresados
	 */
	private Usuario usuario;
	private String banderaModif = "false";
	private Integer documento;
	private String apellido;
	private String estado;
	private List<Usuario> usuarioList;
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
			Rol rol= new Rol();
			rol.setRolId(1);
			usuario.setRol(rol);
			
			dao.insert(usuario);
		}else{
			dao.update(usuario);
		}
		return "listaUsuarios.xhtml?faces-redirect=true";
	}
	
<<<<<<< HEAD
	// 
=======
	/**
	 *Instancia un nuevo usuario y asigna el valor "false" a banderaModif 
	 * @return un {@code String} con la url para crear o modificar un usuario
	 */
>>>>>>> origin/master
	public String preInsert(){
//		setUsuario(new Usuario());
		System.out.println("preinsert de usuario");
		logger.debug("........pre Insert... usuario");
		setUsuario(new Usuario());
		rol1 = RolUsuarioDAOImp.clas;
		usuario.setRol(rol1);
		banderaModif = "false";

		return "altaUsuario.xhtml?faces-redirect=true";
	}
	
	/**
	 *Asigna el valor "true" a banderaModif 
	 * @return un {@code String} con la url para crear o modificar un usuario
	 */	
	public String preModif(){
		banderaModif = "true";
		return "altaUsuario.xhtml?faces-redirect=true";
	}
	
	/**
	 * Elimina el usuario seleccionado en la lista de usuarios
	 */
	public String preEliminar(){
		UsuarioDAO dao = new UsuarioDAOImp();
		dao.delete(usuario);
		search();
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


}
