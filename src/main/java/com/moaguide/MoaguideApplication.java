package com.moaguide;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.moaguide.controller")
public class MoaguideApplication {


    public static void main(String[] args) {
        SpringApplication.run(MoaguideApplication.class, args);
    }

}
