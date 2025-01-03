package com.global.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.global.entity.Product;
import com.global.error.FileStorageException;
import com.global.repository.ProductRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service

@RequiredArgsConstructor
@Log4j2
public class FileUploadService {
	private ProductService productService;

    @Autowired
    public void ProductService(@Lazy ProductService productService) {
        this.productService = productService;
    }
	
	private final ProductRepo productRepo;
	
	private Path fileStorageLocation;
	
	@Value("${file.upload.base-path}")
	private String basePath = "";

	public String[] uploadFile(String id, String pathType, File file){
		
		log.info("basePath is " + basePath + " and pathType is " + pathType);
		
		this.fileStorageLocation = Paths.get(basePath + "/" + pathType).toAbsolutePath().normalize();
		
		try {
			Files.createDirectories(this.fileStorageLocation);
		}catch(Exception ex) {
			throw new FileStorageException("Error occured while trying to create directory!", ex);
		}
		
		String fileName = StringUtils.cleanPath(id + "--" + file.getName());
		
		try {
			
			if(fileName.contains("..")) {
				throw new FileStorageException("Invlaid file name (contains '..')!");
			}
			
			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			
			log.info(targetLocation.toString());
			
			InputStream targetStream = new FileInputStream(file);
			Files.copy(targetStream, targetLocation, StandardCopyOption.REPLACE_EXISTING);

			//updateImagePath(id, pathType, fileName);
			
			return new String[] {id, pathType, fileName};
		}catch(Exception ex) {
			throw new FileStorageException("Could not store file " + fileName + ", Try again!", ex);
		}
	}
	
	public boolean deleteFile(String filePath) {

		log.info("filePath is " + filePath);

		if(filePath == null) return false;
		
		File file = new File(filePath);
		
		if(file.exists()) {
			log.info(">> file.getPath() >>>" + file.getPath());
			return file.delete();
		}else {
            System.err.println("File not found: " + filePath);
            return false;
		}
	}
	
	public void updateFilePath(String id, String pathType, String fileName) {					
		Product product = productService.findById(id).get();
		product.setImageUrl(basePath + "/" + pathType + "/" + fileName); 	
		productRepo.save(product);
	}
	
    public File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        // Create a temporary file
        File file = new File(multipartFile.getOriginalFilename());

        // Write the MultipartFile to the temporary file
        try (OutputStream os = new FileOutputStream(file)) {
            os.write(multipartFile.getBytes());
        }

        // Return the temporary file
        return file;
    }
}
