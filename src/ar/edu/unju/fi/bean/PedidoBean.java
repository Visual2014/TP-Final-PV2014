package ar.edu.unju.fi.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.event.RowEditEvent;

import ar.edu.unju.fi.dao.DetallePedidoDAO;
import ar.edu.unju.fi.dao.PedidoDAO;
import ar.edu.unju.fi.model.DetallePedido;
import ar.edu.unju.fi.model.Pedido;
import ar.edu.unju.fi.model.Producto;
import ar.edu.unju.fi.model.Rol;
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
	
	/** atributo usado para almacenar el usuario con el cual se hace la busqueda de pedidos en la BD  */
	private Integer userBusqueda; 

	// listas
	/** atributo usado para almacenar una lista de Pedidos para carga el DataTable de pedidos  */
	private List<Pedido> listPedidos;
	
	/** atributo usado para almacenar una lista detalles de pedido que despues sera asignada al pedido */
	private List<DetallePedido> listDetallePedido;

	// pedido
	/** atributo usado para almacenar la fecha del nuevo pedido */
	private Date fechaNuevoPedido;
	
	/** atributo usado para crear el nuevo Pedido */
	private Pedido pedido;
	
	/** Almacena el usuario logeado luego de la validacion */
	private Usuario logedUser;
	
	/** atributo usado para modificar los estados de un Pedido */
	private String nuevoEstado;

	
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
		fechaBusqueda=null;
		estado="TODO";
		userBusqueda=0;
		search();
		return "listaPedidos.xhtml?faces-redirect=true";
	}

	
	
	
	
	/** metodo que realiza la Busqueda de pedidos en la BD y carga el DataTable de pedidos. */
	public void search() {
		logger.debug("------------- search Pedidos");
		if (estado == null)
			estado = EstadoPedido.TODO;
		listPedidos = getService().getPedidoDAO().search(fechaBusqueda, estado,userBusqueda,logedUser);
	}





	/**
	 * redirige a la pagina para realizar un Nuevo pedido e instancia un detalle
	 * vacio y el pedido con sus datos correspondientes.
	 * @return un {@code String} con la url de la pagina para crear un nuevo pedido
	 */
	public String crearNuevoPedido() {
		logger.debug("---------- nuevoPedido");

		Boolean repiteFecha = false;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String fechaNueva = sdf.format(fechaNuevoPedido);

		// VALIDO SI YA EXISTE ALGUN PEDIDO CON LA FECHA NUEVA solo para Vendedores
		if(logedUser.getRol().getDescripcion().equals(Rol.VENDEDOR)){
			for (int i = 0; i < listPedidos.size() && !repiteFecha; i++) {
				String fechaExistente = sdf.format(listPedidos.get(i)
						.getFechaPedido());
	
				if (fechaExistente.equals(fechaNueva)) {
					repiteFecha = true;
				}
				logger.debug("existente: " + fechaExistente + " nueva" + fechaNueva
						+ repiteFecha);
			}
		}
		
		// SI EXISTE UN PEDIDO CON FECHA DE HOY MUESTRO MENSAJE
		logger.debug(repiteFecha);
		if (repiteFecha) {
			FacesMessage mensaje = new FacesMessage(
					"Ya Existe un pedido para esa fecha");
			FacesContext.getCurrentInstance().addMessage(null, mensaje);

			return null;

			// SI NO existe CREO EL PEDIDO Y LO GUARDO
		} else {
			listDetallePedido = new ArrayList<DetallePedido>();
			unDetalle = new DetallePedido();
			pedido = new Pedido();
			pedido.setFechaPedido(fechaNuevoPedido);
			pedido.setEstado(EstadoPedido.INICIADO);
			pedido.setFechaCreacion(new Date());
			pedido.setUsuarioCreacion(logedUser.getDocumento());

			PedidoDAO pedidoDAO = getService().getPedidoDAO();
			pedidoDAO.insert(pedido);
			return "nuevoPedido.xhtml?faces-redirect=true";
		}
	}

	
	
	
	
	
	
	/** crean un DetallePedido nuevo con el Producto y cantidad seleccionados y lo agrega a la lista de detalles. */
	public void addProducto() {
		boolean repetido = false;
		logger.debug("producto:" + producto.getDescripcion());

//		determino si el producto seleccionado ya se ha agregado
		for (int i = 0; i < listDetallePedido.size() && !repetido; i++) {
			Integer codigoEnLista = listDetallePedido.get(i).getProducto()
					.getCodigo();
			if (producto.getCodigo() == codigoEnLista)
				repetido = true;
		}

//		si el producto ya se ah agregado muestro mensaje, sino lo agrego 
		if (repetido) {
			FacesMessage mensaje = new FacesMessage(
					"el producto ya se encuentra en la lista.");
			FacesContext.getCurrentInstance().addMessage(null, mensaje);
		} else {
//			verifico si hay suficiente stock
			if (producto.getStock() < cantidad) {
				FacesMessage mensaje = new FacesMessage(
						"no hay Stock suficiente..");
				FacesContext.getCurrentInstance().addMessage(null, mensaje);
			} else {
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

				DetallePedidoDAO dao = getService().getDetallePedidoDAO();
				dao.insert(unDetalle);
				cargarListaDetalles();
			}
		}
	}

	
	
	
	
	
	
	/** elimina el Producto seleccionado de la Lista de Detalles */
	public void removeProducto() {
		logger.debug("remover el prod: " + unDetalle.getProducto().getNombre());

		DetallePedido nuevoDetalle = clonarDetalle(unDetalle);

		DetallePedidoDAO dao = getService().getDetallePedidoDAO();
		dao.delete(nuevoDetalle);

		cargarListaDetalles();
	}

	
	
	
	
	
	
	/** asigna la lista de detalles al pedido y guarda el pedido con sus correspondientes detalle en la BD. 
	 * @return un {@code String} con la url a la pagina de lista de Pedidos  */
	public String grabarPedido() {

		if (listDetallePedido.isEmpty()) {
			FacesMessage mensaje = new FacesMessage(
					"agregue productos al pedido");
			FacesContext.getCurrentInstance().addMessage(null, mensaje);

			return null;
		} else {
			// actualizo el HashSet de pedido usado para calcular el total
			DetallePedidoDAO detallePedidoDAO = getService()
					.getDetallePedidoDAO();
			List<DetallePedido> detallesList = detallePedidoDAO.getAll(pedido);
			Set<DetallePedido> detalles = new HashSet<>(detallesList);
			pedido.setDetallePedidos(detalles);

			// creo una copia del pedido antes de actualizarlo para evitar error
			// de 2 sessiones
			Pedido nuevoPedido = clonarPedido(pedido);

			PedidoDAO pedidoDAO = getService().getPedidoDAO();
			pedidoDAO.update(nuevoPedido);

			logger.info("el Usuario " + logedUser.getDocumento() + "-"
					+ logedUser.getApellido() + " " + logedUser.getNombre()
					+ " creo el pedido Id:" + nuevoPedido.getPedidoId()
					+ " con fecha:" + nuevoPedido.getFechaPedido() + " total: $"
					+ nuevoPedido.getTotal());

			return urlListaPedidos();

		}
	}

		
	
	
	
	
	
	public void updateEstado() {
		logger.debug("update nuevo estado " + nuevoEstado + " estado viejo"
				+ pedido.getEstado());

//		seteo el nuevo estado fecha y usuario de modificacion
		pedido.setEstado(nuevoEstado);
		pedido.setFechaModificacion(new Date());
		pedido.setUsuarioModificacion(logedUser.getDocumento());

		Pedido unPedido = clonarPedido(pedido);

		PedidoDAO dao = getService().getPedidoDAO();
		dao.update(unPedido);

		logger.info("el Usuario " + logedUser.getDocumento() + "-"
				+ logedUser.getApellido() + " " + logedUser.getNombre()
				+ " cambio a " + nuevoEstado + " el pedido Id: "
				+ pedido.getPedidoId());
		search();
	}


	
	
	
	
	
	public String preEdit() {
		
		pedido.setUsuarioModificacion(logedUser.getDocumento());
		pedido.setFechaModificacion(new Date());

//		cargo la lista de detalles q usa el DataTable con los detalles del pedido seleccionado
		cargarListaDetalles();

		return "nuevoPedido.xhtml?faces-redirect=true";
	}
	
	
	
	
	
	

	public void onRowEdit(RowEditEvent event) {

		// recupero el objeto detalle, seleccionado en el DataTable
		DetallePedido unDetalle = (DetallePedido) event.getObject();
		if (unDetalle.getProducto().getStock() < unDetalle.getCantidad()) {
			FacesMessage mensaje = new FacesMessage("no hay Stock suficiente..");
			FacesContext.getCurrentInstance().addMessage(null, mensaje);
		} else {
			// duplico el detalle para evitar 2 sessiones abiertas
			DetallePedido nuevoDetallePedido = clonarDetalle(unDetalle);

			DetallePedidoDAO detallePedidoDAO = getService()
					.getDetallePedidoDAO();
			detallePedidoDAO.update(nuevoDetallePedido);
			logger.info("el Usuario " + logedUser.getDocumento() + "-"
					+ logedUser.getApellido() + " " + logedUser.getNombre()
					+ " edito el detalle ID:" + nuevoDetallePedido.getDetallePedidoId());
			
		}
	}
	
		
		
		
		
	
	
	
	
	private void cargarListaDetalles() {
		DetallePedidoDAO dao = getService().getDetallePedidoDAO();
		Pedido unPedido = clonarPedido(pedido);
		listDetallePedido = dao.getAll(unPedido);
	}
	
	
	
	
	
	
	
	private Pedido clonarPedido(Pedido pedido) {
		Pedido unPedido = new Pedido();

		unPedido.setEstado(pedido.getEstado());
		unPedido.setFechaCreacion(pedido.getFechaCreacion());
		unPedido.setFechaModificacion(new Date());
		unPedido.setFechaPedido(pedido.getFechaPedido());
		unPedido.setPedidoId(pedido.getPedidoId());
		unPedido.setTotal(pedido.getTotal());
		unPedido.setUsuarioCreacion(pedido.getUsuarioCreacion());
		unPedido.setUsuarioModificacion(logedUser.getDocumento());
		Set detallesList = new HashSet<>(pedido.getDetallePedidos());
		unPedido.setDetallePedidos(detallesList);

		return unPedido;
	}
	
	
	
	
	
	
	private DetallePedido clonarDetalle(DetallePedido detallePedido) {
		DetallePedido nuevoDetalle = new DetallePedido();

		nuevoDetalle.setCantidad(detallePedido.getCantidad());
		nuevoDetalle.setDetallePedidoId(detallePedido.getDetallePedidoId());
		nuevoDetalle.setFechaCreacion(detallePedido.getFechaCreacion());
		nuevoDetalle.setFechaModificacion(detallePedido.getFechaModificacion());
		nuevoDetalle.setPedido(clonarPedido(detallePedido.getPedido()));
		nuevoDetalle.setPrecioUnitario(detallePedido.getPrecioUnitario());
		nuevoDetalle.setProducto(clonarProducto(detallePedido.getProducto()));
		nuevoDetalle.setUsuarioCreacion(detallePedido.getUsuarioCreacion());
		nuevoDetalle.setUsuarioModificacion(detallePedido
				.getUsuarioModificacion());

		return nuevoDetalle;
	}
	
	
	
	
	
	
	private Producto clonarProducto(Producto producto) {
		Producto clon = new Producto();

		clon.setCodigo(producto.getCodigo());
		clon.setDescripcion(producto.getDescripcion());
		clon.setEstado(producto.getEstado());
		clon.setFechaVencimiento(producto.getFechaVencimiento());
		clon.setNombre(producto.getNombre());
		clon.setPrecioFardo(producto.getPrecioFardo());
		clon.setPrecioUnitario(producto.getPrecioUnitario());
		clon.setStock(producto.getStock());
		clon.setTamanio(producto.getTamanio());

		return clon;
	}
	
	
	
	
	
	
	
	
	
	
	public void deletePedido() {
		DetallePedidoDAO detallesDAO = getService().getDetallePedidoDAO();
		PedidoDAO pedidoDAO = getService().getPedidoDAO();

		// traigo de la BD todos los detalles del pedido Seleccionado en el
		// DataTable para eliminarlos
		List<DetallePedido> listDetalles = detallesDAO.getAll(pedido);

		// por cada detalle de la lista lo clono y lo elimino de la BD
		for (DetallePedido detalle : listDetalles) {
			DetallePedido detalleClon = clonarDetalle(detalle);

			detallesDAO.delete(detalleClon);
		}

		// clono y elimino el pedido de la BD
		Pedido pedidoClon = clonarPedido(pedido);
		pedidoDAO.delete(pedidoClon);
		logger.info("el Usuario " + logedUser.getDocumento() + "-"
				+ logedUser.getApellido() + " " + logedUser.getNombre()
				+ " elimino el pedido ID:" + pedidoClon.getPedidoId() + "-"
				+ pedidoClon.getEstado() + " con fecha de Pedido:"
				+ pedidoClon.getFechaPedido() + " y fecha de creacion:"
				+ pedidoClon.getFechaCreacion());
		search();
	}
	
	
	
	
	
	
	public String getTotalDetalles(){
		double total=0d;
		for (DetallePedido d : listDetallePedido) {
			total +=d.getPrecioUnitario();
		}
		return "TOTAL: $ "+total;
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

	public Integer getUserBusqueda() {
		return userBusqueda;
	}

	public void setUserBusqueda(Integer userBusqueda) {
		this.userBusqueda = userBusqueda;
	}

}
