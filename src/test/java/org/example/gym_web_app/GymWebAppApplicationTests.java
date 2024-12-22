package org.example.gym_web_app;

import static org.assertj.core.api.Assertions.assertThat;

import org.example.gym_web_app.controller.MemberController;
import org.example.gym_web_app.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GymWebAppApplicationTests {

	@Autowired
	private MemberController memberController;

	@Autowired
	private MemberService memberService;

	@Test
	void contextLoads() {
		// Verify that the application context loads successfully
	}

	@Test
	void testMemberControllerBean() {
		assertThat(memberController).isNotNull();
	}

	@Test
	void testMemberServiceBean() {
		assertThat(memberService).isNotNull();
	}
}
