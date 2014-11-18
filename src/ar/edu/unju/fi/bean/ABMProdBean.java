package ar.edu.unju.fi.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;

import ar.edu.unju.fi.dao.ProductoDAO;
import ar.edu.unju.fi.model.Producto;

@ManagedBean
@SessionScoped
public class ABMProdBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Producto producto;
	private String banderaModif;
	private Integer codigo;
	private String nombre;
	private String estado;
	private List<Producto> productList;
	static Logger logger = Logger.getLogger(ABMProdBean.class);

	public ABMProdBean() {
		banderaModif = "false";
	}

	public String search() {
		logger.debug("test log4j");
		logger.debug("-------Busqueda");

		ProductoDAO dao = getService().getProductoDAO();
		productList = dao.buscar(codigo, nombre, estado);

		return null;
	}

	/*
	 * llama al metodo add del manager para agregar el nuevo producto a la lista
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

	// instancia el objeto producto de esta clase antes de ingresar a la pagina
	public String preInsert() {
		System.out.println("preinsert de Producto");
		logger.debug("--------preInsert");
		setProducto(new Producto());
		banderaModif = "false";
		return "altaProd.xhtml?faces-redirect=true";
	}

	public String preModif() {
		logger.debug("-------- preModif");
		banderaModif = "true";
		return "altaProd.xhtml?faces-redirect=true";
	}

	public String preEliminar() {
		logger.debug("--------preEliminar");
		ProductoDAO dao = getService().getProductoDAO();
		dao.delete(producto);
		search();
		return null;
	}

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

	public String url() {
		return "listaProductos?faces-redirect=true";
	}
}