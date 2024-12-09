package com.project.StudentsManagement.config;

import com.project.StudentsManagement.dtos.StudentDto;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    public BatchConfig(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
    }

    @Bean
    public Job generateCertificatesJob(Step generateCertificatesStep) {
        return new JobBuilder("generateCertificatesJob", jobRepository)
                .start(generateCertificatesStep)
                .build();
    }

    @Bean
    public Step generateCertificatesStep(ItemReader<StudentDto> reader,
                                         ItemProcessor<StudentDto, StudentDto> processor,
                                         ItemWriter<StudentDto> writer) {
        return new StepBuilder("generateCertificatesStep", jobRepository)
                .<StudentDto, StudentDto>chunk(10, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}
