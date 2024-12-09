package com.project.StudentsManagement.utils;

import com.project.StudentsManagement.dtos.StudentDto;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
public class CertificateItemWriter implements ItemWriter<StudentDto> {

    @Override
    public void write(Chunk<? extends StudentDto> chunk) {
        chunk.forEach(student -> {
            try {
                CertificateGenerator.generateCertificate(
                        student.getStudentName(),
                        student.getNote(),
                        student.getMention().name()
                );
            } catch (Exception e) {
                System.err.println("Failed to generate certificate for student: " + student.getStudentName());
            }
        });
    }
}


