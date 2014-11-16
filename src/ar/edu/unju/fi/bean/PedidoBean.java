package ar.edu.unju.fi.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;

import ar.edu.unju.fi.dao.DetallePedidoDAO;
import ar.edu.unju.fi.dao.PedidoDAO;
import ar.edu.unju.fi.model.DetallePedido;
import ar.edu.unju.fi.model.Pedido;
import ar.edu.unju.fi.model.Producto;
import ar.edu.unju.fi.model.Usuario;
import ar.edu.unju.fi.model.constantes.EstadoPedido;

/**
 * clase bean asociada a las paginas de Lista de Pedidos y nuevos pedidos.
 * 
 * @author MaT-iaS
 * 
 */
@ManagedBean
@SessionScoped
public class PedidoBean extends BaseBean implements Serializable {

	static Logger logger = Logger.getLogger(PedidoBean.class);

	private static final long serialVersionUID = 1L;

	// atributos para la busqueda
	/** atributo usado para almacenar la fecha con la cual se hace la busqueda de pedidos en la BD */
	private Date fechaBusqueda;
	/** atributo usado para almacenar un Estado de pedido con la cual se hace la busqueda de pedidos en la BD  */
	private String estado;

	// listas
	/** atributo usado para almacenar una lista de Pedidos para carga el DataTable de pedidos  */
	private List<Pedido> listPedidos;
	/** atributo usado para almacenar una lista detalles de pedido que despues sera asignada al pedido */
	private List<DetallePedido> listDetallePedido;

	// pedido
	/** atributo usado para almacenar la fecha del nuevo pedido */
	private Date fechaNuevoPedido=new Date();
	/** atributo usado para crear el nuevo Pedido */
	private Pedido pedido;
	private Usuario logedUser;
	String nuevoEstado;
	
	
	
	
	// detalle Pedido
	/** atributo usado para crear un nuevo detalle y asignarlo al pedido. y para eliminar un detalle existente de la lista de detalles  */
	private DetallePedido unDetalle;
	/** atributo usado para almacenar un producto que luego sera asignado a un detalle de pedido.  */
	private Producto producto;
	/** cantidad: almacena la cantidad de un roducto que luego sera asignado a un detalle de pedido	 */
	private Integer cantidad;


	
	
	
	/** redirige a la pagina listaPedidos.html 
	 * @return un {@code String} con la url de lista de pedidos */
	public String urlListaPedidos() {
		search();
		return "listaPedidos.xhtml?faces-redirect=true";
	}

	
	
	
	
	
	
	/** metodo que realiza la Busqueda de pedidos en la BD y carga el DataTable de pedidos. */
	public void search() {
		logger.debug("------------- search Pedidos");
		if(estado==null)
			estado=EstadoPedido.TODO;
		listPedidos = getService().getPedidoDAO().search(fechaBusqueda, estado,logedUser);
	}





	/**
	 * redirige a la pagina para realizar un Nuevo pedido e instancia un detalle
	 * vacio y el pedido con sus datos correspondientes.
	 * @return un {@code String} con la url de la pagina para crear un nuevo pedido
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

	
	
	
	
	
	/** crean un DetallePedido nuevo con el Producto y cantidad seleccionados y lo agrega a la lista de detalles. */
	public void addProducto() {

		logger.debug("producto:" + producto.getDescripcion());
		DetallePedido unDetalle = new DetallePedido();
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

	
	
	
	
	
	
	/** elimina el Producto seleccionado de la Lista de Detalles */
	public void removeProducto() {
		logger.debug("remover el prod: " + unDetalle.getProducto().getNombre());
		listDetallePedido.remove(unDetalle);
	}

	
	
	
	
	
	
	/** asigna la lista de detalles al pedido y guarda el pedido con sus correspondientes detalle en la BD. 
	 * @return un {@code String} con la url a la pagina de lista de Pedidos  */
	public String grabarPedido() {
		
		//GUARDO EL PEDIDO
		PedidoDAO pedidoDAO = getService().getPedidoDAO();
		pedidoDAO.insert(pedido);
		
		//GUARDO LOS DETALLES
		DetallePedidoDAO detalleDao=getService().getDetallePedidoDAO();
		for (DetallePedido d : listDetallePedido) {
			detalleDao.insert(d);
		}
				
		logger.info("el Usuario "+logedUser.getDocumento()+"-"+logedUser.getApellido()+" "+logedUser.getNombre()+" guardo un nuevo pedido Id: "+pedido.getPedidoId());
		return urlListaPedidos();
	}

		
	
	
	
	
	
	public void updateEstado(){
		logger.debug("update nuevo estado "+nuevoEstado+" estado viejo"+ pedido.getEstado());
		
		pedido.setEstado(nuevoEstado);
		
		Pedido unPedido = new Pedido();
		
		unPedido.setEstado(pedido.getEstado());
		unPedido.setFechaCreacion(pedido.getFechaCreacion());
		unPedido.setFechaModificacion(new Date());
		unPedido.setFechaPedido(pedido.getFechaPedido());
		unPedido.setPedidoId(pedido.getPedidoId());
		unPedido.setTotal(pedido.getTotal());
		unPedido.setUsuarioCreacion(pedido.getUsuarioCreacion());
		unPedido.setUsuarioModificacion(logedUser.getDocumento());
		
		PedidoDAO dao=getService().getPedidoDAO();
		dao.update(unPedido);
		unPedido=null;
		
		logger.info("el Usuario "+logedUser.getDocumento()+"-"+logedUser.getApellido()+" "+logedUser.getNombre()+" cambio a "+nuevoEstado+" el pedido Id: "+pedido.getPedidoId());
		search();
	}


	
	
	
	
	
	
	
	
//Getters and Setters	
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

	public String getNuevoEstado() {
		return nuevoEstado;
	}

	public void setNuevoEstado(String nuevoEstado) {
		this.nuevoEstado = nuevoEstado;
	}

	
	
}
