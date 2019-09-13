package com.emissenger.metier;

import java.io.FileNotFoundException;
import java.util.List;

import org.springframework.data.domain.Page;
import com.emissenger.entites.Ami;
import com.emissenger.entites.Membre;
import com.emissenger.entites.Publication;


public interface InterfaceMetierEmissenger {
	boolean estUnDepartement(String departement);
	boolean estMotDePasseValide(String motDePasse);
	public List<String> inscrire(String nom, String prenom, String departement, String dateNaissanceEnChaine, String email, String motDePasse, String promotionEnChaine);
	//Compte
	int mettreAJour();
	public List<Ami> ListRelation(long monId, List<Ami> LRelation);
	public List<Ami> ADDMembreRelation(long monId,List<Ami> LRelation);
	public Membre consulterCompte(String email,String motDePasse);
	public Membre membreInscrit(String email);
	public Membre modifierUsername(String email,String motDePasse,String username);
	public Membre modifierEmail(String email,String motDePasse,String nouveauEmail);
	public void modifierMotDePasse(String email,String motDePasse,String nouveauMotDePasse);
	public Membre modifierPromotion(String email,String motDePasse,String datePromotion) ;
	public Membre modifierDescription(String email, String motDePasse, String nouveauDescription);
	public void Supprimer(String email,String motDePasse) ;
	public  Membre changerPhotoDeProfil(String nomphoto,String email,String motDePasse);
	// a changer
//	Page<Membre>ListNotificationAmitie(Long monId,int page,int size);
	//Recherche
	public Page<Membre> rechercherMembre(String motCle, int page, int sizePage);
	//Amitié
	Ami getAmitie(Long idAmitie) ;
	public void SupprimerInvitation(long codemembresrc,long codemembredest);
	public void AjouterInvitation(long codemembresrc,long codemembredest);
	public List<Membre> afficherMembre() ;
	public void AjouterAmi(long codemembresrc, long codemembredest);
	public void SupprimerAmi(Long idAmitie) ;
	public List<Ami> ListDesIdentifiantsDeMesAmis(Long monId);
	
	//Invitation
	/*
	 * public String checkamis(long codemembre, long codamis);
		public String checkinvit(long codemembre, long codamis);
	 * public void inviter(Long idDemandeur,Long idCible);
	 */
		
	//Publication
	public void publier(String objet,String contenu,Membre membre) throws FileNotFoundException;
	public Publication getPublications(long idPublication);
	public List<Publication> afficherPublications();
	public void supprimerPublication(long idPublication);
	//Journal
	public List<Publication> afficherMesPublications(Membre membre);
}
