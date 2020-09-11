package com.go.tiny.persistence;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.go.tiny.*")
public class GoTinyJpaTestApplication {
  public static void main(String[] args) {
    SpringApplication.run(GoTinyJpaTestApplication.class, args);
  }
}
