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

/**
 *clase para acceder a la tabla Productos de la BD 
 *
 */
public class ProductoDAOImp extends HibernateDaoSupport implements ProductoDAO {

	/**
	 * metodo que permite guardar un producto en la BD
	 * @param p es el producto que será guardado en la BD
	 */
	@Override
	public void insert(Producto p) {
		try {
			getHibernateTemplate().save(p);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}

	}

	/**
	 * metodo que permite actualizar los datos de un producto en la BD
	 * @param p es el producto que sera modificado en la BD
	 */
	@Override
	public void update(Producto p) {
		try {
			getHibernateTemplate().update(p);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	/**
	 * metodo para eliminar un producto de la BD
	 * @param p es el producto a eliminar dela BD
	 */
	@Override
	public void delete(Producto p) {
		try {
			getHibernateTemplate().delete(p);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	/**
	 * metodo para buscar un producto en la BD
	 * @param codigo es el parametro de busqueda que se utilizara
	 * @return producto es el producto cuyo codigo coincide con el codigo ingresado como parametro
	 */
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

	/**
	 * metodo para traer todos los productos almacenados en la BD
	 * @return list es la lista con todos los productos almacenados en la BD
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Producto> getAll() {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Producto.class);
		List<Producto> list = criteria.list();
		session.close();
		return list;
	}

	/**
	 * metodo para buscar una lista de productos en la BD
	 * @param codigo busca un solo producto cuyo codigo coincida con el parametro ingresado
	 * @param nombre busca todos los productos cuyo nombre coincidan con el parametro ingresado
	 * @param estado busca todos los productos cuyo estado coincidan con el parametro ingresado
	 * @return list es la lista de todos los productos que coinciden con los parametros ingresados
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Producto> buscar(int codigo, String nombre, String estado) {
		logger.debug("buscarDAO  cod:"+codigo+ " nombre:"+nombre+" estado:"+estado);
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
