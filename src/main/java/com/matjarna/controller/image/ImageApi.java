package com.matjarna.controller.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.matjarna.facade.image.IImageFacade;

@RestController
@RequestMapping("api")
public class ImageApi {

	@Autowired
	private IImageFacade imageFacade;

	@PostMapping("private/image/upload")
	public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
		String imageUrl = imageFacade.saveImage(file);
		return ResponseEntity.ok(imageUrl);
	}

	@DeleteMapping("private/image/delete")
	public ResponseEntity<Void> deleteImage(@RequestParam("filename") String filename) {
		imageFacade.deleteImage(filename);
		return ResponseEntity.status(200).build();
	}
}
