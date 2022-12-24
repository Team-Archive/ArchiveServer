package site.archive.domain;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@TestConfiguration
public class JpaTestConfig {
    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }
}

