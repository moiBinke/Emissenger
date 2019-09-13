package com.emissenger.entites;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name="commentaire")
public class Commentaire implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_commentaire")
	private Long idCommentaire;
	@Column(name="contenu")
	private String contenu;
	@Column(name="date_commentaire")
	private Date dateCommentaire;
	
	//On va modeliser notre classe d'association en faisant deux maping ManyToMany: (un entre membre et commentaire) et (un autre publication et commentaire)
	
	//Plusieurs commentaires peuvent etre fait par un membre
	@ManyToOne
	private Membre membre;
	//Plusieurs commentaires peuvent appartenir à une publication
	@ManyToOne
	private Publication publication;
	
	public Long getIdCommentaire() {
		return idCommentaire;
	}
	public void setIdCommentaire(Long idCommentaire) {
		this.idCommentaire = idCommentaire;
	}
	public String getContenu() {
		return contenu;
	}
	public void setContenu(String contenu) {
		this.contenu = contenu;
	}
	public Date getDateCommentaire() {
		return dateCommentaire;
	}
	public void setDateCommentaire(Date dateCommentaire) {
		this.dateCommentaire = dateCommentaire;
	}
	public Membre getMembre() {
		return membre;
	}
	public void setMembre(Membre membre) {
		this.membre = membre;
	}
	
	public Publication getPublication() {
		return publication;
	}
	public void setPublication(Publication publication) {
		this.publication = publication;
	}
	public Membre getUnMembre() {
		return unMembre;
	}
	public void setUnMembre(Membre unMembre) {
		this.unMembre = unMembre;
	}
	@ManyToOne
	private Membre unMembre;
	
	//Les constructeurs et les méthodes
	public Commentaire() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Commentaire(String contenu, Date dateCommentaire, Membre membre, Publication publication) {
		super();
		this.setContenu(contenu);
		this.setDateCommentaire(dateCommentaire);
		this.setMembre(membre);
		this.setPublication(publication);
	}
}