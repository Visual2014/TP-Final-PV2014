package ar.edu.unju.fi.dao.imp;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import ar.edu.unju.fi.dao.RolUsuarioDAO;
import ar.edu.unju.fi.model.Rol;
/**
 *clase para acceder a la tabla de roles de la BD 
 *
 */
public class RolUsuarioDAOImp extends HibernateDaoSupport implements
		RolUsuarioDAO {

	/**
	 * Busca un rol por su Id de la lista de roles de la BD
	 * @param rolId es el identificador del rol que deseamos encontrar
	 * @return unRol retorna el rol deseado segun el Id ingresado como parametro
	 */
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
