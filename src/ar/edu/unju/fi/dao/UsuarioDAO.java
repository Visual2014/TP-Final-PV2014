package ar.edu.unju.fi.dao;

import ar.edu.unju.fi.model.Usuario;
import java.util.List;

public interface UsuarioDAO {
	void insert(Usuario u);
	
	void update(Usuario u);
	
	void delete(Usuario u);

	Usuario get(int documento);

	List<Usuario> getAll();

	Usuario validarPassword(Integer documento, String pass);
	
	List<Usuario> buscarUser(int documento, String apellido, String nombre, String estado);
}
