package site.archive;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public abstract class MySQLTestContainer {

    private static final String MY_SQL_DOCKER_IMAGE = "mysql:8";
    private static final MySQLContainer<?> MY_SQL_CONTAINER;

    static {
        MY_SQL_CONTAINER = new MySQLContainer<>(MY_SQL_DOCKER_IMAGE)
                               .withDatabaseName("archive-test")
                               .withUsername("test")
                               .withPassword("test-archive");
        MY_SQL_CONTAINER.start();
    }

    @DynamicPropertySource
    public static void mySQLProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.driver-class-name", MY_SQL_CONTAINER::getDriverClassName);
        registry.add("spring.datasource.url", MY_SQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", MY_SQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", MY_SQL_CONTAINER::getPassword);
    }

}
