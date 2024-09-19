package com.mfac.email;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "com.mfac.email.client")
@SpringBootApplication
public class EmailApplication {
    public static void main(String[] args) {
        SpringApplication.run(EmailApplication.class, args);
    }
}
