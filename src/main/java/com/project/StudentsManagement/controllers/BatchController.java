package com.project.StudentsManagement.controllers;

import com.project.StudentsManagement.utils.ExcelUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/batch")
@RequiredArgsConstructor
public class BatchController {

    private final JobLauncher jobLauncher;
    private final Job job;
    private final Job importExcelJob;



    @GetMapping("/start")
    public String startBatch() {
        try {
            jobLauncher.run(job, new JobParameters());
            return "Batch job started successfully!";
        } catch (Exception e) {
            return "Failed to start batch job: " + e.getMessage();
        }
    }

//    @GetMapping("/start")
//    public String startBatch() {
//        try {
//            JobParameters jobParameters = new JobParametersBuilder()
//                    .addLong("timestamp", System.currentTimeMillis())
//                    .toJobParameters();
//            jobLauncher.run(job, jobParameters);
//            return "Batch job started successfully!";
//        } catch (Exception e) {
//            return "Failed to start batch job: " + e.getMessage();
//        }
//    }


    @PostMapping("/import")
    public String importStudents(@RequestParam("file") MultipartFile file) throws Exception {
        // Sauvegarder temporairement le fichier (si nécessaire)
        String tempFilePath = saveTempFile(file);

        // Créer un JobParameters avec le chemin du fichier
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("file", tempFilePath)  // Passer le chemin du fichier comme paramètre
                .toJobParameters();

        // Lancer le job Spring Batch
        jobLauncher.run(importExcelJob, jobParameters);
        Files.deleteIfExists(Paths.get(tempFilePath));  // Supprimer le fichier temporaire
        System.out.println("Fichier temporaire supprimé : " + tempFilePath);
        return "Job started successfully!";
    }

    private String saveTempFile(MultipartFile file) throws IOException {
        // Sauvegarder le fichier sur le disque pour le passer à Spring Batch
        Path tempFile = Files.createTempFile("import-", ".xlsx");
        System.out.println("path ::"+tempFile );
        file.transferTo(tempFile.toFile());
        return tempFile.toString();
    }


}

