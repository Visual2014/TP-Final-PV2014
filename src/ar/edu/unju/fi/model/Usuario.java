package ar.edu.unju.fi.model;



/**
 * Usuario generated by hbm2java
 */
public class Usuario implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3305705219115929886L;
	private int documento;
	private Rol rol;
	private String nombre;
	private String apellido;
	private String estado;
	private String password;
	private String email;

	public Usuario() {
	}

	public Usuario(int documento) {
		this.documento = documento;
	}

	public Usuario(int documento, Rol rol, String nombre, String apellido,
			String estado, String password) {
		this.documento = documento;
		this.rol = rol;
		this.nombre = nombre;
		this.apellido = apellido;
		this.estado = estado;
		this.password = password;
	}

	public int getDocumento() {
		return this.documento;
	}

	public void setDocumento(int documento) {
		this.documento = documento;
	}

	public Rol getRol() {
		return this.rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return this.apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	

}
