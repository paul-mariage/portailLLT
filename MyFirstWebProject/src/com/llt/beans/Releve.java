package com.llt.beans;

import java.util.Date;


public class Releve {
	String annee;
	String mois;
	String jour;
	String heure;
	String minute;
	String seconde;
	String nomVariable;
	Float value;
	String site;
	
	public Releve(String site,String annee, String mois, String jour, String heure,
			String minute, String seconde, String nomVariable, Float value) {
		this.site = site;
		this.annee = annee;
		this.mois = mois;
		this.jour = jour;
		this.heure = heure;
		this.minute = minute;
		this.seconde = seconde;
		this.nomVariable = nomVariable;
		this.value = value;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getNomVariable() {
		return nomVariable;
	}
	public void setNomVariable(String nomVariable) {
		this.nomVariable = nomVariable;
	}
	public Float getValue() {
		return value;
	}
	public void setValue(Float value) {
		this.value = value;
	}

	public String getAnnee() {
		return annee;
	}

	public void setAnnee(String annee) {
		this.annee = annee;
	}

	public String getMois() {
		return mois;
	}

	public void setMois(String mois) {
		this.mois = mois;
	}

	public String getJour() {
		return jour;
	}

	public void setJour(String jour) {
		this.jour = jour;
	}

	public String getHeure() {
		return heure;
	}

	public void setHeure(String heure) {
		this.heure = heure;
	}

	public String getMinute() {
		return minute;
	}

	public void setMinute(String minute) {
		this.minute = minute;
	}

	public String getSeconde() {
		return seconde;
	}

	public void setSeconde(String seconde) {
		this.seconde = seconde;
	}

	@Override
	public String toString() {
		return "Releve [annee=" + annee + ", mois=" + mois + ", jour=" + jour
				+ ", heure=" + heure + ", minute=" + minute + ", seconde="
				+ seconde + ", nomVariable=" + nomVariable + ", value=" + value
				+ ", site=" + site + "]";
	}


	
	
	
}
