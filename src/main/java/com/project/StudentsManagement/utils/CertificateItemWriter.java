package com.project.StudentsManagement.utils;

import com.project.StudentsManagement.dtos.StudentDto;
import com.project.StudentsManagement.entities.Student;
import com.project.StudentsManagement.mappers.StudentMapper;
import com.project.StudentsManagement.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

//@Component
//public class CertificateItemWriter implements ItemWriter<StudentDto> {
//
//    @Override
//    public void write(Chunk<? extends StudentDto> chunk) {
//        chunk.forEach(student -> {
//            try {
//                System.out.println(student.toString());
//                CertificateGenerator.generateCertificate(
//                        student.getStudentName(),
//                        student.getNote(),
//                        student.getMention().name()
//                );
//            } catch (Exception e) {
//                System.err.println("Failed to generate certificate for student: " + student.getStudentName());
//            }
//        });
//    }
//}

@RequiredArgsConstructor
public class CertificateItemWriter implements ItemWriter<Student> {

    private final StudentRepository studentRepository;
    @Override
    public void write(Chunk<? extends Student> chunks) throws Exception {
        for (Student chunk : chunks) {
            studentRepository.save(chunk);
        }
    }
}


