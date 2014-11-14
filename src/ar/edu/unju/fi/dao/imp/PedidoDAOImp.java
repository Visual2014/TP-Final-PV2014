package ar.edu.unju.fi.dao.imp;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import ar.edu.unju.fi.dao.PedidoDAO;
import ar.edu.unju.fi.model.Pedido;

/**
 * clase para acceder a la tabla Pedido de la BD. 
 * 
 * @author Mat-iaS
 * 
 */
public class PedidoDAOImp extends HibernateDaoSupport implements PedidoDAO {

	/**
	 * metodo que permite hacer una busqueda de pedidos en la BD
	 * 
	 * @param fecha Date para hacer la busqueda
	 * @param estado String un tipo de Estado de Pedido para Hacer la busqueda
	 * 
	 * @return retorna una lista con todos los elementos q coinciden con los parametros recibidos.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Pedido> search(Date fecha, String estado) {
		Criteria criteria = getSession().createCriteria(Pedido.class);
		if (fecha != null){
			criteria.add(Restrictions.ge("fechaPedido", fecha));
			criteria.add(Restrictions.le("fechaPedido", fecha));
		}
		if(!estado.equals("TODO"))
			criteria.add(Restrictions.eq("estado", estado));
		return criteria.list();
	}

	/**
	 * metodo que permite guardar un pedido en la BD.
	 * @param pedido Pedido que sera guardado en la BD
	 */
	@Override
	public void insert(Pedido pedido) {
		try {
			getHibernateTemplate().save(pedido);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	/**
	 * metodo que permite modificar un pedido en la BD.
	 * @param pedido Pedido que sera modificado en la BD
	 */
	@Override
	public void update(Pedido pedido) {
		try {
			getHibernateTemplate().update(pedido);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	/**
	 * metodo que permite eliminar un pedido de la BD.
	 * @param pedido Pedido que sera eliminado de la BD
	 */
	@Override
	public void delete(Pedido pedido) {
		try {
			getHibernateTemplate().delete(pedido);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	
}
