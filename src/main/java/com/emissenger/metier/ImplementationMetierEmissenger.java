package com.emissenger.metier;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emissenger.dao.AmiRepository;
import com.emissenger.dao.MembreRepository;
import com.emissenger.dao.PublicationRepository;
import com.emissenger.entites.Ami;
import com.emissenger.entites.Membre;
import com.emissenger.entites.Publication;

@Service
@Transactional
public class ImplementationMetierEmissenger implements InterfaceMetierEmissenger {
	DateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd");
	
	@Autowired
	MembreRepository gerantMembre;
	@Autowired  
	private AmiRepository gerantAmi;
	@Autowired
	private PublicationRepository gerantPublication;
	
	//Inscription
	String departements[]= {"Civil","Industriel","Informatique", "Electrique", "Mécanique", "Minéral","MIS","RéseauxTélécommunication", "Procédés"};
	public boolean estUnDepartement(String departement) {
		 if(departement!=null) {
			 for(String dept:departements) {
				 if(departement.compareTo(dept)==0) {
					 return true;
				 }
			 }
			 return false;
		 }
		 else {
			 return false;
	 	 }
	}
	//Le mot de passe doit contenir au moins 8 letttres avec des majuscules
  public boolean estMotDePasseValide(String motDePasse) {
		
		if(motDePasse.length()>8) {
			for(int i=0;i<motDePasse.length();i++) {
				if(motDePasse.charAt(i)==motDePasse.toUpperCase().charAt(i)) {
					return true;
				}
			}
			return false;
		}
		else {
			return false;
		}
	}
	public List<String> inscrire(String nom, String prenom, String departement, String dateNaissanceEnChaine, String email, String motDePasse, String promotionEnChaine) {
		
		List<String> erreurs = new ArrayList<String>() ;
		try{
        Date dateNaissance = dateFormat.parse (dateNaissanceEnChaine);
        Date promotion=dateFormat.parse (promotionEnChaine);
		if(nom!=null) {
			if(prenom!=null) {
				if(estUnDepartement(departement)) {
					if(dateNaissance!=null) {
						if(email!=null) {
							email=email.toLowerCase();
							if(estMotDePasseValide(motDePasse)) {
								if(promotion!=null) {
									String codePhoto=MD5Util.md5Hex(email);
									String photo="https://www.gravatar.com/avatar/";
									photo=photo+codePhoto;
									Membre membre=new Membre( nom,  prenom,  departement, dateNaissance,  email, motDePasse,  promotion, "inscrit",photo); 
									membre.setUsername(nom+prenom);
									membre.setDescription("Je me nomme "+nom+" " +prenom+". Je suis EMI'ste du département "+departement+". promotion "+promotionEnChaine.substring(0, 4));
									gerantMembre.save(membre);	
								}
								else {
									erreurs.add("Veuillez saisir le champ \"promotion\" \n");
								}
							}
							else {
								erreurs.add("Le mot de passe doit contenir au moins 8 letttres avec des majuscules");
							}
						}
						else {
							erreurs.add("veuillez saisir le mot de passe");
						}
					}
				else {
						erreurs.add("Veuillez remplir le champ \"date de naissance\" ");
					}
				}
				else {
					erreurs.add("Ce departement n'est pas à l'EMI veuillez choisir dans la liste box du champ departement");
				}
			}
			else {
				erreurs.add("Le champ \"prenom\" ne peut pas etre vide");
			}
		}
		else {
			erreurs.add("Le champ \"nom\" ne peut pas etre vide");
		}
        }catch(ParseException pe){
            pe.printStackTrace();}
        return erreurs;
	}
	
	
	//Fin inscription
	
	//Debut Compte
	
	public Membre consulterCompte(String email,String motDePasse) {
        List<Membre> membre=new ArrayList<Membre>();
        membre=gerantMembre.ChercherMembreByEmailPass(email,motDePasse);
		if(membre==null) {throw new RuntimeException("membre non inscrit");};
		System.out.println("la taille est"+membre.size());
		return membre.get(0);
	}
	
	public Membre membreInscrit(String email) {
		
		List<Membre> membres=gerantMembre.membreExiste(email);
		
		if(membres.isEmpty()) return null;
		return membres.get(0);
	}
	
