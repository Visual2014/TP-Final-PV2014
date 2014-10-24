package ar.edu.unju.fi.bean;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import ar.edu.unju.fi.dao.ProductoDAO;
import ar.edu.unju.fi.dao.imp.ProductoDAOimp;
import ar.edu.unju.fi.model.Producto;

@ManagedBean
@SessionScoped
public class AltaProdBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Producto producto;
	private String banderaModif = "false";

	/*
	 * llama al metodo add del manager para agregar el nuevo producto a la lista
	 */
	public String aceptar() {
		System.out.println("------- aceptar");
		ProductoDAO dao = new ProductoDAOimp();

		if (banderaModif.equals("false")) {
			dao.insert(producto);
		} else {
			dao.update(producto);
		}
		return "listaProductos.xhtml?faces-redirect=true";
	}

	// instancia el objeto producto de esta clase antes de ingresar a la pagina
	public String preInsert() {
		System.out.println("--------preInsert");
		setProducto(new Producto());
		banderaModif = "false";
		return "altaProd.xhtml?faces-redirect=true";
	}

	public String preModif() {
		System.out.println("-------- preModif");
		banderaModif = "true";
		return "altaProd.xhtml?faces-redirect=true";
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

}