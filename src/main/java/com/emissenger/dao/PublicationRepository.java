package com.emissenger.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.emissenger.entites.Membre;
import com.emissenger.entites.Publication;

public interface PublicationRepository extends JpaRepository<Publication,Long> {
	@Query("select pub from Publication pub where pub.membre=:membre")
	public List<Publication> getMesPublications(@Param("membre")Membre membre );
}
