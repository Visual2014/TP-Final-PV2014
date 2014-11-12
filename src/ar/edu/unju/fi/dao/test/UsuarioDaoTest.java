package ar.edu.unju.fi.dao.test;

import ar.edu.unju.fi.dao.UsuarioDAO;
import ar.edu.unju.fi.dao.imp.UsuarioDAOImpl;
import ar.edu.unju.fi.model.Usuario;

public class UsuarioDaoTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		UsuarioDAO dao = new UsuarioDAOImpl();

		for (Usuario u : dao.getAll()) {
			System.out.println(u.getNombre() + " " + u.getApellido());
		}
	}

}
