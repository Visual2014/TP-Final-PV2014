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

@ManagedBean
@SessionScoped
public class ABMUserBean extends BaseBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Usuario usuario;
	private String banderaModif = "false";
	private Integer documento;
	private String apellido;
	private String estado;
	private List<Usuario> usuarioList;
	static Logger logger = Logger.getLogger(ABMUserBean.class);
	
	public String search(){
		logger.debug("test log4j");
		logger.debug("-------Busqueda");
		
		UsuarioDAO dao = getService().getUsuarioDAO();
		usuarioList = dao.buscarUser(documento, apellido, estado);
		return null;
	}
	
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
	
	// 
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
	
	public String preModif(){
		banderaModif = "true";
		return "altaUsuario.xhtml?faces-redirect=true";
	}
	
	public String preEliminar(){
		UsuarioDAO dao = new UsuarioDAOImp();
		dao.delete(usuario);
		search();
		return null;
	}
	
	public String url() {
		return "listaUsuarios.xhtml?faces-redirect=true";
	}
	
	
	public ABMUserBean() {
	}


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
