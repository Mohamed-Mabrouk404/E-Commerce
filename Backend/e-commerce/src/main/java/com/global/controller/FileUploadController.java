package com.global.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.global.service.FileUploadService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("api/file")

@Log4j2
@RequiredArgsConstructor
public class FileUploadController {

	private final FileUploadService fileUploadService;

	@PostMapping("/upload")
	public ResponseEntity<?> uploadFile(@RequestParam String id, @RequestParam String pathType,
			@RequestParam MultipartFile file) throws IOException {
		String[] fileDetails = fileUploadService.uploadFile(id, pathType,
				fileUploadService.convertMultipartFileToFile(file));
		return ResponseEntity.ok(fileDetails[2]);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteFile(@RequestParam String filePath) throws IOException {
		boolean isDeleted = fileUploadService.deleteFile(filePath);
		return ResponseEntity.ok("Supposed file deleted? " + isDeleted);
	}

}
