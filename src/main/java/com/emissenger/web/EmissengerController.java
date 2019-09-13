package com.emissenger.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.emissenger.dao.MembreRepository;
import com.emissenger.dao.PublicationRepository;
import com.emissenger.entites.Ami;
import com.emissenger.entites.Membre;
import com.emissenger.entites.Publication;
import com.emissenger.entites.Relation;
import com.emissenger.metier.InterfaceMetierEmissenger;
import com.emissenger.metier.MD5Util;

@Controller //Parce qu'on va utiliser Spring MVC
public class EmissengerController {
	
	@Autowired
	InterfaceMetierEmissenger metierEmissenger;
	@Autowired
	private PublicationRepository gerantPubication;
	
	@Autowired
	MembreRepository gerantMembre;
	
	@Value("${dir.images}")
	private String cheminPhotosProfil;
	
	@RequestMapping("/")
	public String accueil() {
		return "index";
	}
	
	@RequestMapping("/inscription")
	public String inscription() {
		return "inscription";
	}
	
	
	@RequestMapping(value="/enregistrement",method=RequestMethod.POST)
	public String enregistrement(HttpSession session,Model model,String nom,String prenom,String departement, String dateNaissanceEnChaine,String email, String motDePasse,  String promotionEnChaine) throws ParseException {
		System.out.println(dateNaissanceEnChaine);
		String erreurDeMotDePasse;
		Membre membre=metierEmissenger.membreInscrit(email);
		if(membre != null) {
			session.setAttribute("moi", membre);
			return "profil";
		}
		else
		{
			if(!metierEmissenger.estMotDePasseValide(motDePasse)) {
				erreurDeMotDePasse="Le mot de passe doit contenir au moins 8  chiffres avec au moins une majuscule ";
				model.addAttribute("erreurDeMotDePasse", erreurDeMotDePasse);
			}
			List<String>   erreurs=metierEmissenger.inscrire(nom, prenom, departement, dateNaissanceEnChaine, email, motDePasse, promotionEnChaine);
			if(erreurs.isEmpty()) {
				membre= metierEmissenger.membreInscrit(email);
				session.setAttribute("moi", membre);
				return "profil";
			}
			else
			{
				model.addAttribute("erreurs", erreurs);
				return "inscription";	
			}
			
		}
		
	}
	
	@RequestMapping(value="/modifierUsername",method=RequestMethod.POST)
	public String modifierMotDePasse(HttpSession session, Model model,String username,String email, String motDePasse) {
		Membre membre=metierEmissenger.modifierUsername(email, motDePasse, username);
		session.setAttribute("moi", membre);
		return "profil";
	}
	
	@RequestMapping(value="/modifierPromotion",method=RequestMethod.POST)
	public String modifierPromotion(HttpSession session,Model  model,String promotion,String email, String motDePasse) {
		Membre membre=metierEmissenger.modifierPromotion(email, motDePasse, promotion);
		session.setAttribute("moi", membre);
		return "profil";
	}
	@RequestMapping(value="/modifierEmail",method=RequestMethod.POST)
	public String modifierEmail(HttpSession session,Model model,String email, String motDePasse,String nouveauEmail) {
		Membre membre=metierEmissenger.modifierEmail(email, motDePasse, nouveauEmail);
		session.setAttribute("moi", membre);
		return "profil";
	}
	
	@RequestMapping(value="/modifierDescription",method=RequestMethod.POST)
	public String modifierDescription(HttpSession session,Model model,String email, String motDePasse,String nouveauDescription) {
		Membre membre=metierEmissenger.modifierDescription(email, motDePasse, nouveauDescription);
		session.setAttribute("moi", membre);
		return "profil";
	}
	
	@RequestMapping(value="/filActualites")
	public String actualites(Model model) {
		List<Publication> actualites=metierEmissenger.afficherPublications();
		model.addAttribute("actualites", actualites);
		return "filActualites";
	}
	
