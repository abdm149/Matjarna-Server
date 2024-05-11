package com.matjarna.facade.image;

import org.springframework.web.multipart.MultipartFile;

public interface IImageFacade {

	String saveImage(MultipartFile file);

	void deleteImage(String filename);

}
