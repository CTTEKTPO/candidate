package com.example.candidate;

import com.example.candidate.model.PersonalCard;
import com.example.candidate.repository.PersonalCardRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class CandidateApplicationTests {

//	@Autowired
//	private PersonalCardRepository repo;
//	@Test
//	void contextLoads() {
//	}
//	@Test
//	public void testUpdate() {
//		Long userId = 4L;
//		Optional<PersonalCard> optionalUser = repo.findById(userId);
//		PersonalCard personalCard = optionalUser.get();
//		personalCard.setSalary(11111111);
//		repo.save(personalCard);
//
//		PersonalCard updatedUser = repo.findById(userId).get();
//		Assertions.assertThat(updatedUser.getSalary()).isEqualTo(99999);
//	}
}