	@RequestMapping(value="/resultatRecherche")
	public String ResultatRecherche(HttpSession session,Model model, String motCle,long membre) {
		
		model.addAttribute("motCle", motCle);
		model.addAttribute("membreid", membre);
		try {
			//Page<Membre> pageRecherche=metierEmissenger.rechercherMembre(motCle, 0, 4);
			//List<Membre> LMembre = metierEmissenger.afficherMembre();
			List<Ami> LAmis = new ArrayList<Ami>();
			LAmis = metierEmissenger.ListRelation(membre, LAmis) ;
			LAmis = metierEmissenger.ADDMembreRelation(membre, LAmis);
			model.addAttribute("listeRecherche", LAmis);
			//List<Relation> Lrelation = new ArrayList<Relation>();
			
			
		}
		catch(Exception e) {
		}
		
		
		return "resultatRecherche";
	
	/*
		
		Membre mbsrc = invitationMetier.ConsulterMembre(codemembresrc);
		Membre mbdest = invitationMetier.ConsulterMembre(codemembredest);
		List<Membre> LMembre = invitationMetier.ListMembre();
		
		
		String status = invitationMetier.checkamis(codemembresrc,codemembredest);
		if(status.compareTo("NULL") == 0)
			status = invitationMetier.checkinvit(codemembresrc,codemembredest);
		
		
		model.addAttribute("membresource",mbsrc);
		model.addAttribute("membredestination",mbdest);
		model.addAttribute("listMembre",LMembre);
		model.addAttribute("status",status);
		
		*/
		
		
	}
	
	
	@RequestMapping(value="/ajouterAmi")
	public String ajouterAmi(Model model,long idDemandeurEnChaine, long idCibleEnChaine,String demande) {
		
		if(demande.compareTo("AnnulerInvitation")== 0 )
		{
			metierEmissenger.SupprimerInvitation(idDemandeurEnChaine,idCibleEnChaine);
		}
		else if(demande.compareTo("RefuserInvitation")== 0)
		{
			metierEmissenger.SupprimerInvitation(idCibleEnChaine,idDemandeurEnChaine);
		}
		else if(demande.compareTo("AjouterInvitation")==0)
		{
			metierEmissenger.AjouterInvitation(idDemandeurEnChaine,idCibleEnChaine);
		}
		else if(demande.compareTo("AccepterInvitation")==0)
		{
			metierEmissenger.AjouterAmi(idDemandeurEnChaine,idCibleEnChaine);
		}
		
		model.addAttribute("motCle", "");
		model.addAttribute("membreid", idDemandeurEnChaine);
		try {
			//Page<Membre> pageRecherche=metierEmissenger.rechercherMembre(motCle, 0, 4);
			//List<Membre> LMembre = metierEmissenger.afficherMembre();
			List<Ami> LAmis=new ArrayList<Ami>();
			LAmis = metierEmissenger.ListRelation(idDemandeurEnChaine, LAmis) ;
			LAmis = metierEmissenger.ADDMembreRelation(idDemandeurEnChaine, LAmis);
			model.addAttribute("listeRecherche", LAmis);
						
		}
		catch(Exception e) {
		}
		
		return "redirect:/resultatRecherche?membre="+idDemandeurEnChaine+"&motCle=";
	}
	@RequestMapping(value="/changerPhoto")
	public String changerPhoto(HttpSession session,Model model, MultipartFile photoEnFichier,String email, String motDePasse) {
		if(!(photoEnFichier.isEmpty())) {
			String nomPhoto=email;//Ladresse email est le nom de la photo
			try {
			photoEnFichier.transferTo(new File(cheminPhotosProfil+nomPhoto));
			Membre membre=metierEmissenger.changerPhotoDeProfil(nomPhoto, email, motDePasse);
			session.setAttribute("moi", membre);
			model.addAttribute("moi",membre);
			}
			catch(Exception e) {	
			}		
		}
		return "profil";
	}
	@RequestMapping(value="/chargerProfil",produces=MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	public byte[] chargerPhoto(String email) throws Exception{
		File image=new File(cheminPhotosProfil+email);
		return IOUtils.toByteArray(new FileInputStream(image));	
	}
	
	@RequestMapping(value="/publier")
	public String publier(HttpSession session, String objet,String contenu) {
		Membre membre=(Membre)session.getAttribute("moi");
		try {
			metierEmissenger.publier(objet,contenu, membre);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		List<Publication> actualites=metierEmissenger.afficherPublications();
		session.setAttribute("actualites", actualites);
		return "redirect:/filActualites";
	}
	
	@RequestMapping(value="/aime")
	public String aime(long idpub) {
		Publication pub =  metierEmissenger.getPublications(idpub);
		int a = pub.getAime();
		pub.setAime(++a);
		gerantPubication.save(pub);
		
		return "redirect:/filActualites";
	}
	
	@RequestMapping(value="/com")
	public String com(Model model,long idpub) {
		Publication pub =  metierEmissenger.getPublications(idpub);
		
		model.addAttribute("actualite", pub);
		
		return "redirect:/filActualitesCom";
	}
	
	@RequestMapping(value="/merci")
	public String merci(long idpub) {
		Publication pub =  metierEmissenger.getPublications(idpub);
		int a = pub.getMerci();
		pub.setMerci(++a);
		gerantPubication.save(pub);
		
		return "redirect:/filActualites";
	}
	
	@RequestMapping(value="/supprimerPublication")
	public String supprimerPublication(HttpSession session,String idPubASupprimerEnChaine) {
		long idPubASupprimer=(long)Integer.parseInt(idPubASupprimerEnChaine);
		metierEmissenger.supprimerPublication(idPubASupprimer);
		session.removeAttribute("actualites");
		List<Publication> actualites=metierEmissenger.afficherPublications();
		session.setAttribute("actualites", actualites);
		Membre membre=(Membre)session.getAttribute("moi");
		List<Publication> mesPublications=metierEmissenger.afficherMesPublications(membre);
		session.setAttribute("mesPublications", mesPublications);
		return "journal";
	}
	
	@RequestMapping(value="/journal")
	public String  journal(HttpSession session,Model model) {
		Membre moi=(Membre)session.getAttribute("moi");
		List<Publication> mesPublications=metierEmissenger.afficherMesPublications(moi);
		session.setAttribute("mesPublications", mesPublications);

		List<Ami> mesAmis=metierEmissenger.ListDesIdentifiantsDeMesAmis(moi.getIdMembre());
		List<Membre> amisDemandes=new ArrayList<Membre>();
		Membre membre;
		for(Ami ami:mesAmis) {
			if(ami.getEtat().compareTo("Amis")==0) {
			}
			else {
				membre=gerantMembre.getOne(ami.getIdCible());
				amisDemandes.add(membre);
			}
			int nombreAmitieDemande=0;
			if(amisDemandes!=null) {
				nombreAmitieDemande	=amisDemandes.size();
				}
			model.addAttribute("nombreAmitieDemande", nombreAmitieDemande);
		}
		
		return "journal" ;
	}
	
	@RequestMapping(value="/journal2")
	public String  journal2(HttpSession session,Model model) {
		Membre moi=(Membre)session.getAttribute("moi");
		List<Ami> mesAmis=metierEmissenger.ListDesIdentifiantsDeMesAmis(moi.getIdMembre());
		List<Membre> amisAcceptes=new ArrayList<Membre>();
		List<Membre> amisDemandes=new ArrayList<Membre>();
		
		Membre membre;
		for(Ami ami:mesAmis) {
			if(ami.getEtat().compareTo("Amis")==0) {
				membre=gerantMembre.getOne(ami.getIdCible());
				amisAcceptes.add(membre);
			}
			else {
				membre=gerantMembre.getOne(ami.getIdCible());
				amisDemandes.add(membre);
			}
			model.addAttribute("amisAcceptes", amisAcceptes);
			model.addAttribute("amisDemandes", amisDemandes);
		}
		return "journal2";
	}
	@RequestMapping(value="/profil")
	public String profil() {
		
		return "profil";
	}
	@RequestMapping(value="/messagerie")
	public String messagerie() {
		
		return "messagerie";
	}
	
}