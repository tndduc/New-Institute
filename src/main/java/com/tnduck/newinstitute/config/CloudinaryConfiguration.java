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
                "cloud_name", "dpksmnl3p",
                "api_key", "148671664393439",
                "api_secret", "yd_ZostmmlyroO93Km2J7b8p2qA",
                "secure", true));
    }
}
