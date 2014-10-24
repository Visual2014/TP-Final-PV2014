package ar.edu.unju.fi.dao;

import ar.edu.unju.fi.model.Usuario;
import java.util.List;

public interface UsuarioDAO {

	Usuario get(int dni);

	List<Usuario> getAll();

	Usuario validarPassword(Integer dni, String pass);
}
