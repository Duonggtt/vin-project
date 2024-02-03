package com.example.vin_project.service;

import com.example.vin_project.entities.Translation;
import com.example.vin_project.repository.TranslationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TranslationCSVGenerator {

    private static final String SENTENCES_FILE_PATH = "sentences.csv";

    @Autowired
    private TranslationRepository translationRepository;

    private Map<String, Translation>jpnWorks = new HashMap<>();
    private Map<String,Translation>engWorks = new HashMap<>();

    public void handleCsv(){
        // Step1: get eng and jpn words
        loadJpnEngWorksFromCSV();
        // Step2: set audio url
        loadAudioUrlsFromCSV();
        // Step3: set other values and merge CSV
        mergeCSV();
    }
    private  void saveListToFile(List<Translation> translations, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Loop through each object in the list
            for (Translation obj : translations) {
                // Write object properties to the file
                writer.write(obj.toString());
                writer.newLine();
            }
            System.out.println("List of objects has been saved to the file.");
        } catch (IOException e) {
            System.err.println("Error saving list of objects to the file: " + e.getMessage());
        }
    }
    public void loadJpnEngWorksFromCSV() {
        try (BufferedReader br = new BufferedReader(new FileReader(SENTENCES_FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length >= 3) {
                    String id = parts[0];
                    String lang = parts[1];
                    String text = parts[2];
                    if ("eng".equals(lang)) {
                        Translation translation = new Translation();
                        translation.setId(Long.parseLong(id));
                        translation.setText(text);
                        translation.setLang(lang);
                        engWorks.put(id, translation);
                    } else if("jpn".equals(lang)){
                        Translation translation = new Translation();
                        translation.setId(Long.parseLong(id));
                        translation.setText(text);
                        jpnWorks.put(id ,translation);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Map<String, String> loadAudioUrlsFromCSV() {
        Map<String, String> audioUrls = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("sentences_with_audio.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length >= 4) {
                    // Only save eng's audio
                    // Set audio_url
                    if(engWorks.containsKey(parts[0]))
                        engWorks.get(parts[0]).setAudioUrl(parts[3]+"/"+engWorks.get(parts[0]).getLang()+"/" + parts[0] + ".mp3");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return audioUrls;
    }

    private void mergeCSV() {
        try (BufferedReader reader = new BufferedReader(new FileReader("links.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length >= 2) {
                    // Save links with key = eng's key & value = jpn's key
                    if(jpnWorks.containsKey(parts[1]) && engWorks.containsKey(parts[0])){
                        engWorks.get(parts[0]).setTranslateId(jpnWorks.get(parts[1]).getId());
                        engWorks.get(parts[0]).setTranslateText(jpnWorks.get(parts[1]).getText());
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        saveListToFile((List<Translation>) engWorks.values(), "csv_parse_and_merge");
        saveTranslationTosDB();
    }

    private void saveTranslationTosDB(){
        translationRepository.saveAll(engWorks.values());
    }
}
