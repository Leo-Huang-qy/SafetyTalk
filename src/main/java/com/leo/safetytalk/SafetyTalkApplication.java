package com.leo.safetytalk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.leo.safetytalk.mapper")
public class SafetyTalkApplication {

    public static void main(String[] args) {
        SpringApplication.run(SafetyTalkApplication.class, args);
    }

}
