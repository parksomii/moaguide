package com.moaguide;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync // 비동기 활성화
@EnableScheduling
public class MoaguideApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoaguideApplication.class, args);
    }

}
