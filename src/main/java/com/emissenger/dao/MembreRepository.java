package com.emissenger.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.emissenger.entites.Membre;

public interface MembreRepository extends JpaRepository<Membre,Long> {
	@Query("select m from Membre m where m.email= :mail and m.motDePasse= :motDePasse")
	public List<Membre> ChercherMembreByEmailPass(@Param("mail")String email,@Param("motDePasse") String motDePasse );

	@Query("select m from Membre m where m.email= :mail")
	public List<Membre> membreExiste(@Param("mail")String email );
	
//	//Recherche de membre
	@Query("select m from Membre m where m.nom like :motCle or m.prenom like :motCle")
	public Page<Membre> rechercherUnMembre(@Param("motCle")String motCle, Pageable page);
	

} 
