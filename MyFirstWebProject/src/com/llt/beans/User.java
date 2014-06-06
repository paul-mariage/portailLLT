package com.llt.beans;

/**
 * 
 * @author Paul Mariage
 *
 */

public class User {

	private String login, password,groupe;
	private boolean autorisation;
	private int userCount = 0;
	
	public User(){
		this.login = "anonyme";
		this.password = "default";
	}
	
	public User(String login, String password,String groupe, boolean autorisation){
		this.login = login;
		this.password = password;
		this.groupe = groupe;
		this.autorisation=autorisation;
		userCount++;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAllowed() {
		return autorisation;
	}

	public void setAutorisation(boolean autorisation) {
		this.autorisation = autorisation;
	}
	
	public String getGroupe() {
		return groupe;
	}

	public void setGroupe(String groupe) {
		this.groupe = groupe;
	}
	
	public String toString()
	{
		return new String("Login : "+this.getLogin()+",Password : "+this.getPassword()+",Groupe : "+this.getGroupe()+",Allowed : "+this.isAllowed());
	}
	
}
