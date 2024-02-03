package com.example.vin_project;

import com.example.vin_project.service.TranslationCSVGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VinProjectApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(VinProjectApplication.class, args);
    }
    @Autowired
    private TranslationCSVGenerator translationCSVGenerator;

    @Override
    public void run(String... args) throws Exception {
        translationCSVGenerator.handleCsv();
    }
}
