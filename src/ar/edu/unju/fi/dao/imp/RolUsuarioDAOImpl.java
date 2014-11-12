package ar.edu.unju.fi.dao.imp;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import ar.edu.unju.fi.dao.RolUsuarioDAO;
import ar.edu.unju.fi.model.Rol;

public class RolUsuarioDAOImpl extends HibernateDaoSupport implements
		RolUsuarioDAO {

	@Override
	public Rol get(int rolId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Rol.class);
		criteria.add(Restrictions.eq("rolId", rolId));
		Rol unRol = (Rol) criteria.list().get(0);
		// session.close();
		return unRol;
	}

}
