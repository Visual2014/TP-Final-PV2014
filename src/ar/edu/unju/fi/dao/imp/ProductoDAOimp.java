package ar.edu.unju.fi.dao.imp;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import ar.edu.unju.fi.dao.ProductoDAO;
import ar.edu.unju.fi.model.Producto;

public class ProductoDAOImp extends HibernateDaoSupport implements ProductoDAO {

	@Override
	public void insert(Producto p) {
		try {
			getHibernateTemplate().save(p);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void update(Producto p) {
		try {
			getHibernateTemplate().update(p);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Producto p) {
		try {
			getHibernateTemplate().delete(p);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Producto get(int codigo) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Producto.class);
		criteria.add(Restrictions.eq("codigo", codigo));
		Producto producto = null;
		try {
			producto = (Producto) criteria.list().get(0);
		} catch (java.lang.IndexOutOfBoundsException e) {
		}
		session.close();
		return producto;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Producto> getAll() {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Producto.class);
		List<Producto> list = criteria.list();
		session.close();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Producto> buscar(int codigo, String nombre, String estado) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Producto.class);
		if (codigo != 0) {
			criteria.add(Restrictions.eq("codigo", codigo));
		} else {
			if (!nombre.equals("")) {
				criteria.add(Restrictions.ilike("nombre", nombre,
						MatchMode.ANYWHERE));
			}
			if (!estado.equals("TODO")) {
				criteria.add(Restrictions.eq("estado", estado));
			}
		}
		criteria.addOrder(Order.asc("codigo"));
		List<Producto> list = criteria.list();

		session.close();
		return list;
	}

}
