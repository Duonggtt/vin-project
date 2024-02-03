package com.example.vin_project.controller;

import com.example.vin_project.entities.Translation;
import com.example.vin_project.service.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TranslationController {

    @Autowired
    private TranslationService translationService;

    @GetMapping("/translations")
    public ResponseEntity<List<Translation>> getTranslations(
            @RequestParam(name = "page_number",defaultValue = "0") int pageNumber,
            @RequestParam(name = "page_size" ,defaultValue = "10") int pageSize) {


        return ResponseEntity.ok().body(translationService.getTranslationData(PageRequest.of(pageNumber, pageSize)));
    }
}
