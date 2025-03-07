package org.nharbachyk.diplomabackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DiplomaBackEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiplomaBackEndApplication.class, args);
    }

}
