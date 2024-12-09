package com.project.StudentsManagement.controllers;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/batch")
public class BatchController {

    private final JobLauncher jobLauncher;
    private final Job job;

    public BatchController(JobLauncher jobLauncher, Job job) {
        this.jobLauncher = jobLauncher;
        this.job = job;
    }

    @GetMapping("/start")
    public String startBatch() {
        try {
            jobLauncher.run(job, new JobParameters());
            return "Batch job started successfully!";
        } catch (Exception e) {
            return "Failed to start batch job: " + e.getMessage();
        }
    }
}
