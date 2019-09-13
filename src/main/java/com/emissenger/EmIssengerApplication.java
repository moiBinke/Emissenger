package com.emissenger;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.emissenger.dao.MembreRepository;
import com.emissenger.entites.Membre;
import com.emissenger.metier.InterfaceMetierEmissenger;

@SpringBootApplication
public class EmIssengerApplication implements CommandLineRunner{
	
	@Autowired
	MembreRepository gerantMembre;
	@Autowired
	InterfaceMetierEmissenger metierEmissenger ;
	public static void main(String[] args) {
		SpringApplication.run(EmIssengerApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
//		Membre M=new Membre();
//		gerantMembre.save(M);
//		metierEmissenger.inscrire("SISO", "Mouhammad", "Industriel","1996-02-18","kadija@gmail.com","modjDJDldjdk","2017-07-25");
//		metierEmissenger.inscrire("SISSOKO", "Mouhammad", "Industriel","1996-02-18","kadija@gail.com","modjDJDldjdk","2017-07-25");

	
	}
}
