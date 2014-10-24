package ar.edu.unju.fi.dao.imp;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import ar.edu.unju.fi.dao.ProductoDAO;
import ar.edu.unju.fi.hibernate.HibernateUtil;
import ar.edu.unju.fi.model.Producto;

public class ProductoDAOimp extends HibernateUtil implements ProductoDAO {

	@Override
	public void insert(Producto p) {
		Session session = getSessionFactory().openSession();
		session.beginTransaction();
		session.save(p);
		session.getTransaction().commit();
		session.close();

	}

	@Override
	public void update(Producto p) {
		Session session = getSessionFactory().openSession();
		session.beginTransaction();
		session.update(p);
		session.getTransaction().commit();
		session.close();
	}

	@Override
	public void delete(Producto p) {
		Session session = getSessionFactory().openSession();
		session.beginTransaction();
		session.delete(p);
		session.getTransaction().commit();
		session.close();
	}

	@Override
	public Producto get(int codigo) {
		Session session = getSessionFactory().openSession();
		Criteria criteria = session.createCriteria(Producto.class);
		criteria.add(Restrictions.eq("codigo", codigo));
		Producto producto = (Producto) criteria.list().get(0);
		session.close();
		return producto;
	}

	@Override
	public List<Producto> getAll() {
		Session session = getSessionFactory().openSession();
		Criteria criteria = session.createCriteria(Producto.class);
		List<Producto> list = criteria.list();
		session.close();
		return list;
	}

	@Override
	public List<Producto> buscar(int codigo, String nombre, String estado) {
		System.out.println("-----------buscar dao");
		System.out.println(codigo + " " + nombre + " " + estado);

		Session session = getSessionFactory().openSession();
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
