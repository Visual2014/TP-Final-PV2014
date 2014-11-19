package ar.edu.unju.fi.dao.imp;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import ar.edu.unju.fi.dao.UsuarioDAO;
import ar.edu.unju.fi.model.Usuario;

/**
 *Clase para acceder a la tabla de usuarios de la BD 
 *
 */
public class UsuarioDAOImp extends HibernateDaoSupport implements UsuarioDAO {
	
	
	/**
	 * metodo para traer la lista completa de usuarios de la BD
	 * @return list es la lista completa de usuarios existente en la BD 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> getAll() {
		Criteria criteria = getSession().createCriteria(Usuario.class);
		criteria.addOrder(Order.asc("nombre"));

		List<Usuario> list = criteria.list();
		return list;
	}

	/**
	 * metodo para buscar un usuario segun su numero de documento
	 * @param documento es el criterio de busqueda a utilizar
	 * @return user es el usuario cuyo documento coincide con el parametro ingresado
	 */
	@Override
	public Usuario get(int documento) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Usuario.class);
		criteria.add(Restrictions.eq("documento", documento));
		Usuario user = null;
		try {
			user = (Usuario) criteria.list().get(0);
		} catch (java.lang.IndexOutOfBoundsException e) {
		}
		return user;
	}

	/**
	 * metodo para validar el password ingresado
	 * @param documento es el identificador del usuario a validar
	 * @param pass es la contraseña ingresada, la cual sera validada
	 * @return user es el usuario cuyo documento y contraseña coinciden con los parametros ingresados
	 */
	@Override
	public Usuario validarPassword(Integer documento, String pass) {
		List<Usuario> listaUsuarios = getAll();
		Usuario user = null;
		for (Usuario u : listaUsuarios) {
			if (u.getDocumento() == documento && u.getPassword().equals(pass)) {
				user = u;
			}
		}
		return user;
	}

	/**
	 * metodo para crear un nuevo usuario en la BD
	 * @param u es el usuario que se agregará a la BD
	 */
	@Override
	public void insert(Usuario u) {
		try{
			System.out.println("agregando...............");
			getHibernateTemplate().save(u);
		}catch(DataAccessException e){
			e.printStackTrace();
		}
//		Session session = getSession();
//		session.beginTransaction();
//		session.save(u);
//		session.getTransaction().commit();
//		session.close();
		
	}

	/**
	 * metodo para actualizar los datos de un usario especifico
	 * @param u es el usuario cuyos datos seran modificados en la BD
	 */
	@Override
	public void update(Usuario u) {
		try{
			getHibernateTemplate().update(u);
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		
	}

	/**
	 * metodo para eliminar un usuario de la BD
	 * @param u es el usuario que será eliminado de la BD
	 */
	@Override
	public void delete(Usuario u) {
		try{
			getHibernateTemplate().delete(u);
		}catch(DataAccessException e){
			e.printStackTrace();
		}
		
	}

	/**
	 * metodo para buscar una lista de usuarios de la BD
	 * @param documento busca un solo usuario cuyo documento coincidaa con el parametro ingsado
	 * @param apellido busca todos los usuarios cuyo apellido coincida con el parametro ingresado
	 * @param estado busca todos los usuarios cuyo estado sea igual al ingresado
	 * @return list es la lista de los usuarios que coinciden con los parametros ingresados
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> buscarUser(int documento, String apellido, String estado) {

		Session session = getSession();
		Criteria criteria = getSession().createCriteria(Usuario.class);
		//DetachedCriteria criteria = DetachedCriteria.forClass(Usuario.class);
		if (documento != 0) {
			criteria.add(Restrictions.eq("documento", documento));
		} else {
			if(!apellido.equals("")){
				criteria.add(Restrictions.ilike("apellido", apellido,
						MatchMode.ANYWHERE));
			}
			if (!estado.equals("TODO")) {
				criteria.add(Restrictions.eq("estado", estado));
			}
		}
		criteria.addOrder(Order.asc("documento"));
		List<Usuario> list = criteria.list();

		return list;
		//return getHibernateTemplate().findByCriteria(criteria);
	}
}
