package com.depromeet.archive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@EnableJpaAuditing
public class ArchiveApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArchiveApplication.class, args);
    }

}
