package ar.edu.unju.fi.services;

import ar.edu.unju.fi.dao.PedidoDAO;
import ar.edu.unju.fi.dao.ProductoDAO;
import ar.edu.unju.fi.dao.RolUsuarioDAO;
import ar.edu.unju.fi.dao.UsuarioDAO;

public class ServiceFacade {
	private UsuarioDAO usuarioDAO;
	private RolUsuarioDAO rolUsuarioDAO;
	private ProductoDAO productoDAO;
	private PedidoDAO pedidoDAO;

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

	public PedidoDAO getPedidoDAO() {
		return pedidoDAO;
	}

	public void setPedidoDAO(PedidoDAO pedidoDAO) {
		this.pedidoDAO = pedidoDAO;
	}

}
