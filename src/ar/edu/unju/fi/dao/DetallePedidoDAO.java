package ar.edu.unju.fi.dao;

import java.util.List;

import ar.edu.unju.fi.model.DetallePedido;

public interface DetallePedidoDAO {
	
	List<DetallePedido> getAll();
	
	void insert(DetallePedido detallePedido);
	void update(DetallePedido detallePedido);
	void delete(DetallePedido detallePedido);
}
