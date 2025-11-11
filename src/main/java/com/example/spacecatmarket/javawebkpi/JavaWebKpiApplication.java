package com.example.spacecatmarket.javawebkpi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class JavaWebKpiApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaWebKpiApplication.class, args);
    }

}
