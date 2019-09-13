package com.emissenger.entites;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.*;



@SuppressWarnings("serial")
@Entity
@Table(name="Membre")
public class Membre implements Serializable{
	//Les enumérations
	public 
	enum Departements{
		Civil,Industriel,Informatique, Electrique, Mécanique, Minéral,MIS,RéseauxTélécommunication, Procédés}
	public enum Etats{
		inscrit,active,desactive
	}
	//Les colonnes dans la table Membre;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_membre")
	private Long idMembre;
	@Column(name="nom",length=30)
	private String nom;
	@Column(name="prenom",length=30)
	private String prenom;
	@Column(name="departement",length=30)
	private String departement;
	@Column(name="date_naissance")
	@Temporal(TemporalType.DATE)
	private Date dateNaissance ;
	@Column(name="email",length=30)
	private String email;
	@Column(name="mot_de_passe",length=30)
	private String motDePasse;
	@Column(name="promotion")
	@Temporal(TemporalType.DATE)
	private Date promotion;
	@Column(name="etat",length=12)
	private String etat;
	@Column(name="description",length=255)
	private String description;
	@Column(name="username",length=255)
	private String username;
	@Column(name="photo",length=255)
	private String photo;
	
	@OneToMany(mappedBy="membre",fetch = FetchType.LAZY)
	public List<Ami> amis;
	
	
	//Les associations UML
	@OneToMany(mappedBy="membre")//Un membre peut faire parti de plusieurs groupe
	private Collection <Groupe> groupes;
	
	@OneToMany(mappedBy="membre")//un membre peut etre abonné à  plusieurs pages
	private Collection <Page> pages;

	@OneToMany(mappedBy="membre")//Un membre peut effectuer plusieurs publications
	private Collection <Publication> publications;
	
	@OneToMany(mappedBy="unMembre")//Peut faire plusieurs commentaires
	private Collection<Commentaire> commentaires;

	
	
	
	public Long getIdMembre() {
		return idMembre;
	}

	public void setIdMembre(Long idMembre) {
		this.idMembre = idMembre;
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

	public String getDepartement() {
		return departement;
	}

	public void setDepartement(String departement) {
		this.departement = departement;
	}

	public Date getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	public Date getPromotion() {
		return promotion;
	}

	public void setPromotion(Date promotion) {
		this.promotion = promotion;
	}

	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}

	public Collection<Groupe> getGroupes() {
		return groupes;
	}

	public void setGroupes(Collection<Groupe> groupes) {
		this.groupes = groupes;
	}

	public Collection<Page> getPages() {
		return pages;
	}

	public void setPages(Collection<Page> pages) {
		this.pages = pages;
	}

	public Collection<Publication> getPublications() {
		return publications;
	}

	public void setPublications(Collection<Publication> publications) {
		this.publications = publications;
	}

	public Collection<Commentaire> getCommentaires() {
		return commentaires;
	}

	public void setCommentaires(Collection<Commentaire> commentaires) {
		this.commentaires = commentaires;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public Membre() {
		super();
		this.amis = null ;
		// TODO Auto-generated constructor stub
	}

	public Membre( String nom, String prenom, String departement, Date dateNaissance, String email,
			String motDePasse, Date promotion, String etat, String photo) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.departement = departement;
		this.dateNaissance = dateNaissance;
		this.email = email;
		this.motDePasse = motDePasse;
		this.promotion = promotion;
		this.etat = etat;
		this.photo=photo;
		this.amis = null ;
	}
	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
	

	public List<Ami> getAmis() {
		return amis;
	}

	public void setAmis(List<Ami> amis) {
		this.amis = amis;
	}

	public Membre( String nom, String prenom, String departement, Date dateNaissance, String email,
			String motDePasse, Date promotion) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.departement = departement;
		this.dateNaissance = dateNaissance;
		this.email = email;
		this.motDePasse = motDePasse;
		this.promotion = promotion;
		this.amis = null ;
	}
	
	//Le constructeur et les méthodes:
	
	}
