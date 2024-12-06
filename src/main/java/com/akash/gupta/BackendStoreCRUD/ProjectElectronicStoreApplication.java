package com.akash.gupta.BackendStoreCRUD;

import com.akash.gupta.BackendStoreCRUD.entities.Role;
import com.akash.gupta.BackendStoreCRUD.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class ProjectElectronicStoreApplication implements CommandLineRunner{
//public class ElectonicStoreApplication{


	public static void main(String[] args){
		SpringApplication.run(ProjectElectronicStoreApplication.class, args);

	}
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepository roleRepository;

	@Value("${normal.role.id}")
	private String role_normal_id;

	@Value("${admin.role.id}")
	private String role_admin_id;



	@Override
	public void run(String... args) throws Exception{
		System.out.println(passwordEncoder.encode("ghij"));

	  try{

		  Role role_admin =  Role.builder().roleId(role_admin_id).roleName("ROLE_ADMIN").build();
		  Role role_normal = Role.builder().roleId(role_normal_id).roleName("ROLE_NORMAL").build();
       	 	roleRepository.save(role_normal);
			roleRepository.save(role_admin);


	  }catch(Exception e){

		  System.out.println(e.getMessage());

	  }


	}
}
