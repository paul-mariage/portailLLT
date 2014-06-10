package com.llt.beans;

public class Group {

	private String nomGroup, link;

	public Group(String nomGroup, String link) {
		this.nomGroup=nomGroup;
		this.link=link;
	}

	public Group() {
	}

	public String getNomGroup() {
		return nomGroup;
	}

	public void setNomGroup(String nomGroup) {
		this.nomGroup = nomGroup;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	@Override
	public String toString() {
		return "Group [nomGroup=" + nomGroup + ", link=" + link + "]";
	}

	
	
}
