package com.matjarna.image;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.matjarna.LoginService;
import com.matjarna.exception.ServiceException;

import jakarta.servlet.ServletException;
import jakarta.validation.ValidationException;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ImagesTests {

	private static List<String> imagePaths = new ArrayList<>();

	private String token;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private LoginService loginService;

	@BeforeAll
	public void beforeAll() throws Exception {
		token = loginService.login("admin@matjarna.com", "Matjarna123");
	}

	@Order(1)
	@Test
	public void testHandleFileUploadValidImageJpg() throws Exception {
		byte[] fileContent = Files.readAllBytes(Paths.get("src/test/resources/test-image.jpg"));
		MockMultipartFile file = new MockMultipartFile("file", "test-image.jpg", MediaType.IMAGE_JPEG_VALUE,
				fileContent);
		LocalDateTime timeBefore = LocalDateTime.now().minusSeconds(1).withNano(0);

		mockMvc.perform(
				multipart("/api/private/image/upload").file(file).header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
				.andExpect(result -> {

					LocalDateTime timeAfter = LocalDateTime.now().plusSeconds(1).withNano(0);

					// Status check
					int status = result.getResponse().getStatus();
					assertTrue(status == 200);

					// Naming check
					String name = result.getResponse().getContentAsString();
					assertTrue(name.endsWith(".jpg"));

					// To get the name without the milliseconds and the directory name
					String datetimePart = name.substring(name.lastIndexOf('/') + 1, name.length() - 7);
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
					LocalDateTime parsedDateTime = LocalDateTime.parse(datetimePart, formatter);
					boolean validNaming = parsedDateTime.isEqual(timeBefore) || parsedDateTime.isEqual(timeAfter)
							|| (parsedDateTime.isAfter(timeBefore) && parsedDateTime.isBefore(timeAfter));
					assertTrue("Naming of the file should be within the current time!", validNaming);
					imagePaths.add(name);
				});
	}

	@Order(2)
	@Test
	public void testHandleFileUploadValidImagePng() throws Exception {
		byte[] fileContent = Files.readAllBytes(Paths.get("src/test/resources/test-image.png"));
		MockMultipartFile file = new MockMultipartFile("file", "test-image.png", MediaType.IMAGE_PNG_VALUE,
				fileContent);

		LocalDateTime timeBefore = LocalDateTime.now().minusSeconds(1).withNano(0);

		mockMvc.perform(
				multipart("/api/private/image/upload").file(file).header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
				.andExpect(result -> {

					LocalDateTime timeAfter = LocalDateTime.now().plusSeconds(1).withNano(0);

					// Status check
					int status = result.getResponse().getStatus();
					assertTrue(status == 200);

					// Naming check
					String name = result.getResponse().getContentAsString();
					assertTrue(name.endsWith(".png"));

					// To get the name without the milliseconds and the directory name
					String datetimePart = name.substring(name.lastIndexOf('/') + 1, name.length() - 7);
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
					LocalDateTime parsedDateTime = LocalDateTime.parse(datetimePart, formatter);
					boolean validNaming = parsedDateTime.isEqual(timeBefore) || parsedDateTime.isEqual(timeAfter)
							|| (parsedDateTime.isAfter(timeBefore) && parsedDateTime.isBefore(timeAfter));
					assertTrue("Naming of the file should be within the current time!", validNaming);
					imagePaths.add(name);
				});
	}

	@Order(3)
	@Test
	public void testHandleFileUploadValidImagePngWithJpgFormat() throws Exception {
		byte[] fileContent = Files.readAllBytes(Paths.get("src/test/resources/test-image.png"));
		MockMultipartFile file = new MockMultipartFile("file", "test-image.png", MediaType.IMAGE_JPEG_VALUE,
				fileContent);

		LocalDateTime timeBefore = LocalDateTime.now().minusSeconds(1).withNano(0);

		mockMvc.perform(
				multipart("/api/private/image/upload").file(file).header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
				.andExpect(result -> {

					LocalDateTime timeAfter = LocalDateTime.now().plusSeconds(1).withNano(0);

					// status check
					int status = result.getResponse().getStatus();
					assertTrue(status == 200);

					// Naming check
					String name = result.getResponse().getContentAsString();
					assertTrue(name.endsWith(".png"));

					// To get the name without the milliseconds and the directory name
					String datetimePart = name.substring(name.lastIndexOf('/') + 1, name.length() - 7);
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
					LocalDateTime parsedDateTime = LocalDateTime.parse(datetimePart, formatter);
					boolean validNaming = parsedDateTime.isEqual(timeBefore) || parsedDateTime.isEqual(timeAfter)
							|| (parsedDateTime.isAfter(timeBefore) && parsedDateTime.isBefore(timeAfter));
					assertTrue("Naming of the file should be within the current time!", validNaming);
					imagePaths.add(name);
				});
	}

	@Order(4)
	@Test
	public void testHandleFileUploadTxtFile() throws Exception {
		MockMultipartFile mockFile = new MockMultipartFile("file", "test-invalid-image.txt", "text/plain",
				"file.txt".getBytes());

		Exception exception = assertThrows("Upload invalid image failed!", ServletException.class, () -> {
			mockMvc.perform(MockMvcRequestBuilders.multipart("/api/private/image/upload").file(mockFile)
					.header(HttpHeaders.AUTHORIZATION, "Bearer " + token).contentType(MediaType.MULTIPART_FORM_DATA))
					.andExpect(result -> {
						assertTrue(false);
					});
		});
		assertTrue("The cause is not ValidationException as expected.",
				exception.getCause() instanceof ValidationException);
	}

	@Order(5)
	@Test
	public void testHandleFileUploadCorruptedImage() throws Exception {
		MockMultipartFile mockFile = new MockMultipartFile("file", "test-invalid-image.jpg", MediaType.IMAGE_JPEG_VALUE,
				"file.txt".getBytes());

		Exception exception = assertThrows("Upload corrupted image failed!", ServletException.class, () -> {
			mockMvc.perform(MockMvcRequestBuilders.multipart("/api/private/image/upload").file(mockFile)
					.header(HttpHeaders.AUTHORIZATION, "Bearer " + token).contentType(MediaType.MULTIPART_FORM_DATA))
					.andExpect(result -> {
						assertTrue(false);
					});
		});
		assertTrue("The cause is not ValidationException as expected.",
				exception.getCause() instanceof ValidationException);
	}

	@Order(31)
	@Test
	public void testDeleteImageSuccessfully() throws Exception {
		for (String filename : imagePaths) {
			mockMvc.perform(MockMvcRequestBuilders.delete("/api/private/image/delete").param("filename", filename)
					.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)).andExpect(result -> {
						int status = result.getResponse().getStatus();
						assertEquals("Image deletion should return 200", 200, status);
					});
			assertEquals("Image should not exist after deletion", false,
					Files.exists(Paths.get("./public/images", filename)));
		}
	}

	@Order(32)
	@Test
	public void testDeleteNonExistingImage() throws Exception {
		String filename = "non-existing-image123.jpg";
		Exception exception = assertThrows(String.format("Delete test failed on image path = %s", filename),
				ServletException.class, () -> {
					mockMvc.perform(delete("/api/private/image/delete").param("filename", filename)
							.header(HttpHeaders.AUTHORIZATION, "Bearer " + token));
				});
		assertTrue("The cause is not ServiceException as expected.", exception.getCause() instanceof ServiceException);
	}
}