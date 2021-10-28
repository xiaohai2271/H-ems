package cn.celess.j2ee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class J2eeApplication {

    public static void main(String[] args) {
        SpringApplication.run(J2eeApplication.class, args);
    }

}
