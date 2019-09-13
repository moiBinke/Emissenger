package com.emissenger.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.emissenger.entites.Ami;

public interface AmiRepository extends JpaRepository<Ami,Long> {

	@Query("select a from Ami a where a.IdDemandeur= :monId ")
	  public List<Ami> ListDeMesAmis(@Param("monId")long monId);

	
	
}
