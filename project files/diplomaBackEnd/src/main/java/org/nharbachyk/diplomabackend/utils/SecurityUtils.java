package org.nharbachyk.diplomabackend.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityUtils {

    @Value("${security.password-encode-strength}")
    private int encodingStrength;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(encodingStrength);
    }

}
