package com.lin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;


@SpringBootApplication
@MapperScan("com.lin.dao")
@ComponentScan("com.lin")
public class AwesomeVideoApplication {

    public static void main(String[] args) {
        SpringApplication.run(AwesomeVideoApplication.class, args);
    }
}
