package ar.edu.unju.fi.dao;

import java.util.List;
import ar.edu.unju.fi.model.Producto;

public interface ProductoDAO {
	void insert(Producto p);

	void update(Producto p);

	void delete(Producto p);

	Producto get(int codigo);

	List<Producto> getAll();

	List<Producto> buscar(int codigo, String nombre, String estado);
}
