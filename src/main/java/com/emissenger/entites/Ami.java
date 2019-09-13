package com.emissenger.entites;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name="ami")
public class Ami implements Serializable {
	public enum Etat{
		demandee, acceptee, supprimee
	}
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_ami")
	private Long idAmi;
	@Column(name="etat",length=10)
	private String etat;
	@Column(name="Id_demandeur")
	private Long IdDemandeur;
	@Column(name="id_cible")
	private Long IdCible;
	@Column(name="date_demande")
	private Date dateDemande;
	
	@ManyToOne
	@JoinColumn(name="membredemandeur")
	private Membre membre;
	
	public Ami() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Ami(String etat, Long idDemandeur, Long idCible, Date date,Membre membre) {
		super();
		this.etat = etat;
		IdDemandeur = idDemandeur;
		IdCible = idCible;
		this.dateDemande = date;
		this.membre = membre;
	}
	
	public Ami(Long idDemandeur, Long idCible, Date date) {
		super();
		IdDemandeur = idDemandeur;
		IdCible = idCible;
		this.dateDemande = date;
	}
	
	public String getEtat() {
		return etat;
	}
	public void setEtat(String etat) {
		this.etat = etat;
	}
	public Long getIdDemandeur() {
		return IdDemandeur;
	}
	public void setIdDemandeur(Long idDemandeur) {
		IdDemandeur = idDemandeur;
	}
	public Long getIdCible() {
		return IdCible;
	}
	public void setIdCible(Long idCible) {
		IdCible = idCible;
	}
	public Date getDateDemande() {
		return dateDemande;
	}
	public void setDateDemande(Date dateDemande) {
		this.dateDemande = dateDemande;
	}
	public Long getIdAmi() {
		return idAmi;
	}
	public void setIdAmi(Long idAmi) {
		this.idAmi = idAmi;
	}
	public Membre getMembre() {
		return membre;
	}
	public void setMembre(Membre membre) {
		this.membre = membre;
	}
	
	
}
