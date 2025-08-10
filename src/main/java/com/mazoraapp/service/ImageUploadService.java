package com.mazoraapp.service;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;
public interface ImageUploadService {
	 public   String uploadFile(MultipartFile file) throws IOException ;
	}

