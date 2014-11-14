package ar.edu.unju.fi.dao;

import java.util.Date;
import java.util.List;

import ar.edu.unju.fi.model.Pedido;

public interface PedidoDAO {
	
	List<Pedido> search(Date fecha, String estado);
	
	void insert(Pedido pedido);
	void update(Pedido pedido);
	void delete(Pedido pedido);
}