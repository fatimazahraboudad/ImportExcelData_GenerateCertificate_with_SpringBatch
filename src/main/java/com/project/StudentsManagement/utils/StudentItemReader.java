package com.project.StudentsManagement.utils;

import com.project.StudentsManagement.dtos.StudentDto;
import com.project.StudentsManagement.entities.Student;
import com.project.StudentsManagement.services.StudentService;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

//@Component
//public class StudentItemReader implements ItemReader<StudentDto> {
//
//    private final StudentService studentService;
//    private Iterator<StudentDto> studentIterator;
//
//    public StudentItemReader(StudentService studentService) {
//        this.studentService = studentService;
//    }
//
//    @Override
//    public StudentDto read() {
//        if (studentIterator == null) {
//
////            studentIterator = studentService.getAll().iterator();
//            List<StudentDto> students = studentService.getAll();
//            System.out.println("Students fetched: " + students);
//            studentIterator = students.iterator();
//
//        }
//        return studentIterator.hasNext() ? studentIterator.next() : null;
//    }
//}

public class StudentItemReader implements ItemReader<Student> {

    private final String filePath;
    private Iterator<Student> studentIterator;
    private List<Student> students;

    public StudentItemReader(@Value("#{jobParameters['file']}") String filePath) throws IOException {
        this.filePath = filePath;
    }

    @Override
    public Student read() throws Exception {
        if (students == null) {
            try (FileInputStream fis = new FileInputStream(filePath)) {
                students = ExcelUtility.excelToStuList(fis);
                studentIterator = students.iterator();
            } catch (IOException e) {
                throw new Exception("Error reading Excel file", e);
            }
        }
        return studentIterator.hasNext() ? studentIterator.next() : null;
    }
}