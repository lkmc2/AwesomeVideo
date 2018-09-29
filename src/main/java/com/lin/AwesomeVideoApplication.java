package com.lin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;


@SpringBootApplication
@MapperScan("com.lin.dao")
public class AwesomeVideoApplication {

    public static void main(String[] args) {
        SpringApplication.run(AwesomeVideoApplication.class, args);
    }
}
