package com.tnduck.newinstitute.config;

import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ductn
 * @project The new institute
 * @created 21/02/2024 - 10:31 PM
*/
@Configuration
public class CloudinaryConfiguration {
    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "da7j0ful6",
                "api_key", "366263729642465",
                "api_secret", "mvn5GQ6p2tZao7VkwYHpDtIWFpI",
                "secure", true));
    }
}
