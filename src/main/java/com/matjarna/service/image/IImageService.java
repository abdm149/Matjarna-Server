package com.matjarna.service.image;

import org.springframework.web.multipart.MultipartFile;

public interface IImageService {

	String saveImage(MultipartFile file);

	void deleteImage(String filename);

}
