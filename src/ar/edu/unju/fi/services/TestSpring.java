package ar.edu.unju.fi.services;

import java.util.List;

import ar.edu.unju.fi.model.Usuario;

public class TestSpring {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ServiceFacade service = (ServiceFacade) SpringUtil
				.getBean("serviceFacade");

		List<Usuario> users = service.getUsuarioDAO().getAll();

		for (Usuario usuario : users) {
			System.out.println(usuario.getNombre());
		}
	}

}
