package ar.edu.unju.fi.dao.imp;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import ar.edu.unju.fi.dao.UsuarioDAO;
import ar.edu.unju.fi.hibernate.HibernateUtil;
import ar.edu.unju.fi.model.Usuario;

public class UsuarioDAOImp extends HibernateUtil implements UsuarioDAO {
	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> getAll() {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Usuario.class);
		criteria.addOrder(Order.asc("nombre"));

		List<Usuario> list = criteria.list();
		session.close();
		return list;
	}

	@Override
	public Usuario get(int dni) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Usuario.class);
		criteria.add(Restrictions.eq("documento", dni));
		Usuario user = null;
		try {
			user = (Usuario) criteria.list().get(0);
		} catch (java.lang.IndexOutOfBoundsException e) {
		}
		session.close();
		return user;
	}

	@Override
	public Usuario validarPassword(Integer dni, String pass) {
		List<Usuario> listaUsuarios = getAll();
		Usuario user = null;
		for (Usuario u : listaUsuarios) {
			if (u.getDocumento() == dni && u.getPassword().equals(pass)) {
				user = u;
			}
		}
		return user;
	}
}