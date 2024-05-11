package com.matjarna.facade.image;

import java.io.IOException;

import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.matjarna.service.image.IImageService;

import jakarta.validation.ValidationException;

@Service
public class ImageFacade implements IImageFacade {

	@Autowired
	private IImageService imageService;

	@Override
	public String saveImage(MultipartFile file) {

		try {
			// Use Commons Imaging to check if the file is a valid image
			Imaging.getMetadata(file.getInputStream(), null);
		} catch (IOException | ImageReadException e) {
			throw new RuntimeException("Error while reading image from stream");
		} catch (IllegalArgumentException e) {
			throw new ValidationException("Invalid file format");
		}
		return imageService.saveImage(file);

	}

	@Override
	public void deleteImage(String filename) {
		imageService.deleteImage(filename);
	}

}
