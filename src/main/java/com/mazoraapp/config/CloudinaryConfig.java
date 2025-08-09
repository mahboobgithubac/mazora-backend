package com.mazoraapp.config;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "dppzmyrou",
            "api_key", "115578945884252",
            "api_secret", "8KiruPfVyrhtgXBzC-vamZY3i5k"
            
        ));
    }
}
