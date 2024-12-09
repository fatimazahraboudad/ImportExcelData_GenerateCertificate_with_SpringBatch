package com.project.StudentsManagement.controllers;

import com.project.StudentsManagement.dtos.StudentDto;
import com.project.StudentsManagement.services.StudentService;
import com.project.StudentsManagement.utils.ExcelUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/excel/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        if (ExcelUtility.hasExcelFormat(file)) {
            try {
                studentService.save(file);
                message = "the excel file is uploaded " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(message);

            } catch (Exception e) {
                message = "the excel file is not uploaded " + file.getOriginalFilename();
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);

            }
        }
        message = "please upload an excel file";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);

    }

    @GetMapping("/generateCertificate")
    public ResponseEntity<String> generateCertificate() {
        try {
            studentService.generateCertificate();
            return ResponseEntity.ok("Certificates generated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }


    @GetMapping("/certificate/{studentName}")
    public ResponseEntity<Resource> downloadCertificate(@PathVariable String studentName) throws IOException {

        Resource fileContent = studentService.downloadCertificate(studentName);

        return ResponseEntity.ok()
//              forcé le téléchargement file
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + studentName + "_Certificate.pdf")
//              lire file
//              .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + studentName + "_Certificate.pdf")
                .contentLength(fileContent.contentLength())
                .contentType(MediaType.APPLICATION_PDF)
                .body(fileContent);

    }

    @PostMapping("/add")
    public ResponseEntity<StudentDto> addStudent(@RequestBody StudentDto studentDto) {
        return ResponseEntity.status(201).body(studentService.add(studentDto));
    }

    @GetMapping("/all")
    public ResponseEntity<List<StudentDto>> getAllStudent() {
        return ResponseEntity.ok().body(studentService.getAll());
    }

    @GetMapping("/student/{id}")
    public ResponseEntity<StudentDto> getStudentById(@PathVariable String id) {
        return ResponseEntity.ok().body(studentService.getStudentById(id));
    }

    @PutMapping("/update")
    public ResponseEntity<StudentDto> updateStudent(@RequestBody StudentDto studentDto) {
        return ResponseEntity.ok().body(studentService.updateStudent(studentDto));
    }

    @DeleteMapping("/student/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable String id) {
        return ResponseEntity.ok().body(studentService.deleteStudent(id));
    }

}
