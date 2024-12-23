package com.example.uni_dubna;

import com.example.uni_dubna.models.Role;
import com.example.uni_dubna.models.ScientificUser;
import com.example.uni_dubna.repo.RoleRepository;
import com.example.uni_dubna.repo.ScientificUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class UniDubnaApplication {

	private final RoleRepository roleRepository;
	private final ScientificUserRepository scientificUserRepository;
	private final PasswordEncoder passwordEncoder;

	// Внедряем зависимости через конструктор
	public UniDubnaApplication(RoleRepository roleRepository,
							   ScientificUserRepository scientificUserRepository,
							   PasswordEncoder passwordEncoder) {
		this.roleRepository = roleRepository;
		this.scientificUserRepository = scientificUserRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public static void main(String[] args) {
		SpringApplication.run(UniDubnaApplication.class, args);
	}

	@Bean
	public CommandLineRunner initRolesAndUser() {
		// Выводим роли в консоль
		roleRepository.findAll().forEach(role -> System.out.println("Role: " + role.getName()));

		return args -> {
			// Создание пользователя с ролями, если пользователя с таким именем нет
			if (!scientificUserRepository.existsByUsername("admin")) {
				String encodedPassword = passwordEncoder.encode("password");
				ScientificUser user = new ScientificUser();
				user.setUsername("admin");
				user.setPassword(encodedPassword);

				// Находим или создаём роль для пользователя
				Role role = createRoleIfNotExist("ROLE_ADMIN");
				user.setRole(role);  // Присваиваем роль пользователю

				// Сохраняем пользователя в базе данных
				scientificUserRepository.save(user);
			}
		};
	}

	// Метод для создания роли, если она не существует
	private Role createRoleIfNotExist(String roleName) {
		// Ищем роль по имени
		Role role = (Role) roleRepository.findByName(roleName);
		if (role == null) {
			role = new Role();
			role.setName(roleName);
			roleRepository.save(role);
		}
		return role;
	}
}
