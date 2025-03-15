package org.nharbachyk.diplomabackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = "org.nharbachyk.diplomabackend.repository.jpa")
public class DiplomaBackEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiplomaBackEndApplication.class, args);
    }

}
