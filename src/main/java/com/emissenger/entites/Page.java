package com.emissenger.entites;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import com.emissenger.entites.Groupe.Etats;

@SuppressWarnings("serial")
@Entity
@Table(name="page")
public class Page implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_page")
	private Long idPage;
	@Column(name="id_createur",length=30)
	private String nom;
	@Enumerated(EnumType.STRING)
	@Column(name="etat",length=10)
	private Etats etat;
	@Column(name="date_creation")
	private Date dateCreation;
	@ManyToOne
	@JoinColumn(name="adherant")
	private Membre membre;
	@Column(name="est_admin",length=2)
	private Integer estAdmin;
	
	//Constructeurs et methodes:
	public Page() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public Etats getEtat() {
		return etat;
	}
	public void setEtat(Etats etat) {
		this.etat = etat;
	}
	public Date getDateCreation() {
		return dateCreation;
	}
	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}
	public Membre getMembre() {
		return membre;
	}
	public void setMembre(Membre membre) {
		this.membre = membre;
	}
	public Page(String nom, Etats etat, Date dateCreation, Membre membre) {
		super();
		this.nom = nom;
		this.etat = etat;
		this.dateCreation = dateCreation;
		this.membre = membre;
	}

}
