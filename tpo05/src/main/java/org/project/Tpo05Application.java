package org.project;

import org.project.config.FallbackProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
public class Tpo05Application {

    public static void main(String[] args) {
        SpringApplication.run(Tpo05Application.class, args);
    }

}
