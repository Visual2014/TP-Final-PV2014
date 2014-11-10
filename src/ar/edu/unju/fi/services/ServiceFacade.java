package ar.edu.unju.fi.services;

import ar.edu.unju.fi.dao.ProductoDAO;
import ar.edu.unju.fi.dao.RolUsuarioDAO;
import ar.edu.unju.fi.dao.UsuarioDAO;

public class ServiceFacade {
	private UsuarioDAO usuarioDAO;
	private RolUsuarioDAO rolUsuarioDAO;
	private ProductoDAO productoDAO;

	public RolUsuarioDAO getRolUsuarioDAO() {
		return rolUsuarioDAO;
	}

	public void setRolUsuarioDAO(RolUsuarioDAO rolUsuarioDAO) {
		this.rolUsuarioDAO = rolUsuarioDAO;
	}

	public ProductoDAO getProductoDAO() {
		return productoDAO;
	}

	public void setProductoDAO(ProductoDAO productoDAO) {
		this.productoDAO = productoDAO;
	}

	public UsuarioDAO getUsuarioDAO() {
		return usuarioDAO;
	}

	public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}

}
