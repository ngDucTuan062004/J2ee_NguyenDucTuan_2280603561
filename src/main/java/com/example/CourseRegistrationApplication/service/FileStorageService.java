package com.example.CourseRegistrationApplication.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.nio.file.*;
import java.util.UUID;

@Service
public class FileStorageService {

	@Value("${app.upload.dir:uploads}")
	private String uploadDir;

	public String storeFile(MultipartFile file) throws IOException {
		Path uploadPath = Paths.get(uploadDir);
		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}

		String originalFilename = file.getOriginalFilename();
		String ext = "";
		if (originalFilename != null && originalFilename.contains(".")) {
			ext = originalFilename.substring(originalFilename.lastIndexOf("."));
		}
		String fileName = UUID.randomUUID().toString() + ext;

		Path filePath = uploadPath.resolve(fileName);
		Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

		return "/uploads/" + fileName;
	}
}