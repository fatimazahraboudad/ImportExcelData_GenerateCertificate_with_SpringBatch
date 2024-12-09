package com.project.StudentsManagement.services;

import com.project.StudentsManagement.dtos.StudentDto;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface StudentService {

    void save(MultipartFile file) throws IOException;

    void generateCertificate();

    Resource downloadCertificate(String studentName);

    StudentDto add(StudentDto studentDto);

    List<StudentDto> getAll();

    StudentDto getStudentById(String idStudent);

    StudentDto updateStudent(StudentDto studentDto);

    String deleteStudent(String idStudent);

}
