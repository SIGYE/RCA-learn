package com.practice.neBanking;

import com.practice.neBanking.enums.ERole;
import com.practice.neBanking.repositories.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.practice.neBanking.models.Role;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@SpringBootApplication
public class NeApplication {
	private IRoleRepository roleRepository;

	@Autowired
	public NeApplication(IRoleRepository roleRepository){
		this.roleRepository = roleRepository;
	}
	public static void main(String[] args) {
		SpringApplication.run(NeApplication.class, args);
	}

	@Bean
	public boolean registerRoles(){
		Set<ERole> roles = new HashSet<>();
		roles.add(ERole.ADMIN);
		roles.add(ERole.CUSTOMER);

		for(ERole role: roles){
			Optional<Role> roleByName = roleRepository.findByName(role);
			if(roleByName.isEmpty()){
				Role newRole = new Role(role, role.toString());
				roleRepository.save(newRole);
				System.out.println("Created:" +role);
			}

		}
		return true;
	}

}
