package ar.edu.unju.fi.dao;


import ar.edu.unju.fi.model.Usuario;
import java.util.List;

public interface UsuarioDAO {
	public Usuario getUser(String dni);
	public List<Usuario> getAll();
	
}
