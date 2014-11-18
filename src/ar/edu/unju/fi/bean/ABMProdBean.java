package ar.edu.unju.fi.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;

import ar.edu.unju.fi.dao.ProductoDAO;
import ar.edu.unju.fi.model.Producto;

/**
 * clase bean asociada a las paginas de Lista de Productos y nuevos productos.
 * 
 */

@ManagedBean
@SessionScoped
public class ABMProdBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	//Atributos
	/**
	 * producto: Se utiliza para realizar todas las operaciones del ABM 
	 * banderaModif:se utiliza para decidir entre modificar un producto o crear uno nuevo
	 * codigo: almacena el codigo del producto ingresado por el usuario para realizar la busqueda
	 * nombre: almacena el nombre del producto ingresado por el usuario para realizar la busqueda
	 * estado: almacena el estado seleccionado por el usuario para realizar la busqueda
	 * productList: almacena la lista de productos que coinciden con los parametros de busqueda ingresados
	 */
	
	private Producto producto;
	private String banderaModif;
	private Integer codigo;
	private String nombre;
	private String estado;
	private List<Producto> productList;
	static Logger logger = Logger.getLogger(ABMProdBean.class);

	//Metodos
	/**
	 * Constructor del Bean
	 */
	public ABMProdBean() {
		banderaModif = "false";
	}

	/**
	 * Metodo que realiza la busqueda de productos en la BD y carga el DataTable de productos
	 */
	
	public String search() {
		logger.debug("test log4j");
		logger.debug("-------Busqueda");

		ProductoDAO dao = getService().getProductoDAO();
		productList = dao.buscar(codigo, nombre, estado);

		return null;
	}

	/**
	 * Metodo que realiza el alta de un producto nuevo o la modificacion de uno ya existente
	 * dependiendo de el valor del atributo banderaModif
	 * @return un {@code String} con la url de la lista de prodctos
	 */
	public String aceptar() {
		logger.debug("------ aceptar");
		ProductoDAO dao = getService().getProductoDAO();

		if (banderaModif.equals("false")) {
			dao.insert(producto);
		} else {
			dao.update(producto);
		}
		return "listaProductos.xhtml?faces-redirect=true";
	}

	/**
	 *Instancia un nuevo producto y asigna el valor "false" a banderaModif 
	 * @return un {@code String} con la url para crear o modificar un producto
	 */
	public String preInsert() {
		System.out.println("preinsert de Producto");
		logger.debug("--------preInsert");
		setProducto(new Producto());
		banderaModif = "false";
		return "altaProd.xhtml?faces-redirect=true";
	}

	/**
	 *Asigna el valor "true" a banderaModif 
	 * @return un {@code String} con la url para crear o modificar un producto
	 */
	public String preModif() {
		logger.debug("-------- preModif");
		banderaModif = "true";
		return "altaProd.xhtml?faces-redirect=true";
	}

	/**
	 * Elimina el producto seleccionado en la lista de productos
	 */
	public String preEliminar() {
		logger.debug("--------preEliminar");
		ProductoDAO dao = getService().getProductoDAO();
		dao.delete(producto);
		search();
		return null;
	}
	
	/**
	 * redirige a la pagina listaProductos.html
	 * @return un {@code String} con la url de la lista de productos
	 */
	public String url() {
		return "listaProductos?faces-redirect=true";
	}

	//Getters y Setters de los atributos
	
	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto product) {
		this.producto = product;
	}

	public String getBanderaModif() {
		return banderaModif;
	}

	public void setBanderaModif(String banderaModif) {
		this.banderaModif = banderaModif;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public List<Producto> getProductList() {
		return productList;
	}

	public void setProductList(List<Producto> productList) {
		this.productList = productList;
	}

	
}