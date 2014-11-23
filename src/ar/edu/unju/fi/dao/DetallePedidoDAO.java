package ar.edu.unju.fi.dao;

import java.util.List;

import ar.edu.unju.fi.model.DetallePedido;
import ar.edu.unju.fi.model.Pedido;

public interface DetallePedidoDAO {
	
	List<DetallePedido> getAll(Pedido pedido);
	
	void insert(DetallePedido detallePedido);
	void update(DetallePedido detallePedido);
	void delete(DetallePedido detallePedido);
}
