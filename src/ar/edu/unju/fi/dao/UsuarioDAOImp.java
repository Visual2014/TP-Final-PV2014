package ar.edu.unju.fi.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;

import ar.edu.unju.fi.hibernate.HibernateUtil;
import ar.edu.unju.fi.model.Usuario;

public class UsuarioDAOImp extends HibernateUtil implements UsuarioDAO{
	
	@Override
	public List<Usuario> getAll() {
		Criteria criteria = getSession().createCriteria(Usuario.class);
	    criteria.addOrder(Order.asc("nombre"));
	    return criteria.list();
	}
	
	@Override
	public Usuario getUser(String dni) {
		return null;
	}
}