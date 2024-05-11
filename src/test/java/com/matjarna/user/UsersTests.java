package com.matjarna.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matjarna.LoginService;
import com.matjarna.dto.EntityDtoList;
import com.matjarna.dto.user.ChangePasswordRequest;
import com.matjarna.dto.user.LoginRequest;
import com.matjarna.dto.user.RegisterRequest;
import com.matjarna.dto.user.UserDto;
import com.matjarna.exception.ServiceException;
import com.matjarna.model.user.User;
import com.matjarna.service.image.ImageService;

import jakarta.servlet.ServletException;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsersTests {

	private String token;

	private static List<UserDto> users = new ArrayList<>();

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private LoginService loginService;

	@MockBean
	private ImageService imageServiceMock;

	@BeforeAll
	public void beforeAll() throws Exception {
		token = loginService.login("admin@matjarna.com", "Matjarna123");

		mockMvc.perform(get("/api/private/user").contentType(MediaType.APPLICATION_JSON).header("Authorization",
				"Bearer " + token)).andExpect(result -> {
					UserDto user = objectMapper.readValue(result.getResponse().getContentAsString(), UserDto.class);
					users.add(user);
				});
	}

	static Stream<Arguments> userDtoProvider() {
		return Stream.of(
				Arguments.of(UserHelperMethods.createValidRegisterRequest("email@gmail.com", "Password123"), 200),
				Arguments.of(UserHelperMethods.createValidRegisterRequest("emailaaa@gmail.com", "Password123"), 200),
				Arguments.of(UserHelperMethods.createValidRegisterRequest("emailnew@gmail.com", "Password123"), 200),
				Arguments.of(UserHelperMethods.createBlankRegisterRequest(), 400),
				Arguments.of(UserHelperMethods.createBlankRegisterRequest(), 400),
				Arguments.of(UserHelperMethods.createInvalidEmailRegisterRequest(), 400),
				Arguments.of(UserHelperMethods.createDuplicateEmailRegisterRequest(), 409),
				Arguments.of(UserHelperMethods.createDuplicateEmailWithChangeInCaseRegisterRequest(), 409),
				Arguments.of(UserHelperMethods.createInvalidPasswordRegisterRequest(), 400),
				Arguments.of(UserHelperMethods.createDifferentPasswordsRegisterRequest(), 400),
				Arguments.of(UserHelperMethods.createBlankFirstNameRegisterRequest(), 400),
				Arguments.of(UserHelperMethods.createBlankFirstNameRegisterRequest(), 400));
	}

	@Order(1)
	@ParameterizedTest
	@MethodSource("userDtoProvider")
	public void testCreateUser(RegisterRequest registerRequest, int expected) throws Exception {
		mockMvc.perform(post("/api/private/user/create").header("Authorization", "Bearer " + token)
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(registerRequest)))
				.andExpect(result -> {
					int status = result.getResponse().getStatus();
					if (status == 200) {
						assertEquals(String.format("Test failed on user email = %s: ", registerRequest.getEmail()),
								expected, status);
						UserDto userReturned = objectMapper.readValue(result.getResponse().getContentAsString(),
								UserDto.class);
						users.add(userReturned);
					} else {
						assertEquals(String.format("Test failed on email: %s", registerRequest.getEmail()), expected,
								status);
					}

				});
	}

	static Stream<Arguments> deactivateDataSource() {
		return Stream.of(Arguments.of(1, 400), Arguments.of(2, 200), Arguments.of(3, 200));
	}

	@Order(10)
	@MethodSource("deactivateDataSource")
	@ParameterizedTest
	public void deactivateTests(long userId, int expected) throws Exception {
		MockHttpServletRequestBuilder requestBuilder = patch("/api/private/user/{userId}/deactivate", userId)
				.header("Authorization", "Bearer " + token);
		mockMvc.perform(requestBuilder).andExpect(result -> {
			int status = result.getResponse().getStatus();
			assertEquals("Status code mismatch", expected, status);
		});
		if (expected == 200) {
			UserDto user = users.get((int) userId - 1);
			user.setActive(false);
			mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(new LoginRequest(user.getEmail(), "Password123"))))
					.andExpect(status().isUnauthorized());
		}
	}

	static Stream<Arguments> activateDataSource() {
		return Stream.of(Arguments.of(1, 400), Arguments.of(2, 200));
	}

	@Order(11)
	@MethodSource("activateDataSource")
	@ParameterizedTest
	public void activateTests(long userId, int expected) throws Exception {
		MockHttpServletRequestBuilder requestBuilder = patch("/api/private/user/{userId}/activate", userId)
				.header("Authorization", "Bearer " + token);
		mockMvc.perform(requestBuilder).andExpect(result -> {
			int status = result.getResponse().getStatus();
			assertEquals("Status code mismatch", expected, status);

		});
		if (expected == 200) {
			UserDto user = users.get((int) userId - 1);
			user.setActive(true);
			mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(new LoginRequest(user.getEmail(), "Password123"))))
					.andExpect(status().isOk());
		}
	}

	@Order(12)
	@Test
	public void activateTestsInvalidId() throws Exception {
		Exception exception = assertThrows(String.format("activate test failed on user id = %d", -5),
				ServletException.class, () -> {
					mockMvc.perform(patch("/api/private/user/{userId}/activate", -5).header("Authorization",
							"Bearer " + token));
				});
		assertTrue("The cause is not ServiceException as expected.", exception.getCause() instanceof ServiceException);
	}

	@Order(13)
	@Test
	public void deactivateTestsInvalidId() throws Exception {
		Exception exception = assertThrows(String.format("deactivate test failed on user id = %d", -5),
				ServletException.class, () -> {
					mockMvc.perform(patch("/api/private/user/{userId}/deactivate", -5).header("Authorization",
							"Bearer " + token));
				});
		assertTrue("The cause is not ServiceException as expected.", exception.getCause() instanceof ServiceException);
	}

	@Order(14)
	@Nested
	@AutoConfigureMockMvc
	@TestInstance(TestInstance.Lifecycle.PER_CLASS)
	@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
	class patchTests {

		private String authToken;

		UserDto user = users.get(1);

		@BeforeAll
		public void beforeAll() throws Exception {
			authToken = loginService.login(user.getEmail(), "Password123");
		}

		public static Stream<Arguments> patchNameDataProvider() {
			return Stream.of(Arguments.of("Amro", null, 200), Arguments.of(null, "Abulaban", 400),
					Arguments.of("tareq", "quraan", 200), Arguments.of(null, null, 400));
		}

		@Order(1)
		@ParameterizedTest
		@MethodSource("patchNameDataProvider")
		public void testPatchName(String firstName, String lastName, int expected) throws Exception {
			MockHttpServletRequestBuilder requestBuilder = patch("/api/private/user/names")
					.contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + authToken);
			mockMvc.perform(requestBuilder.param("firstName", firstName).param("lastName", lastName))
					.andExpect(result -> {
						assertEquals(String.format("Test failed with params: %s + %s", firstName, lastName), expected,
								result.getResponse().getStatus());
						if (expected != 400) {
							UserDto user = objectMapper.readValue(result.getResponse().getContentAsString(),
									UserDto.class);
							assertEquals(
									String.format("Test failed with id: %d, first name doesn't match!", user.getId()),
									firstName, user.getFirstName());
							assertEquals(
									String.format("Test failed with id: %d, last name doesn't match!", user.getId()),
									lastName, user.getLastName());
						}
					});
		}

		public static Stream<Arguments> changeUserImageDataProvider() {
			return Stream.of(Arguments.of("imageUrl.png", 200), Arguments.of("imageUrl2.png", 200),
					Arguments.of(null, 200));
		}

		@Order(2)
		@ParameterizedTest
		@MethodSource("changeUserImageDataProvider")
		public void testPatchUserImage(String imageUrl, int expected) throws Exception {
			mockMvc.perform(patch("/api/private/user/image").param("imageUrl", imageUrl).header("Authorization",
					"Bearer " + authToken)).andExpect(result -> {
						UserDto user = objectMapper.readValue(result.getResponse().getContentAsString(), UserDto.class);
						assertEquals(String.format("Test failed on id: %d , image URL does not match!", user.getId()),
								imageUrl, user.getProfilePicture());
						assertEquals(String.format("Test failed on id: %d , status does not match!", user.getId()),
								expected, result.getResponse().getStatus());
					});

		}

		public static Stream<Arguments> changePasswordDataProvider() {
			return Stream.of(
					Arguments.of(UserHelperMethods.createPasswordRequest("Password123", "matjarna234", "matjarna234"),
							200),
					Arguments.of(UserHelperMethods.createPasswordRequest("Matjarna123", "matjarna234", "matjarna234"),
							400),
					Arguments.of(UserHelperMethods.createPasswordRequest("matjarna234", "matjarna", "matjarna234"),
							400),
					Arguments.of(UserHelperMethods.createPasswordRequest("matjarna234", "matjarna234", "matjarna234"),
							400),
					Arguments.of(UserHelperMethods.createPasswordRequest("matjarna234", null, null), 400));
		}

		@Order(3)
		@ParameterizedTest
		@MethodSource("changePasswordDataProvider")
		public void changePasswordTest(ChangePasswordRequest changePasswordRequest, int expected) throws Exception {
			mockMvc.perform(patch("/api/private/user/password").header("Authorization", "Bearer " + authToken)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(changePasswordRequest))).andExpect(result -> {
						int status = result.getResponse().getStatus();
						assertEquals(String.format("Test failed"), expected, status);
					});
			if (expected == 200) {
				UserDto user = users.get(1);
				mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(
								new LoginRequest(user.getEmail(), changePasswordRequest.getPassword()))))
						.andExpect(status().isOk());
			}
		}
	}

	public static Stream<Arguments> userDataProvider() {
		return Stream.of(Arguments.of(1));
	}

	@Order(20)
	@ParameterizedTest
	@MethodSource("userDataProvider")
	public void testGetUserInfo(long id) throws Exception {
		mockMvc.perform(get("/api/private/user").contentType(MediaType.APPLICATION_JSON).header("Authorization",
				"Bearer " + token)).andExpect(result -> {
					UserDto user = objectMapper.readValue(result.getResponse().getContentAsString(), UserDto.class);
					assertEquals(String.format("Wrong user info with id: %d", id), id, user.getId());
				});
	}

	static Stream<Arguments> getUsersDataSource() {
		return Stream.of(Arguments.of(0, 2));
	}

	@Order(21)
	@MethodSource("getUsersDataSource")
	@ParameterizedTest
	public void getUsersTest(Integer page, Integer size) throws Exception {

		MockHttpServletRequestBuilder requestBuilder = get("/api/private/users/").header("Authorization",
				"Bearer " + token);
		if (page != null) {
			requestBuilder.param("page", String.valueOf(page));
		}
		if (size != null) {
			requestBuilder.param("size", String.valueOf(size));
		}
		mockMvc.perform(requestBuilder).andExpect(result -> {
			// Status check
			int status = result.getResponse().getStatus();
			assertEquals(String.format("Test failed on page = %d and size = %d with a status = %d", page, size, status),
					status, 200);

			// Total number check
			@SuppressWarnings("unchecked")
			EntityDtoList<UserDto, User> UsersList = objectMapper.readValue(result.getResponse().getContentAsString(),
					EntityDtoList.class);
			long totalNumberToCheck = UsersList.getTotalNumber();
			long totalNumberGiven = users.size();
			assertEquals("The users list totalNumber is not correct!", totalNumberGiven, totalNumberToCheck);

			// Size returned check
			int pageToCheck = (page == null) ? 0 : page;
			int sizeToCheck = (size == null) ? 10 : size;
			long currentPageSize = 0;
			if (totalNumberToCheck > sizeToCheck * pageToCheck) {
				currentPageSize = Math.min(totalNumberToCheck - pageToCheck * sizeToCheck, sizeToCheck);
			}
			assertEquals(
					String.format("Test failed with expectedCurrentPageSize = %d and found the actual size = %d",
							currentPageSize, UsersList.getResults().size()),
					currentPageSize, UsersList.getResults().size());
		});
	}

	static Stream<Arguments> filteringDataSource() {
		return Stream.of(
				// Null search term
				Arguments.of(null, false),

				// different search term values with different active values
				Arguments.of("Adm", false), Arguments.of("S", false));
	}

	@Order(22)
	@MethodSource("filteringDataSource")
	@ParameterizedTest
	public void filteringTest(String searchTerm, Boolean active) throws Exception {
		MockHttpServletRequestBuilder requestBuilder = get("/api/private/users/")
				.header("Authorization", "Bearer " + token).param("active", String.valueOf(active));
		if (searchTerm != null) {
			requestBuilder.param("searchTerm", searchTerm);
		}

		mockMvc.perform(requestBuilder).andExpect(result -> {

			@SuppressWarnings("unchecked")
			EntityDtoList<UserDto, User> entityDtoList = objectMapper
					.readValue(result.getResponse().getContentAsString(), EntityDtoList.class);
			long totalNumberToCheck = entityDtoList.getTotalNumber();
			long totalNumber = UserHelperMethods.totalNumberCalculator(users, searchTerm, active);
			assertEquals("The number of filtered users is not correct!", totalNumber, totalNumberToCheck);
		});
	}
}