package com.saminassim.mvgjava;

import com.saminassim.mvgjava.config.StorageProperties;
import com.saminassim.mvgjava.service.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class MvgJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(MvgJavaApplication.class, args);
    }

    @Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
         //   storageService.deleteAll();
         //  storageService.init();
        };
    }

}
