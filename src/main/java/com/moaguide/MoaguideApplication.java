package com.moaguide;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

@SpringBootApplication
@EnableAsync // 비동기 활성화
@EnableScheduling
public class MoaguideApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoaguideApplication.class, args);
    }

}
