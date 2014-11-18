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

public class UsuarioDAOImp extends HibernateDaoSupport implements UsuarioDAO {
	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> getAll() {
		Criteria criteria = getSession().createCriteria(Usuario.class);
		criteria.addOrder(Order.asc("nombre"));

		List<Usuario> list = criteria.list();
		return list;
	}

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

	@Override
	public void update(Usuario u) {
		Session session = getSession();
		session.beginTransaction();
		session.update(u);
		session.getTransaction().commit();
		session.close();
		
	}

	@Override
	public void delete(Usuario u) {
		Session session = getSession();
		session.beginTransaction();
		session.delete(u);
		session.getTransaction().commit();
		session.close();
		
	}

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

		session.close();
		return list;
		//return getHibernateTemplate().findByCriteria(criteria);
	}
}
