package com.lin;

import com.lin.utils.MyMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan("com.lin.dao")
public class AwesomeVideoApplication {

    public static void main(String[] args) {
        SpringApplication.run(AwesomeVideoApplication.class, args);
    }
}
