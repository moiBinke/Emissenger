package com.emissenger.entites;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@SuppressWarnings("serial")
@Entity
@Table(name="groupe")
public class Groupe implements Serializable{
	public enum Etats{
		active,supprime
	}
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_groupe")
	private Long idGroupe;
	@Column(name="nom",length=30)
	private String nom;
	@Enumerated(EnumType.STRING)
	@Column(name="etat")
	private Etats etat;
	@Column(name="date_creation")
	private Date dateCreation;
	@ManyToOne
	@JoinColumn(name ="adherent")
	private Membre membre;
	@Column(name="est_admin",length=2)
	private Integer estAdmin;
	public void setIdGroupe(Long idGroupe) {
		this.idGroupe = idGroupe;
	}
	public Groupe() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Groupe(String nom, Etats etat, Date dateCreation, Membre membre) {
		super();
		this.setNom(nom);
		this.setEtat(etat);
		this.setDateCreation(dateCreation);
		this.setMembre(membre);
	}
	public Long getIdGroupe() {
		return idGroupe;
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
	public Integer getEstAdmin() {
		return estAdmin;
	}
	public void setEstAdmin(Integer estAdmin) {
		this.estAdmin = estAdmin;
	}
}
