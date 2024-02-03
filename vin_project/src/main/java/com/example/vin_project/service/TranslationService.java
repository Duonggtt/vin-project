package com.example.vin_project.service;

import com.example.vin_project.entities.Translation;
import com.example.vin_project.repository.TranslationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TranslationService {
    @Autowired
    private TranslationRepository translationRepository;


    public List<Translation> getTranslationData(Pageable pageable){
        return translationRepository.findAll(pageable).getContent();
    }
}
