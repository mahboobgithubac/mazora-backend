//package com.mazoraapp.controller;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.mazoraapp.service.ImageUploadService;
//
//@RestController
//@RequestMapping("/api/images")
//public class ImageController {
//
//    private final ImageUploadService imageUploadService;
//
//    public ImageController(ImageUploadService imageUploadService) {
//        this.imageUploadService = imageUploadService;
//    }
//
//    @PostMapping("/upload")
//    public String uploadImage(@RequestParam("file") MultipartFile file) throws Exception {
//        return imageUploadService.uploadFile(file);
//    }
//}
