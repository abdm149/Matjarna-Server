package com.matjarna.authentication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.stream.Stream;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matjarna.dto.user.LoginRequest;
import com.matjarna.service.email.IEmailService;
import com.matjarna.service.jwt.JwtService;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
public class AuthenticationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private JwtService jwtService;

	@Autowired
	IEmailService emailService;

	static Stream<Arguments> authenticationDataProvider() {
		return Stream.of(Arguments.of("admin@matjarna.com", "Matjarna123", 200),
				Arguments.of("invalid_username", "invalid_password", 401),
				Arguments.of("ADMIN@matjarna.Com", "Matjarna123", 200),
				Arguments.of("ADMIN@matjarna.Com", "MATJARNA123", 401),
				Arguments.of("admin@matjarna.com", "MATJARNA123", 401), Arguments.of("admin@matjarna.com", null, 400),
				Arguments.of(null, null, 400), Arguments.of(null, "Matjarna123", 400), Arguments.of(" ", " ", 400),
				Arguments.of("admin@matjarna.com", "Matjarna111", 401),
				Arguments.of("wrongadmin@matjarna.com", "Matjarna123", 401));
	}

	@Order(1)
	@ParameterizedTest
	@MethodSource("authenticationDataProvider")
	public void testSuccessfulLogin(String email, String password, int expected) throws Exception {
		mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(new LoginRequest(email, password)))).andExpect(result -> {
					int status = result.getResponse().getStatus();
					String token = result.getResponse().getContentAsString();
					assertEquals(String.format("Test status failed on email = %s and password = %s", email, password),
							expected, status);
					if (status == 200) {
						String extractedEmail = jwtService.extractEmail(token);
						assertEquals(String.format("extracted email doesn't equal provided email failed on email = %s",
								email), extractedEmail, email.toLowerCase());
						assertTrue("token is not valid", jwtService.isTokenValid(token));
					}

				});
	}

	@Order(2)
	@Test
	public void sendEmail() {
		emailService.sendEmail("tquraan@ejada.com", "Test Subject", "Test Body");
	}
}