package ar.edu.unju.fi.model;



import java.util.Date;

/**
 * DetallePedido generated by hbm2java
 */
public class DetallePedido implements java.io.Serializable {

	private static final long serialVersionUID = -4125974410518925306L;
	private int detallePedidoId;
	private Producto producto;
	private Pedido pedido;
	private Double precioUnitario;
	private Integer cantidad;
	private Integer usuarioCreacion;
	private Date fechaCreacion;
	private Integer usuarioModificacion;
	private Date fechaModificacion;

	public DetallePedido() {
	}

	public DetallePedido(int detallePedidoId) {
		this.detallePedidoId = detallePedidoId;
	}

	public DetallePedido(int detallePedidoId, Producto producto, Pedido pedido,
			Double precioUnitario, Integer cantidad,
			Integer usuarioCreacion, Date fechaCreacion,
			Integer usuarioModificacion, Date fechaModificacion) {
		this.detallePedidoId = detallePedidoId;
		this.producto = producto;
		this.pedido = pedido;
		this.precioUnitario = precioUnitario;
		this.cantidad = cantidad;
		this.usuarioCreacion = usuarioCreacion;
		this.fechaCreacion = fechaCreacion;
		this.usuarioModificacion = usuarioModificacion;
		this.fechaModificacion = fechaModificacion;
	}

	public int getDetallePedidoId() {
		return this.detallePedidoId;
	}

	public void setDetallePedidoId(int detallePedidoId) {
		this.detallePedidoId = detallePedidoId;
	}

	public Producto getProducto() {
		return this.producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Pedido getPedido() {
		return this.pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public Double getPrecioUnitario() {
		return this.producto.getPrecioUnitario()*this.getCantidad();
	}

	public void setPrecioUnitario(Double precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public Integer getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Integer getUsuarioCreacion() {
		return this.usuarioCreacion;
	}

	public void setUsuarioCreacion(Integer usuarioCreacion) {
		this.usuarioCreacion = usuarioCreacion;
	}

	public Date getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Integer getUsuarioModificacion() {
		return this.usuarioModificacion;
	}

	public void setUsuarioModificacion(Integer usuarioModificacion) {
		this.usuarioModificacion = usuarioModificacion;
	}

	public Date getFechaModificacion() {
		return this.fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

}
