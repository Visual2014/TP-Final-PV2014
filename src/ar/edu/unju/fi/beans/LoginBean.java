package ar.edu.unju.fi.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class LoginBean {
	private String dni;
	private String password;
	
/****	valida el usuario logeado   ****/
	public String validar(){
		return dni;
		
	}
	
	
	
	
	
	
/****	getters and setters   ****/
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
