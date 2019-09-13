package com.emissenger.entites;



public class Relation  {

	private Membre membre;
	private String status;
	
	public Relation(Membre membre, String status) {
		super();
		this.membre = membre;
		this.status = status;
	}

	public Relation() {
		super();
	}

	public Membre getMembre() {
		return membre;
	}

	public void setMembre(Membre membre) {
		this.membre = membre;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	

	
}
