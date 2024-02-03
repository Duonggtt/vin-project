package com.example.vin_project.repository;


import com.example.vin_project.entities.Translation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TranslationRepository extends JpaRepository<Translation, String> {
}