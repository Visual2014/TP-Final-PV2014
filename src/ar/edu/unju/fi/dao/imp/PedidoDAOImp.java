package ar.edu.unju.fi.dao.imp;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import ar.edu.unju.fi.dao.PedidoDAO;
import ar.edu.unju.fi.model.Pedido;
import ar.edu.unju.fi.model.Rol;
import ar.edu.unju.fi.model.Usuario;

/**
 * clase para acceder a la tabla Pedido de la BD. 
 * 
 * @author Mat-iaS
 * 
 */
public class PedidoDAOImp extends HibernateDaoSupport implements PedidoDAO {

	/**
	 * metodo que permite hacer una busqueda de pedidos en la BD
	 * 
	 * @param fecha Date para hacer la busqueda
	 * @param estado String un tipo de Estado de Pedido para Hacer la busqueda
	 * 
	 * @return retorna una lista con todos los elementos q coinciden con los parametros recibidos.
	 */
	@SuppressWarnings("unchecked")
	@Override
	
	
	public List<Pedido> search(Date fecha, String estado,Integer user, Usuario logedUser) {
		logger.debug("date recibida" +fecha);
		
		
		String rolUsuario=logedUser.getRol().getDescripcion();
		Integer usuarioCreacion=logedUser.getDocumento();
		
		//creo calendario para sumar dias
		Calendar cal=Calendar.getInstance();
		
		Criteria criteria = getSession().createCriteria(Pedido.class);
		
		//si la fecha no es nula filtro por fecha
		if(fecha != null){
			//seteo el calendario y determino las fechas max y min
			cal.setTime(fecha);
			cal.add(Calendar.DATE, 1);
			Date maxFecha=cal.getTime();
			cal.add(Calendar.DATE, -2);
			Date minFecha=cal.getTime();
			logger.debug("desde: "+minFecha+" hasta "+maxFecha);
			
			//filtro objetos q esten entre las 2 fechas max y min
			criteria.add(Restrictions.gt("fechaPedido", minFecha));
			criteria.add(Restrictions.lt("fechaPedido", maxFecha));
		}
		
		//filtro por estado segun el valor q traiga la varible estado
		if(!estado.equals("TODO"))
			criteria.add(Restrictions.eq("estado", estado));
		
//		si el user logeado es Admin filtro pedidos creados por el dni de usuario ingresado
		if(rolUsuario.equals(Rol.ADMINISTRADOR)){
			if(user!=0)
				criteria.add(Restrictions.eq("usuarioCreacion", user));
		}
		
		// si el usuario no es un ADMINISTADOR filtro objetos por dni de usuario
		if(!rolUsuario.equals(Rol.ADMINISTRADOR)){
			criteria.add(Restrictions.eq("usuarioCreacion", usuarioCreacion));
		}
		
		criteria.addOrder(Order.asc("pedidoId"));
		return criteria.list();
		
	}

	/**
	 * metodo que permite guardar un pedido en la BD.
	 * @param pedido Pedido que sera guardado en la BD
	 */
	@Override
	public void insert(Pedido pedido) {
		try {
			getHibernateTemplate().save(pedido);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	
	
	/**
	 * metodo que permite modificar un pedido en la BD.
	 * @param pedido Pedido que sera modificado en la BD
	 */
	@Override
	public void update(Pedido pedido) {
		try {
			getHibernateTemplate().update(pedido);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		
	}

	
	
	/**
	 * metodo que permite eliminar un pedido de la BD.
	 * @param pedido Pedido que sera eliminado de la BD
	 */
	@Override
	public void delete(Pedido pedido) {
		try {
			getHibernateTemplate().delete(pedido);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	
}
