package com.saminassim.mvgjava;

import com.saminassim.mvgjava.config.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class MvgJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(MvgJavaApplication.class, args);
    }

}
