package com.emissenger.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.emissenger.entites.Commentaire;
public interface CommentaireRepository extends JpaRepository<Commentaire,Long> {

}
