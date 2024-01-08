package com.picpaysimplificado.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.domain.user.UserType;
import com.picpaysimplificado.dtos.UserDTO;

import jakarta.persistence.EntityManager;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {
	
	/* 
	A ideia desse teste em um repository foi apenas para exemplicar como é criado os testes em repositories,
	porém nesse caso não seria necessário, pois esse repository é feito pela JPA e dificilmente irá apresentar um erro
	*/
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	EntityManager entityManager;
	
	@Test
	@DisplayName("Should get User successfully from DB")
	void findUserByDocumentSuccess() {
		String document = "99999999901";
		UserDTO data = new UserDTO("John", "Test", document, new BigDecimal(10), "test@gmail.com", "123456", UserType.COMMON);
		this.createUser(data);
		
		Optional<User> result = this.userRepository.findUserByDocument(document);
		
		assertThat(result.isPresent()).isTrue();	
	}
	
	@Test
	@DisplayName("Should not get User successfully from DB")
	void findUserByDocumentFailed() {
		String document = "99999999901";
		
		Optional<User> result = this.userRepository.findUserByDocument(document);
		
		assertThat(result.isEmpty()).isTrue();
	}
	
	private User createUser(UserDTO data) {
		User newUser = new User(data);
		this.entityManager.persist(newUser);
		return newUser;
	}

}
