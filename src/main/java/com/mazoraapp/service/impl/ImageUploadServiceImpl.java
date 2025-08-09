package com.mazoraapp.service.impl;
import com.cloudinary.Cloudinary;

import com.cloudinary.utils.ObjectUtils;
import com.mazoraapp.service.ImageUploadService;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class ImageUploadServiceImpl implements ImageUploadService{

    private final Cloudinary cloudinary;

    public ImageUploadServiceImpl(Cloudinary cloudinary)    {
        this.cloudinary = cloudinary;
    }

    public String uploadFile(MultipartFile file) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(),
                ObjectUtils.asMap("folder", "mazora_products"));
        return uploadResult.get("secure_url").toString();
    }
}
