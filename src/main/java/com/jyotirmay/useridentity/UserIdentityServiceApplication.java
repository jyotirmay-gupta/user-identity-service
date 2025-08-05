package com.jyotirmay.useridentity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserIdentityServiceApplication {

    public static void main(String[] args) {
        Thread t = Thread.currentThread();
        t.setName("User-Identity-Service");
        SpringApplication.run(UserIdentityServiceApplication.class, args);
    }

}
