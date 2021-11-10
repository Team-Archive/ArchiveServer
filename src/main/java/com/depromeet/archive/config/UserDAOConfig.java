package com.depromeet.archive.config;

import com.depromeet.archive.infra.user.UserRepository;
import com.depromeet.archive.infra.user.jpa.UserJpaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserDAOConfig {

    @Bean
    public UserRepository userRepository(UserJpaRepository jpaRepository) {
        return new UserRepository(jpaRepository);
    }

}
