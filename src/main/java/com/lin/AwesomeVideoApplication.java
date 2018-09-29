package com.lin;

import com.lin.utils.MyMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;


@SpringBootApplication
@MapperScan(basePackages = {"com.lin.dao"}, markerInterface = MyMapper.class)
public class AwesomeVideoApplication {

    public static void main(String[] args) {
        SpringApplication.run(AwesomeVideoApplication.class, args);
    }
}
