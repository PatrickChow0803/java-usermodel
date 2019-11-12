package com.lambdaschool.usermodel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class UserModelApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(UserModelApplication.class,
                              args);
    }
}
