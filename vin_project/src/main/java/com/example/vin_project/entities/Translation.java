package com.example.vin_project.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "translation")
public class Translation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "TEXT")
    private String text;
    private String audioUrl;
    private Long translateId;
    @Column(columnDefinition = "TEXT")
    private String translateText;

    @Transient
    private String lang;

}
