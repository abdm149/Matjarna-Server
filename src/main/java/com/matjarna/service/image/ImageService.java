package com.matjarna.service.image;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.matjarna.exception.ServiceException;

@Service
public class ImageService implements IImageService {

	private static final String PUBLIC_DIRECTORY = "./public/";

	private static final String UPLOAD_DIRECTORY = "images/";

	@Override
	public String saveImage(MultipartFile file) {
		try {
			String fileName = StringUtils.cleanPath(file.getOriginalFilename());
			Path uploadPath = Paths.get(PUBLIC_DIRECTORY + UPLOAD_DIRECTORY);
			// Ensure the upload directory exists
			Files.createDirectories(uploadPath);
			String uniqueFileName = generateUniqueFileName(fileName);
			// Save the file to the server
			OutputStream outputStream = Files.newOutputStream(uploadPath.resolve(uniqueFileName));
			outputStream.write(file.getBytes());
			return UPLOAD_DIRECTORY + uniqueFileName;
		} catch (IOException e) {
			throw new ServiceException("Service error while saving image", e);
		}

	}

	private String generateUniqueFileName(String originalFileName) {
		LocalDateTime currentDateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
		String formattedDateTime = currentDateTime.format(formatter);
		String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
		return formattedDateTime + fileExtension;
	}

	@Override
	public void deleteImage(String filename) {
		String imagePath = PUBLIC_DIRECTORY + filename;
		try {
			Path imageFilePath = Paths.get(imagePath);
			if (Files.exists(imageFilePath)) {
				Files.delete(imageFilePath);
			} else {
				throw new ServiceException("File not found: " + imagePath);
			}
		} catch (IOException e) {
			throw new ServiceException("Service error while deleting image", e);
		}
	}

}
