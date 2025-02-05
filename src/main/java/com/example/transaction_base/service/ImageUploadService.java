package com.example.transaction_base.service;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class ImageUploadService {

    @Autowired
    private static Cloudinary cloudinary;

    public static String uploadImage(MultipartFile file) throws IOException {
        Map<String, Object> options = new HashMap<>();
        options.put("folder", "your_folder_name");

        Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), options);
        return (String) uploadResult.get("url");
    }
}