	public Membre modifierUsername(String email,String motDePasse,String username){
		Membre membre =null;
		membre=consulterCompte(email,motDePasse);
		if(membre!=null) {
		membre.setUsername(username);
		gerantMembre.save(membre);
		}
		return membre;
	}
	public Membre modifierEmail(String email,String motDePasse,String nouveauEmail){
		Membre membre =null;
		membre=consulterCompte(email,motDePasse);
		membre.setEmail(nouveauEmail);
		String codePhoto=MD5Util.md5Hex(nouveauEmail);
		String photo="https://www.gravatar.com/avatar/";
		photo=photo+codePhoto;
		membre.setPhoto(photo);
		gerantMembre.save(membre);
		return membre;
	}
	public void modifierMotDePasse(String email,String motDePasse,String nouveauMotDePasse){
		if(estMotDePasseValide(nouveauMotDePasse)) {
			Membre membre =consulterCompte(email,motDePasse);
			membre.setMotDePasse(nouveauMotDePasse);
			gerantMembre.save(membre);
		}
		else  throw new RuntimeException("Le mot de passe doit contenir au moins 8 letttres avec des majuscules");
	}
	public Membre modifierPromotion(String email,String motDePasse,String datePromotionEnChaine) {
        Date promotion = null;
		try {
			promotion = dateFormat.parse (datePromotionEnChaine);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Membre membre=null;
		membre=consulterCompte(email,motDePasse);
		membre.setPromotion(promotion);
		gerantMembre.save(membre);
		return membre;
		}
	public Membre modifierDescription(String email, String motDePasse, String nouveauDescription) {
		Membre membre =null;
		membre=consulterCompte(email,motDePasse);
		membre.setDescription(nouveauDescription);
		gerantMembre.save(membre);
		return membre;
	}
	public void Supprimer(String email,String motDePasse) {
		Membre membre =consulterCompte(email,motDePasse);
		membre.setEtat("desactive");
		gerantMembre.save(membre);
	}
	public  Membre changerPhotoDeProfil(String nomPhoto,String email,String motDePasse) {
		Membre membre =consulterCompte(email,motDePasse);
		membre.setPhoto(nomPhoto);
		gerantMembre.save(membre);
		return membre;
	}
	// a changer
//	Page<Membre>ListNotificationAmitie(Long monId,int page,int size){
//		return  gerantMembre.LesNotificationAmitie(monId,new PageRequest(page,size));
//	}
//	void ModifierVotreProfil() {
//	}
	
	//Fin Compte
	
	//Début de la recherche
	public Page<Membre> rechercherMembre(String motCle, int page, int sizePage){
		
		
		motCle="%"+motCle+"%";
		return gerantMembre.rechercherUnMembre(motCle, new PageRequest(page,sizePage));
		
	}
	
	
	//Fonction de recherche
//	public float nombreDeDifference(String motCle, String chaine) {
//		float ressemblance=0;
//		for(int i=0;i<Math.min(motCle.length(),chaine.length());i++){
//			if(motCle.charAt(i)==chaine.charAt(i)) {
//				ressemblance++;
//			}
//		}
//		ressemblance=ressemblance/motCle.length();
//		return ressemblance;
//	}
//	
//	public List<Membre>rechercheMembre(String motCle){
//		List<Membre> candidats=null;
//		candidats=gerantMembre.rechercherUnMembre(motCle);
//		int taille=0;
//		if(candidats!=null) {
//			taille=candidats.size();
//			float PointsCandidats[]=new float[taille];
//			for(int i=0;i<taille;i++) {
//				String choixAttribut;
//				float pointNom=nombreDeDifference(motCle,candidats.get(i).getNom());
//				float pointPrenom=nombreDeDifference(motCle,candidats.get(i).getPrenom());
//				PointsCandidats[i]=Math.max(pointNom, pointPrenom);
//			}
//		}
//		return null;
//	}
//	
	
	//Fin Recherche
	//début amitié
	
	public Ami getAmitie(Long idAmitie) {
		Ami ami=gerantAmi.getOne(idAmitie);
		if(ami==null) throw new RuntimeException("Amitie non exitant");
		return ami;
	}
	public Membre getMembre(Long idMembre) {
		Membre Membre=gerantMembre.getOne(idMembre);
		if(Membre==null) throw new RuntimeException("Mmebre non exitant");
		return Membre;
	}
	public void SupprimerAmi(Long idAmitie) {
		Ami ami=getAmitie(idAmitie);
		ami.setEtat("supprimee");
		gerantAmi.save(ami);
	}
	
	//Debut effectuer une invitation
	public void AjouterInvitation(long idDemandeur,long idCible) {
		Date dateDemande=new Date();
		String dateDemandeEnChaine=dateFormat.format(dateDemande);		
		 try {
			 dateDemande=dateFormat.parse (dateDemandeEnChaine);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Ami amitie1=new Ami("Envoi",  idDemandeur,  idCible,  dateDemande,getMembre(idCible));
		Ami amitie2=new Ami("Recu",  idCible , idDemandeur,  dateDemande,getMembre(idDemandeur));
		
		gerantAmi.save(amitie1);	
		gerantAmi.save(amitie2);	
	}
	public List<Ami> ListDesIdentifiantsDeMesAmis(Long monId){
		return  gerantAmi.ListDeMesAmis(monId); 
	}
	@Override
	public int mettreAJour() {
		// TODO Auto-generated method stub
		return 0;
	}
	public void publier(String objet, String contenu,Membre membre ) throws FileNotFoundException {
		Publication pub;
		Date datePublication= new Date(); 
		File piece = null ;
		String datePubEnChaine=dateFormat.format(datePublication);	
		FileDS FDS = new FileDS();
		 try {
			datePublication=dateFormat.parse (datePubEnChaine);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 if(piece == null )
			 pub=new Publication("active",objet,contenu, datePublication, membre,membre.getUsername(),membre.getPhoto(),0,0,null , null);
		 else
			 pub=new Publication("active",objet,contenu, datePublication, membre,membre.getUsername(),membre.getPhoto(),0,0,piece, piece.getName());
		 gerantPublication.save(pub);
		
	}
	
	public List<Publication> afficherPublications() {
		List<Publication> publications=gerantPublication.findAll();
		return publications;
	}
	
	public List<Membre> afficherMembre() {
		List<Membre> Membres=gerantMembre.findAll();
		return Membres;
	}
	
	public List<Ami> ListRelation(long monId, List<Ami> LRelation) {
		List<Ami> LAmis=gerantAmi.findAll();
		for(Ami a : LAmis)
		{
			if(a.getIdDemandeur() == monId && a.getEtat().compareTo("Envoi") == 0) LRelation.add(a);
			if(a.getIdDemandeur() == monId && a.getEtat().compareTo("Recu") == 0) LRelation.add(a);
			if(a.getIdDemandeur() == monId && a.getEtat().compareTo("Amis") == 0) LRelation.add(a);
			
		}
		return  LRelation;
	}
	
	public List<Ami> ADDMembreRelation(long monId,List<Ami> LRelation) {
		List<Membre> LMembre=gerantMembre.findAll();
		
		for(Membre m : LMembre)
		{
			int drap = 0 ;
			for(Ami a : LRelation)
			{
				if((a.getIdCible() == m.getIdMembre() || a.getIdDemandeur() == m.getIdMembre() )) drap = 1 ;
			}
			if(drap == 0 && monId != m.getIdMembre()) LRelation.add(new Ami("Null",  monId , m.getIdMembre(),  new Date() ,m));
				
		}
	
		return  LRelation;
	}
	
	public Publication getPublications(long idPublication) {
		Publication pub=gerantPublication.getOne(idPublication);
		return pub;
	}
	
	public void supprimerPublication(long idPublication) {
		Publication pub =gerantPublication.getOne(idPublication);
		gerantPublication.delete(pub);
	}
	//journal
	
	@Override
	public List<Publication> afficherMesPublications(Membre membre) {
		List<Publication> publications=gerantPublication.getMesPublications(membre);
		return publications;
	}
	
	@Override
	public void AjouterAmi(long codemembresrc, long codemembredest) {
		
		SupprimerInvitation(codemembresrc,codemembredest);
		gerantAmi.save(new Ami("Amis",codemembresrc,codemembredest,new Date(),getMembre(codemembredest)));
		gerantAmi.save(new Ami("Amis",codemembredest,codemembresrc,new Date(),getMembre(codemembresrc)));
	}
	
	@Override
	public void SupprimerInvitation(long codemembresrc,long  codemembredest) {
		
		List<Ami> ListInvit =  gerantAmi.findAll();
		for(Ami i : ListInvit)
		{
			if ((i.getEtat().compareTo("Envoi") == 0 && i.getIdDemandeur() == codemembresrc  && i.getIdCible() == codemembredest) ||
				(i.getEtat().compareTo("Envoi") == 0 && i.getIdDemandeur() == codemembredest  && i.getIdCible() == codemembresrc) )
				
			{
				gerantAmi.delete(i);
				ListInvit =  gerantAmi.findAll();
			}
			if ((i.getEtat().compareTo("Recu") == 0 && i.getIdDemandeur() == codemembresrc  && i.getIdCible() == codemembredest) ||
				(i.getEtat().compareTo("Recu") == 0 && i.getIdDemandeur() == codemembredest  && i.getIdCible() == codemembresrc) )
			{
				gerantAmi.delete(i);
				ListInvit =  gerantAmi.findAll();
			}
		}
		
	}
	
}
