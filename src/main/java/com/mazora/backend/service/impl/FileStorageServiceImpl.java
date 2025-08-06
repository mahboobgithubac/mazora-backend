package com.mazora.backend.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageServiceImpl {

    private final static Path uploadDir = Paths.get("uploads");

    public FileStorageServiceImpl() throws IOException {
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
    }

    public static String saveFile(MultipartFile file) throws IOException {
    //	System.out.println("file.getOriginalFilename());->"+file.getOriginalFilename());
    	 String fileName1 = replaceBlanksWithUnderscore(file.getOriginalFilename());
        String fileName = UUID.randomUUID() + "_" +replaceBlanksWithUnderscore(fileName1);
        Path filePath = uploadDir.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
//        return "/uploads" + fileName;
        //System.out.println("filePath->"+filePath.toString());
        return  fileName;
    }
    
    public static String replaceBlanksWithUnderscore(String input) {
        if (input == null) {
            return null;
        }
        return input.trim().replaceAll("\\s+", "_");
    }
}