package ar.edu.unju.fi.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import ar.edu.unju.fi.dao.UsuarioDAO;
import ar.edu.unju.fi.dao.imp.UsuarioDAOImp;
import ar.edu.unju.fi.model.Usuario;

@ManagedBean
@SessionScoped
public class ABMUserBean implements Serializable{
	
	private Usuario usuario;
	private String banderaModif = "false";
	private Integer documento;
	private String apellido;
	private String nombre;
	private String estado;
	private List<Usuario> usuarioList;
	private String listaVacia ="true";
	
	public String search(){
		listaVacia = "true";
		UsuarioDAO dao = new UsuarioDAOImp();
		usuarioList = dao.buscarUser(documento, apellido, nombre, estado);
		if(!usuarioList.isEmpty()){
			listaVacia = "false";
			}

		return null;
	}
	
	public String aceptar(){
		UsuarioDAO dao = new UsuarioDAOImp();
		
		if(banderaModif.equals("false")){
			dao.insert(usuario);
		}else{
			dao.update(usuario);
		}
		return "listaUsuarios.xhtml?faces-redirect=true";
	}
	
	public String preInsert(){
		setUsuario(new Usuario());
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
		return "listaUsuarios?faces-redirect=true";
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

	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
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

	public String getListaVacia() {
		return listaVacia;
	}

	public void setListaVacia(String listaVacia) {
		this.listaVacia = listaVacia;
	}

}
