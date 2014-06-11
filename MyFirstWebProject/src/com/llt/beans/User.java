package com.llt.beans;

/**
 * 
 * @author Paul Mariage
 *
 */

public class User {

	private String login,password,nom,prenom,email,groupe;
	private boolean autorisation;
	private int userCount = 0;
	
	public User(){
		this.login = "anonyme";
		this.password = "default";
	}
	
	public User(String login, String password,String nom,String prenom,String email,String groupe, boolean autorisation){
		this.login = login;
		this.password = password;
		this.groupe = groupe;
		this.nom=nom;
		this.prenom=prenom;
		this.email=email;
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

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getUserCount() {
		return userCount;
	}

	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}

	public boolean isAutorisation() {
		return autorisation;
	}

	@Override
	public String toString() {
		return "User [login=" + login + ", password=" + password + ", nom="
				+ nom + ", prenom=" + prenom + ", email=" + email + ", groupe="
				+ groupe + ", autorisation=" + autorisation + ", userCount="
				+ userCount + "]";
	}
	
	
	
}
