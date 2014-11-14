package ar.edu.unju.fi.dao.imp;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import ar.edu.unju.fi.dao.PedidoDAO;
import ar.edu.unju.fi.model.Pedido;

public class PedidoDAOImp extends HibernateDaoSupport implements PedidoDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<Pedido> search(Date fecha, String estado) {
		Criteria criteria = getSession().createCriteria(Pedido.class);
		if (fecha != null){
			criteria.add(Restrictions.ge("fechaPedido", fecha));
			criteria.add(Restrictions.le("fechaPedido", fecha));
		}
		if(!estado.equals("TODO"))
			criteria.add(Restrictions.eq("estado", estado));
		return criteria.list();
	}

}
