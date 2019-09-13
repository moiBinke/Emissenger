package com.emissenger.entites;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.sql.Blob;
import java.util.Collection;
import java.util.Date;

import javax.persistence.*;

import com.emissenger.metier.FileDS;


@SuppressWarnings("serial")
@Entity
@Table(name="publication")
public class Publication implements Serializable{
	public Publication() {
		super();
		// TODO Auto-generated constructor stub
	}

	public enum Etats{
		active, supprimee
	}
//	public enum Fichier{
//		photo, nonPhoto
//	}
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_publication")
	private Long idPublication;
	@Column(name="etat",length=10)
	private String etat;
	@Column(name="objet")
	private String objet;
	@Column(name="contenu")
	private String contenu;
	@Column(name="date_publication")
	private Date datePublication;
//	@Column(name="type_fichier",length=10)
//	private Fichier typeFichier;
	@Column(name="emplacement_fichier")
	private Blob emplacementFichier;
	@Column(name="name_fichier")
	private String nameFichier;
	@Column(name="username")
	private String username;
	@Column(name="user_picture")
	private String userPicture;
	private int aime , merci ;
	@ManyToOne
	@JoinColumn(name="id_membre")
	private Membre membre;
	
	
	public Long getIdPublication() {
		return idPublication;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserPicture() {
		return userPicture;
	}
	public void setUserPicture(String userPicture) {
		this.userPicture = userPicture;
	}

	@OneToMany(mappedBy="publication")
	private Collection<Commentaire>commentaires;
	
	public String getEtat() {
		return etat;
	}
	public void setEtat(String etat) {
		this.etat = etat;
	}
	public String getContenu() {
		return contenu;
	}
	public void setContenu(String contenu) {
		this.contenu = contenu;
	}
	public Date getDatePublication() {
		return datePublication;
	}
	public void setDatePublication(Date datePublication) {
		this.datePublication = datePublication;
	}
	
	public Blob  getEmplacementFichier() {
		return emplacementFichier;
	}
	public void setEmplacementFichier(Blob emplacementFichier) {
		this.emplacementFichier = emplacementFichier;
	}
	public Membre getMembres() {
		return membre;
	}
	public void setMembres(Membre membre) {
		this.membre = membre;
	}
//	public Fichier getTypeFichier() {
//		return typeFichier;
//	}
//	public void setTypeFichier(Fichier typeFichier) {
//		this.typeFichier = typeFichier;
//	}
	
	public String getObjet() {
		return objet;
	}
	public int getAime() {
		return aime;
	}
	public void setAime(int aime) {
		this.aime = aime;
	}
	public int getMerci() {
		return merci;
	}
	public void setMerci(int merci) {
		this.merci = merci;
	}
	public void setObjet(String objet) {
		this.objet = objet;
	}
	
	
	public String getNameFichier() {
		return nameFichier;
	}
	public void setNameFichier(String nameFichier) {
		this.nameFichier = nameFichier;
	}
	public Membre getMembre() {
		return membre;
	}
	public void setMembre(Membre membre) {
		this.membre = membre;
	}
	public Collection<Commentaire> getCommentaires() {
		return commentaires;
	}
	public void setCommentaires(Collection<Commentaire> commentaires) {
		this.commentaires = commentaires;
	}
	public void setIdPublication(Long idPublication) {
		this.idPublication = idPublication;
	}
	public Publication(String etat,String objet, String contenu, Date datePublication,Membre membre, 
			String username, String userPicture, int merci , int aime ,File empfile ,String nomfile ) throws FileNotFoundException {
		super();
		
		this.setEtat(etat);
		this.setContenu(contenu);
		this.setDatePublication(datePublication);
		this.membre=membre;
		this.username=username;
		this.userPicture=userPicture;
		this.objet=objet;
		this.aime = aime ;
		this.merci = merci ;
		@SuppressWarnings("unused")
		FileDS FDS = new FileDS();
		this.nameFichier = nomfile;
	}
	
}
