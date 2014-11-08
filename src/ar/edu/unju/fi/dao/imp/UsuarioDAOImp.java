package ar.edu.unju.fi.dao.imp;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
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
	public Usuario get(int dni) {
		Criteria criteria = getSession().createCriteria(Usuario.class);
		criteria.add(Restrictions.eq("documento", dni));
		Usuario user = null;
		try {
			user = (Usuario) criteria.list().get(0);
		} catch (java.lang.IndexOutOfBoundsException e) {
		}
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