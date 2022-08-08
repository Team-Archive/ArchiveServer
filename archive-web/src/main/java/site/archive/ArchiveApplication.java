package site.archive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "site.archive")
public class ArchiveApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArchiveApplication.class, args);
    }

}
