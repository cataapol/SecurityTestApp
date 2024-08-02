package com.example.SpringSecApp;

import com.example.SpringSecApp.persistence.entity.Permission;
import com.example.SpringSecApp.persistence.entity.Role;
import com.example.SpringSecApp.persistence.entity.RoleNumber;
import com.example.SpringSecApp.persistence.entity.User;
import com.example.SpringSecApp.persistence.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Set;

@SpringBootApplication
public class SpringSecAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecAppApplication.class, args);
	}


	@Bean
	CommandLineRunner init(UserRepository userRepository) {
		return args -> {
			//CREACIÓN DE PERMISOS
			Permission createPermission = Permission.builder()
					.name("CREATE")
					.build();

			Permission readPermission = Permission.builder()
					.name("READ")
					.build();

			Permission updatePermission = Permission.builder()
					.name("UPDATE")
					.build();

			Permission deletePermission = Permission.builder()
					.name("DELETE")
					.build();


			//CREACIÓN DE ROLES
			Role roleAdmin = Role.builder()
					.roleNumber(RoleNumber.ADMIN)
					.permissions(Set.of(createPermission,readPermission,updatePermission,deletePermission))
					.build();

			Role roleClient = Role.builder()
					.roleNumber(RoleNumber.CLIENT)
					.permissions(Set.of(readPermission))
					.build();



			//CREATE USERS
			User userCatalina = User.builder()
					.username("Catalina")
					.password("123456")
					.isEnabled(true)
					.accountNoExpired(true)
					.accountNoLocked(true)
					.credentialNoExpired(true)
					.roles(Set.of(roleAdmin))
					.build();


			User userJuan = User.builder()
					.username("Juan")
					.password("54321")
					.isEnabled(true)
					.accountNoExpired(true)
					.accountNoLocked(true)
					.credentialNoExpired(true)
					.roles(Set.of(roleClient))
					.build();

			userRepository.saveAll(List.of(userCatalina, userJuan));
		};

	}

}
