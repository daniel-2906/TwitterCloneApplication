package com.TwitterClone.TwitterApplication.Service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;

@Service
public class ImageUploadService {

    @Autowired
    private Cloudinary cloudinary;

   
    public Map uploadImageFromBase64(String base64Image, String publicId) throws IOException {
        // Remove the prefix if it exists (data:image/png;base64,)
        String[] parts = base64Image.split(",");
        String imageString = parts.length > 1 ? parts[1] : parts[0];

        // Decode the Base64 string
        byte[] imageBytes = Base64.getDecoder().decode(imageString);

        // Prepare options for upload, including the public ID
        Map<String, Object> options = ObjectUtils.asMap("public_id", publicId);

        // Upload to Cloudinary
        return cloudinary.uploader().upload(imageBytes, options);
    }
    
    public void deleteImage(String publicId) throws IOException {
        cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
    }

}

