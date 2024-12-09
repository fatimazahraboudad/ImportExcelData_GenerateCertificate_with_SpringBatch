package com.project.StudentsManagement.services;

import com.project.StudentsManagement.dtos.StudentDto;
import com.project.StudentsManagement.entities.Student;
import com.project.StudentsManagement.enumeration.Emention;
import com.project.StudentsManagement.exceptions.CertificateNotFoundException;
import com.project.StudentsManagement.exceptions.InvalidDataException;
import com.project.StudentsManagement.exceptions.StudentNotFoundException;
import com.project.StudentsManagement.mappers.StudentMapper;
import com.project.StudentsManagement.repositories.StudentRepository;
import com.project.StudentsManagement.utils.CertificateGenerator;
import com.project.StudentsManagement.utils.ExcelUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ServiceStudentImpl implements StudentService{

    private final StudentRepository studentRepository;
   @Override
    public void save(MultipartFile file) {
        try {
            List<Student> students = ExcelUtility.excelToStuList(file.getInputStream());
            students.forEach(System.out::println);

            studentRepository.saveAll(students);
        } catch (IOException e) {
            throw new RuntimeException("Excel data is failed to store: " + e.getMessage());
        }

    }

    @Override
    public void generateCertificate() {
        getAll().forEach(studentDto -> {
            try {
                if (studentDto.getStudentName() == null || studentDto.getStudentName().isEmpty()) {
                    throw new InvalidDataException(studentDto.getStudentName());
                }
                if (studentDto.getNote() < 0 || studentDto.getNote() > 20) {
                    throw new InvalidDataException(studentDto.getNote());
                }

                if (studentDto.getNote() >= 10) {
                    CertificateGenerator.generateCertificate(
                            studentDto.getStudentName(),
                            studentDto.getNote(),
                            studentDto.getMention().name()
                    );
                }
            } catch (InvalidDataException e) {
                System.err.println("Cannot generate certificate for student: " + studentDto.getStudentName() +
                        ". Reason: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("An unexpected error occurred for student: " + studentDto.getStudentName() +
                        ". Reason: " + e.getMessage());
            }
        });
    }



    @Override
    public Resource downloadCertificate(String studentName) {
        String outputDirectory = "src/main/resources/certificates";
        String normalizedStudentName = Normalizer.normalize(studentName, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "")
                .replaceAll(" ", "_");
//        String filePath = outputDirectory + "/" + normalizedStudentName + "_Certificate.pdf";
        Path filePath = Paths.get(outputDirectory, normalizedStudentName + "_Certificate.pdf");

//        File file = new File(filePath);
        if (!Files.exists(filePath)) {
            throw new CertificateNotFoundException(studentName);
        }

        return new PathResource(filePath);
    }
    @Override
    public StudentDto add(StudentDto studentDto) {
        Student student = StudentMapper.mappe.toEntity(studentDto);
        student.setId(UUID.randomUUID().toString());
        return StudentMapper.mappe.toDto(studentRepository.save(student));
    }

    @Override
    public List<StudentDto> getAll() {
        return StudentMapper.mappe.toDtos(studentRepository.findAll());
    }

    @Override
    public StudentDto getStudentById(String idStudent) {
        return StudentMapper.mappe.toDto(getById(idStudent));
    }

    @Override
    public StudentDto updateStudent(StudentDto studentDto) {
        Student student = getById(studentDto.getId());
        student.setStudentName(studentDto.getStudentName());
        student.setEmail(studentDto.getEmail());
        student.setMobileNo(studentDto.getMobileNo());
        student.setNote(studentDto.getNote());
        student.setMention(studentDto.getNote()<10?Emention.FAILED:Emention.PASSED);

        return StudentMapper.mappe.toDto(studentRepository.save(student));
    }

    @Override
    public String deleteStudent(String idStudent) {
       studentRepository.deleteById(idStudent);
        return "student deleted successfully";
    }

    public Student getById(String id) {
        return studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));
    }
}
