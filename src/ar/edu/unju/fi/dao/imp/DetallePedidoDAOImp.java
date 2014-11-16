package ar.edu.unju.fi.dao.imp;

import java.util.List;

import org.hibernate.Criteria;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import ar.edu.unju.fi.dao.DetallePedidoDAO;
import ar.edu.unju.fi.model.DetallePedido;

public class DetallePedidoDAOImp extends HibernateDaoSupport implements DetallePedidoDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<DetallePedido> getAll() {
		Criteria criteria=getSession().createCriteria(DetallePedidoDAO.class);
		return criteria.list();
	}

	@Override
	public void insert(DetallePedido detallePedido) {
		try {
			getHibernateTemplate().save(detallePedido);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(DetallePedido detallePedido) {
		try {
			getHibernateTemplate().update(detallePedido);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(DetallePedido detallePedido) {
		try {
			getHibernateTemplate().delete(detallePedido);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}
	
}
