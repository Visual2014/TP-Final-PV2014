package ar.edu.unju.fi.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;
import org.primefaces.event.RowEditEvent;
import org.springframework.dao.DeadlockLoserDataAccessException;

import ar.edu.unju.fi.dao.PedidoDAO;
import ar.edu.unju.fi.model.DetallePedido;
import ar.edu.unju.fi.model.Pedido;
import ar.edu.unju.fi.model.Producto;
import ar.edu.unju.fi.model.Usuario;
import ar.edu.unju.fi.model.constantes.EstadoPedido;

@ManagedBean
@SessionScoped
public class PedidoBean extends BaseBean implements Serializable {

	static Logger logger = Logger.getLogger(PedidoBean.class);

	private static final long serialVersionUID = 1L;

	// atributos para la busqueda
	private Date fechaBusqueda;
	private String estado;

	// listas
	public List<Pedido> listPedidos;
	public List<DetallePedido> listDetallePedido;

	// pedido
	public Date fechaNuevoPedido = new Date();
	public Pedido pedido;
	public Usuario logedUser;

	// detalle Pedido
	public DetallePedido unDetalle;
	public Producto producto;
	public Integer cantidad;
	public Integer detalleId;

	public String urlListaPedidos() {
		return "listaPedidos.xhtml?faces-redirect=true";
	}

	/*
	 * carga DataTable de listaPedidos.xhtml
	 */
	public void search() {
		logger.debug("------------- search Pedidos");
		listPedidos = getService().getPedidoDAO().search(fechaBusqueda, estado);
	}

	/*
	 * redirige a la pagina para realizar un Nuevo pedido
	 */
	public String urlNuevoPedido() {
		logger.debug("---------- nuevoPedido");
		
		listDetallePedido = new ArrayList<DetallePedido>();
		unDetalle = new DetallePedido();
		pedido = new Pedido();
		pedido.setFechaPedido(fechaNuevoPedido);
		pedido.setEstado(EstadoPedido.INICIADO);
		pedido.setFechaCreacion(new Date());
		pedido.setUsuarioCreacion(logedUser.getDocumento());
		return "nuevoPedido.xhtml?faces-redirect=true";
	}

	/*
	 * agreaga el producto seleccionado al detalle de Pedido
	 */
	public void addProducto() {

		logger.debug("producto:" + producto.getDescripcion());
		 DetallePedido unDetalle=new DetallePedido();
		unDetalle.setPedido(pedido);
		unDetalle.setCantidad(cantidad);
		unDetalle.setFechaCreacion(new Date());
		unDetalle.setFechaModificacion(null);
		unDetalle.setPrecioUnitario(unDetalle.getCantidad()
				* producto.getPrecioUnitario());
		unDetalle.setProducto(producto);
		unDetalle.setUsuarioCreacion(logedUser.getDocumento());
		unDetalle.setUsuarioModificacion(null);
		listDetallePedido.add(unDetalle);
	}

	public void removeProducto() {
		logger.debug("------- removeDet");
		logger.debug("remover el prod: "+unDetalle.getProducto().getNombre());
		listDetallePedido.remove(unDetalle);
	}
	
	public String grabarPedido(){
		Set<DetallePedido> detallesPedido=new HashSet<DetallePedido>(listDetallePedido);
		pedido.setDetallePedidos(detallesPedido);
		
		PedidoDAO pedidoDAO=getService().getPedidoDAO();
		
		pedidoDAO.insert(pedido);
		
		return urlListaPedidos();
	}
//	public void editRow(RowEditEvent event) {
//		logger.debug("fila editada");
//		logger.debug(listDetallePedido.get(0).getProducto().getNombre()+" cant:"+listDetallePedido.get(0).getCantidad());
//		DetallePedido detallePedido=(DetallePedido) event.getObject();
//		logger.debug(detallePedido.getProducto().getNombre()+" cant:"+detallePedido.getCantidad());
//	}
//	
//	public void cancelEditRow(RowEditEvent event){
//		logger.debug("edit fila cancelado");
//	}
	
	// Getters and Setters
	public List<Pedido> getListPedidos() {
		return listPedidos;
	}

	public void setListPedidos(List<Pedido> listPedidos) {
		this.listPedidos = listPedidos;
	}

	public Date getFechaBusqueda() {
		return fechaBusqueda;
	}

	public void setFechaBusqueda(Date fechaBusqueda) {
		this.fechaBusqueda = fechaBusqueda;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public List<DetallePedido> getListDetallePedido() {
		return listDetallePedido;
	}

	public void setListDetallePedido(List<DetallePedido> listDetallePedido) {
		this.listDetallePedido = listDetallePedido;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Date getFechaNuevoPedido() {
		return fechaNuevoPedido;
	}

	public void setFechaNuevoPedido(Date fechaNuevoPedido) {
		this.fechaNuevoPedido = fechaNuevoPedido;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Integer getDetalleId() {
		return detalleId;
	}

	public void setDetalleId(Integer detalleId) {
		this.detalleId = detalleId;
	}

	public Usuario getLogedUser() {
		return logedUser;
	}

	public void setLogedUser(Usuario logedUser) {
		this.logedUser = logedUser;
	}

	public DetallePedido getUnDetalle() {
		return unDetalle;
	}

	public void setUnDetalle(DetallePedido unDetalle) {
		this.unDetalle = unDetalle;
	}

}
