package com.project.StudentsManagement.utils;

import com.project.StudentsManagement.dtos.StudentDto;
import com.project.StudentsManagement.entities.Student;
import com.project.StudentsManagement.exceptions.InvalidDataException;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

//@Component
//public class StudentItemProcessor implements ItemProcessor<StudentDto, StudentDto> {
//
//    @Override
//    public StudentDto process(StudentDto student) {
//        try {
//            if (student.getStudentName() == null || student.getStudentName().isEmpty()) {
//                throw new InvalidDataException(student.getStudentName());
//            }
//            if (student.getNote() < 0 || student.getNote() > 20) {
//                throw new InvalidDataException(student.getNote());
//            }
//        } catch (InvalidDataException e) {
//            System.err.println(e.getMessage());
//            return null;
//        }
//        return student;
//    }
//
//}

public class StudentItemProcessor implements ItemProcessor<Student,Student> {
    @Override
    public Student process(Student item) throws Exception {

        return item;
    }
}

