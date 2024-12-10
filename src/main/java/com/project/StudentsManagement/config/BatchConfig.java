package com.project.StudentsManagement.config;

import com.project.StudentsManagement.dtos.StudentDto;
import com.project.StudentsManagement.entities.Student;
import com.project.StudentsManagement.repositories.StudentRepository;
import com.project.StudentsManagement.utils.CertificateItemWriter;
import com.project.StudentsManagement.utils.StudentItemProcessor;
import com.project.StudentsManagement.utils.StudentItemReader;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.IOException;
import java.io.InputStream;

//@Configuration
//public class BatchConfig {
//
//    private final JobRepository jobRepository;
//    private final PlatformTransactionManager transactionManager;
//
//    public BatchConfig(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
//        this.jobRepository = jobRepository;
//        this.transactionManager = transactionManager;
//    }
//
//    @Bean
//    public Job generateCertificatesJob(Step generateCertificatesStep) {
//        return new JobBuilder("generateCertificatesJob", jobRepository)
//                .start(generateCertificatesStep)
//                .build();
//    }
//
//    @Bean
//    public Step generateCertificatesStep(ItemReader<StudentDto> reader,
//                                         ItemProcessor<StudentDto, StudentDto> processor,
//                                         ItemWriter<StudentDto> writer) {
//        return new StepBuilder("generateCertificatesStep", jobRepository)
//                .<StudentDto, StudentDto>chunk(5, transactionManager)
//                .reader(reader)
//                .processor(processor)
//                .writer(writer)
//                .build();
//    }
//}
@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final StudentRepository studentRepository;

    @Bean
    public Job importStudentJob(Step fetchDataFromExcelToData) throws IOException {
        return new JobBuilder("fetchDataFromExcelToDataJob", jobRepository)
                .start(importStudentStep())
                .build();
    }

    @Bean
    public Step importStudentStep() throws IOException {
        return new StepBuilder("fetchDataFromExcelToData", jobRepository)
                .<Student, Student>chunk(3, transactionManager)
                .reader(studentItemReader(null))
                .processor(studentItemProcessor())
                .writer(studentItemWriter())
                .build();
    }

    @Bean
    @StepScope  // Utilisation de StepScope pour permettre l'accès aux JobParameters
    public StudentItemReader studentItemReader(@Value("#{jobParameters['file']}") String filePath) throws IOException {
        return new StudentItemReader(filePath);  // Passer le chemin du fichier au lecteur
    }

    @Bean
    public ItemProcessor<Student, Student> studentItemProcessor() {
        return new StudentItemProcessor(); // Si vous avez un ItemProcessor
    }

    @Bean
    public ItemWriter<Student> studentItemWriter() {
        return new CertificateItemWriter(studentRepository); // Votre writer pour persister les étudiants dans la base de données
    }
}